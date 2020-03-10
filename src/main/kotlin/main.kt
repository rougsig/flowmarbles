import component.timeline.TimelineItem
import core.appendComponent
import core.createSvgElement
import kotlin.browser.document

fun main() {
  val svg = createSvgElement("svg") {
    setAttribute("style", "overflow: visible;")
    setAttribute("viewBox", "0 0 100 10")
    setAttribute("height", "64px")
  }

  repeat(9) { i ->
    val item = TimelineItem()
    item.setColor("#$i$i$i")
    item.setTime(i * 10.0)
    item.setDragListener {
      println("$i -> $it")
      item.setTime(it)
    }
    svg.appendComponent(item)
  }

  document.getElementById("app")!!.append(svg)
}
