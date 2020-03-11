import component.sandbox.SandBoxInput
import component.timeline.Marble
import component.timeline.Timeline
import core.appendComponent
import kotlin.browser.document

fun main() {
  val marbles = (0..9).map { i ->
    Marble.Model(
      color = "#$i$i$i",
      time = 10.0 * i
    )
  }
  val sandbox = SandBoxInput()
  sandbox.setModel(SandBoxInput.Model(
    timelines = (0..3).map { Timeline.Model(marbles) }
  ))
  sandbox.setTimelinesChangeListener { console.log(it) }
  document.getElementById("app")!!.appendComponent(sandbox)
}
