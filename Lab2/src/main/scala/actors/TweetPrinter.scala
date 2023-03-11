package actors

import actors.TweetPrinter.PrintTweet
import akka.actor.{Actor, ActorLogging, Props}
import io.circe._
import io.circe.parser._
import objects.{Message, Tweet, TweetResponse}

import scala.concurrent.duration.FiniteDuration
import scala.util.Random
class TweetPrinter(sleepTime: FiniteDuration) extends Actor with ActorLogging {
  log.info(s"TweetPrinter actor created with sleep time of $sleepTime")
  override def receive: Receive = {
    case PrintTweet(tweet) =>
      val mappedTweet = parse(tweet).getOrElse(Json.Null)
      val tweetParsed = mappedTweet.asObject.map { obj =>
        val tweet = obj.toMap.get("message").flatMap(_.asObject).flatMap(_.toMap.get("tweet")).flatMap(_.asObject).flatMap(_.toMap.get("text")).flatMap(_.asString)
        TweetResponse(Message(Tweet(tweet.getOrElse(""))))
      }.getOrElse(TweetResponse(Message(Tweet(""))))

      if (tweetParsed.message.tweet.text.contains("kill")) throw new Exception("Killed by tweet")
      log.info(s"${tweetParsed.message.tweet.text}")
      Thread.sleep(Random.between(30, 450) +  sleepTime.toMillis)
  }


}

object TweetPrinter {
  def props(sleepTime: FiniteDuration): Props = Props(new TweetPrinter(sleepTime))
  case class PrintTweet(tweet: String)
}