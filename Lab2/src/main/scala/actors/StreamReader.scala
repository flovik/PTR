package actors

import actors.StreamReader.Send
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ThrottleMode
import akka.stream.alpakka.sse.scaladsl.EventSource
import akka.stream.scaladsl.Sink

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class StreamReader(uriAddress: String, printer: ActorRef) extends Actor with ActorLogging {
  implicit val system = context.system
  implicit val dispatcher = context.dispatcher
  implicit val requestTimeout = akka.util.Timeout(5.second)

  private val tweetPrinter = printer
  override def receive: Receive = {
    case Send =>
      // set up an HTTP event stream (using Server-Sent Events) to receive tweets
      // from the specified container

      // send an empty request to the server to establish the connection
      // and get back the tweets as response
      val request: HttpRequest => Future[HttpResponse] = Http().singleRequest(_)

      // define a Source that will consume from the given URI, using the provided send function
      // delay of 1 second between events
      val eventSource = EventSource(
        uri = uriAddress,
        send = request,
        initialLastEventId = None,
        retryDelay = 1.second
      )

      // run a continuous loop to receive tweets
      while (true) {
        // when the response is received, parse the response data as a ServerSentEvent, which contains
        // the tweet data and metadata

        // runnable graph that will consume the event source
        // throttle the stream to 1 tweet per millisecond
        // limit the number of events to 10
        // run the stream and collect the events in a Seq
        val responseFuture = eventSource.throttle(1, 1.milliseconds, 1, ThrottleMode.Shaping)
          .take(10).runWith(Sink.seq)

        responseFuture.foreach(serverSentEvent => serverSentEvent.foreach(
          event => {
            val tweetData = event.getData()
            tweetPrinter ! TweetPrinter.PrintTweet(tweetData)
          }
        ))

        while (!responseFuture.isCompleted) {}
      }
  }
}

object StreamReader {
  def props(uriAddress: String, printer: ActorRef): Props = Props(new StreamReader(uriAddress, printer))
  case object Send
}

// https://www.baeldung.com/scala/alpakka-server-sent-events

// https://doc.akka.io/docs/akka/current/stream/stream-introduction.html#motivation