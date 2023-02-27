package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, Props}

class StringSplitterClass extends Actor with ActorLogging {
  override def receive: Receive = ???
}

object StringSplitter {
  def props(): Props = Props(new StringSplitterClass())

  final case class SplitMessage(message: String)
}
