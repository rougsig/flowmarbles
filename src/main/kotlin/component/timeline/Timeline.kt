package component.timeline

import core.Component
import core.appendComponent
import core.createSvgElement
import org.w3c.dom.Element

class Timeline(private val editable: Boolean = true) : Component {
  data class Model(
    val marbles: List<Marble.Model>
  )

  private var timelineChangeListener: ((Model) -> Unit)? = null
  private var model: Model = Model(marbles = emptyList())
    set(value) {
      inflateMarbles(field, value)
      field = value
      invalidateTimeline()
      timelineChangeListener?.invoke(field)
    }
  private var marbles: List<TimelineItem> = emptyList()
  override val rootNode: Element = createSvgElement("svg") {
    setAttribute("style", "overflow: visible; background: wheat; display: block;")
    setAttribute("viewBox", "0 0 100 10")
    setAttribute("height", "64px")
    setAttribute("width", "640px")
  }

  fun setModel(model: Model) {
    if (model == this.model) return
    this.model = model
  }

  fun setTimelineChangeListener(listener: ((Model) -> Unit)?) {
    this.timelineChangeListener = listener
  }

  private fun inflateMarbles(currentModel: Model, newModel: Model) {
    if (currentModel.marbles.size == newModel.marbles.size) return

    rootNode.innerHTML = ""
    marbles = newModel.marbles.mapIndexed { index, marble ->
      val item = TimelineItem()
      item.setModel(marble)
      if (editable) item.setDragListener { time ->
        this.model = Model(this.model.marbles.toMutableList().apply {
          removeAt(index)
          add(index, model.marbles[index].copy(time = time))
        })
      }
      rootNode.appendComponent(item)
      item
    }
  }

  private fun invalidateTimeline() {
    marbles.forEachIndexed { index, timelineItem ->
      timelineItem.setModel(model.marbles[index])
    }
  }
}
