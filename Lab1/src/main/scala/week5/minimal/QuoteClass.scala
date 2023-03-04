package org.victor
package week5.minimal

class QuoteClass(_quote: String, _author: String, _tags: List[String]) {
  var quote: String = _quote
  var author: String = _author
  var tags = _tags

  override def toString: String = {
    s"$quote \n Author: $author \n Tags: $tags \n"
  }
}
