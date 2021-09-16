package com.github.rougsig.flowmarbles.operators

import arrowKtOperators
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
val operators = listOf(
  contextOperators(),
  delayOperators(),
  distinctOperators(),
  emittersOperators(),
  limitOperators(),
  mergeOperators(),
  transformOperators(),
  zipOperators(),
  arrowKtOperators()
).flatten()
