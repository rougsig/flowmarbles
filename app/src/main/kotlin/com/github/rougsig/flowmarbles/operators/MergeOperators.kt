package com.github.rougsig.flowmarbles.operators

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
fun mergeOperators() = listOf(
  menuHeader("merge"),
  menuItem(
    label("flatMapConcat"),
    sandbox(
      "flatMapConcat",
      inputs(
        input(
          marble("1", 0),
          marble("2", 300),
          marble("3", 600)
        ),
        input(
          marble("A", 0),
          marble("B", 100)
        )
      ),
      "flow1.flatMapConcat { flow2 }"
    ) { inputs -> inputs[0].flatMapConcat { inputs[1] } }
  ),
  menuItem(
    label("flatMapMerge"),
    sandbox(
      "flatMapMerge",
      inputs(
        input(
          marble("1", 0),
          marble("2", 100),
          marble("3", 600)
        ),
        input(
          marble("A", 0),
          marble("B", 200)
        )
      ),
      "flow1.flatMapMerge { flow2 }"
    ) { inputs -> inputs[0].flatMapMerge { inputs[1] } }
  ),
  menuItem(
    label("flatMapLatest"),
    sandbox(
      "flatMapLatest",
      inputs(
        input(
          marble("1", 0),
          marble("2", 100),
          marble("3", 600)
        ),
        input(
          marble("A", 0),
          marble("B", 200)
        )
      ),
      "flow1.flatMapLatest { flow2 }"
    ) { inputs -> inputs[0].flatMapLatest { inputs[1] } }
  ),
  menuItem(
    label("merge"),
    sandbox(
      "merge",
      inputs(
        input(
          marble("1", 0),
          marble("2", 200),
          marble("3", 400),
          marble("4", 600),
          marble("5", 800)
        ),
        input(
          marble("A", 100),
          marble("B", 300),
          marble("C", 500),
          marble("D", 700),
          marble("E", 900)
        )
      ),
      "merge()"
    ) { inputs -> inputs.merge() }
  ),
  menuItem(
    label("transformLatest"),
    sandbox(
      "transformLatest",
      inputs(
        input(
          marble("A", 0),
          marble("B", 100),
          marble("C", 350),
          marble("D", 600),
          marble("E", 700)
        )
      ),
      "flow1.transformLatest { delay(100); emit(it) }"
    ) { inputs -> inputs[0].transformLatest { delay(100); emit(it) } }
  ),
  menuItem(
    label("mapLatest"),
    sandbox(
      "mapLatest",
      inputs(
        input(
          marble("A", 0),
          marble("B", 100),
          marble("C", 350),
          marble("D", 600),
          marble("E", 700)
        )
      ),
      "flow1.mapLatest { delay(100); it }"
    ) { inputs -> inputs[0].mapLatest { delay(100); it } }
  )
)
