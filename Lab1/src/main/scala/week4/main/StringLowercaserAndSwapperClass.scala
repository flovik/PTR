package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import org.victor.week4.main.StringJoiner.JoinMessage

class StringLowercaserAndSwapperClass(nextActor: ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case StringLowercaserAndSwapper.LowercaseAndSwapMessage(words) => {
      val lowercasedWords = words.map(_.toLowerCase)
      val swappedMsWithNs = lowercasedWords.map(str => str.map {
        case 'm' => 'n'
        case 'n' => 'm'
        case c => c
      })

      nextActor ! JoinMessage(swappedMsWithNs)
    }
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info("StringLowercaserAndSwapper restarted")
  }
}

object StringLowercaserAndSwapper {
  def props(nextActor: ActorRef): Props = Props(new StringLowercaserAndSwapperClass(nextActor))

  final case class LowercaseAndSwapMessage(words: Array[String])
}