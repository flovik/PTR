package org.victor
package week3actors.main2

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

object SemaphoreSupervisor {
  val replyTo: ActorRef[Semaphore.Response] = ActorSystem(SemaphoreSupervisor(), "semaphore")

  def new_semaphore(count: Int): ActorRef[Semaphore.semaphoreCommand] = {
    ActorSystem(Semaphore(count), "semaphore")
  }

  def acquire(semaphore: ActorRef[Semaphore.semaphoreCommand]): Unit = {
    semaphore ! Semaphore.Acquire(replyTo)
  }

  def release(semaphore: ActorRef[Semaphore.semaphoreCommand]): Unit = {
    semaphore ! Semaphore.Release(replyTo)
  }

  private def apply(): Behavior[Semaphore.Response] = Behaviors.setup { context =>
    Behaviors.receiveMessage[Semaphore.Response] {
      case Semaphore.Response(count) =>
        context.log.info(count.toString)
        context.log.info(context.self.path.toString)
        Behaviors.same
    }
  }
}
