package com.github.rougsig.flowmarbles.operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

fun mergeOperators() = listOf(
  menuItem(header("merge"), null),
  menuItem(
    label("flatMapConcat"),
    sandbox(
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
      "flow1.flatMapConcat { a -> flow2 }"
    ) { inputs -> inputs[0].flatMapConcat { a -> inputs[1] } }
  ),
  menuItem(
    label("flatMapMerge"),
    sandbox(
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
      "flow1.flatMapMerge { a -> flow2 }"
    ) { inputs -> inputs[0].flatMapMerge { a -> inputs[1] } }
  ),
  menuItem(
    label("flatMapLatest"),
    sandbox(
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
      "flow1.flatMapLatest { a -> flow2 }"
    ) { inputs -> inputs[0].flatMapLatest { a -> inputs[1] } }
  ),
  menuItem(
    label("merge"),
    sandbox(
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
