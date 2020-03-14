package com.github.rougsig.flowmarbles.component.timeline

import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.svg

class Marble<T : Any>(model: Model<T>) : Component {
  data class Model<T : Any>(
    val color: String,
    val time: Long,
    val value: T
  ) {
    operator fun plus(other: Model<T>): Model<T> {
      val newValue: T = if (this.value as? Int != null && other.value as? Int != null) {
        (value + other.value) as T
      } else if (this.value as? String != null && other.value as? String != null) {
        (this.value + other.value) as T
      } else {
        error("unknown value type: $value. Expected String or Int.")
      }

      return Model(
        other.color,
        time + other.time,
        newValue
      )
    }
  }

  private val circle = svg("circle") {
    attr("fill", model.color)
    attr("r", "25")
    attr("stroke", "black")
    attr("stroke-width", "3.5")
  }

  private val value = svg("text") {
    attr("style", "user-select: none; font-size: 25px; font-family: 'Roboto Mono', monospace;")
    attr("text-anchor", "middle")
    attr("y", "8")
    text = model.value.toString()
  }

  override val rootNode = svg("g") {
    attr("transform", "translate(${model.time}, 5)")
    element(circle)
    element(value)
  }
}
