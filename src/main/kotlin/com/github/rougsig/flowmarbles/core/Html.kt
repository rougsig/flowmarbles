package com.github.rougsig.flowmarbles.core

import org.w3c.dom.Element
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.appendText

open class ComponentBuilder(tag: String) {
  private val element = document.createElement(tag)
  private var attrs: Map<String, String> = emptyMap()
  var text: String? = null
  var clickListener: ((Event) -> Unit)? = null

  fun attr(attr: String, value: String) {
    attrs += attr to value.trim()
  }

  fun tag(tag: String, block: ComponentBuilder.() -> Unit = {}) {
    element.appendChild(ComponentBuilder(tag).also(block).build())
  }

  fun component(component: Component) {
    element.appendComponent(component)
  }

  fun element(element: Element) {
    this.element.appendChild(element)
  }

  fun build(): Element {
    attrs.forEach { (n, v) -> element.setAttribute(n, v) }
    element.appendText(text ?: "")
    clickListener?.let { element.addEventListener("click", it) }
    return element
  }
}

fun html(tag: String, block: ComponentBuilder.() -> Unit = {}): Element {
  return ComponentBuilder(tag).also(block).build()
}
