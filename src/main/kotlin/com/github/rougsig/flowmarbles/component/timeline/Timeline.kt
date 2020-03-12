package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.appendComponent
import com.github.rougsig.flowmarbles.core.createSvgElement
import org.w3c.dom.Element

class Timeline<T : Any>(private val editable: Boolean = true) :
  Component {
  data class Model<T : Any>(
    val marbles: List<Marble.Model<T>>
  )

  private var timelineChangeListener: ((Model<T>) -> Unit)? = null
  private var model: Model<T> =
    Model(marbles = emptyList())
    set(value) {
      inflateMarbles(field, value)
      field = value
      invalidateTimeline()
      timelineChangeListener?.invoke(field)
    }
  private var marbles: List<TimelineItem<T>> = emptyList()
  override val rootNode: Element = createSvgElement("svg") {
    setAttribute("style", "overflow: visible; background: wheat; display: block;")
    setAttribute("viewBox", "0 0 100 10")
    setAttribute("height", "64px")
    setAttribute("width", "640px")
  }

  fun setModel(model: Model<T>) {
    if (model == this.model) return
    this.model = model
  }

  fun setTimelineChangeListener(listener: ((Model<T>) -> Unit)?) {
    this.timelineChangeListener = listener
  }

  private fun inflateMarbles(currentModel: Model<T>, newModel: Model<T>) {
    if (currentModel.marbles.size == newModel.marbles.size) return

    rootNode.innerHTML = ""
    marbles = newModel.marbles.mapIndexed { index, marble ->
      val item = TimelineItem<T>()
      item.setModel(marble)
      if (editable) item.setDragListener { time ->
        this.model =
          Model(this.model.marbles.toMutableList().apply {
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
