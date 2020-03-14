package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.svg

class EndMarker(maxMarbleTime: Long) : Component {
  override val rootNode = svg("line") {
    if (maxMarbleTime in 0..100) attr("style", "stroke: black; stroke-width: 0.3px;")
    attr("x1", "${maxMarbleTime + 7}")
    attr("x2", "${maxMarbleTime + 7}")
    attr("y1", "3.2")
    attr("y2", "6.8")
  }
}
