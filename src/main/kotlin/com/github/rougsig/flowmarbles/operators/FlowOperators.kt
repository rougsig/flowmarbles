package com.github.rougsig.flowmarbles.operators

val operators = listOf(
  contextOperators(),
  distinctOperators(),
  emittersOperators(),
  limitOperators(),
  mergeOperators(),
  transformOperators(),
  zipOperators()
).flatten()
