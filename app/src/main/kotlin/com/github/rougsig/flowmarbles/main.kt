package com.github.rougsig.flowmarbles

import com.github.rougsig.flowmarbles.component.menu.Menu
import com.github.rougsig.flowmarbles.component.menu.Menu.Model.Item
import com.github.rougsig.flowmarbles.component.row
import com.github.rougsig.flowmarbles.component.sandbox.SandBox
import com.github.rougsig.flowmarbles.core.html
import com.github.rougsig.flowmarbles.operators.operators
import org.w3c.dom.HashChangeEvent
import kotlinx.browser.document
import kotlinx.browser.window

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
  val items = operators.map { it.first }

  fun findItemByHash(hash: String): Item? {
    return (operators.find { it.first.label == hash && it.second != null })?.first ?: items.find { it is Item.Label }
  }

  val menu = Menu(items, findItemByHash(window.location.hash.drop(1)))
  val sandBox = SandBox<Any>()

  fun updateSandBox() {
    menu.selectedItem?.let { selectedItem ->
      val selectedSandBox = operators.find { it.first == selectedItem }?.second
      if (selectedSandBox != null) sandBox.setModel(selectedSandBox)
    }
  }

  val row = row {
    col(sandBox) {
      attr("id", "sandbox")
      attr("style", "flex: 1;")
    }
    col(menu) {
      attr("id", "menu")
    }
  }

  menu.itemSelectedListener = { _, item -> window.location.hash = "#${item.label}" }
  updateSandBox()

  window.addEventListener("hashchange", {
    val hash = (it as HashChangeEvent).newURL.dropWhile { it != '#' }.drop(1)
    val selectedItem = findItemByHash(hash)
    menu.selectedItem = selectedItem
    updateSandBox()
  })

  app.appendChild(header)
  app.appendChild(row)
}
