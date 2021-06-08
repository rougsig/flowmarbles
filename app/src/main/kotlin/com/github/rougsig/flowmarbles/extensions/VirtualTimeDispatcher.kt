package com.github.rougsig.flowmarbles.extensions

import kotlinx.coroutines.*
import kotlinx.coroutines.internal.ThreadSafeHeap
import kotlinx.coroutines.internal.ThreadSafeHeapNode
import kotlin.coroutines.CoroutineContext
import kotlin.math.max


interface DelayController {
  val currentTime: Long
  fun advanceTimeBy(delayTimeMillis: Long): Long
  fun advanceUntilIdle(): Long
  fun runCurrent()
  suspend fun pauseDispatcher(block: suspend () -> Unit)
  fun pauseDispatcher()
  fun resumeDispatcher()
}

// Huge thanks to
// https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test
@ExperimentalCoroutinesApi
@OptIn(InternalCoroutinesApi::class)
class VirtualTimeDispatcher : CoroutineDispatcher(), Delay,
  DelayController {
  private var dispatchImmediately = true
    set(value) {
      field = value
      if (value) advanceUntilIdle()
    }

  private val queue = ThreadSafeHeap<TimedRunnable>()
  private var counter = 0L
  override var currentTime: Long = 0L
    private set

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    if (dispatchImmediately) block.run()
    else post(block)
  }

  override fun dispatchYield(context: CoroutineContext, block: Runnable) {
    post(block)
  }

  override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
    postDelayed(CancellableContinuationRunnable(continuation) { resumeUndispatched(Unit) }, timeMillis)
  }

  override fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
    val node = postDelayed(block, timeMillis)
    return object : DisposableHandle {
      override fun dispose() {
        queue.remove(node)
      }
    }
  }

  private fun post(block: Runnable) {
    queue.addLast(TimedRunnable(block, ++counter))
  }

  private fun postDelayed(block: Runnable, delayTime: Long): TimedRunnable {
    return TimedRunnable(block, ++counter, currentTime + delayTime)
      .also { queue.addLast(it) }
  }

  private fun doActionsUntil(targetTime: Long) {
    while (true) {
      val current = queue.removeFirstIf { it.time <= targetTime } ?: break
      if (current.time != 0L) currentTime = current.time
      current.run()
    }
  }

  override fun advanceTimeBy(delayTimeMillis: Long): Long {
    val oldTime = currentTime
    advanceUntilTime(oldTime + delayTimeMillis)
    return currentTime - oldTime
  }

  private fun advanceUntilTime(targetTime: Long) {
    doActionsUntil(targetTime)
    currentTime = max(currentTime, targetTime)
  }

  override fun advanceUntilIdle(): Long {
    val oldTime = currentTime
    while (!queue.isEmpty) {
      runCurrent()
      val next = queue.peek() ?: break
      advanceUntilTime(next.time)
    }
    return currentTime - oldTime
  }

  override fun runCurrent() {
    doActionsUntil(currentTime)
  }

  override suspend fun pauseDispatcher(block: suspend () -> Unit) {
    val previous = dispatchImmediately
    dispatchImmediately = false
    try {
      block()
    } finally {
      dispatchImmediately = previous
    }
  }

  override fun pauseDispatcher() {
    dispatchImmediately = false
  }

  override fun resumeDispatcher() {
    dispatchImmediately = true
  }
}

private class CancellableContinuationRunnable<T>(
  val continuation: CancellableContinuation<T>,
  private val block: CancellableContinuation<T>.() -> Unit
) : Runnable {
  override fun run() {
    continuation.block()
  }
}

@OptIn(InternalCoroutinesApi::class)
private class TimedRunnable(
  val runnable: Runnable,
  private val count: Long = 0,
  val time: Long = 0
) : Comparable<TimedRunnable>, Runnable by runnable, ThreadSafeHeapNode {
  override var heap: ThreadSafeHeap<*>? = null
  override var index: Int = 0

  override fun compareTo(other: TimedRunnable): Int {
    return if (time == other.time) count.compareTo(other.count)
    else time.compareTo(other.time)
  }
}
