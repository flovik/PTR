package org.victor
package classes

class CaesarCipher {

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
}
