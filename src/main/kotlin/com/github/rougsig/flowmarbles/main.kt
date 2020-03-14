package com.github.rougsig.flowmarbles

import com.github.rougsig.flowmarbles.component.menu.Menu
import com.github.rougsig.flowmarbles.component.menu.Menu.Model.Item
import com.github.rougsig.flowmarbles.component.row
import com.github.rougsig.flowmarbles.component.sandbox.SandBox
import com.github.rougsig.flowmarbles.core.html
import com.github.rougsig.flowmarbles.operators.operators
import kotlin.browser.document

fun main() {
  val app = document.getElementById("app")!!
  val header = html("header") {
    attr("class", "header")
    tag("p") {
      attr("class", "header_title")
      tag("span") {
        text = "Flow Marbles"
      }
      text = " Interactive diagram of Kotlin Flow"
    }
  }
  val menu = Menu()
  val sandBox = SandBox<Any>()
  sandBox.setModel(operators[8].second)
  val row = row {
    col(sandBox) { attr("style", "flex: 1;") }
    col(menu)
  }

  menu.setModel(Menu.Model(
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
      Item.Label("timer"),
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
      Item.Label("timer"),
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
      Item.Label("timer"),
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
      Item.Label("timer"),
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
  ))
  menu.itemSelectedListener = { index, item -> println("$index, $item") }

  app.appendChild(header)
  app.appendChild(row)
}
