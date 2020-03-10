package component.timeline

import core.Component
import core.createSvgElement
import org.w3c.dom.Element

class Marble : Component {
  override val rootNode: Element = createSvgElement("g")

  private val circle = createSvgElement("circle") {
    setAttribute("r", "2.5")
    setAttribute("stroke", "black")
    setAttribute("stroke-width", "0.5")
  }.also { rootNode.appendChild(it) }

  fun setTime(time: Double) {
    rootNode.setAttribute("transform", "translate($time, 5)")
  }

  fun setColor(color: String) {
    circle.setAttribute("fill", color)
  }
}
