import actors.{StreamReader, TweetPrinterSupervisor, TweetRouter}
import akka.actor

object Main {
  def main(args: Array[String]): Unit = {
    val system = actor.ActorSystem("StreamReaderSystem")

    val tweets1 = "http://localhost:4000/tweets/1"
    val tweets2 = "http://localhost:4000/tweets/2"

    // val printerActor = system.actorOf(TweetPrinter.props(30.milliseconds), "TweetPrinter")

    val router = system.actorOf(TweetRouter.props, "TweetRouter")
    val supervisor = system.actorOf(TweetPrinterSupervisor.props(router), "TweetPrinterSupervisor")

    val streamReader = system.actorOf(StreamReader.props(tweets1, router), "StreamReader")
    val streamReader2 = system.actorOf(StreamReader.props(tweets2, router), "StreamReader2")
    streamReader ! StreamReader.Send
    streamReader2 ! StreamReader.Send
  }
}