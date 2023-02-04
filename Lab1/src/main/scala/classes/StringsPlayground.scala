package org.victor
package classes

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.{break, breakable}

class StringsPlayground {
  private val line1keyboard = "qwertyuiopQWERTYUIOP"
  private val line2keyboard = "asdfghjklASDFGHJKL"
  private val line3keyboard = "zxcvbnmZXCVBNM"
  def lineWords(words: List[String]): List[String] = {
    // return the words that can
    //be typed using only one row of the letters on an English keyboard layout
    var result = ListBuffer[String]()

    for (word <- words) {
      var line1 = false
      var line2 = false
      var line3 = false
      for (letter <- word) {
        if (line1keyboard.contains(letter)) line1 = true
        if (line2keyboard.contains(letter)) line2 = true
        if (line3keyboard.contains(letter)) line3 = true
      }
      if (!((line1 && line2) || (line1 && line3) || (line2 && line3))) result += word
    }

    result.toList
  }

  def commonPrefix(list: List[String]): String = {
    var result = new StringBuilder("")
    if (list.length == 0) return result.toString()
    if (list.length == 1) return list(0)
    result.append(list(0))

    for (i <- 1 until list.length) {
      val currentWord = list(i)

      breakable {
        for (j <- 0 until result.length) {
          if (j >= result.length) break
          if (j >= currentWord.length) {
            result.delete(j, result.length)
            break
          }

          if (currentWord.charAt(j) != result.charAt(j)) {
            result.delete(j, result.length)
            break
          }
        }
      }
    }

    result.toString()
  }
}
