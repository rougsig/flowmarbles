package com.github.rougsig.flowmarbles.operators

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.onEach

@FlowPreview
@ExperimentalCoroutinesApi
fun contextOperators() = listOf(
  menuHeader("context"),
  menuItem(
    label("conflate"),
    sandbox(
      inputs(
        input(
          marble("1", 0),
          marble("2", 100),
          marble("3", 200),
          marble("4", 800),
          marble("5", 900)
        )
      ),
      "conflate().onEach { delay(250) }"
    ) { inputs -> inputs[0].conflate().onEach { delay(250) } }
  )
)
