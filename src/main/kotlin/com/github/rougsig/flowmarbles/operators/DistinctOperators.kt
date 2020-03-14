package com.github.rougsig.flowmarbles.operators

import kotlinx.coroutines.flow.distinctUntilChanged

fun distinctOperators() = listOf(
  menuItem(header("distinct"), null),
  menuItem(
    label("distinctUntilChanged"),
    sandbox(
      inputs(
        input(
          marble("1", 0, color = Colors.colors[0]),
          marble("1", 150, color = Colors.colors[0]),
          marble("2", 300, color = Colors.colors[1]),
          marble("2", 450, color = Colors.colors[1]),
          marble("3", 600, color = Colors.colors[2]),
          marble("3", 750, color = Colors.colors[2]),
          marble("4", 900, color = Colors.colors[3])
        )
      ),
      "distinctUntilChanged()"
    ) { inputs -> inputs[0].distinctUntilChanged { old, new -> old.value == new.value } }
  )
)
