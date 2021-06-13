package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.ListComponent
import com.github.rougsig.flowmarbles.core.html
import com.github.rougsig.flowmarbles.core.svg

class Timeline<T : Any>(
  timeline: List<Marble.Model<T>>,
  private val isEditable: Boolean = true
) : Component {
  companion object {
    const val MAX_TIME = 1000L
  }

  private val list = ListComponent<List<Marble.Model<T>>>(svg("svg") {
    attr("style", "overflow: visible;")
    attr("viewBox", "0 0 $MAX_TIME 10")
    attr("height", "72px")
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
      marbles.filter { it.time <= MAX_TIME }.mapIndexed { index, marble ->
        val prevMarble = marbles.getOrNull(index - 1)
        val nextMarble = marbles.getOrNull(index + 1)
        val isNext = nextMarble != null
          && (nextMarble.time + 20) >= marble.time
          && (nextMarble.time - 20) <= marble.time
        val isPrev = prevMarble != null
          && (prevMarble.time + 20) >= marble.time
          && (prevMarble.time - 20) <= marble.time
        val posY = when {
          isNext && isPrev -> 1L
          isPrev -> 7L
          isNext -> -4L
          else -> 1L
        }
        TimelineItem(marble, posY).apply {
          if (isEditable) dragListener = { time ->
            timelineChangeListener?.invoke(list.data?.toMutableList()?.apply {
              set(index, get(index).copy(time = time))
            } ?: emptyList())
          }
        }.rootNode
      }.plus(EndMarker(marbles.maxByOrNull { it.time }?.time ?: 0).rootNode)
    }

    setMarbles(timeline)
  }
}
