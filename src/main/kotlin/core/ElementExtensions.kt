package core

import org.w3c.dom.Element
import org.w3c.dom.Node

fun Element.appendComponent(component: Component): Node {
  return appendChild(component.rootNode)
}
