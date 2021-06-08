package com.github.rougsig.flowmarbles.component.menu

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.html

class MenuSearch : Component {
  private val input = html("input") {
    attr("class", "search_input")
    attr("placeholder", "Search operator")
  }

  override val rootNode = html("div") {
    attr("class", "menu_search search")
    element(input)
  }

  init {
    input.addEventListener("input", { onTextChangeListener?.invoke(it.asDynamic().target.value) })
  }

  var onTextChangeListener: ((String) -> Unit)? = null
}
