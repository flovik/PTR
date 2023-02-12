package org.victor
package week3actors.minimal1

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object MessagePrinterMain {

  final case class PrintAnyMessage(message: String)

  def apply(): Behavior[PrintAnyMessage] =
    Behaviors.setup { context =>
      // that actor is responsible for spawning other actors in the ActorContext
      val printer = context.spawn(MessagePrinter(), "printer")

      Behaviors.receiveMessage { message =>
        // forward the message from the mailbox to the MessagePrinter actor
        printer ! MessagePrinter.PrintMessage(message.message)
        Behaviors.same
      }
    }
}
