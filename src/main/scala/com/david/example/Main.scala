package com.david.example

import java.util.Date
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import com.david.example.Model.{Cinema, CinemaExtra, Film}
import com.david.example.ctx.CustomSqlContext
import com.david.example.repository.jasync.{CinemasRepository, FilmsRepository}

object Main extends App {
  implicit val ctx = new CustomSqlContext
  import scala.concurrent.ExecutionContext.Implicits.global
  val filmsRepository = FilmsRepository()
  val avengers = Film(None, "The avengers", 1, new Date(), "superheroes")
  val daredevil = Film(None, "Daredevil", 2, new Date(), "superheroes")
  val resultFuture = for{
    _ ←  filmsRepository.add(avengers)
    _ ←  filmsRepository.add(daredevil)
    allFilms ← filmsRepository.all()
  }yield allFilms

  val films = Await.result(resultFuture, Duration.Inf)

  val cinemasRepository = CinemasRepository()

//  val curzonExtra = CinemaExtra("London", "shoreditch Park 22", "+443433333232", 4)
//  val curzonShoreditch = Cinema(None, "Curzon Shoreditch", curzonExtra)

//  Await.result(cinemasRepository.add(curzonShoreditch), Duration.Inf)
  println(films)
}
