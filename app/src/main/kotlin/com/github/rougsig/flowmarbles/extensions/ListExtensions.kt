package com.github.rougsig.flowmarbles.extensions

import com.github.rougsig.flowmarbles.component.timeline.Marble
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
fun <T : Any> List<Marble.Model<T>>.toTimedFlow(): Flow<Marble.Model<T>> {
  return channelFlow {
    forEach {
      launch {
        delay(it.time)
        send(it)
      }
    }
  }
}
