package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.ListComponent
import com.github.rougsig.flowmarbles.core.html
import com.github.rougsig.flowmarbles.core.svg

class Timeline<T : Any>(
  timeline: List<Marble.Model<T>>,
  private val isEditable: Boolean = true
) : Component {
  private val list = ListComponent<List<Marble.Model<T>>>(svg("svg") {
    attr("style", "overflow: visible;")
    attr("viewBox", "0 0 100 10")
    attr("height", "64px")
    attr("width", "640px")
  })

  override val rootNode = html("div") {
    element(svg("svg") {
      attr("style", "overflow: visible; width: 48px")
      attr("viewBox", "0 0 7 10")
      tag("line") {
        attr("style", "stroke: black; stroke-width: 0.3px")
        attr("x1", "0")
        attr("x2", "112")
        attr("y1", "5")
        attr("y2", "5")
      }
      tag("polygon") {
        attr("style", "color: black;")
        attr("points", "111.7,6.1 111.7,3.9 114,5")
      }
    })
    component(list)
  }

  var timelineChangeListener: ((List<Marble.Model<T>>) -> Unit)? = null
  fun setMarbles(marbles: List<Marble.Model<T>>) {
    list.data = marbles.sortedBy { it.time }
  }

  init {
    list.adapter = { marbles ->
      marbles.mapIndexed { index, marble ->
        TimelineItem(marble).apply {
          if (isEditable) dragListener = { time ->
            timelineChangeListener?.invoke(list.data?.toMutableList()?.apply {
              set(index, get(index).copy(time = time))
            } ?: emptyList())
          }
        }.rootNode
      }
    }

    setMarbles(timeline)
  }
}
