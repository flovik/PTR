package org.victor
package week4.minimal

import akka.actor.{Actor, ActorLogging, SupervisorStrategy, Terminated}
import akka.pattern.ask
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

class SupervisorClass extends Actor with ActorLogging {

  import SupervisorActor._

  override val supervisorStrategy: SupervisorStrategy =
    SupervisorStrategy.defaultStrategy
    
  private val router: Router = {
    Router(RoundRobinRoutingLogic(), routees)
  }
  private val workerCount = 3
  private var routees = IndexedSeq.fill(workerCount) {
    val name = workerName()
    val r = context.actorOf(EchoActorObject.props(name), name)
    context.watch(r)
    ActorRefRoutee(r)
  }

  println(routees)

  override def receive: Receive = {
    case SendMessage(message) =>
      log.info("Broadcasting message: {}", message)
      router.route(EchoActorObject.Echo(message), sender())
    case SendWorkers =>
      log.info("Sending workers")
      sender() ! routees
    case Terminated(ref) =>
      log.info("Worker {} terminated, restarting...", ref.path.name)

      val index = routees.indexOf(ref.actorRef)
      routees = routees.patch(index, Nil, 1)

      val name = workerName()
      val r = context.actorOf(EchoActorObject.props(name), name)
      context.watch(r)
      router.removeRoutee(ref)
      router.addRoutee(ActorRefRoutee(r))
  }

  private def workerName(): String = {
    s"worker-${java.util.UUID.randomUUID().toString}"
  }
}