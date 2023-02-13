package org.victor
package week3actors.main1

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

import scala.collection.mutable.ListBuffer

object QueueSupervisor {
  val replyTo: ActorRef[QueueActor.queueResponse] = ActorSystem(QueueSupervisor(), "queue")

  def new_queue(): ActorRef[QueueActor.queueCommand] = {
    val queue = ListBuffer.empty[Int]
    ActorSystem(QueueActor(queue), "queue")
  }
  def push(queue: ActorRef[QueueActor.queueCommand], x: Int): Unit = {
    queue ! QueueActor.Enqueue(x, replyTo)
  }

  def pop(queue: ActorRef[QueueActor.queueCommand]): Unit = {
    queue ! QueueActor.Dequeue(replyTo)
  }

  private def apply(): Behavior[QueueActor.queueResponse] = Behaviors.setup { context =>
    Behaviors.receiveMessage[QueueActor.queueResponse] {
      case QueueActor.Message(message) =>
        context.log.info(message)
        Behaviors.same
      case QueueActor.DequeueResult(num) =>
        num match {
          case Some(number) => context.log.info(number.toString)
          case None => context.log.info("Queue is empty.")
        }
        Behaviors.same
    }
  }
}
