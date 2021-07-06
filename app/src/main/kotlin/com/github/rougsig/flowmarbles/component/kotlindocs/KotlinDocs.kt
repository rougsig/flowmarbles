package com.github.rougsig.flowmarbles.component.kotlindocs

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.html
import com.github.rougsig.flowmarbles.extensions.toCamelKebabCase

private const val KOTLIN_DOCS_URL = "https://kotlin.github.io/kotlinx.coroutines/"

class KotlinDocs : Component {
  fun setModel(model: String) {
    content.setAttribute("height", "0")
    content.setAttribute("src", "https://flowmarbles.com/docs/$model")
    linkToOriginal.setAttribute(
      "href",
      "${KOTLIN_DOCS_URL}kotlinx-coroutines-core/kotlinx.coroutines.flow/${model.toCamelKebabCase()}.html"
    )
  }

  private val linkToOriginal = html("a") {
    attr("class", "docs_original")
    attr("target", "_blank")
    text = "(original)"
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
    element(linkToOriginal)
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
