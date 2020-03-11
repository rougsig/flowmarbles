package component.timeline

import core.Component
import core.createSvgElement
import org.w3c.dom.Element

class Marble : Component {
  data class Model(
    val color: String,
    val time: Double
  )

  override val rootNode: Element = createSvgElement("g")

  private val circle = createSvgElement("circle") {
    setAttribute("r", "2.5")
    setAttribute("stroke", "black")
    setAttribute("stroke-width", "0.5")
  }.also { rootNode.appendChild(it) }

  fun setModel(model: Model) {
    rootNode.setAttribute("transform", "translate(${model.time}, 5)")
    circle.setAttribute("fill", model.color)
  }
}
