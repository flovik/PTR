package org.victor
package week5.minimal

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.jsoup.Jsoup

import scala.collection.mutable.{HashMap, ListBuffer}
import scala.concurrent.Future
import scala.jdk.CollectionConverters.ListHasAsScala
import scala.util.{Failure, Success}

class PageVisitorClass extends Actor with ActorLogging {
  implicit val system = context.system
  implicit val dispatcher = context.dispatcher

  // quote to quote class
  var listQuotes = ListBuffer.empty[HashMap[String, QuoteClass]]

  override def receive: Receive = {
    case PageVisitorClass.VisitPage(page) =>
      log.info("Visiting page: {}", page)

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

                  log.info("------------------------------------------------------")
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

                      log.info("Quote: {}", quoteClass)
                    }
                  )

                  val mapper = new ObjectMapper().registerModule(DefaultScalaModule);
                  // Convert the list of maps to a JSON string
                  val jsonString = mapper.writeValueAsString(listQuotes)
                  val jsonFile = new java.io.File("src/main/scala/week5/quotes/quotes.json")
                  mapper.writeValue(jsonFile, jsonString)

                case Failure(_) => sys.error("something wrong")
              }
          case Failure(_) => sys.error("Failed to send HTTP request")
        }
  }

}

object PageVisitorClass {
  def props(): Props = Props(new PageVisitorClass)

  case class VisitPage(page: String)
}
