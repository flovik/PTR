package org.victor
package classes

import scala.collection.mutable.ListBuffer


class ListExtensions {

  def reverse(list: List[Int]): List[Int] = {
     list.reverse
  }

  def uniqueSum(list: List[Int]): Int = {
    list.distinct.sum
  }

    def extractRandomN(list: ListBuffer[Int], n: Int): List[Int] = {
    // ListBuffer is mutable, so we can remove elements from it
    var r = list.length - n;
    while (r > 0) {
      val randomIndex = scala.util.Random.nextInt(list.length)
      val randomElement = list(randomIndex)
      list -= randomElement
      r = r - 1
    }

    list.toList
  }

  def removeConsecutiveDuplicates(list: List[Integer]): List[Integer] = {
    var newList = ListBuffer[Integer]()
    var current = Integer.MIN_VALUE;
    for (i <- 0 until list.length) {
      if (list(i) != current) {
        newList += list(i)
        current = list(i)
      }
    }
    newList.toList
  }
}
