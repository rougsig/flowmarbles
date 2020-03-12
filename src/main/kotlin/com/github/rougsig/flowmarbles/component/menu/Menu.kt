package com.github.rougsig.flowmarbles.component.menu

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.appendComponent
import com.github.rougsig.flowmarbles.core.createElement

class Menu : Component {
  data class Model(
    val labels: List<String>
  )

  private var onItemClickListener: ((Int) -> Unit)? = null

  override val rootNode = createElement("div") {
    setAttribute("class", "menu")
  }

  fun setModel(model: Model) {
    rootNode.innerHTML = ""
    model.labels.forEachIndexed { index, label ->
      val item = MenuItem()
      item.setModel(label)
      item.rootNode.addEventListener("click", {
        onItemClickListener?.invoke(index)
      })
      rootNode.appendComponent(item)
    }
  }

  fun setOnItemClickListener(listener: ((Int) -> Unit)?) {
    this.onItemClickListener = listener
  }
}
