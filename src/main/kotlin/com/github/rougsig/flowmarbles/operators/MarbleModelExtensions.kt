package com.github.rougsig.flowmarbles.operators

import com.github.rougsig.flowmarbles.component.menu.Menu
import com.github.rougsig.flowmarbles.component.sandbox.SandBox
import com.github.rougsig.flowmarbles.component.sandbox.SandBoxTransformer
import com.github.rougsig.flowmarbles.component.timeline.Marble
import kotlinx.coroutines.ExperimentalCoroutinesApi

// Do no delete object
// without it we have that error TypeError: $receiver is undefined
object Colors {
  val colors = listOf("#F90000", "#F69602", "#F9EE07", "#45C938", "#1E66FF")
  val accentColors = listOf("#b388ff")
}

private var currentIndex = -1
fun nextColor(): String {
  if (currentIndex >= Colors.colors.lastIndex) currentIndex = 0
  else currentIndex += 1
  return Colors.colors[currentIndex]
}

fun <T : Any> marble(value: T, time: Long, color: String? = null): Marble.Model<T> {
  return Marble.Model(color ?: nextColor(), time, value)
}

fun <T : Any> input(vararg marbles: Marble.Model<T>): List<Marble.Model<T>> {
  return marbles.toList()
}

fun <T : Any> inputs(vararg inputs: List<Marble.Model<T>>): List<List<Marble.Model<T>>> {
  return inputs.toList()
}

@ExperimentalCoroutinesApi
fun <T : Any> sandbox(
  input: List<List<Marble.Model<T>>>,
  label: String,
  transformer: SandBoxTransformer<T>
): SandBox.Model<Any> {
  return SandBox.Model(
    input.unsafeCast<List<List<Marble.Model<Any>>>>(),
    label,
    transformer.unsafeCast<SandBoxTransformer<Any>>()
  )
}

@ExperimentalCoroutinesApi
fun menuItem(
  label: Menu.Model.Item,
  sandBox: SandBox.Model<Any>?
): Pair<Menu.Model.Item, SandBox.Model<Any>?> {
  return label to sandBox
}

fun label(label: String, hasBug: Boolean = false): Menu.Model.Item {
  return if (hasBug) Menu.Model.Item.Bug(label)
  else Menu.Model.Item.Label(label)
}

fun header(label: String): Menu.Model.Item.Header {
  return Menu.Model.Item.Header(label)
}
