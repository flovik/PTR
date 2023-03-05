# FAF.PTR16.1 -- Project 0
> **Performed by:** Victor Florescu, group FAF-201
> **Verified by:** asist. univ. Alexandru Osadcenco

## P0W1 - Welcome...

**Task 1** -- *Minimal Task* - Follow an installation guide to install the language / development environment of your choice.

**Task 2** -- *Minimal Task* - Write a script that would print the message “Hello PTR” on the screen.
Execute it.


```scala
  def helloPtr(): String = {
    "Hello PTR!"
  }
```

A simple function that returns a string "Hello PTR".

**Task 3** -- *Main Task* - Initialize a VCS repository for your project. Push your project to a remote repo.

**Task 4** -- *Bonus Task* - Write a comprehensive readme for your repository.

**Task 5** -- *Bonus Task* - Create a unit test for your project. Execute it.

```scala
class HelloWorderTests extends AnyFunSuite {
  test("HelloWorder.helloPtr") {
    val helloWorder = new HelloWorder;
    assert(helloWorder.helloPtr() == "Hello PTR!")
  }
}
```

That unit tests checks if the class from Task2 returns "Hello PTR!". Test passes.

## P0W2 - ...to the rice fields

**Task 1** -- *Minimal Task* - Write a function that determines whether an input integer is prime.

```scala
  def IsPrime(n: Int): Boolean = {
    for (i <- 2 until sqrt(n).toInt) {
      if (n % i == 0) return false
    }

    return true;
  }
```

Here we iterate all the numbers from 2 to $\sqrt{n}$ and check if any divide to *n*.  

**Task 2** -- *Minimal Task* - Write a function to calculate the area of a cylinder, given it’s height and radius.

```scala
  def cylinderArea(h: Int, r: Int): Double = {
    2 * math.Pi * r * h + 2 * math.Pi * r * r
  }
```

The formula of cylinder area: $A = 2 \pi r h + 2 \pi r^2$

**Task 3** -- *Minimal Task* - Write a function to reverse a list.

```scala
   def reverse(list: List[Int]): List[Int] = {
     list.reverse
  }
```

I used built in function *reverse* to do the job. 

**Task 4** -- *Minimal Task* - Write a function to calculate the sum of unique elements in a list.

```scala
   def uniqueSum(list: List[Int]): Int = {
    list.distinct.sum
  }
```

I used built in function *distinct* and applied *sum* to it to do the job. 

**Task 5** -- *Minimal Task* - Write a function that extracts a given number of randomly selected elements from a list.

```scala
    def extractRandomN(list: ListBuffer[Int], n: Int): List[Int] = {
      // ListBuffer is mutable, so we can remove elements from it
      var r = list.length - n;
      while (r > 0) {
        val randomIndex = scala.util.Random.nextInt(list.length)
        val randomElement = list(randomIndex)
        list -= randomElement
        r = r - 1
      }
  
      list.toList
    }
```

Here I used scala.random library to take a random index from the list, afterwards the picked element is added to the result list and removed from the original list. 

**Task 6** -- *Minimal Task* - Write a function that returns the first n elements of the Fibonacci sequence

```scala
  def firstFibonacciElements(n: Int): List[Int] = {
    var a = 1
    var b = 1
    var c = 0
    var list = List(a, b)
    for (_ <- 1 until n - 1) {
      c = a + b
      a = b
      b = c
      list = list :+ c
    }
    list
  }
```

I use three different variables to keep track of the memoized values and get new fibonacci values by adding together a and b. 

**Task 7** -- *Minimal Task* - Write a function that, given a dictionary, would translate a sentence. Words not found in the dictionary need not be translated.

```scala
  def translator(map: Map[String, String], sentence: String): String = {
    var result = ""
    val words = sentence.split(" ")
    for (word <- words) {
      if (map.contains(word)) result += map(word) + " "
      else result += word + " "
    }
    if (result.length > 0) result = result.dropRight(1)
    result
  }
```

