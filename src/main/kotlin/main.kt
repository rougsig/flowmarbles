import component.sandbox.SandBoxInput
import component.sandbox.SandBoxOutput
import component.timeline.Marble
import component.timeline.Timeline
import core.appendComponent
import extensions.toFlow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.browser.document

fun main() {
  val sandboxInput = SandBoxInput<Int>()
  val initialState = SandBoxInput.Model<Int>(
    timelines = listOf(
      Timeline.Model(listOf(
        Marble.Model(
          color = "red",
          time = 20.0,
          value = 20
        ),
        Marble.Model(
          color = "red",
          time = 40.0,
          value = 40
        ),
        Marble.Model(
          color = "red",
          time = 60.0,
          value = 60
        )
      )),
      Timeline.Model(listOf(
        Marble.Model(
          color = "yellow",
          time = 30.0,
          value = 30
        ),
        Marble.Model(
          color = "yellow",
          time = 50.0,
          value = 50
        ),
        Marble.Model(
          color = "yellow",
          time = 70.0,
          value = 70
        )
      ))
    )
  )
  sandboxInput.setModel(initialState)
  val sandBoxOutput = SandBoxOutput<Int>()
  fun updateOutput(model: SandBoxInput.Model<Int>) {
    val timelines = model.timelines
    val source0 = timelines[0].marbles.toFlow()
    val source1 = timelines[1].marbles.toFlow()
    GlobalScope.launch {
      sandBoxOutput.setModel(Timeline.Model(merge(source0, source1).filter { it.value > 45 }.toList()))
    }
  }
  sandboxInput.setTimelinesChangeListener { model ->
    updateOutput(model)
  }
  updateOutput(initialState)
  document.getElementById("app")!!.appendComponent(sandboxInput)
  document.getElementById("app")!!.appendComponent(sandBoxOutput)
}
