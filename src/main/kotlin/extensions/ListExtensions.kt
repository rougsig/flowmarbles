package extensions

import component.timeline.Marble
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

@InternalCoroutinesApi
fun <T : Any> List<Marble.Model<T>>.toTimedFlow(vtDispatcher: VirtualTimeDispatcher): Flow<Marble.Model<T>> {
  return map {
    flow {
      delay(it.time.toLong())
      emit(it.copy(time = vtDispatcher.currentTime))
    }
  }.merge()
}

