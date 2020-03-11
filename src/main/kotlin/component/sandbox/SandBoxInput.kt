package component.sandbox

import component.timeline.Timeline
import core.Component
import core.appendComponent
import core.createElement
import org.w3c.dom.Element

class SandBoxInput : Component {
  data class Model(
    val timelines: List<Timeline.Model>
  )

  private var timelinesChangeListener: ((Model) -> Unit)? = null
  private var model: Model = Model(timelines = emptyList())
    set(value) {
      inflateTimelines(field, value)
      field = value
      invalidateTimelines()
      timelinesChangeListener?.invoke(field)
    }
  private var timelines: List<Timeline> = emptyList()
  override val rootNode: Element = createElement("div")

  fun setModel(model: Model) {
    if (model == this.model) return
    this.model = model
  }

  fun setTimelinesChangeListener(listener: ((Model) -> Unit)?) {
    this.timelinesChangeListener = listener
  }

  private fun inflateTimelines(currentModel: Model, newModel: Model) {
    if (currentModel.timelines.size == newModel.timelines.size) return

    rootNode.innerHTML = ""

    timelines = newModel.timelines.mapIndexed { index, timeline ->
      val item = Timeline()
      item.setModel(timeline)
      item.setTimelineChangeListener { model ->
        this.model = Model(this.model.timelines.toMutableList().apply {
          removeAt(index)
          add(index, model)
        })
      }
      rootNode.appendComponent(item)
      item
    }
  }

  private fun invalidateTimelines() {
    timelines.forEachIndexed { index, timeline ->
      timeline.setModel(model.timelines[index])
    }
  }
}
