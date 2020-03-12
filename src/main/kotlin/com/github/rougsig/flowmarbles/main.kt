package com.github.rougsig.flowmarbles

import com.github.rougsig.flowmarbles.component.menu.Menu
import com.github.rougsig.flowmarbles.component.sandbox.SandBox
import com.github.rougsig.flowmarbles.component.sandbox.SandBoxInput
import com.github.rougsig.flowmarbles.component.timeline.Marble
import com.github.rougsig.flowmarbles.component.timeline.Timeline
import com.github.rougsig.flowmarbles.core.appendComponent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlin.browser.document

@InternalCoroutinesApi
fun main() {
  val sandBox = SandBox<Any>()
  val menu = Menu()
  menu.setModel(Menu.Model((0..10).map { it.toString() }))
  menu.setOnItemClickListener { println(it) }
  val input = SandBoxInput.Model<Any>(
    timelines = listOf(
      Timeline.Model<Any>(
        listOf(
          Marble.Model<Any>(
            color = "blue",
            time = 0L,
            value = "A"
          ),
          Marble.Model<Any>(
            color = "green",
            time = 42L,
            value = "B"
          ),
          Marble.Model<Any>(
            color = "orange",
            time = 55L,
            value = "C"
          )
        )
      ),
      Timeline.Model(
        listOf(
          Marble.Model<Any>(
            color = "yellow",
            time = 0L,
            value = "1"
          ),
          Marble.Model<Any>(
            color = "green",
            time = 10L,
            value = "2"
          ),
          Marble.Model<Any>(
            color = "orange",
            time = 20L,
            value = "3"
          )
        )
      )
    )
  )
  sandBox.setModel(SandBox.Model(
    input = input,
    label = "flatMapLatest",
    transformer = { inputs ->
      val s0 = inputs[0]
      val s1 = inputs[1]

      s0.flatMapLatest { x ->
        s1.map { y -> y.copy(value = "${x.value}${y.value}") }
      }
    }
  ))
  document.getElementById("app")!!.appendComponent(menu)
  document.getElementById("app")!!.appendComponent(sandBox)
}
