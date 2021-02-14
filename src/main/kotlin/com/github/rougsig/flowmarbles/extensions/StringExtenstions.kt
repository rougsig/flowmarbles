package com.github.rougsig.flowmarbles.extensions

fun String.toLowerKebabCase(): String {
  val builder = StringBuilder()
  forEach {
    if (it == it.toUpperCase()) {
      builder.append("-")
      builder.append(it.toLowerCase())
    } else {
      builder.append(it)
    }
  }
  return builder.toString()
}
