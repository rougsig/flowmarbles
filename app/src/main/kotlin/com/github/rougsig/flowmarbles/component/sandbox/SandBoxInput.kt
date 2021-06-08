package com.github.rougsig.flowmarbles.component.sandbox

import com.github.rougsig.flowmarbles.component.timeline.Marble
import com.github.rougsig.flowmarbles.component.timeline.Timeline
import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.ListComponent
import com.github.rougsig.flowmarbles.core.html

class SandBoxInput<T : Any> : Component {
  private val list = ListComponent<List<List<Marble.Model<T>>>>(html("div"))
  override val rootNode = list.rootNode
  var timelinesChangeListener: ((List<List<Marble.Model<T>>>) -> Unit)? = null

  fun setModel(model: List<List<Marble.Model<T>>>) {
    list.data = model
  }

  init {
    list.adapter = { timelines ->
      timelines.mapIndexed { index, timeline ->
        Timeline(timeline).apply {
          timelineChangeListener = { model ->
            timelinesChangeListener?.invoke(list.data?.toMutableList()?.apply {
              set(index, model)
            } ?: emptyList())
          }
        }.rootNode
      }
    }
  }
}
