package com.github.rougsig.flowmarbles.operators

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.zip

@ExperimentalCoroutinesApi
fun zipOperators() = listOf(
  menuItem(header("zip"), null),
  menuItem(
    label("combine"),
    sandbox(
      inputs(
        input(
          marble("1", 0),
          marble("2", 100),
          marble("3", 300),
          marble("4", 600)
        ),
        input(
          marble("A", 0),
          marble("B", 200),
          marble("C", 400),
          marble("D", 700)
        )
      ),
      "flow1.combine(flow2) { a, b -> a + b }"
    ) { inputs -> inputs[0].combine(inputs[1]) { a, b -> a + b } }
  ),
  menuItem(
    label("combineTransform"),
    sandbox(
      inputs(
        input(
          marble("1", 0),
          marble("2", 100),
          marble("3", 300),
          marble("4", 600)
        ),
        input(
          marble("A", 0),
          marble("B", 200),
          marble("C", 400),
          marble("D", 700)
        )
      ),
      "flow1.combineTransform(flow2) { a, b -> emit(a + b) }"
    ) { inputs -> inputs[0].combineTransform(inputs[1]) { a, b -> emit(a + b) } }
  ),
  menuItem(
    label("zip"),
    sandbox(
      inputs(
        input(
          marble("1", 0),
          marble("2", 100),
          marble("3", 900)
        ),
        input(
          marble("A", 0),
          marble("B", 200),
          marble("C", 400),
          marble("D", 700)
        )
      ),
      "flow1.zip(flow2) { a, b -> a + b }"
    ) { inputs -> inputs[0].zip(inputs[1]) { a, b -> a + b } }
  )
)
