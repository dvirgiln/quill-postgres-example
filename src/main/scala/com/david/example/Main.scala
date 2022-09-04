package com.david.example

import java.util.Date
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import com.david.example.Model.Film
import com.david.example.ctx.CustomSqlContext
object Main extends App {
  implicit val ctx = new CustomSqlContext
  import scala.concurrent.ExecutionContext.Implicits.global
  val repo = ExampleRepository()
  val avengers = Film(None, "The avengers", 1, new Date(), "superheroes")
  val daredevil = Film(None, "Daredevil", 2, new Date(), "superheroes")
  val resultFuture = for{
    _ ←  repo.add(avengers)
    _ ←  repo.add(daredevil)
    allFilms ← repo.all()
  }yield allFilms

  val films = Await.result(resultFuture, Duration.Inf)
  println(films)
}
