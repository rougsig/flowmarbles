package extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T : Any> List<T>.toFlow(): Flow<T> = flow {
  for (element in this@toFlow) {
    emit(element)
  }
}
