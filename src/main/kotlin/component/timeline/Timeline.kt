package component.timeline

import core.Component
import core.appendComponent
import core.createSvgElement
import org.w3c.dom.Element

class Timeline : Component {
  private var timelineChangeListener: ((List<Marble.Model>) -> Unit)? = null
  private var models: List<Marble.Model> = emptyList()
    set(value) {
      inflateMarbles(field, value)
      field = value
      invalidateTimeline()
      timelineChangeListener?.invoke(field)
    }
  private var marbles: List<TimelineItem> = emptyList()
  override val rootNode: Element = createSvgElement("svg") {
    setAttribute("style", "overflow: visible; background: wheat;")
    setAttribute("viewBox", "0 0 100 10")
    setAttribute("height", "64px")
    setAttribute("width", "640px")
  }

  fun setModel(models: List<Marble.Model>) {
    this.models = models
  }

  fun setTimelineChangeListener(listener: ((List<Marble.Model>) -> Unit)?) {
    this.timelineChangeListener = listener
  }

  private fun inflateMarbles(currentModels: List<Marble.Model>, newModels: List<Marble.Model>) {
    if (currentModels.size == newModels.size) return

    rootNode.innerHTML = ""
    marbles = newModels.mapIndexed { index, model ->
      val item = TimelineItem()
      item.setModel(model)
      item.setDragListener { time ->
        this.models = models.toMutableList().apply {
          removeAt(index)
          add(index, models[index].copy(time = time))
        }
      }
      rootNode.appendComponent(item)
      item
    }
  }

  private fun invalidateTimeline() {
    marbles.forEachIndexed { index, timelineItem ->
      timelineItem.setModel(models[index])
    }
  }
}
