package com.github.rougsig.flowmarbles.component.sandbox

import com.github.rougsig.flowmarbles.component.timeline.Marble
import com.github.rougsig.flowmarbles.core.Component
import com.github.rougsig.flowmarbles.core.html
import com.github.rougsig.flowmarbles.extensions.VirtualTimeDispatcher
import com.github.rougsig.flowmarbles.extensions.toTimedFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlin.browser.window

typealias SandBoxTransformer<T> = (inputs: List<Flow<Marble.Model<T>>>) -> Flow<Marble.Model<T>>

@ExperimentalCoroutinesApi
class SandBox<T : Any> : Component {
  data class Model<T : Any>(
    val input: List<List<Marble.Model<T>>>,
    val label: String,
    val transformer: SandBoxTransformer<T>
  )

  private val input = SandBoxInput<T>()
  private val label = SandBoxLabel()
  private val output = SandBoxOutput<T>()
  override val rootNode = html("div") {
    attr("class", "sandbox")
    component(input)
    component(label)
    component(output)
  }

  fun setModel(model: Model<T>) {
    label.label = model.label
    input.setModel(model.input)
    invalidateOutput(model.input, model.transformer)
    input.timelinesChangeListener = { newModel ->
      input.setModel(newModel)
      invalidateOutput(newModel, model.transformer)
    }
  }

  private var job: Job? = null
  private fun invalidateOutput(input: List<List<Marble.Model<T>>>, transformer: SandBoxTransformer<T>) {
    val virtualTimeDispatcher = VirtualTimeDispatcher()
    virtualTimeDispatcher.pauseDispatcher()
    job?.cancel()
    job = (GlobalScope + virtualTimeDispatcher).launch {
      output.setModel(
        transformer(input.map { it.toTimedFlow() })
          .map { it.copy(time = virtualTimeDispatcher.currentTime) }
          .toList()
      )
    }
    window.setTimeout({
      virtualTimeDispatcher.resumeDispatcher()
      virtualTimeDispatcher.advanceUntilIdle()
    }, 0)
  }

  init {
    window.onscroll = {
      rootNode.setAttribute("style", "transform: translateY(${window.pageYOffset}px)")
    }
  }
}
