package org.victor

import classes._
import week3actors.minimal1.MessagePrinterMain
import week3actors.minimal1.MessagePrinterMain.PrintAnyMessage
import week3actors.minimal2.GenericMessagePrinter
import week3actors.minimal3.SupervisorActor
import week3actors.minimal3.SupervisorActor.{Command, StartActor}
import week3actors.minimal4.foo

import akka.actor.typed.ActorSystem

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

    println(mathClass.toRoman("13"))
    println(mathClass.toRoman("914"))
    println(mathClass.toRoman("909"))

    println(str.commonPrefix(List("flower","flow","flight")))
    println(str.commonPrefix(List("flower","flower","flowers", "flowe", "flowers")))
    println(str.commonPrefix(List("alpha" , "beta" , "gamma")))

    // WEEK 3 - an actor is born
    // in Akka you can't create actors directly
    // you need to create an actor system that uses spawn() to create actors
    // it returns a reference called ActorRef that points to the actor

    // actorSystem with a guardian, which is the root of the actor hierarchy
    // it bootstraps the actor system
    // it is the entry point to the Akka
    val messagePrinterSystem: ActorSystem[PrintAnyMessage] = ActorSystem(MessagePrinterMain(), "MessagePrinterSystem")

    // actors are reactive and message driven
    // an actor won't do anything until it receives a message
    // actors communicate using asynchronous message passing
    // the sender puts the message in the mailbox of the receiver
    // and is free to continue doing other work
    // the actor's mailbox is a queue of messages
    // the order is preserved, but can be interleaved with messages from other actors
    // when actor doesn't have any work to do, it is in suspended state when doesn't consume resources
    // the following commands puts a message in the mailbox of the actor
    // messagePrinterSystem sends a message MessagePrinterMain
    messagePrinterSystem ! PrintAnyMessage("Hello PTR!")
    messagePrinterSystem ! PrintAnyMessage("Hello Week3!")
    messagePrinterSystem ! PrintAnyMessage("Hello the new Actor!")

    val genericSystem: ActorSystem[Any] = ActorSystem(GenericMessagePrinter(), "GenericMessagePrinter")
    genericSystem ! 10
    genericSystem ! "Hello"
    genericSystem ! true
    genericSystem ! 10.5
    genericSystem ! List(1, 2, 3, 4, 5)

    // actors can watch other actors
    // if the watched actor terminates, the watcher will be notified
    val watcher: ActorSystem[Command] = ActorSystem(SupervisorActor(), "SupervisorActor")
    watcher ! StartActor("Pechea", "Hello Pechea!")

    // change internal state of the actors
    val internal: ActorSystem[Double] = ActorSystem(foo(), "foo")
    internal ! 10
    internal ! 10
    internal ! 10
    internal ! 10
    internal ! 10
  }
}