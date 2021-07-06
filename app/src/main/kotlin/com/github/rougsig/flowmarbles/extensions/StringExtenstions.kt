package com.github.rougsig.flowmarbles.extensions

fun String.toCamelKebabCase(): String {
  val builder = StringBuilder()
  this.forEach {
    if (it.toUpperCase() == it) {
      builder.append("-")
      builder.append(it.toLowerCase())
    } else {
      builder.append(it)
    }
  }
  return builder.toString()
}
