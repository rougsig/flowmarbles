package com.github.rougsig.flowmarbles.component.menu

import com.github.rougsig.flowmarbles.component.menu.Menu.Model.Item
import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.ListComponent
import com.github.rougsig.flowmarbles.core.html

class Menu(
  private val items: List<Item>,
  selectedItem: Item?
) : Component {
  data class Model(
    val items: List<Item>,
    val selectedItem: Item?
  ) {
    sealed class Item {
      abstract val label: String

      data class Header(override val label: String) : Item()
      data class NothingFound(override val label: String) : Item()
      data class Label(override val label: String) : Item()
      data class Bug(override val label: String) : Item()
    }
  }

  private val search = MenuSearch()
  private val list = ListComponent<Model>(html("div") {
    attr("class", "menu_list")
  })

  override val rootNode = html("div") {
    attr("class", "menu")
    component(search)
    component(list)
  }

  var itemSelectedListener: ((Int, Item) -> Unit)? = null

  var selectedItem: Item? = selectedItem
    set(value) {
      list.data = list.data?.copy(selectedItem = value)
      field = value
    }

  init {
    list.adapter = { model ->
      model.items.mapIndexed { index, item ->
        when (item) {
          is Item.Header -> html("p") {
            attr("class", "menu_header")
            text = item.label
          }
          is Item.NothingFound -> html("p") {
            attr("class", "menu_header menu_header--text-center")
            text = item.label
          }
          is Item.Label -> html("p") {
            attr("class", buildString {
              append("menu_item")
              if (item === model.selectedItem) append(" menu_item--selected")
            })
            text = item.label
            clickListener = { itemSelectedListener?.invoke(index, item) }
          }
          is Item.Bug -> html("p") {
            attr("class", buildString {
              append("menu_item menu_item--bugged")
              if (item === model.selectedItem) append(" menu_item--selected")
            })
            text = item.label
            clickListener = { itemSelectedListener?.invoke(index, item) }
          }
        }
      }
    }

    list.data = Model(items, selectedItem)

    val nothingFound = listOf(Item.NothingFound("operators not found"))
    search.onTextChangeListener = { query ->
      val filteredItems = items.filter { it.label.contains(query) && it !is Item.Header || query.isBlank() }
      list.data = list.data?.copy(items = if (filteredItems.isNullOrEmpty()) nothingFound else filteredItems)
    }
  }
}
