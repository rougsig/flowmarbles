package com.github.rougsig.flowmarbles

import com.github.rougsig.flowmarbles.component.menu.Menu
import com.github.rougsig.flowmarbles.component.menu.Menu.Model.Item
import com.github.rougsig.flowmarbles.component.row
import com.github.rougsig.flowmarbles.component.sandbox.SandBox
import com.github.rougsig.flowmarbles.core.html
import com.github.rougsig.flowmarbles.operators.operators
import org.w3c.dom.HashChangeEvent
import kotlin.browser.document
import kotlin.browser.window

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
  val sandBox = SandBox<Any>()
  val row = row {
    col(sandBox) { attr("style", "flex: 1;") }
    col(menu)
  }

  val items = operators.map { it.first }
  fun updateSandBox() {
    menu.model?.selectedItem?.let { selectedItem ->
      val selectedSandBox = operators.find { it.first == selectedItem }?.second
      if (selectedSandBox != null) sandBox.setModel(selectedSandBox)
    }
  }

  fun findItemByHash(hash: String): Item? {
    return items.find { it.label == hash } ?: items.find { it is Item.Label }
  }

  menu.model = Menu.Model(items, findItemByHash(window.location.hash.drop(1)))
  menu.itemSelectedListener = { _, item -> window.location.hash = "#${item.label}" }
  updateSandBox()

  window.addEventListener("hashchange", {
    val hash = (it as HashChangeEvent).newURL.dropWhile { it != '#' }.drop(1)
    val selectedItem = findItemByHash(hash)
    menu.model = Menu.Model(items, selectedItem)
    updateSandBox()
  })

  app.appendChild(header)
  app.appendChild(row)
}
