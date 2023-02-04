package org.victor
package classes

import scala.collection.mutable.ListBuffer

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
}
