package com.github.rougsig.flowmarbles

import com.github.rougsig.flowmarbles.component.menu.Menu
import com.github.rougsig.flowmarbles.component.sandbox.SandBox
import com.github.rougsig.flowmarbles.component.sandbox.SandBoxInput
import com.github.rougsig.flowmarbles.component.timeline.Marble
import com.github.rougsig.flowmarbles.component.timeline.Timeline
import com.github.rougsig.flowmarbles.core.appendComponent
import com.github.rougsig.flowmarbles.operators.operators
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlin.browser.document

@InternalCoroutinesApi
fun main() {
  val sandBox = SandBox<Any>()
  val menu = Menu()

  menu.setModel(Menu.Model(operators.map { it.first }))
  menu.setOnItemClickListener { sandBox.setModel(operators[it].second) }
  sandBox.setModel(operators.first().second)

  document.getElementById("app")!!.appendComponent(menu)
  document.getElementById("app")!!.appendComponent(sandBox)
}