So I split the sentence into words by removing whitespaces, iterate each word and check if it is present in the dictionary, if that's true I apply the 
change from the dictioanry, if not I simply add the original word. In the end I drop a whitespace added from iteration. 

**Task 8** -- *Minimal Task* - Write a function that receives as input three digits and arranges them in an 
order that would create the smallest possible number. Numbers cannot start with a 0.

```scala
  def smallestNumber(a: Int, b: Int, c: Int): Int = {
    var d: StringBuilder = new StringBuilder;
    if (a >= b && a >= c) {
      if (b >= c && c == 0) {
        d.append(b.toString)
        d.append(c.toString)
      }
      else {
        d.append(c.toString)
        d.append(b.toString)
      }
      d.append(a.toString)
    }
    if (b >= a && b >= c)
    ...

    var ret = d.toString()
    // case with 2 zeros
    if (ret(0) == '0' && ret(1) == '0') {
      ret = ret.reverse
    }

    Integer.parseInt(ret)
  }
```

Not a very clean solution in my honest opinion. Basically I compare every each number with each other and put numbers in their corresponding place 
to get the smallest number. In the end I check the case with two zeros. I ommited a chunk of code so the code snippet doesn't look bloated. 

**Task9** -- *Minimal Task* - Write a function that would rotate a list n places to the left.

```scala
  def rotateLeft(list: List[Int], n: Int): List[Int] = {
    var myList = list
    var m = 0
    if (n > list.size) m = n % list.size
    m = list.size - n;

    //rotate the whole array
    myList = myList.reverse
    
    //rotate the first m elements and rotate the elements after m
    myList = myList.slice(0, m).reverse ++ myList.slice(m, myList.size).reverse
    
    myList
  }
```

I need to lower n in case it is bigger than the actual size of the array. The logic is to reverse the whole array, and then rotate the first m (list.size - n) and 
then the the elements after m. 

**Task10** -- *Minimal Task* - Write a function that lists all tuples a, b, c such that $a^2 + b^2 = c^2, \quad \text{where } a,b \le 20$

```scala
  def listRightAngleTriangles(): List[(Int, Int, Int)] = {
    val n = 20
    var list = List[(Int, Int, Int)]()
    for (a <- 1 to n) {
      for (b <- 1 to n) {
        for (c <- 1 to n) {
          if (a * a + b * b == c * c) {
            list = list :+ (a, b, c)
          }
        }
      }
    }
    list
  }
```

Nothing special, just take every a, b and c of the equation and check the formula $a^2 + b^2 = c^2$. 

**Task11** -- *Main Task* - Write a function that eliminates consecutive duplicates in a list.

```scala
  def removeConsecutiveDuplicates(list: List[Integer]): List[Integer] = {
    var newList = ListBuffer[Integer]()
    var current = Integer.MIN_VALUE;
    for (i <- 0 until list.length) {
      if (list(i) != current) {
        newList += list(i)
        current = list(i)
      }
    }
    newList.toList
  }
```

If a new occurence appears, add it to the resulting array and set it to current. If further the same values appear as the current, ignore them. 

**Task12** -- *Main Task* - Write a function that, given an array of strings, will return the words that can
be typed using only one row of the letters on an English keyboard layout.

```scala
  private val line1keyboard = "qwertyuiopQWERTYUIOP"
  private val line2keyboard = "asdfghjklASDFGHJKL"
  private val line3keyboard = "zxcvbnmZXCVBNM"
  def lineWords(words: List[String]): List[String] = {
    var result = ListBuffer[String]()

    for (word <- words) {
      var line1 = false
      var line2 = false
      var line3 = false
      for (letter <- word) {
        if (line1keyboard.contains(letter)) line1 = true
        if (line2keyboard.contains(letter)) line2 = true
        if (line3keyboard.contains(letter)) line3 = true
      }
      if (!((line1 && line2) || (line1 && line3) || (line2 && line3))) result += word
    }

    result.toList
  }
```

I have predefined values of the keyboard rows, I iterate each word and check each letter in what row belongs to. If two or more rows are true, the word is skipped. 

