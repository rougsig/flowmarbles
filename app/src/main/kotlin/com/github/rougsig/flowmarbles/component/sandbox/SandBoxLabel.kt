package com.github.rougsig.flowmarbles.component.sandbox

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.html

class SandBoxLabel : Component {
  override val rootNode = html("div") {
    attr("class", "sandbox_label")
  }

  var label: String = ""
    set(value) {
      rootNode.innerHTML = value
      val fontSize = if (value.length >= 45) 1.3f else if (value.length >= 30) 1.5f else 2f
      rootNode.setAttribute("style", "font-size: ${fontSize}rem; font-family: 'Roboto Mono', monospace;")
      field = value
    }
}
