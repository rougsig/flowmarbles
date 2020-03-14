package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.component.timeline.Timeline.Companion.MAX_TIME
import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.svg

class EndMarker(maxMarbleTime: Long) : Component {
  override val rootNode = svg("line") {
    if (maxMarbleTime in 0..MAX_TIME) attr("style", "stroke: black; stroke-width: 3px;")
    attr("x1", "${maxMarbleTime + 70}")
    attr("x2", "${maxMarbleTime + 70}")
    attr("y1", "-14")
    attr("y2", "30")
  }
}