**Task13** -- *Main Task* - Create a pair of functions to encode and decode strings using the Caesar cipher

```scala
  private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
  def encrypt(text: String, key: Int): String = {
    var result = ""
    for (letter <- text) {
      if(IsInAlphabet(letter)) {
        var index = alphabet.indexOf(letter)
        index = (index + key) % alphabet.length
        if (index < 0) index = alphabet.length + index
        result += alphabet(index)
      }
      else result += letter //whitespaces, commas, etc.

    }
    result
  }

  def decrypt(text: String, key: Int): String = {
    encrypt(text, -key)
  }

  def IsInAlphabet(letter: Char): Boolean = {
    alphabet.contains(letter)
  }
```

The encrypt method iterates through each letter of the text and checks if the letter is present in the alphabet. If the letter is present, 
it calculates the index of the letter in the alphabet and then adds the key value to it. The result is then added to the encrypted text string. 
If the letter is not present in the alphabet (e.g. comma, whitespace) it is added without modification. 

The decrypt method simply calls the encrypt method with the negative key value to get the decrypted text.

The IsInAlphabet method takes a character as input and returns a Boolean value indicating 
if the character is present in the alphabet or not. 

**Task14** -- *Main Task* - White a function that, given a string of digits from 2 to 9, would return all
possible letter combinations that the number could represent (think phones with buttons).

```scala
  private val map = Map(
    '2' -> "abc",
    '3' -> "def",
    '4' -> "ghi",
    '5' -> "jkl",
    '6' -> "mno",
    '7' -> "pqrs",
    '8' -> "tuv",
    '9' -> "wxyz"
  )
  var result = ListBuffer[String]()
  def letterCombinations(digits: String): List[String] = {
    if (digits.length == 0) return List()
    appendLetters("", digits)
    result.toList
  }

  private def appendLetters(currentCombination: String, digits: String): Unit = {
    //if no more letters to add, add to the end result
    if (digits.length == 0) {
      result += currentCombination
      return
    }

    //for each letter in current letter, add its letter to the final combination and remove the letter
    val digit = digits(0)
    val letters = map(digit)
    for (letter <- letters) {
      appendLetters(currentCombination + letter, digits.drop(1))
    }
  }
```
I have a map of predefined letter combinations. The method appendLetters is a recursive function, it's base case is if the digits string is empty and adds 
the last combination to the result array. Next if the base condition is not met, we take one digit, take the letters of that digit in the map and for each letter, 
call the appendLetters function with the current letter and by dropping the first digit from the digits string. 

**Task15** -- *Main Task* - White a function that, given an array of strings, would group the anagrams
together.

```scala
  private var groups = Map[String, ListBuffer[String]]()

  def groupAnagrams(words: List[String]): Map[String, ListBuffer[String]] = {
    for (word <- words) {
      val unsortedWord = word.toCharArray
      val sortedWord = unsortedWord.sorted.mkString
      if (groups.contains(sortedWord)) {
        var a = groups.get(sortedWord)
        groups.update(sortedWord, a.get += word)
      }
      else {
        groups += (sortedWord -> ListBuffer(word))
      }
    }

    groups
  }
```
I have a map which will keep all the anagrams in one place. I iterate every word, sort it and check if the sorted word is in the map. If it is not, I add it 
(key is the **sorted** word, and a new list with the original word), if it is present, then it is an anagram and added to the corresponding list. 

**Task16** -- *Bonus Task* - Write a function to find the longest common prefix string amongst a list of
strings.

```scala
  def commonPrefix(list: List[String]): String = {
    var result = new StringBuilder("")
    if (list.length == 0) return result.toString()
    if (list.length == 1) return list(0)
    result.append(list(0))

    for (i <- 1 until list.length) {
      val currentWord = list(i)

      breakable {
        for (j <- 0 until result.length) {
          if (j >= result.length) break
          if (j >= currentWord.length) {
            result.delete(j, result.length)
            break
          }

          if (currentWord.charAt(j) != result.charAt(j)) {
            result.delete(j, result.length)
            break
          }
        }
      }
    }

    result.toString()
  }
```

