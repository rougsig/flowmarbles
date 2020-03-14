package com.github.rougsig.flowmarbles

import com.github.rougsig.flowmarbles.component.menu.Menu
import com.github.rougsig.flowmarbles.component.menu.Menu.Model.Item
import com.github.rougsig.flowmarbles.component.row
import com.github.rougsig.flowmarbles.component.sandbox.SandBox
import com.github.rougsig.flowmarbles.core.html
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.browser.document

fun main() {
  val app = document.getElementById("app")!!
  val header = html("header") {
    attr("class", "header")
    tag("p") {
      attr("class", "header_title")
      tag("span") {
        text = "Flow Marbles:"
      }
      text = " Interactive diagram of Kotlin Flow"
    }
  }
  val menu = Menu()
  val row = row {
    col(SandBox<Any>()) {
      attr("style", "flex: 1;")
    }
    col(menu)
  }

  menu.model = Menu.Model(
    listOf(
      Item.Header("creation"),
      Item.Label("from"),
      Item.Label("interval"),
      Item.Label("of"),
      Item.Label("timer"),
      Item.Header("creation"),
      Item.Label("from"),
      Item.Label("interval"),
      Item.Label("of"),
      Item.Label("timer"),
      Item.Header("creation"),
      Item.Label("from"),
      Item.Label("interval"),
      Item.Label("of"),
      Item.Label("timer")
    ),
    selectedItem = null
  )
  menu.itemClickListener = { item ->
    menu.model = menu.model?.copy(selectedItem = item)
  }

  app.appendChild(header)
  app.appendChild(row)
}
