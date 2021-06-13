package com.github.rougsig.flowmarbles.component.kotlindocs

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.html
import org.w3c.dom.HTMLIFrameElement

private const val KOTLIN_DOCS_URL = "https://kotlin.github.io/kotlinx.coroutines/"

class KotlinDocs : Component {
  fun setModel(model: String) {
    content.setAttribute("src", model)
  }

  private val docsSource = html("p") {
    attr("class", "docs_source")
    tag("span") {
      text = "Docs provided by "
    }
    tag("a") {
      attr("target", "_blank")
      attr("href", KOTLIN_DOCS_URL)
      text = "Kotlin docs"
    }
  }
  private val content = html("iframe") {
    attr("class", "docs_content")
    attr("width", "100%")
    attr("onLoad", "calcIframeHeight(this);")
  }

  override val rootNode = html("div") {
    attr("class", "docs")
    element(docsSource)
    element(content)
  }
}
