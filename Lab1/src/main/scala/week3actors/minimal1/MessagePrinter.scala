package org.victor
package week3actors.minimal1

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

// actor
object MessagePrinter {
  // case class are immutable message signatures that can be shared between actors
  final case class PrintMessage(message: String)

  // apply is the factory method for the behavior
  // Behaviors.receive is a factory method for a behavior that can react to incoming messages
  def apply(): Behavior[PrintMessage] = Behaviors.receive { (context, message) =>
    context.log.info(message.message)
    Behaviors.same
  }
}
