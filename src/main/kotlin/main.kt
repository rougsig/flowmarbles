import component.timeline.Marble
import component.timeline.Timeline
import component.timeline.TimelineItem
import core.appendComponent
import core.createSvgElement
import kotlin.browser.document

fun main() {
  val models = (0..9).map { i ->
    Marble.Model(
      color = "#$i$i$i",
      time = 10.0 * i
    )
  }
  val timeline = Timeline()
  timeline.setModel(models)
  timeline.setTimelineChangeListener { console.log(it) }
  document.getElementById("app")!!.appendComponent(timeline)
}
