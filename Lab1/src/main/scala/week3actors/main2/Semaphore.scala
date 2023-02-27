package org.victor
package week3actors.main2

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object Semaphore {
  sealed trait semaphoreCommand

  final case class Acquire(replyTo: ActorRef[Response]) extends semaphoreCommand

  final case class Release(replyTo: ActorRef[Response]) extends semaphoreCommand

  final case class Response(initialCount: Int)

  def apply(initialCount: Int, maximumValue: Int = 3): Behavior[semaphoreCommand] = Behaviors.receive { (context, message) =>
    message match {
      case Acquire(replyTo) =>
        if (initialCount > 0) {
          val newCount = initialCount - 1
          context.log.info("Semaphore is still available");
          context.log.info(context.self.path.toString)
          replyTo ! Response(newCount)
          Semaphore(newCount)
        } else {
          context.log.info("Semaphore is empty");
          Behaviors.same
        }
      case Release(replyTo) =>
        if (initialCount < maximumValue) {
          val newCount = initialCount + 1
          replyTo ! Response(newCount)
          Semaphore(newCount)
        } else {
          Behaviors.same
        }
    }
  }
}
