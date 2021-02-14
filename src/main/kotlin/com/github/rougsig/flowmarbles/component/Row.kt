package com.github.rougsig.flowmarbles.component

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.ComponentBuilder
import com.github.rougsig.flowmarbles.core.html
import org.w3c.dom.Element
import kotlin.browser.document

class RowBuilder : ComponentBuilder("div", { document.createElement(it) }) {
  fun col(component: Component, block: ComponentBuilder.() -> Unit = {}) {
    element(html("div") {
      this.block()
      attr("class", "row_col")
      component(component)
    })
  }

  fun col(element: Element, block: ComponentBuilder.() -> Unit = {}) {
    element(html("div") {
      this.block()
      attr("class", "row_col")
      element(element)
    })
  }

  fun col(components: List<Component>, block: ComponentBuilder.() -> Unit = {}) {
    element(html("div") {
      this.block()
      attr("class", "row_col")
      components.forEach { component ->
        component(component)
      }
    })
  }

  fun col(elements: List<Element>, block: ComponentBuilder.() -> Unit = {}) {
    element(html("div") {
      this.block()
      attr("class", "row_col")
      elements.forEach { element ->
        element(element)
      }
    })
  }
}

fun row(block: RowBuilder.() -> Unit = {}): Element {
  val builder = RowBuilder().also(block)
  builder.attr("class", "row")
  return builder.build()
}
