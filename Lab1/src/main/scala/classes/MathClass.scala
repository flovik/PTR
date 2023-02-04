package org.victor
package classes

import scala.math.sqrt
import scala.collection.mutable.Map

class MathClass {

  def IsPrime(n: Int): Boolean = {
    for (i <- 2 until sqrt(n).toInt + 1) {
      if (n % i == 0) return false
    }

    return true;
  }

  def cylinderArea(h: Int, r: Int): Double = {
    2 * math.Pi * r * h + 2 * math.Pi * r * r
  }

  def firstFibonacciElements(n: Int): List[Int] = {
    var a = 1
    var b = 1
    var c = 0
    var list = List(a, b)
    for (_ <- 1 until n - 1) {
      c = a + b
      a = b
      b = c
      list = list :+ c
    }
    list
  }

  def smallestNumber(a: Int, b: Int, c: Int): Int = {
    var d: StringBuilder = new StringBuilder;
    if (a >= b && a >= c) {
      if (b >= c && c == 0) {
        d.append(b.toString)
        d.append(c.toString)
      }
      else {
        d.append(c.toString)
        d.append(b.toString)
      }
      d.append(a.toString)
    }
    if (b >= a && b >= c) {
      if (a >= c && c == 0) {
        d.append(a.toString)
        d.append(c.toString)
      }
      else {
        d.append(c.toString)
        d.append(a.toString)
      }
      d.append(b.toString)
    }
    else {
      if (a >= b && b == 0) {
        d.append(a.toString)
        d.append(b.toString)
      }
      else {
        d.append(b.toString)
        d.append(a.toString)
      }

      d.append(c.toString)
    }

    var ret = d.toString()
    // case with 2 zeros
    if (ret(0) == '0' && ret(1) == '0') {
      ret = ret.reverse
    }

    Integer.parseInt(ret)
  }

  def rotateLeft(list: List[Int], n: Int): List[Int] = {
    var myList = list
    var m = 0
    if (n > list.size) m = n % list.size
    m = list.size - n;

    //rotate the whole array
    myList = myList.reverse
    //rotate the first m elements
    myList = myList.slice(0, m).reverse ++ myList.slice(m, myList.size)

    //rotate the elements after m
    myList = myList.slice(0, m) ++ myList.slice(m, myList.size).reverse

    myList
  }

  def listRightAngleTriangles(): List[(Int, Int, Int)] = {
    // a^2 + b^2 = c^2
    // a, b <= 20
    val n = 20
    var list = List[(Int, Int, Int)]()
    for (a <- 1 to n) {
      for (b <- 1 to n) {
        for (c <- 1 to n) {
          if (a * a + b * b == c * c) {
            list = list :+ (a, b, c)
          }
        }
      }
    }
    list
  }

  private val numsToRomans = Map(
    1 -> "I",
    4 -> "IV",
    5 -> "V",
    9 -> "IX",
    10 -> "X",
    40 -> "XL",
    50 -> "L",
    90 -> "XC",
    100 -> "C",
    400 -> "CD",
    500 -> "D",
    900 -> "CM",
    1000 -> "M"
  )

  private val numbers = List(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)

  def toRoman(n: String): String = {
    val result = new StringBuilder("")
    var number = n.toInt
    for (num <- numbers) {
      while (num <= number) {
        result.append(numsToRomans(num))
        number -= num
      }
    }

    result.toString()
  }
}