Here I have some base cases at the start, if the list is empty or if it has only one element. If not, I add the first element to the list and iterate the rest of words. 
In the for loop, I check if result string is exhausted and currentWord is longer than it, so we can break from the loop. Another case is if currentWord is shorter than
the result string, here we need to delete some characters to match the currentWord. Last if statement which checks if current character of the current Word matches the 
result string characters, when a character doesn't match, the result substring is deleted. 

**Task17** -- *Bonus Task* - Write a function to convert arabic numbers to roman numerals

```scala
  private val numsToRomans = Map(
    1 -> "I",
    4 -> "IV",
    5 -> "V",
    9 -> "IX",
    10 -> "X",
    40 -> "XL",
    50 -> "L",
    90 -> "XC",
    100 -> "C",
    400 -> "CD",
    500 -> "D",
    900 -> "CM",
    1000 -> "M"
  )

  private val numbers = List(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)

  def toRoman(n: String): String = {
    val result = new StringBuilder("")
    var number = n.toInt
    for (num <- numbers) {
      while (num <= number) {
        result.append(numsToRomans(num))
        number -= num
      }
    }

    result.toString()
  }
```

I have predefined map of all possible combinations of integers to roman characters that can be. The list with roman characters as **integers** is iterated so we get 
every single possibility of the resulting roman number. If current roman number is lower than n, append the current roman number to the resulting string. 

## P0W2 - An Actor is Born

**Task1** -- *Minimal Task* - Create an actor that prints on the screen any message it receives.

```scala
object MessagePrinter {
  final case class PrintMessage(message: String)

  def apply(): Behavior[PrintMessage] = Behaviors.receive { (context, message) =>
    context.log.info(message.message)
    Behaviors.same
  }
} 

object MessagePrinterMain {
  final case class PrintAnyMessage(message: String)

  def apply(): Behavior[PrintAnyMessage] =
    Behaviors.setup { context =>
      val printer = context.spawn(MessagePrinter(), "printer")

      Behaviors.receiveMessage { message =>
        printer ! MessagePrinter.PrintMessage(message.message)
        Behaviors.same
      }
    }
} 
```

The first minimal task of week 3. Here I have an actor MessagePrinter that has a behavior to print messages. The apply() method is a factory method that creates an instance of the 'Behavior' MessagePrinter. In my case, PrintMessage actor reacts only to behaviors of type 'PrintMessage'. The actor handles the incoming message using Behaviors.receive method, the function takes an incoming message and logs it. Then returns Behaviors.same, which means the internal state of the actor hadn't changed.

Another class MessagePrinterMain has another message signature PrintAnyMessage and accepts only messages of that type. The reason behind that actor is to create the MessagePrinter actor and forward the message to it.

**Task2** -- *Minimal Task* - Create an actor that returns any message it receives, while modifying it.

```scala
object GenericMessagePrinter {
  def apply(): Behavior[Any] = Behaviors.setup { context =>
    Behaviors.receiveMessage {
      case message: Int =>
        val res = message + 1
        context.log.info(s"Received: $res")
        Behaviors.same
      case message: String =>
        val res = message.toLowerCase()
        context.log.info(s"Received: $res")
        Behaviors.same
      case _ =>
        context.log.info {
          "Received : I don ’ t know how to HANDLE this !"
        }
        Behaviors.same
    }
  }
}
```

The actor 'GenericMessagePrinter' takes as input message Any data type, which means Any is the root of the Scala class hierarchy and takes any data type. Here I process the message using pattern matching and check of which type is the message. Int and String are only handled accordingly, other data types can't be handled.

**Task3** -- *Minimal Task* - Create a two actors, actor one ”monitoring” the other. If the second actor stops, actor one gets notified via a message

