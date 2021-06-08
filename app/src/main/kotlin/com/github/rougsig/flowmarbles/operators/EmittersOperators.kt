package com.github.rougsig.flowmarbles.operators

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform

@ExperimentalCoroutinesApi
fun emittersOperators() = listOf(
  menuHeader("emitters"),
  menuItem(
    label("transform"),
    sandbox(
      "transform",
      inputs(
        input(
          marble(1, 0),
          marble(2, 150),
          marble(3, 300),
          marble(4, 450),
          marble(5, 600),
          marble(6, 750)
        )
      ),
      "transform { if (it % 2 == 0) emit(it) }"
    ) { inputs -> inputs[0].transform { if (it.value % 2 == 0) emit(it) } }
  ),
  menuItem(
    label("onStart"),
    sandbox(
      "onStart",
      inputs(
        input(
          marble("1", 50),
          marble("2", 150),
          marble("3", 300),
          marble("4", 450),
          marble("5", 600)
        )
      ),
      "onStart { emit(\"S\") }"
    ) { inputs -> inputs[0].onStart { emit(marble("S", 0, Colors.accentColors[0])) } }
  ),
  menuItem(
    label("onCompletion"),
    sandbox(
      "onCompletion",
      inputs(
        input(
          marble("1", 50),
          marble("2", 150),
          marble("3", 300),
          marble("4", 450),
          marble("5", 600)
        )
      ),
      "onCompletion { emit(\"D\") }"
    ) { inputs ->
      inputs[0].onCompletion {
        emit(marble("D", 0, Colors.accentColors[0]))
      }
    }
  )
)
