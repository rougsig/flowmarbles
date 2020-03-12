package com.github.rougsig.flowmarbles.extensions

import com.github.rougsig.flowmarbles.component.timeline.Marble
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

@InternalCoroutinesApi
fun <T : Any> List<Marble.Model<T>>.toTimedFlow(virtualTimeDispatcher: VirtualTimeDispatcher): Flow<Marble.Model<T>> {
  return map {
    flow {
      delay(it.time)
      emit(it.copy(time = virtualTimeDispatcher.currentTime))
    }
  }.merge()
}