Child Actor
```scala
  def apply(name: String): Behavior[Command] = Behaviors
    .receive[Command]((context, message) => {
      message match {
        case DoWork(message) =>
          context.log.info("Worker {} received message {}", name, message)
          Behaviors.stopped
      }
    }
```

Monitoring actor
```scala
case StartActor(name, message) =>
  context.log.info("Starting actor {}", name)
  val child = context.spawn(WorkerActor(name), name)
  context.watch(child)
  child ! WorkerActor.DoWork(message)
  Behaviors.same 
}
```

The actor WorkerActor receives messages of type *Command* and prints any input it gets. Then actor is stopped by the *Behavior.stopped* which is then handled by the 
*.receiveSignal* which informs that actor has been stopped. The actor SupervisorActor takes messages of type *Command*. When the actor receives a message, it spawn the WorkerActor and watches when the WorkerActor, the SupervisorActor will receive a notification when the WorkerActor is Terminated. That notification is handled with the *.receiveSignal* and logs the name of the Worker that was stopped. 

**Task4** -- *Minimal Task* - Create an actor which receives numbers and with each request prints out the current average.

Averager
```scala
val average = (numbers.sum + number) / 2
context.log.info(s"Current average is $average")
numbers.update(0, average)
```

AveragerSupervisor
```scala
val averageCalculator = context.spawn(averager(ListBuffer(0)), "averageCalculator")
context.watch(averageCalculator)
averageCalculator ! averager.CalculateAverage(0)

Behaviors
  .receiveMessage({ message =>
    averageCalculator ! averager.CalculateAverage(message)
    Behaviors.same
  })
```

That task introduces the concept of internal state of an actor. The variables defined inside the apply method are part of the internal state of an actor, in my case the numbers ListBuffer is a variable from the internal state of the average actor. When a message is received by an actor and processed by the apply method, the actor can update its internal state by modifying these variables, and here I calculate the average of the two numbers and update the list with the new average. The updated state will be available for the next message processing. This allows actors to maintain state between message processing, making it possible to implement stateful behavior. The object foo is an actor that creates averager actors and forwards the incoming messages to them. 

**Task5** -- *Main Task* - Create an actor which maintains a simple FIFO queue. You should write helper functions to create an API for the user, which hides how the queue is implemented.

Queue
```scala
case Enqueue(x, replyTo) =>
  queue.append(x)
  replyTo ! Message("ok")
  QueueActor(queue)
case Dequeue(replyTo) =>
  val result = if (queue.nonEmpty) {
  val head = queue.head
  val newQueue = queue.tail
  replyTo ! DequeueResult(Some(head))
  newQueue
  } else {
    replyTo ! DequeueResult(None)
    queue
  }
  QueueActor(result)

```

Queue supervisor
```scala
  val replyTo: ActorRef[QueueActor.queueResponse] = ActorSystem(QueueSupervisor(), "queue")

  def new_queue(): ActorRef[QueueActor.queueCommand] = {
    val queue = ListBuffer.empty[Int]
    ActorSystem(QueueActor(queue), "queue")
  }
  def push(queue: ActorRef[QueueActor.queueCommand], x: Int): Unit = {
    queue ! QueueActor.Enqueue(x, replyTo)
  }

  def pop(queue: ActorRef[QueueActor.queueCommand]): Unit = {
    queue ! QueueActor.Dequeue(replyTo)
  }
```

Here I had to implement a simple FIFO queue and to create an API to hide how the queue is implemented. I used a listBuffer to implement the internal queue of the actor. When a queueCommand is received, I apply pattern matching to see what message I received -> of Enqueuing or Dequeuing. In the Enqueue message I get the value that should be enqueued and reference to the Actor the queueActor should reply to. The value is added, a success message is transmitted and I return the current queue with the updated value as a Behavior response. The Dequeue message takes as input the receiver of the DequeueResult which contains the optional dequeued value. I check if the queue is empty, if it is I return None, if it is not the first value is returned and the queue is updated without the dequeued value. 

