# FAF.PTR16.1 -- Project 1 - Stream Processing with Actors
> **Performed by:** Victor Florescu, group FAF-201
> **Verified by:** asist. univ. Alexandru Osadcenco

### General Requirements

&nbsp;&nbsp;&nbsp;&nbsp; Compared to the previous Project, all weeks for this one aim to build upon the same application.
The goal is to finish the Project with a more or less functional stream processing system.
Since you will be working on a complex application, each presentation will now require you
present 2 diagrams: a Message Flow Diagram and a Supervision Tree Diagram. The Message
Flow Diagram describes the message exchange between actors of your system whereas the
Supervision Tree Diagram analyzes the monitor structures of your application.
Every task you work on should be easily verifiable. Your system should provide logs about
starting / stopping actors, auto-scaling / load balancing workers and printing processed tweets
on screen.

## P1W1

**Task 1** -- *Minimal Task* - Initialize a VCS repository for your project.

**Task 2** -- *Minimal Task* - Write an actor that would read SSE streams. The SSE streams for this lab
are available on [Docker Hub](https://hub.docker.com/r/alexburlacu/rtp-server/tags) at alexburlacu/rtp-server, courtesy of our beloved FAFer Alex
Burlacu.

# StreamReader
```scala
case Send =>
      val request: HttpRequest => Future[HttpResponse] = Http().singleRequest(_)

      val eventSource = EventSource(
        uri = uriAddress,
        send = request,
        initialLastEventId = None,
        retryDelay = 1.second
      )

      while (true) {
        val responseFuture = eventSource.throttle(1, 1.milliseconds, 1, ThrottleMode.Shaping)
          .take(10).runWith(Sink.seq)

        while (!responseFuture.isCompleted) {}
      }
```

&nbsp;&nbsp;&nbsp;&nbsp; Server-Sent Events (SSE) are a tool for sending data streams from a server to a client. The library I used is called Alpakka. 
To use it, import _akka-stream-alpakka-sse_, _akka-stream_ and _akka-http_. To set up and HTTP event stream to receive tweets from the specifief container, 
I send an empty request to the server to establish the connection and get back the tweets as a response. 

&nbsp;&nbsp;&nbsp;&nbsp; An event Source should be defined that will consume the empty request, using the provided send function and setting a delay of 1 second between events. 
Then a continuous loop should be run to consume the tweets while the application is running. In the loop, a **runnable graph** is defined which will 
consume the event source. The stream is throttled to consume 1 tweet per millisecond, limit the number of events to 10 and run the 
stream in a **Sink** that will collect the events in a Sequence.

**Task 3** -- *Minimal Task* - Create an actor that would print on the screen the tweets it receives from
the SSE Reader. You can only print the text of the tweet to save on screen space.

```scala
responseFuture.foreach(serverSentEvent => serverSentEvent.foreach(
          event => {
            val tweetData = event.getData()
            tweetPrinter ! TweetPrinter.PrintTweet(tweetData)
          }
        ))
```

# TweetPrinter
```scala
val mappedTweet = parse(tweet).getOrElse(Json.Null)
val tweetParsed = mappedTweet.asObject.map { obj =>
  val tweet = obj.toMap.get("message").flatMap(_.asObject).flatMap(_.toMap.get("tweet")).flatMap(_.asObject).flatMap(_.toMap.get("text")).flatMap(_.asString)
  TweetResponse(Message(Tweet(tweet.getOrElse(""))))
}.getOrElse(TweetResponse(Message(Tweet(""))))
```

# TweetClass
```scala
case class TweetResponse(message: Message)
case class Message(tweet: Tweet)

case class Tweet(
                text: String,
                )          
```

&nbsp;&nbsp;&nbsp;&nbsp; The Server Sent Events are processed further in the while loop by the [Stream Reader](#StreamReader) and sent to the [TweetPrinter](#TweetPrinter).
Tweet printer parses the tweet and maps its properties to a [Tweet class](#TweetClass) and then prints the text from the tweet.

**Task 4** -- *Main Task* - Create a second Reader actor that will consume the second stream provided by
the Docker image. Send the tweets to the same Printer actor.

```scala
    val tweets1 = "http://localhost:4000/tweets/1"
    val tweets2 = "http://localhost:4000/tweets/2"
    val streamReader = system.actorOf(StreamReader.props(tweets1, router), "StreamReader")
    val streamReader2 = system.actorOf(StreamReader.props(tweets2, router), "StreamReader2")
    streamReader ! StreamReader.Send
    streamReader2 ! StreamReader.Send        
```

&nbsp;&nbsp;&nbsp;&nbsp; We have two different requests and I pass each request a different StreamReader to process each stream independently.

**Task 5** -- *Main Task* - Continue your Printer actor. Simulate some load on the actor by sleeping every
time a tweet is received. Suggested time of sleep – 5ms to 50ms. Consider using Poisson
distribution. Sleep values / distribution parameters need to be parameterizable

```scala
Thread.sleep(Random.between(10, 50) +  sleepTime.toMillis)
```

&nbsp;&nbsp;&nbsp;&nbsp; To simulate some load I put the Thread on sleep a random value between 10 to 50 and add the parametrizable value to the sleep value. 

### Diagrams

## Message flow diagram

![week1_message_flow_diagram](https://user-images.githubusercontent.com/57410984/224481769-99e194fc-fcf9-4fb1-b272-a661e2eda5b5.png)

## Supervision Tree diagram

![week1_tree_supervision](https://user-images.githubusercontent.com/57410984/224481775-b7c2158d-f579-4b04-a1ca-6882303ac80c.png)

## P1W2

**Task 1** -- *Minimal Task* - Create a Worker Pool to substitute the Printer actor from previous week. The
pool will contain 3 copies of the Printer actor which will be supervised by a Pool Supervisor.
Use the one-for-one restart policy.

# WorkerPool
```scala
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

```

&nbsp;&nbsp;&nbsp;&nbsp; Nothing new here, a [Supervisor](#WorkerPool) is created with a default supervision strategy (which is one-for-one in scala akka). Static number of 3 
workers are created. 

**Task 2** -- *Minimal Task* - Create an actor that would mediate the tasks being sent to the Worker Pool.
Any tweet that this actor receives will be sent to the Worker Pool in a Round Robin fashion.
Direct the Reader actor to sent it’s tweets to this actor.

# TweetRouter
```scala
 private var router: Router = {
    Router(RoundRobinRoutingLogic())
  }

  override def receive: Receive = {
    case PrintTweet(tweet) =>
      router.route(TweetPrinter.PrintTweet(tweet), sender())
    case ReceiveRoutees(routees) =>
      router = Router(RoundRobinRoutingLogic(), routees)
    case RemoveRoutee(routee) =>
      router.removeRoutee(routee)
    case AddRoutee(routee) => 
      context.watch(routee.ref)
      router.addRoutee(routee)
  }
```

```scala
tweetRouter ! TweetRouter.PrintTweet(tweetData)
```

&nbsp;&nbsp;&nbsp;&nbsp; Here also nothing new, we implemented the Router with Round Robin logic in lab1. The idea of Round Robin logic is to pass messages to every actor one by one, so the load of messages 
of each actor is the same. 

**Task 3** -- *Main Task* - Continue your Worker actor. Occasionally, the SSE events will contain a “kill
message”. Change the actor to crash when such a message is received. Of course, this should
trigger the supervisor to restart the crashed actor.

```scala
if (tweetParsed.message.tweet.text.contains("kill")) throw new Exception("Killed by tweet")
```

&nbsp;&nbsp;&nbsp;&nbsp; The exception will kill the actor and make the supervisor restart it. 

**Task 4** -- *Bonus Task* - Continue your Load Balancer actor. Modify the actor to implement the “Least
connected” algorithm for load balancing (or other interesting algorithm). Refer to [this article](https://blog.envoyproxy.io/examining-load-balancing-algorithms-with-envoy-1be643ea121c)
by Tony Allen.

```scala
  private var router: Router = {
    Router(SmallestMailboxRoutingLogic())
  }
```

&nbsp;&nbsp;&nbsp;&nbsp; I changed the logic of the router to work in the "Least connected" manner. It has been already implemented in the scala akka.
It tries to send a message to the actor with fewest messages in mailbox. 

### Diagrams

## Message flow diagram

![week2_message_flow_diagram](https://user-images.githubusercontent.com/57410984/224481890-b50081e5-80dd-4739-b9f5-4442236f2967.png)

## Supervision Tree diagram

![week2_supervision_tree](https://user-images.githubusercontent.com/57410984/224481895-5e5d40de-f15f-4bc8-bcf0-44f5d81379bf.png)

## Conclusion

In the first week, I learnt how to send requests to a docker container and receive Server Sent Events and handle them.

In the second week, I learnt how to change Routing logic of the Router to manage the load balacing of the system. 

## Bibliography

https://www.baeldung.com/scala/alpakka-server-sent-events

https://doc.akka.io/docs/akka/current/stream/stream-introduction.html#motivation


