package actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, SupervisorStrategy, Terminated}
import akka.pattern.ask
import akka.routing.ActorRefRoutee

import scala.concurrent.duration.DurationInt

class TweetPrinterSupervisor(router: ActorRef) extends Actor with ActorLogging {
  override val supervisorStrategy: SupervisorStrategy =
    SupervisorStrategy.defaultStrategy

  private val sleepTime = 30.milliseconds
  private val workerCount = 3
  private var routees = IndexedSeq.fill(workerCount) {
    val name = workerName()
    val r = context.actorOf(TweetPrinter.props(sleepTime), name)
    context.watch(r)
    ActorRefRoutee(r)
  }

  router ! TweetRouter.ReceiveRoutees(routees)

  override def receive: Receive = {
    case Terminated(ref) =>
      log.info("Worker {} terminated, restarting...", ref.path.name)

      val deadTweetPrinter = ref.actorRef
      val index = routees.indexOf(deadTweetPrinter)
      routees = routees.patch(index, Nil, 1)

      val name = workerName()
      val newTweetPrinter = context.actorOf(TweetPrinter.props(sleepTime), name)
      context.watch(newTweetPrinter)
      router ! TweetRouter.RemoveRoutee(ActorRefRoutee(deadTweetPrinter))
      router ! TweetRouter.AddRoutee(ActorRefRoutee(newTweetPrinter))
  }

  private def workerName(): String = {
    s"tweetPrinter-${java.util.UUID.randomUUID().toString}"
  }
}

object TweetPrinterSupervisor {
  def props(router: ActorRef): Props = Props(new TweetPrinterSupervisor(router))
}