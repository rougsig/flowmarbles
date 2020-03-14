package com.github.rougsig.flowmarbles.core

import org.w3c.dom.Element

class ListComponent<T : Any>(override val rootNode: Element) : Component {
  var data: T? = null
    set(value) {
      invalidateLayout(field, value)
      field = value
    }
  var adapter: ((T) -> List<Element>)? = null
    set(value) {
      invalidateLayout(null, data)
      field = value
    }

  private fun invalidateLayout(previousData: T?, newData: T?) {
    if (newData == null) rootNode.innerHTML = ""
    if (previousData == newData || newData == null) return
    rootNode.innerHTML = ""
    adapter?.let { adapter ->
      adapter(newData).forEach { rootNode.appendChild(it) }
    }
  }
}
