package actors

import actors.TweetPrinter.PrintTweet
import akka.actor.{Actor, ActorLogging, Props}
import objects.{Message, Tweet, TweetResponse}
import io.circe._
import io.circe.parser._

import scala.concurrent.duration.FiniteDuration
class TweetPrinter(sleepTime: FiniteDuration) extends Actor with ActorLogging {
  override def receive: Receive = {
    case PrintTweet(tweet) =>
      val mappedTweet = parse(tweet).getOrElse(Json.Null)
      val tweetParsed = mappedTweet.asObject.map { obj =>
        val tweet = obj.toMap.get("message").flatMap(_.asObject).flatMap(_.toMap.get("tweet")).flatMap(_.asObject).flatMap(_.toMap.get("text")).flatMap(_.asString)
        TweetResponse(Message(Tweet(tweet.getOrElse(""))))
      }.getOrElse(TweetResponse(Message(Tweet(""))))

      log.info(s"${tweetParsed.message.tweet.text}")
      Thread.sleep(sleepTime.toMillis)
  }


}

object TweetPrinter {
  def props(sleepTime: FiniteDuration): Props = Props(new TweetPrinter(sleepTime))
  case class PrintTweet(tweet: String)
}