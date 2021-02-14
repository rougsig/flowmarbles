package com.github.rougsig.flowmarbles.component.docs

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.html
import kotlinx.browser.window
import kotlinx.coroutines.*
import org.w3c.dom.asList
import org.w3c.dom.parsing.DOMParser

@ExperimentalCoroutinesApi
class Docs : Component {
  private val docsSource = html("p") {
    attr("class", "docs_source")
    text("Docs provided by ")
    tag("a") {
      attr("target", "_blank")
      attr("href", KOTLIN_DOCS_URL)
      text("Kotlin docs")
    }
  }
  private val content = html("div") {
    attr("class", "docs_content")
  }

  override val rootNode = html("div") {
    attr("class", "docs")
    element(docsSource)
    element(content)
  }

  private var fetchDocsJob: Job? = null
  fun setModel(operatorName: String) {
    val docsUrl = "$KOTLIN_DOCS_COMPONENT_URL_PROXY/${operatorName}.html"
    fetchDocsJob?.cancel()
    content.innerHTML = ""
    // TODO(@rougsig)
    //  Extract that code to better place ¯\_(ツ)_/¯
    fetchDocsJob = GlobalScope.launch {
      try {
        val response = window
          .fetch(docsUrl).await()
          .text().await()
        val parser = DOMParser()
        val html = parser.parseFromString(response, "text/html")
        html.getElementsByClassName("page-content").asList()
          .forEach { element -> content.appendChild(element) }
        content.getElementsByTagName("a").asList().forEach { link ->
          val href = link.getAttribute("href")
          link.setAttribute("target", "_blank")
          if (href?.startsWith("http") == false) {
            link.setAttribute("href", "$KOTLIN_DOCS_COMPONENT_URL/$href")
          }
        }
      } catch (e: Exception) {
        console.error(e)
      }
    }
  }
}

private const val KOTLIN_DOCS_URL = "https://kotlin.github.io/kotlinx.coroutines/"
private const val KOTLIN_DOCS_COMPONENT_URL =
  "https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow"
// TODO(@rougsig)
//  Own proxy server
private const val KOTLIN_DOCS_COMPONENT_URL_PROXY =
  "https://thingproxy.freeboard.io/fetch/$KOTLIN_DOCS_COMPONENT_URL"
