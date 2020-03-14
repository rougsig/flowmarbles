package com.github.rougsig.flowmarbles.operators

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile

@ExperimentalCoroutinesApi
fun limitOperators() = listOf(
  menuItem(header("limit"), null),
  menuItem(
    label("drop"),
    sandbox(
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
      "drop(3)"
    ) { inputs -> inputs[0].drop(3) }
  ),
  menuItem(
    label("dropWhile"),
    sandbox(
      inputs(
        input(
          marble(1, 0),
          marble(10, 150),
          marble(2, 300),
          marble(3, 450),
          marble(4, 600),
          marble(5, 750)
        )
      ),
      "dropWhile { it != 10 }"
    ) { inputs -> inputs[0].dropWhile { it.value != 10 } }
  ),
  menuItem(
    label("take"),
    sandbox(
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
      "take(3)"
    ) { inputs -> inputs[0].take(3) }
  ),
  menuItem(
    label("takeWhile"),
    sandbox(
      inputs(
        input(
          marble(1, 0),
          marble(2, 150),
          marble(3, 300),
          marble(10, 450),
          marble(4, 600),
          marble(5, 750),
          marble(6, 750)
        )
      ),
      "takeWhile { it != 10 }"
    ) { inputs -> inputs[0].takeWhile { it.value != 10 } }
  )
)
