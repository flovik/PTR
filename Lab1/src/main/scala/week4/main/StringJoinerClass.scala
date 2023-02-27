package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, Props}

class StringJoinerClass extends Actor with ActorLogging {
  override def receive: Receive = ???
}

object StringJoiner {
  def props(): Props = Props(new StringJoinerClass())

  final case class JoinMessage(words: Array[String])
}