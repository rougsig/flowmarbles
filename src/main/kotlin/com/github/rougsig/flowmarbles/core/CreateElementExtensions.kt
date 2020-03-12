package com.github.rougsig.flowmarbles.core

import org.w3c.dom.Element
import kotlin.browser.document

fun createSvgElement(tag: String, init: Element.() -> Unit = {}): Element {
  val element = document.createElementNS("http://www.w3.org/2000/svg", tag)
  return element.also(init)
}

fun createElement(tag: String, init: Element.() -> Unit = {}): Element {
  val element = document.createElement(tag)
  return element.also(init)
}
