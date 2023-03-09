package objects

case class TweetResponse(message: Message)
case class Message(tweet: Tweet)

case class Tweet(
                text: String,
                // add more fields here
                )