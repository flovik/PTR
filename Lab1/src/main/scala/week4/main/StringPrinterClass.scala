package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, Props}

object StringPrinter {
  def props(): Props = Props(new StringPrinterClass())

  final case class PrintMessage(message: String)
}


class StringPrinterClass extends Actor with ActorLogging {
  override def receive: Receive = {
    case StringPrinter.PrintMessage(message) =>
      log.info(message)

  }
}