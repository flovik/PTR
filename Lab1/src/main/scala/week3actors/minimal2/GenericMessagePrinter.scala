package org.victor
package week3actors.minimal2

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object GenericMessagePrinter {
  def apply(): Behavior[Any] = Behaviors.setup { context =>
    Behaviors.receiveMessage {
      case message: Int =>
        val res = message + 1
        context.log.info(s"Received: $res")
        Behaviors.same
      case message: String =>
        val res = message.toLowerCase()
        context.log.info(s"Received: $res")
        Behaviors.same
      case _ =>
        context.log.info {
          "Received : I don ’ t know how to HANDLE this !"
        }
        Behaviors.same
    }
  }
}