QueueSupervisor is the API between the user and the Queue actor. I defined three methods: new_queue() that return the ActorRef of the QueueActor, push(queue, x) which sends a message to enqueue some value and pop(queue) to dequeue a value. I have a reference for the current QueueSupervisor so he can get messages back from QueueActor. QueueSupervisor receives only queueResponse messages from the QueueActor, and by doing pattern matching of the incoming message, I log the information if the values was pushed or popped successfully. 

**Task6** -- *Main Task* - Create a module that would implement a semaphore

Sempahore
```scala
case Acquire(replyTo) =>
  if (initialCount > 0) {
    val newCount = initialCount - 1
    replyTo ! Response(newCount)
    Semaphore(newCount)
  } else {
    Behaviors.same
  }
case Release(replyTo) =>
  if (initialCount < maximumValue) {
    val newCount = initialCount + 1
    replyTo ! Response(newCount)
    Semaphore(newCount)
  } else {
    Behaviors.same
  }
```

Sempahore supervisor
```scala
  val replyTo: ActorRef[Semaphore.Response] = ActorSystem(SemaphoreSupervisor(), "semaphore")

  def new_semaphore(count: Int): ActorRef[Semaphore.semaphoreCommand] = {
    ActorSystem(Semaphore(count), "semaphore")
  }

  def acquire(semaphore: ActorRef[Semaphore.semaphoreCommand]): Unit = {
    semaphore ! Semaphore.Acquire(replyTo)
  }

  def release(semaphore: ActorRef[Semaphore.semaphoreCommand]): Unit = {
    semaphore ! Semaphore.Release(replyTo)
  }
```

Here I applied the same approach as in the previous task, implementation of the semaphore is straight-forward, I have an initialCount and a maximumPossible value that can access the critical section. If Acquire is called, I will reduce the initialCount if it is bigger than 0. otherwise I won't touch the internal count. For Release same thing, if maximumCount is reached, the count won't be raised. Considering the nature of actors in Actor/Model, the internal state of the semaphore cannot be changed from outside and each message is processed one at a time. 

## P0W4 - The actor is dead.. Long lice the Actor

**Task1** -- *Minimal Task* - Create a supervised pool of identical worker actors. The number of actors
is static, given at initialization. Workers should be individually addressable. Worker actors
should echo any message they receive. If an actor dies (by receiving a “kill” message), it should
be restarted by the supervisor. Logging is welcome.

Echo actor
```scala
    case Echo(message) =>
      log.info("Worker {} received message: {}", name, message)
      sender() ! s"Worker $name echoed: $message"
    case Kill =>
      log.warning("Worker {} received Kill message and will be restarted", name)
      throw new Exception(s"Worker $name killed")
```

Echo actor supervisor
```scala
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
  
  override def receive: Receive = {
      case SendWorkers =>
        sender() ! routees
    case Terminated(ref) =>
      val index = routees.indexOf(ref.actorRef)
      routees = routees.patch(index, Nil, 1)

      val name = workerName()
      val r = context.actorOf(EchoActorObject.props(name), name)
      context.watch(r)
      router.removeRoutee(ref)
      router.addRoutee(ActorRefRoutee(r))
      }
```

In that task, I had to implement a supervised pool of identical worker actors. To do that I used the Pool Router which takes as input the message passing logic and the routees in the pool. Number of routees is static and created when the supervisor is created. When a SendWorkers message is coming through, the routees are sent back to the sender and can be individually addressed. When an actor receives a Kill message, it throws an exception and makes the supervisor to restart the 'dead' actor. It is done using the supervision strategy 'OneForOneStrategy' which is the default one. 

**Task2** -- *Main Task* - Create a supervised processing line to clean messy strings. The first worker in
the line would split the string by any white spaces (similar to Python’s str.split method).
The second actor will lowercase all words and swap all m’s and n’s (you nomster!). The third
actor will join back the sentence with one space between words (similar to Python’s str.join
method). Each worker will receive as input the previous actor’s output, the last actor printing
the result on screen. If any of the workers die because it encounters an error, the whole
processing line needs to be restarted. Logging is welcome.

