package actors

import actors.TweetRouter.{AddRoutee, PrintTweet, ReceiveRoutees, RemoveRoutee}
import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router, SmallestMailboxRoutingLogic}

class TweetRouter extends Actor with ActorLogging {
  private var router: Router = {
    Router(SmallestMailboxRoutingLogic())
    // tries to an actor with fewest messages in mailbox
  }

  override def receive: Receive = {
    case PrintTweet(tweet) =>
      router.route(TweetPrinter.PrintTweet(tweet), sender())
    case ReceiveRoutees(routees) =>
      log.info("Received routees")
      router = Router(RoundRobinRoutingLogic(), routees)
    case RemoveRoutee(routee) =>
      log.info("Killed routee")
      router.removeRoutee(routee)
    case AddRoutee(routee) =>
      log.info("Added routee")
      context.watch(routee.ref)
      router.addRoutee(routee)
  }
}

object TweetRouter {
  def props(): Props = Props(new TweetRouter())
  case class PrintTweet(tweet: String)
  case class ReceiveRoutees(routees: IndexedSeq[ActorRefRoutee])
  case class RemoveRoutee(routee: ActorRefRoutee)
  case class AddRoutee(routee: ActorRefRoutee)
}