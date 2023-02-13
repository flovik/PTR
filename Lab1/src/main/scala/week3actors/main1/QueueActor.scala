package org.victor
package week3actors.main1


import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

import scala.collection.mutable.ListBuffer

object QueueActor {
  sealed trait queueCommand
  sealed trait queueResponse
  final case class Enqueue(x: Int, replyTo: ActorRef[Message]) extends queueCommand
  final case class Dequeue(replyTo: ActorRef[DequeueResult]) extends queueCommand
  final case class Message(message: String) extends queueResponse
  final case class DequeueResult(num: Option[Int]) extends queueResponse

  def apply(queue: ListBuffer[Int]): Behavior[queueCommand] = Behaviors.receive { (context, message) =>
    message match {
      case Enqueue(x, replyTo) =>
        queue.append(x)
        replyTo ! Message("ok")
        QueueActor(queue) // return current queue with the updated values
      case Dequeue(replyTo) =>
        val result = if (queue.nonEmpty) {
          val head = queue.head
          val newQueue = queue.tail
          replyTo ! DequeueResult(Some(head))
          newQueue
        } else {
          replyTo ! DequeueResult(None)
          queue
        }
        QueueActor(result)
    }
  }
}
