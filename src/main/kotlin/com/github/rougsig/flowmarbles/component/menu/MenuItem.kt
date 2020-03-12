package com.github.rougsig.flowmarbles.component.menu

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.createElement

class MenuItem : Component {
  override val rootNode = createElement("p") {
    setAttribute("class", "menu_item")
  }

  fun setModel(model: String) {
    rootNode.innerHTML = model
  }
}
