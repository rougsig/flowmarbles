package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.core.Component
import org.w3c.dom.DOMRect
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import kotlin.browser.document

class TimelineItem<T : Any> : Component {
  private var dragListener: ((Long) -> Unit)? = null
  private val marble = Marble<T>()
  override val rootNode = marble.rootNode

  init {
    rootNode.setAttribute("style", "cursor: ew-resize;")

    rootNode.addEventListener("mousedown", { down ->
      val rect = down.asDynamic().currentTarget.parentElement.getBoundingClientRect() as DOMRect
      val width = rect.width
      val left = rect.left
      val ratio = 100 / width
      val percent = { x: Double ->
        val pos = (x - left) * ratio
        when {
          pos > 100 -> 100.0
          pos < 0 -> 0.0
          else -> pos
        }.toLong()
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

  fun setDragListener(listener: ((Long) -> Unit)) {
    this.dragListener = listener
  }

  fun setModel(model: Marble.Model<T>) {
    marble.setModel(model)
  }
}
