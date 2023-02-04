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

## Conclusion

The first two weeks of the laboratory work nr. 1 are a introduction to the Scala language, by solving problems we get familiar with the language. 

## Bibliography

https://docs.scala-lang.org/tour/tour-of-scala.html
https://docs.scala-lang.org/getting-started/intellij-track/getting-started-with-scala-in-intellij.html
