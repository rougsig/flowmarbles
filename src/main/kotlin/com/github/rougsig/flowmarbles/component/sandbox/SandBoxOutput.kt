package com.github.rougsig.flowmarbles.component.sandbox

import com.github.rougsig.flowmarbles.component.timeline.Timeline
import com.github.rougsig.flowmarbles.core.Component
import org.w3c.dom.Element

class SandBoxOutput<T : Any> : Component {
  private val timeline =
    Timeline<T>(editable = false)
  override val rootNode: Element
    get() = timeline.rootNode

  fun setModel(model: Timeline.Model<T>) {
    timeline.setModel(model.copy(
      marbles = model.marbles.filter { it.time <= 100.0 })
    )
  }
}
