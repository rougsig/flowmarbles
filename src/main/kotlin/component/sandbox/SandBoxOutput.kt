package component.sandbox

import component.timeline.Timeline
import core.Component
import org.w3c.dom.Element

class SandBoxOutput<T : Any> : Component {
  private val timeline = Timeline<T>(editable = false)
  override val rootNode: Element
    get() = timeline.rootNode

  fun setModel(model: Timeline.Model<T>) {
    timeline.setModel(model)
  }
}
