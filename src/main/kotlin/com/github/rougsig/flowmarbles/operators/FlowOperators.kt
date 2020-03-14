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

private val nextColor = colorGenerator(colors.shuffled())

private fun <T : Any> marble(value: T, time: Long, color: String? = null): Marble.Model<T> {
  return Marble.Model(color ?: nextColor(), time, value)
}

private fun <T : Any> input(vararg marbles: Marble.Model<T>): List<Marble.Model<T>> {
  return marbles.toList()
}

private fun <T : Any> inputs(vararg inputs: List<Marble.Model<T>>): List<List<Marble.Model<T>>> {
  return inputs.toList()
}

private fun <T : Any> sandbox(
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

private fun menuItem(
  label: String,
  sandBox: SandBox.Model<Any>
): Pair<String, SandBox.Model<Any>> {
  return label to sandBox
}

val operators = listOf(
  menuItem(
    "delay",
    sandbox(
      inputs(
        input(
          marble("A", 0),
          marble("B", 20),
          marble("C", 40)
        )
      ),
      label = "onEach { delay(15) }",
      transformer = { inputs ->
        val s0 = inputs[0]
        s0.onEach { delay(15) }
      }
    )
  ),
  menuItem(
    "map",
    sandbox(
      inputs(
        input(
          marble(1, 0),
          marble(2, 20),
          marble(3, 40)
        )
      ),
      label = "map { it + 10 }",
      transformer = { inputs ->
        val s0 = inputs[0]
        s0.map { it.copy(value = it.value + 10) }
      }
    )
  ),
  menuItem(
    "take",
    sandbox(
      inputs(
        input(
          marble(1, 0),
          marble(2, 20),
          marble(3, 40)
        )
      ),
      label = "take(2)",
      transformer = { inputs ->
        val s0 = inputs[0]
        s0.take(2)
      }
    )
  ),
  menuItem(
    "reduce",
    sandbox(
      inputs(
        input(
          marble(1, 0),
          marble(2, 20),
          marble(3, 40)
        )
      ),
      label = "reduce { a, b -> a + b }",
      transformer = { inputs ->
        val s0 = inputs[0]
        flow { emit(s0.reduce { accumulator, value -> accumulator.copy(value = accumulator.value + value.value) }) }
      }
    )
  ),
  menuItem(
    "filter",
    sandbox(
      inputs(
        input(
          marble(1, 0),
          marble(2, 10),
          marble(3, 20),
          marble(4, 30),
          marble(5, 40)
        )
      ),
      label = "filter { it % 2 == 0 }",
      transformer = { inputs ->
        val s0 = inputs[0]
        s0.filter { it.value % 2 == 0 }
      }
    )
  ),
  menuItem(
    "zip",
    sandbox(
      inputs(
        input(
          marble("1", 0),
          marble("2", 10),
          marble("3", 20)
        ),
        input(
          marble("A", 25),
          marble("B", 50),
          marble("C", 75)
        )
      ),
      label = "s0.zip(s1) { a, b -> a + b }",
      transformer = { inputs ->
        val s0 = inputs[0]
        val s1 = inputs[1]
        s0.zip(s1) { a, b -> marble(a.value + b.value, 0, a.color) }
      }
    )
  ),
  menuItem(
    "flatMapMerge",
    sandbox(
      inputs(
        input(
          marble("A", 0),
          marble("B", 25),
          marble("C", 50)
        ),
        input(
          marble("1", 0),
          marble("2", 10),
          marble("3", 20)
        )
      ),
      label = "s0.flatMapMerge { a -> s1.map { b -> a + b } }",
      transformer = { inputs ->
        val s0 = inputs[0]
        val s1 = inputs[1]
        s0.flatMapMerge { a -> s1.map { b -> b.copy(value = a.value + b.value) } }
      }
    )
  ),
  menuItem(
    "flatMapConcat",
    sandbox(
      inputs(
        input(
          marble("A", 0),
          marble("B", 25),
          marble("C", 50)
        ),
        input(
          marble("1", 0),
          marble("2", 10),
          marble("3", 20)
        )
      ),
      label = "s0.flatMapConcat { a -> s1.map { b -> a + b } }",
      transformer = { inputs ->
        val s0 = inputs[0]
        val s1 = inputs[1]
        s0.flatMapConcat { a -> s1.map { b -> b.copy(value = a.value + b.value) } }
      }
    )
  ),
  menuItem(
    "flatMapLatest",
    sandbox(
      inputs(
        input(
          marble("A", 0),
          marble("B", 25),
          marble("C", 50),
          marble("D", 75)
        ),
        input(
          marble("1", 0),
          marble("2", 10),
          marble("3", 20)
        )
      ),
      label = "s0.flatMapLatest { a -> s1.map { b -> a + b } }",
      transformer = { inputs ->
        val s0 = inputs[0]
        val s1 = inputs[1]
        s0.flatMapLatest { a -> s1.map { b -> a + b } }
      }
    )
  )
)
