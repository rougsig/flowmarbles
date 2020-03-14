package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.svg

class Marble<T : Any>(model: Model<T>) : Component {
  data class Model<T : Any>(
    val color: String,
    val time: Long,
    val value: T
  )

  private val circle = svg("circle") {
    attr("fill", model.color)
    attr("r", "2.5")
    attr("stroke", "black")
    attr("stroke-width", "0.5")
  }

  private val value = svg("text") {
    attr("style", "user-select: none; font-size: 2.5px; font-family: 'Roboto Mono', monospace;")
    attr("text-anchor", "middle")
    attr("y", "1")
    text = model.value.toString()
  }

  override val rootNode = svg("g") {
    attr("transform", "translate(${model.time}, 5)")
    element(circle)
    element(value)
  }
}
