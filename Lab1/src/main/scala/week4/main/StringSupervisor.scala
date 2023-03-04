package org.victor
package week4.main

import akka.actor.{Actor, ActorLogging, ActorRef, AllForOneStrategy, Props, SupervisorStrategy, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

import scala.concurrent.duration.DurationInt

class StringSupervisor extends Actor with ActorLogging {
  override val supervisorStrategy: SupervisorStrategy =
    AllForOneStrategy(
      maxNrOfRetries = 5,
      withinTimeRange = 1.minute,
      loggingEnabled = true
    )(SupervisorStrategy.defaultDecider)

  private val router: Router = {
    Router(RoundRobinRoutingLogic(), routees)
  }
  private var routees = createRoutees()
  private var splitterRef: ActorRef = _

  override def receive: Receive = {
    case StringSupervisorObject.SendMessage(message) =>
      splitterRef ! StringSplitter.SplitMessage(message)
    case Terminated(ref) =>
      log.info("Worker {} terminated, restarting...", ref.path.name)

      val newRoutees = createRoutees()
      for (r <- routees) {
        router.removeRoutee(r)
      }

      for (r <- newRoutees) {
        router.addRoutee(r)
      }

      routees = newRoutees
  }

  private def createRoutees(): IndexedSeq[ActorRefRoutee] = {
    var result = IndexedSeq.empty[ActorRefRoutee]

    val printer = context.actorOf(StringPrinter.props(), workerName())
    val joiner = context.actorOf(StringJoiner.props(printer), workerName())
    val lowercaserAndSwapper = context.actorOf(StringLowercaserAndSwapper.props(joiner), workerName())
    val splitter = context.actorOf(StringSplitter.props(lowercaserAndSwapper), workerName())
    splitterRef = splitter

    context.watch(printer)
    context.watch(splitter)
    context.watch(lowercaserAndSwapper)
    context.watch(joiner)

    result = result :+ ActorRefRoutee(printer)
    result = result :+ ActorRefRoutee(splitter)
    result = result :+ ActorRefRoutee(lowercaserAndSwapper)
    result = result :+ ActorRefRoutee(joiner)

    result
  }

  private def workerName(): String = {
    s"worker-${java.util.UUID.randomUUID().toString}"
  }
}

object StringSupervisorObject {
  def props(): Props = Props(new StringSupervisor())

  case class SendMessage(message: String)
}