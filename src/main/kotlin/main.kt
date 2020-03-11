import component.sandbox.SandBoxInput
import component.sandbox.SandBoxOutput
import component.timeline.Marble
import component.timeline.Timeline
import core.appendComponent
import extensions.VirtualTimeDispatcher
import extensions.toTimedFlow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.browser.document
import kotlin.browser.window

@InternalCoroutinesApi
fun main() {
  val sandboxInput = SandBoxInput<Any>()
  val initialState = SandBoxInput.Model<Any>(
    timelines = listOf(
      Timeline.Model<Any>(
        listOf(
          Marble.Model<Any>(
            color = "blue",
            time = 0.0,
            value = "A"
          ),
          Marble.Model<Any>(
            color = "green",
            time = 42.0,
            value = "B"
          ),
          Marble.Model<Any>(
            color = "orange",
            time = 55.0,
            value = "C"
          )
        )
      ),
      Timeline.Model(
        listOf(
          Marble.Model<Any>(
            color = "yellow",
            time = 0.0,
            value = "1"
          ),
          Marble.Model<Any>(
            color = "green",
            time = 10.0,
            value = "2"
          ),
          Marble.Model<Any>(
            color = "orange",
            time = 20.0,
            value = "3"
          )
        )
      )
    )
  )
  sandboxInput.setModel(initialState)
  val sandBoxOutput = SandBoxOutput<Any>()
  fun updateOutput(model: SandBoxInput.Model<Any>) {
    val vtDispatcher = VirtualTimeDispatcher()
    val timelines = model.timelines
    val source0 = timelines[0].marbles.toTimedFlow(vtDispatcher)
    val source1 = timelines[1].marbles.toTimedFlow(vtDispatcher)
    GlobalScope.launch {
      val list = source0
        .flatMapLatest { x ->
          source1.map {
            it.copy(
              time = vtDispatcher.currentTime.toDouble(),
              value = x.value.toString() + it.value.toString())
          }
        }
        .flowOn(vtDispatcher)
        .toList()
      sandBoxOutput.setModel(Timeline.Model(list))
    }
    window.setTimeout({
      println(vtDispatcher.advanceUntilIdle())
    }, 0)
  }
  sandboxInput.setTimelinesChangeListener { model ->
    updateOutput(model)
  }
  updateOutput(initialState)
  document.getElementById("app")!!.appendComponent(sandboxInput)
  document.getElementById("app")!!.appendComponent(sandBoxOutput)
}

class Queue {
  private var elements = mutableListOf<Pair<Long, Runnable>>()
  var time: Long = 0L
    private set

  fun post(delay: Long, block: Runnable) {
    println("post $time $delay ${time + delay}")
    elements.add(time + delay to block)
  }

  fun reset() {
    elements = mutableListOf()
    time = 0L
  }

  fun run() {
    do {
      elements.forEachIndexed { index, pair ->
        val (t, b) = pair
        if (time >= t) {
          b.run()
          elements.removeAt(index)
        }
      }
      if (elements.all { it.first > time }) time += 1
    } while (elements.isNotEmpty())
  }
}