Splitter
```scala
if (message.contains('@')) {
  throw new IllegalArgumentException("Message is empty")
}
val words = message.split("\\s+")
nextActor ! LowercaseAndSwapMessage(words)
```

Joiner
```scala
val joinedWords = message.mkString(" ")
nextActor ! StringPrinter.PrintMessage(joinedWords)
```

Lowercaser
```scala
val lowercasedWords = words.map(_.toLowerCase)
val swappedMsWithNs = lowercasedWords.map(str => str.map {
   case 'm' => 'n'
   case 'n' => 'm'
   case c => c
})

nextActor ! JoinMessage(swappedMsWithNs)
```

Supervisor
```scala
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
  
  private def createRoutees(): IndexedSeq[ActorRefRoutee] = {
    var result = IndexedSeq.empty[ActorRefRoutee]

    val printer = context.actorOf(StringPrinter.props(), workerName())
    val joiner = context.actorOf(StringJoiner.props(printer), workerName())
    val lowercaserAndSwapper = context.actorOf(StringLowercaserAndSwapper.props(joiner), workerName())
    val splitter = context.actorOf(StringSplitter.props(lowercaserAndSwapper), workerName())
    splitterRef = splitter
```

That task is similar to the previous one, the only differences are that I change the supervision strategy to 'AllForOneStrategy' which means when any actor dies, all actors will be eventually restarted. When I create new routees, I pass to each one of them the ActorRef to the Actor they should message next. 

## P0W5 - May the Web be with you

