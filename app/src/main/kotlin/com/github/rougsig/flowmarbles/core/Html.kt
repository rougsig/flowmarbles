package com.github.rougsig.flowmarbles.core

import org.w3c.dom.Element
import org.w3c.dom.events.Event
import kotlinx.browser.document
import kotlinx.dom.appendText

open class ComponentBuilder(tag: String, private val createElement: (String) -> Element) {
  private val element = createElement(tag)
  private var attrs: Map<String, String> = emptyMap()
  var text: String? = null
  var clickListener: ((Event) -> Unit)? = null

  fun attr(attr: String, value: String) {
    attrs = attrs + (attr to value.trim())
  }

  fun tag(tag: String, block: ComponentBuilder.() -> Unit = {}) {
    element.appendChild(ComponentBuilder(tag, createElement).also(block).build())
  }

  fun component(component: Component) {
    element.appendChild(component.rootNode)
  }

  fun element(element: Element) {
    this.element.appendChild(element)
  }

  fun build(): Element {
    element.appendText(text ?: "")
    attrs.forEach { (n, v) -> element.setAttribute(n, v) }
    clickListener?.let { element.addEventListener("click", it) }
    return element
  }
}

fun html(tag: String, block: ComponentBuilder.() -> Unit = {}): Element {
  return ComponentBuilder(tag) { document.createElement(it) }
    .also(block)
    .build()
}

fun svg(tag: String, block: ComponentBuilder.() -> Unit = {}): Element {
  return ComponentBuilder(tag) { document.createElementNS("http://www.w3.org/2000/svg", it) }
    .also(block)
    .build()
}
