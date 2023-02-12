package org.victor
package week3actors.minimal3

import akka.actor.typed.{Behavior, PostStop}
import akka.actor.typed.scaladsl.Behaviors

object WorkerActor {
  sealed trait Command
  final case class DoWork(message: String) extends Command

  def apply(name: String): Behavior[Command] = Behaviors
    .receive[Command]((context, message) => { // receive a type of Command, and pattern match the message
      message match {
        case DoWork(message) =>
          context.log.info("Worker {} received message {}", name, message)
          Behaviors.stopped // stop the actor when the work is done
      }
    }) // here we have a behavior chaining
    .receiveSignal {
      case (context, PostStop) =>
        context.log.info("Worker {} stopped", name)
        Behaviors.same
    }
}
