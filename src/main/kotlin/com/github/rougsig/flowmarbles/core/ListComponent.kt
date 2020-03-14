package com.github.rougsig.flowmarbles.core

import org.w3c.dom.Element

class ListComponent<T : Any>(
  rootTag: String = "div",
  block: ComponentBuilder.() -> Unit = {}
) : Component {
  override val rootNode = html(rootTag, block)
  var data: T? = null
    set(value) {
      println(value)
      invalidateData(field, value)
      field = value
    }
  var adapter: ((T) -> List<Element>)? = null
    set(value) {
      invalidateData(null, data)
      field = value
    }

  private fun invalidateData(previousData: T?, newData: T?) {
    if (newData == null) rootNode.innerHTML = ""
    if (previousData == newData || newData == null) return
    rootNode.innerHTML = ""
    adapter?.let { adapter ->
      adapter(newData).forEach { rootNode.appendChild(it) }
    }
  }
}
