package org.victor
package week3actors.minimal3

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{Behavior, Terminated}

object SupervisorActor {
  sealed trait Command
  final case class StartActor(name: String, message: String) extends Command

  def apply(): Behavior[Command] = Behaviors.
    receive[Command] { (context, message) =>
      message match {
        case StartActor(name, message) =>
          context.log.info("Starting actor {}", name)
          val child = context.spawn(WorkerActor(name), name)
          context.watch(child)
          child ! WorkerActor.DoWork(message)
          Behaviors.same
      }
    }
    .receiveSignal({
      case (context, Terminated(ref)) =>
        context.log.info("Actor {} terminated", ref.path.name)
        Behaviors.same
    })
}
