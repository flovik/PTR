package org.victor

import classes.{CaesarCipher, GroupAnagrams, HelloWorder, LetterCombinations, ListExtensions, MathClass, StringsPlayground, Translator}

import scala.collection.mutable.ListBuffer

object Main {
  def main(args: Array[String]): Unit = {
    val helloWorder = new HelloWorder;
    println(helloWorder.helloPtr())

    val mathClass = new MathClass;
    println(mathClass.IsPrime(7))
    println(mathClass.IsPrime(13))
    println(mathClass.IsPrime(15))
    println(mathClass.IsPrime(21))

    println(mathClass.cylinderArea(2, 3))
    println(mathClass.cylinderArea(3, 4))

    val listExtensions = new ListExtensions;

    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(listExtensions.reverse(list))

    val list2 = List(1, 2, 4, 8, 4, 2)
    println(listExtensions.uniqueSum(list2))

    val list3 = ListBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(listExtensions.extractRandomN(list3, 3))

    println(mathClass.firstFibonacciElements(7))

    val dictionary: Map[String, String] = Map("mama" -> "mother", "papa" -> "father");

    val original_string = "mama is with papa"
    val tr = new Translator
    println(tr.translator(dictionary, original_string))
    println(tr.translator(dictionary, ""))

    println(mathClass.smallestNumber(0, 3, 4))
    println(mathClass.smallestNumber(4, 5, 3))
    println(mathClass.smallestNumber(0, 0, 2))

    println(mathClass.rotateLeft(List(1, 2, 4, 8, 4), 3))
    println(mathClass.rotateLeft(List(1, 2, 4, 8, 4), 1))
    println(mathClass.rotateLeft(List(1, 2, 4, 8, 4), 5))
    println(mathClass.rotateLeft(List(1, 2, 4, 8, 4), 4))

    println(mathClass.listRightAngleTriangles)

    println(listExtensions.removeConsecutiveDuplicates(List(1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5)))
    println(listExtensions.removeConsecutiveDuplicates(List(1 , 1, 1, 1, 2 , 2 , 2 , 4 , 8 , 4)))
    println(listExtensions.removeConsecutiveDuplicates(List()))

    val str = new StringsPlayground;
    println(str.lineWords(List(" Hello " ," Alaska " ," Dad " ," Peace ")))

    var caesar = new CaesarCipher
    println(caesar.encrypt("lorem", 3))
    println(caesar.decrypt("oruHp", 3))

    var lc = new LetterCombinations
    println(lc.letterCombinations("23"))
    println(lc.letterCombinations("236"))

    var ga = new GroupAnagrams;
    println(ga.groupAnagrams(List("eat", "tea", "tan", "ate", "nat", "bat")))
  }
}