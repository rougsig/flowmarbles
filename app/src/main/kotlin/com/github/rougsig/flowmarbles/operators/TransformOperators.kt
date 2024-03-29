package com.github.rougsig.flowmarbles.operators

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.runningFold

@ExperimentalCoroutinesApi
fun transformOperators() = listOf(
  menuHeader("transform"),
  menuItem(
    label("filter"),
    sandbox(
      "filter",
      inputs(
        input(
          marble(20, 0),
          marble(2, 150),
          marble(30, 300),
          marble(4, 450),
          marble(50, 600),
          marble(6, 750)
        )
      ),
      "filter { it > 10 }"
    ) { inputs -> inputs[0].filter { it.value > 10 } }
  ),
  menuItem(
    label("filterNot"),
    sandbox(
      "filterNot",
      inputs(
        input(
          marble(10, 0),
          marble(2, 150),
          marble(30, 300),
          marble(4, 450),
          marble(50, 600),
          marble(6, 750)
        )
      ),
      "filterNot { it > 10 }"
    ) { inputs -> inputs[0].filterNot { it.value > 10 } }
  ),
  menuItem(
    label("filterIsInstance"),
    sandbox(
      "filterIsInstance",
      inputs(
        input<Any>(
          marble(1, 0),
          marble("A", 150),
          marble("B", 300),
          marble(2, 450),
          marble("C", 600),
          marble(3, 750)
        )
      ),
      "filterIsInstance&#60;String&#62;()"
    ) { inputs ->
      // type in generic :(
      // filterIsInstance can't be used with generics
      inputs[0].filter { it.value is String }
    }
  ),
  menuItem(
    label("filterNotNull"),
    sandbox(
      "filterNotNull",
      inputs(
        input(
          marble("", 0),
          marble("A", 150),
          marble("B", 300),
          marble("", 450),
          marble("C", 600),
          marble("", 750)
        )
      ),
      "filterNotNull()"
    ) { inputs ->
      // value wrapped in marble model
      // filterNotNull can't be used here
      inputs[0].filter { it.value.isNotBlank() }
    }
  ),
  menuItem(
    label("map"),
    sandbox(
      "map",
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
      "map { it * 10 }"
    ) { inputs -> inputs[0].map { it.copy(value = it.value * 10) } }
  ),
  menuItem(
    label("mapNotNull"),
    sandbox(
      "mapNotNull",
      inputs(
        input(
          marble("", 0),
          marble("A", 150),
          marble("B", 300),
          marble("", 450),
          marble("C", 600),
          marble("", 750)
        )
      ),
      "mapNotNull { it.value }"
    ) { inputs ->
      // value wrapped in ot null marble model
      // mapNotNull can't be used here
      inputs[0].filter { it.value.isNotBlank() }
    }
  ),
  menuItem(
    label("withIndex"),
    sandbox(
      "withIndex",
      inputs(
        input(
          marble("A", 0),
          marble("B", 150),
          marble("C", 300),
          marble("D", 450)
        )
      ),
      "withIndex()"
    ) { inputs -> inputs[0].withIndex().map { (i, v) -> v.copy(value = "$i${v.value}") } }
  ),
  menuItem(
    label("onEach"),
    sandbox(
      "onEach",
      inputs(
        input(
          marble("A", 0),
          marble("B", 150),
          marble("C", 300),
          marble("D", 450)
        )
      ),
      "onEach { delay(100) }"
    ) { inputs -> inputs[0].onEach { delay(100) } }
  ),
  menuItem(
    label("scan"),
    sandbox(
      "scan",
      inputs(
        input(
          marble(1, 0),
          marble(2, 150),
          marble(3, 300),
          marble(4, 450),
          marble(5, 600),
          marble(6, 750),
          marble(7, 900)
        )
      ),
      "scan(0) { acc, v -> acc + v }"
    ) { inputs -> inputs[0].scan(marble(0, 0, Colors.accentColors[0])) { acc, v -> acc + v } }
  ),
  menuItem(
    label("runningReduce"),
    sandbox(
      "runningReduce",
      inputs(
        input(
          marble(1, 0),
          marble(2, 150),
          marble(3, 300),
          marble(4, 450),
          marble(5, 600),
          marble(6, 750),
          marble(7, 900)
        )
      ),
      "runningReduce { acc, v -> acc + v }"
    ) { inputs -> inputs[0].runningReduce { acc, v -> acc + v } }
  ),
  menuItem(
    label("runningFold"),
    sandbox(
      "runningFold",
      inputs(
        input(
          marble(1, 0),
          marble(2, 150),
          marble(3, 300),
          marble(4, 450),
          marble(5, 600),
          marble(6, 750),
          marble(7, 900)
        )
      ),
      "runningFold(0) { acc, v -> acc + v }"
    ) { inputs -> inputs[0].runningFold(marble(0, 0)) { acc, v -> acc + v } }
  )
)
