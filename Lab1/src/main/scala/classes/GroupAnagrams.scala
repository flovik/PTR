package org.victor
package classes
import scala.collection.mutable.{ListBuffer, Map}
class GroupAnagrams {
  private var groups = Map[String, ListBuffer[String]]()

  def groupAnagrams(words: List[String]): Map[String, ListBuffer[String]] = {
    for (word <- words) {
      val unsortedWord = word.toCharArray
      val sortedWord = unsortedWord.sorted.mkString
      if (groups.contains(sortedWord)) {
        var a = groups.get(sortedWord)
        groups.update(sortedWord, a.get += word)
      }
      else {
        groups += (sortedWord -> ListBuffer(word))
      }
    }

    groups
  }
}
