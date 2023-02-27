package org.victor
package week4.minimal

import akka.actor.{Actor, ActorLogging}

class EchoActorClass(name: String) extends Actor with ActorLogging {

  import week4.minimal.EchoActorObject._

  override def receive: Receive = {
    case Echo(message) =>
      log.info("Worker {} received message: {}", name, message)
      sender() ! s"Worker $name echoed: $message"
    case Kill =>
      log.warning("Worker {} received Kill message and will be restarted", name)
      throw new Exception(s"Worker $name killed")
  }

  override def postStop(): Unit = {
    log.info("Worker {} stopped", name)
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info("Worker {} restarting", name)
  }
}
