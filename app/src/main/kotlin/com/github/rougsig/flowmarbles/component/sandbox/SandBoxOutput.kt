package com.github.rougsig.flowmarbles.component.sandbox

import com.github.rougsig.flowmarbles.component.timeline.Marble
import com.github.rougsig.flowmarbles.component.timeline.Timeline
import com.github.rougsig.flowmarbles.core.Component

class SandBoxOutput<T : Any> : Component {
  private val timeline = Timeline<T>(timeline = emptyList(), isEditable = false)
  override val rootNode = timeline.rootNode

  fun setModel(marbles: List<Marble.Model<T>>) {
    timeline.setMarbles(marbles)
  }
}
