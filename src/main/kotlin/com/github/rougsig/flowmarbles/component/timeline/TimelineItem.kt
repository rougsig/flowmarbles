package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.component.timeline.Timeline.Companion.MAX_TIME
import com.github.rougsig.flowmarbles.core.Component
import org.w3c.dom.DOMRect
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import kotlin.browser.document

class TimelineItem<T : Any>(model: Marble.Model<T>) : Component {
  var dragListener: ((Long) -> Unit)? = null
  private val marble = Marble(model)
  override val rootNode = marble.rootNode

  init {
    rootNode.setAttribute("style", "cursor: ew-resize;")

    rootNode.addEventListener("mousedown", { down ->
      val rect = down.asDynamic().currentTarget.parentElement.getBoundingClientRect() as DOMRect
      val width = rect.width
      val left = rect.left
      val ratio = MAX_TIME / width
      val percent = { x: Double ->
        val pos = ((x - left) * ratio).toLong()
        when {
          pos > MAX_TIME -> MAX_TIME
          pos < 0 -> 0L
          else -> pos
        }
      }

      val moveListener = { move: Event ->
        val mouseMoveEvent = move as MouseEvent
        val posX = percent(mouseMoveEvent.pageX)
        dragListener?.invoke(posX)
        Unit
      }
      document.addEventListener("mousemove", moveListener)

      document.addEventListener("mouseup", {
        document.removeEventListener("mousemove", moveListener)
      })
    })
  }
}
