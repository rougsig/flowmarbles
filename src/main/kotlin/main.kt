import component.sandbox.SandBoxInput
import component.sandbox.SandBoxOutput
import component.timeline.Marble
import component.timeline.Timeline
import core.appendComponent
import extensions.toFlow
import kotlinx.coroutines.flow.*
import kotlin.browser.document
import kotlinx.coroutines.*

fun main() {
  val sandboxInput = SandBoxInput()
  sandboxInput.setModel(SandBoxInput.Model(
    timelines = listOf(
      Timeline.Model(listOf(
        Marble.Model(
          color = "red",
          time = 20.0
        ),
        Marble.Model(
          color = "red",
          time = 40.0
        ),
        Marble.Model(
          color = "red",
          time = 60.0
        )
      )),
      Timeline.Model(listOf(
        Marble.Model(
          color = "blue",
          time = 30.0
        ),
        Marble.Model(
          color = "blue",
          time = 50.0
        ),
        Marble.Model(
          color = "blue",
          time = 70.0
        )
      ))
    )
  ))
  val sandBoxOutput = SandBoxOutput()
  sandboxInput.setTimelinesChangeListener { model ->
    val timelines = model.timelines
    val source0 = timelines[0].marbles.toFlow()
    val source1 = timelines[1].marbles.toFlow()
    GlobalScope.launch {
      sandBoxOutput.setModel(Timeline.Model(merge(source0, source1).toList()))
    }
  }
  document.getElementById("app")!!.appendComponent(sandboxInput)
  document.getElementById("app")!!.appendComponent(sandBoxOutput)
}
