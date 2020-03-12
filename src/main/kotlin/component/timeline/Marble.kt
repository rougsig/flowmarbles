package component.timeline

import core.Component
import core.createSvgElement
import org.w3c.dom.Element

class Marble<T : Any> : Component {
  data class Model<T : Any>(
    val color: String,
    val time: Long,
    val value: T
  )

  override val rootNode: Element = createSvgElement("g")

  private val circle = createSvgElement("circle") {
    setAttribute("r", "2.5")
    setAttribute("stroke", "black")
    setAttribute("stroke-width", "0.5")
  }.also { rootNode.appendChild(it) }

  private val text = createSvgElement("text") {
    setAttribute("style", "user-select: none; font-size: 2.5px; font-family: 'Roboto Mono', monospace;")
    setAttribute("text-anchor", "middle")
    setAttribute("y", "1")
  }.also { rootNode.appendChild(it) }

  fun setModel(model: Model<T>) {
    rootNode.setAttribute("transform", "translate(${model.time}, 5)")
    circle.setAttribute("fill", model.color)
    text.innerHTML = model.value.toString()
  }
}
