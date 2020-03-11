package component.sandbox

import component.timeline.Timeline
import core.Component
import org.w3c.dom.Element

class SandBoxOutput : Component {
  private val timeline = Timeline(editable = false)
  override val rootNode: Element
    get() = timeline.rootNode

  fun setModel(model: Timeline.Model) {
    timeline.setModel(model)
  }
}