**Task1** -- *Minimal Task* - Write an application that would visit [this link](https://quotes.toscrape.com/). Print out the HTTP response
status code, response headers and response body.

PageVisitor actor
```scala
val responseFuture: Future[HttpResponse] = Http().singleRequest(
  HttpRequest(uri = page, method = HttpMethods.GET))

  responseFuture
    .onComplete {
       case Success(res) =>
         log.info("Response: {}", res)
         Unmarshal(res.entity).to[String]
          .onComplete {
            case Success(htmlString) =>
              log.info("Page {} visited successfully", page)
              log.info("Body: {}", htmlString)

              case Failure(_) => sys.error("something wrong")
          }
         case Failure(_) => sys.error("Failed to send HTTP request")
   }
```

That task introduced me into the world of web scraping. To finish the task, I sent a single GET request to the specified link as a future, because the response can not be currently available when the request is made. When a response is available, the HttpResponse is caught and deserialised into a String and printed in the console.


**Task2** -- *Minimal Task* - Continue your previous application. Extract all quotes from the HTTP
response body. Collect the author of the quote, the quote text and tags. Save the data
into a list of maps, each map representing a single quote


```scala
val quotes = Jsoup.parse(htmlString).select("div.quote")
quotes.forEach(
  quote => {
  val quoteText = quote.select("span.text").text()
  val quoteAuthor = quote.select("small.author").text()
  val quoteTags = quote.select("div.tags a.tag").eachText().asScala.toList

  val quoteClass = new QuoteClass(quoteText, quoteAuthor, quoteTags)
  val quoteMap = HashMap[String, QuoteClass]()
  quoteMap.put(quoteText, quoteClass)
  listQuotes += quoteMap 
  })
```

Here I had to continue my previous task and to extract all the quotes from the response and insert the quote, author and related tags into a list of maps. For that I used a library called "Jsoup" which is a HTML parser, retrieved all the 'quote' divs and processed each of them. 

**Task3** -- *Minimal Task* - Continue your previous application. Persist the list of quotes into a file.
Encode the data into JSON format. Name the file quotes.json.

```scala
val mapper = new ObjectMapper().registerModule(DefaultScalaModule);
val jsonString = mapper.writeValueAsString(listQuotes)
val jsonFile = new java.io.File("src/main/scala/week5/quotes/quotes.json")
mapper.writeValue(jsonFile, jsonString)
```

For that task I used Jackson ObjectMapper, which is a JSON serialization/deserialization library. I took the whole list of quotes and wrote them into a jsonFile. 

**Task4** -- *Main Task* - Write an application that would implement a Star Wars-themed RESTful API.
The API should implement the following HTTP methods:

* GET /movies
* GET /movies/:id
* POST /movies
* PUT /movies/:id
* PATCH /movies/:id
* DELETE /movies/:id

Use a database to persist your data.

StarWarsRepository
```scala
  private val mapper = new ObjectMapper().registerModule(DefaultScalaModule);
  private val jedis = new Jedis("localhost", 6379)
  seedJedis()
  
   override def receive: Receive = {
    case GetMovies =>
      val moviesJson = jedis.lrange("movies", 0, -1).asScala
      sender() ! moviesJson.toList
    case GetMovies(id) =>
      val moviesJson = jedis.lrange("movies", 0, -1).asScala
      val movie = moviesJson.toList
        .map(mapper.readValue(_, classOf[Movie]))
        .find(_.id == id)

      val movieJson = Option[Movie](movie.get).map(mapper.writeValueAsString(_))

      sender() ! movieJson

    case PostMovies(movie) =>
      jedis.lpush("movies",
        mapper.writeValueAsString(movie),
      )
      sender() ! "Movie added"
```

StarWarsAPI
```scala
Http().newServerAt(host, port).bind(routes)

def routes: Route =
    pathPrefix("movies") {
      pathEnd {
        get {
          val allMovies: Future[List[String]] = (starWarsRepository ? StarWarsRepository.GetMovies).mapTo[List[String]]
          onSuccess(allMovies) { movies =>
            complete(HttpEntity(ContentTypes.`application/json`, movies.toString()))
          }
        } ~
          post {
            entity(as[Movie]) { movie =>
              val movieAdded: Future[String] = (starWarsRepository ? StarWarsRepository.PostMovies(movie)).mapTo[String]
              onComplete(movieAdded) { _ =>
                complete(StatusCodes.Created)
              }
            }
          } ~
          get {
            parameters("id".as[Int]) { id =>
              log.info(s"Get movie with id: $id")
              val movie: Future[Option[String]] = (starWarsRepository ? StarWarsRepository.GetMovies(id.toInt)).mapTo[Option[String]]
              onSuccess(movie) {
                case Some(movie) => complete(HttpEntity(ContentTypes.`application/json`, movie))
                case None => complete(StatusCodes.NotFound)
              }
            }
          }
      }

```

In the main task, I had to implement a themed StarWars RESTful API with all CRUD endpoints. I didn't include all the endpoints to not make the report overbloated. In the StarWarsAPI, I open a new server and assign the following routes to it and it is ready to listen to incoming requests. Requests were made using Postman. I define some routes with the 'movies' prefix, to some requests I add query parameters, to others a request body. The request is forwarded to the repository. In the repository, as a storage I used Jedis which is Java client for Redis, create a docker container with 6379 port and it should work. Use 'docker run -p 6379:6379 -it redis/redis-stack:latest'.

## Conclusion

The first two weeks of the laboratory work nr. 1 are a introduction to the Scala language, by solving problems we get familiar with the language. 
The third week made me familiar with the actor model, each task slowly explains how things work, how messages are passed between actors, how the internal state is handled, how actors can watch other actors and be notfied. 
The fourth week taught me about actor supervision and different supervision strategies. 
The fifth week taught me about web scraping and how to make a REST API using actors and how to handle incoming requests and responses. 

## Bibliography

https://docs.scala-lang.org/tour/tour-of-scala.html

https://docs.scala-lang.org/getting-started/intellij-track/getting-started-with-scala-in-intellij.html

https://developer.lightbend.com/guides/akka-quickstart-scala/index.html

https://doc.akka.io/docs/akka/current/typed/guide/introduction.html

https://github.com/redis/jedis

https://jsoup.org/

https://doc.akka.io/docs/akka-http/current/introduction.html?_ga=2.122991691.1998798118.1677997811-1718736475.1676137804
