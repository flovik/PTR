package org.victor
package week5.main

import week5.main.StarWarsRepository.Movie

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import spray.json.DefaultJsonProtocol._
import spray.json.{RootJsonFormat, _}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class StarwarsAPI(host: String, port: Int) extends Actor with ActorLogging {
  implicit val system = context.system
  implicit val dispatcher = context.dispatcher
  implicit val requestTimeout = akka.util.Timeout(5.second)

  implicit val movieFormat: RootJsonFormat[Movie] = jsonFormat4(Movie)
  private val starWarsRepository = context.actorOf(Props[StarWarsRepository], "starWarsRepository")

  Http().newServerAt(host, port).bind(routes)

  override def receive: Receive = {
    Actor.emptyBehavior
  }

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
      } ~
        parameter("id".as[Int]) { id =>
          get {
            val movie: Future[Option[String]] = (starWarsRepository ? StarWarsRepository.GetMovies(id)).mapTo[Option[String]]
            onSuccess(movie) {
              case Some(movie) => complete(HttpEntity(ContentTypes.`application/json`, movie))
              case None => complete(StatusCodes.NotFound)
            }
          } ~
            delete {
              val movieDeleted: Future[String] = (starWarsRepository ? StarWarsRepository.DeleteMovies(id)).mapTo[String]
              onComplete(movieDeleted) { _ =>
                complete(StatusCodes.OK)
              }
            } ~
            put {
              entity(as[Movie]) { movie =>
                val movieUpdated: Future[String] = (starWarsRepository ? StarWarsRepository.PutMovies(movie, id)).mapTo[String]
                onComplete(movieUpdated) { _ =>
                  complete(StatusCodes.OK)
                }
              }
            } ~
            patch {
              entity(as[Movie]) { movie =>
                val movieUpdated: Future[String] = (starWarsRepository ? StarWarsRepository.PatchMovies(movie, id)).mapTo[String]
                onComplete(movieUpdated) { _ =>
                  complete(StatusCodes.OK)
                }
              }
            }
        }
    }
}

object StarwarsAPI {
  def props(host: String, port: Int): Props = Props(new StarwarsAPI(host, port))
}