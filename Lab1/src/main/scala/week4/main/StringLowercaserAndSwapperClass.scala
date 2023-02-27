package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, Props}

class StringLowercaserAndSwapperClass extends Actor with ActorLogging {
  override def receive: Receive = ???
}

object StringLowercaserAndSwapper {
  def props(): Props = Props(new StringLowercaserAndSwapperClass())

  final case class LowercaseAndSwapMessage(words: Array[String])
}