package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class StringJoinerClass(nextActor: ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case StringJoiner.JoinMessage(message) =>
      val joinedWords = message.mkString(" ")
      nextActor ! StringPrinter.PrintMessage(joinedWords)
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info("StringJoiner restarted")
  }
}

object StringJoiner {
  def props(nextActor: ActorRef): Props = Props(new StringJoinerClass(nextActor))

  final case class JoinMessage(words: Array[String])
}