package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import org.victor.week4.main.StringLowercaserAndSwapper.LowercaseAndSwapMessage

class StringSplitterClass(nextActor: ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case StringSplitter.SplitMessage(message) =>
      if (message.contains('@')) {
        throw new IllegalArgumentException("Message is empty")
      }
      val words = message.split("\\s+")
      nextActor ! LowercaseAndSwapMessage(words)
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info("Restarting StringSplitterClass")
  }
}

object StringSplitter {
  def props(nextActor: ActorRef): Props = Props(new StringSplitterClass(nextActor))

  final case class SplitMessage(message: String)
}
