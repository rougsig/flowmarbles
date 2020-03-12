package com.github.rougsig.flowmarbles.component.sandbox

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.createElement

class SandBoxLabel : Component {
  override val rootNode = createElement("div") {
    setAttribute("class", "sandbox_label")
  }

  fun setLabel(label: String) {
    rootNode.innerHTML = label
    val fontSize = if (label.length >= 45) 1.3f else if (label.length >= 30) 1.5f else 2f
    rootNode.setAttribute("style", "font-size: ${fontSize}rem; font-family: 'Roboto Mono', monospace;")
  }
}
