package com.github.rougsig.flowmarbles.operators

import com.github.rougsig.flowmarbles.component.sandbox.SandBox
import com.github.rougsig.flowmarbles.component.sandbox.SandBoxTransformer
import com.github.rougsig.flowmarbles.component.timeline.Marble
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

private val colors = listOf(
  "#F90000",
  "#F69602",
  "#F9EE07",
  "#45C938",
  "#1E66FF"
)

fun colorGenerator(colors: List<String>): () -> String {
  var currentIndex = -1
  return {
    if (currentIndex >= colors.lastIndex) currentIndex = 0
    else currentIndex += 1
    colors[currentIndex]
  }
}

val nextColor = colorGenerator(colors.shuffled())

fun <T : Any> marble(value: T, time: Long, color: String? = null): Marble.Model<T> {
  return Marble.Model(color ?: nextColor(), time, value)
}

fun <T : Any> input(vararg marbles: Marble.Model<T>): List<Marble.Model<T>> {
  return marbles.toList()
}

fun <T : Any> inputs(vararg inputs: List<Marble.Model<T>>): List<List<Marble.Model<T>>> {
  return inputs.toList()
}

fun <T : Any> sandbox(
  input: List<List<Marble.Model<T>>>,
  label: String,
  transformer: SandBoxTransformer<T>
): SandBox.Model<Any> {
  return SandBox.Model<Any>(
    input as List<List<Marble.Model<Any>>>,
    label,
    transformer as SandBoxTransformer<Any>
  )
}

fun menuItem(
  label: String,
  sandBox: SandBox.Model<Any>?
): Pair<String, SandBox.Model<Any>?> {
  return label to sandBox
}
