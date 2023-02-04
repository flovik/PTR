package org.victor
package classes

class Translator {

  def translator(map: Map[String, String], sentence: String): String = {
    var result = ""
    val words = sentence.split(" ")
    for (word <- words) {
      if (map.contains(word)) result += map(word) + " "
      else result += word + " "
    }
    if (result.length > 0) result = result.dropRight(1)
    result
  }
}
