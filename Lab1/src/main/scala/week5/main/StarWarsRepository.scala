package org.victor
package week5.main

import week5.main.StarWarsRepository._

import akka.actor.{Actor, ActorLogging, Props}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import redis.clients.jedis.Jedis

import scala.jdk.CollectionConverters.ListHasAsScala

class StarWarsRepository extends Actor with ActorLogging {
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
    case PutMovies(movie, id) =>
      var moviesJson = jedis.lrange("movies", 0, -1).asScala
      val movies = moviesJson.toList
        .map(mapper.readValue(_, classOf[Movie]))
        .map(m => if (m.id == id) movie else m)

      jedis.del("movies")
      movies.foreach(m => jedis.lpush("movies",
        mapper.writeValueAsString(m),
      ))

      moviesJson = jedis.lrange("movies", 0, -1).asScala

      sender() ! "OK"
    case PatchMovies(movie, id) =>
      var moviesJson = jedis.lrange("movies", 0, -1).asScala
      val movies = moviesJson.toList
        .map(mapper.readValue(_, classOf[Movie]))
        .map(m => if (m.id == id) {
          val title = if (movie.title == null) m.title else movie.title
          val id = if (movie.id == null) m.id else movie.id
          val director = if (movie.director == null) m.director else movie.director
          val releaseDate = if (movie.release_year == null) m.release_year else movie.release_year
          Movie(id, title, releaseDate, director)
        } else m)

      jedis.del("movies")
      movies.foreach(m => jedis.lpush("movies",
        mapper.writeValueAsString(m),
      ))

      moviesJson = jedis.lrange("movies", 0, -1).asScala

      sender() ! "OK"
    case DeleteMovies(id) =>
      var moviesJson = jedis.lrange("movies", 0, -1).asScala
      val movies = moviesJson.toList
        .map(mapper.readValue(_, classOf[Movie]))
        .filterNot(_.id == id)

      jedis.del("movies")
      movies.foreach(m => jedis.lpush("movies",
        mapper.writeValueAsString(m),
      ))

      moviesJson = jedis.lrange("movies", 0, -1).asScala

      sender() ! "OK"
  }

  private def seedJedis(): Unit = {

    // Convert the list of maps to a JSON string
    jedis.del("movies")
    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(1, "Star Wars: Episode IV - A New Hope ", 1977, "George Lucas")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(2, "Star Wars: Episode V - The Empire Strikes Back", 1980, "Irvin Kershner")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(3, "Star Wars: Episode VI - Return of the Jedi", 1983, "Richard Marquand")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(4, "Star Wars: Episode I - The Phantom Menace", 1999, "George Lucas")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(5, "Star Wars: Episode II - Attack of the Clones", 2002, "George Lucas")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(6, "Star Wars: Episode III - Revenge of the Sith", 2005, "George Lucas")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(7, "Star Wars: The Force Awakens", 2015, "J.J. Abrams")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(8, "Rogue One: A Star Wars Story", 2016, "Gareth Edwards")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(9, "Star Wars: The Last Jedi", 2017, "Rian Johnson")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(10, "Solo: A Star Wars Story", 2018, "Ron Howard")),
    )

    jedis.lpush("movies",
      mapper.writeValueAsString(Movie(11, "Star Wars: The Rise of Skywalker", 2019, "J.J. Abrams")),
    )
  }
}

object StarWarsRepository {
  def props(): Props = Props(new StarWarsRepository)

  case class GetMovies(Id: Int)

  case class PostMovies(movie: Movie)

  case class PutMovies(movie: Movie, Id: Int)

  case class PatchMovies(movie: Movie, Id: Int)

  case class DeleteMovies(Id: Int)

  case class Movie(id: Int, title: String, release_year: Int, director: String)

  case object GetMovies
}
