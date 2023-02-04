package org.victor
package classes

import scala.collection.mutable.ListBuffer

class LetterCombinations {
  private val map = Map(
    '2' -> "abc",
    '3' -> "def",
    '4' -> "ghi",
    '5' -> "jkl",
    '6' -> "mno",
    '7' -> "pqrs",
    '8' -> "tuv",
    '9' -> "wxyz"
  )
  var result = ListBuffer[String]()
  def letterCombinations(digits: String): List[String] = {
    if (digits.length == 0) return List()
    appendLetters("", digits)
    result.toList
  }

  private def appendLetters(currentCombination: String, digits: String): Unit = {
    //if no more letters to add, add to the end result
    if (digits.length == 0) {
      result += currentCombination
      return
    }

    //for each letter in current letter, add its letter to the final combination and remove the letter
    val digit = digits(0)
    val letters = map(digit)
    for (letter <- letters) {
      appendLetters(currentCombination + letter, digits.drop(1))
    }
  }
}
