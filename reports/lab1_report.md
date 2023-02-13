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

```scala
object WorkerActor {
  sealed trait Command
  final case class DoWork(message: String) extends Command

  def apply(name: String): Behavior[Command] = Behaviors
    .receive[Command]((context, message) => {
      message match {
        case DoWork(message) =>
          context.log.info("Worker {} received message {}", name, message)
          Behaviors.stopped
      }
    })
    .receiveSignal {
      case (context, PostStop) =>
        context.log.info("Worker {} stopped", name)
        Behaviors.same
    }
}

object SupervisorActor {
  sealed trait Command
  final case class StartActor(name: String, message: String) extends Command

  def apply(): Behavior[Command] = Behaviors.
    receive[Command] { (context, message) =>
      message match {
        case StartActor(name, message) =>
          context.log.info("Starting actor {}", name)
          val child = context.spawn(WorkerActor(name), name)
          context.watch(child)
          child ! WorkerActor.DoWork(message)
          Behaviors.same
      }
    }
    .receiveSignal({
      case (context, Terminated(ref)) =>
        context.log.info("Actor {} terminated", ref.path.name)
        Behaviors.same
    })
}
```

The actor WorkerActor receives messages of type *Command* and prints any input it gets. Then actor is stopped by the *Behavior.stopped* which is then handled by the 
*.receiveSignal* which informs that actor has been stopped. The actor SupervisorActor takes messages of type *Command*. When the actor receives a message, it spawn the WorkerActor and watches when the WorkerActor, the SupervisorActor will receive a notification when the WorkerActor is Terminated. That notification is handled with the *.receiveSignal* and logs the name of the Worker that was stopped. 

**Task4** -- *Minimal Task* - Create an actor which receives numbers and with each request prints out the current average.

```scala
object averager {
  sealed trait mathCommand
  final case class CalculateAverage(number: Double) extends mathCommand

  def apply(numbers: ListBuffer[Double]): Behavior[mathCommand] = Behaviors
    .receive[mathCommand]((context, message) => {
      message match {
        case CalculateAverage(number) =>
          val average = (numbers.sum + number) / 2
          context.log.info(s"Current average is $average")
          numbers.update(0, average)
          Behaviors.same
      }
    })
}

object foo {
  sealed trait mathCommand
  final case class CalculateAverage(number: Double) extends mathCommand

  def apply(): Behavior[Double] = Behaviors
    .setup({ context =>
      val averageCalculator = context.spawn(averager(ListBuffer(0)), "averageCalculator")
      context.watch(averageCalculator)
      averageCalculator ! averager.CalculateAverage(0)

      Behaviors
        .receiveMessage({ message =>
          averageCalculator ! averager.CalculateAverage(message)
          Behaviors.same
        })
  })
}
```

That task introduces the concept of internal state of an actor. The variables defined inside the apply method are part of the internal state of an actor, in my case the numbers ListBuffer is a variable from the internal state of the average actor. When a message is received by an actor and processed by the apply method, the actor can update its internal state by modifying these variables, and here I calculate the average of the two numbers and update the list with the new average. The updated state will be available for the next message processing. This allows actors to maintain state between message processing, making it possible to implement stateful behavior. The object foo is an actor that creates averager actors and forwards the incoming messages to them. 

**Task5** -- *Main Task* - Create an actor which maintains a simple FIFO queue. You should write helper functions to create an API for the user, which hides how the queue is implemented.

```scala
object QueueActor {
  sealed trait queueCommand
  sealed trait queueResponse
  final case class Enqueue(x: Int, replyTo: ActorRef[Message]) extends queueCommand
  final case class Dequeue(replyTo: ActorRef[DequeueResult]) extends queueCommand
  final case class Message(message: String) extends queueResponse
  final case class DequeueResult(num: Option[Int]) extends queueResponse

  def apply(queue: ListBuffer[Int]): Behavior[queueCommand] = Behaviors.receive { (context, message) =>
    message match {
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
    }
  }
}

object QueueSupervisor {
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

  private def apply(): Behavior[QueueActor.queueResponse] = Behaviors.setup { context =>
    Behaviors.receiveMessage[QueueActor.queueResponse] {
      case QueueActor.Message(message) =>
        context.log.info(message)
        Behaviors.same
      case QueueActor.DequeueResult(num) =>
        num match {
          case Some(number) => context.log.info(number.toString)
          case None => context.log.info("Queue is empty.")
        }
        Behaviors.same
    }
  }
}
```

Here I had to implement a simple FIFO queue and to create an API to hide how the queue is implemented. I used a listBuffer to implement the internal queue of the actor. When a queueCommand is received, I apply pattern matching to see what message I received -> of Enqueuing or Dequeuing. In the Enqueue message I get the value that should be enqueued and reference to the Actor the queueActor should reply to. The value is added, a success message is transmitted and I return the current queue with the updated value as a Behavior response. The Dequeue message takes as input the receiver of the DequeueResult which contains the optional dequeued value. I check if the queue is empty, if it is I return None, if it is not the first value is returned and the queue is updated without the dequeued value. 

QueueSupervisor is the API between the user and the Queue actor. I defined three methods: new_queue() that return the ActorRef of the QueueActor, push(queue, x) which sends a message to enqueue some value and pop(queue) to dequeue a value. I have a reference for the current QueueSupervisor so he can get messages back from QueueActor. QueueSupervisor receives only queueResponse messages from the QueueActor, and by doing pattern matching of the incoming message, I log the information if the values was pushed or popped successfully. 

**Task6** -- *Main Task* - Create a module that would implement a semaphore

```scala
object Semaphore {
  sealed trait semaphoreCommand
  final case class Acquire(replyTo: ActorRef[Response]) extends semaphoreCommand
  final case class Release(replyTo: ActorRef[Response]) extends semaphoreCommand
  final case class Response(initialCount: Int)

  def apply(initialCount: Int, maximumValue: Int = 5): Behavior[semaphoreCommand] = Behaviors.receive { (context, message) =>
    message match {
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
    }
  }
}

object SemaphoreSupervisor {
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

  private def apply(): Behavior[Semaphore.Response] = Behaviors.setup { context =>
    Behaviors.receiveMessage[Semaphore.Response] {
      case Semaphore.Response(count) =>
        context.log.info(count.toString)
        Behaviors.same
    }
  }
}

```

Here I applied the same approach as in the previous task, implementation of the semaphore is straight-forward, I have an initialCount and a maximumPossible value that can access the critical section. If Acquire is called, I will reduce the initialCount if it is bigger than 0. otherwise I won't touch the internal count. For Release same thing, if maximumCount is reached, the count won't be raised. Considering the nature of actors in Actor/Model, the internal state of the semaphore cannot be changed from outside and each message is processed one at a time. 

## Conclusion

The first two weeks of the laboratory work nr. 1 are a introduction to the Scala language, by solving problems we get familiar with the language. 
The third week made me familiar with the actor model, each task slowly explains how things work, how messages are passed between actors, how the internal state is handled, how actors can watch other actors and be notfied. 

## Bibliography

https://docs.scala-lang.org/tour/tour-of-scala.html

https://docs.scala-lang.org/getting-started/intellij-track/getting-started-with-scala-in-intellij.html

https://developer.lightbend.com/guides/akka-quickstart-scala/index.html

https://doc.akka.io/docs/akka/current/typed/guide/introduction.html
