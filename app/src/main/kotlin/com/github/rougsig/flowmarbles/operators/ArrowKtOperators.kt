import com.github.rougsig.flowmarbles.operators.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import arrow.fx.coroutines.metered

@FlowPreview
@ExperimentalCoroutinesApi
fun arrowKtOperators() = listOf(
  menuHeader("arrow-kt"),
  menuItem(
    label("metered"),
    sandbox(
      "metered",
      inputs(
        input(
          marble("1", 0),
          marble("2", 100),
          marble("3", 200),
          marble("4", 800),
          marble("5", 900)
        )
      ),
      "metered(300L)"
    ) { inputs -> inputs[0].metered(300) }
  )
)
