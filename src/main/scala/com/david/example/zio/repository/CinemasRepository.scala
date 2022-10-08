package com.david.example.zio.repository

import scala.concurrent.Future

import com.david.example.Model.{Cinema, CinemaExtra}
import com.david.example.zio.common.ZioRepository
import io.getquill.Literal
import io.getquill.jdbczio.Quill
import zio.{Task, Unsafe, ZIO, ZLayer}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}



case class CinemasRepository (ctx: Quill.Postgres[Literal]) {
  import ctx._
  implicit val cinemasSchemaMeta = schemaMeta[Cinema]("cinemas")
  implicit val personEncoder: JsonEncoder[CinemaExtra] = DeriveJsonEncoder.gen[CinemaExtra]
  implicit val personDecoder: JsonDecoder[CinemaExtra] = DeriveJsonDecoder.gen[CinemaExtra]


  def add(cinema: Cinema)= run(quote {
      query[Cinema].insertValue(lift(cinema)).returningGenerated(_.id)
    })

  def all()= run(quote { query[Cinema]} ).map(a ⇒ a.toList)

  def getByTitle(title: String) = run(quote {query[Cinema].filter(_.title == lift(title))}).map(a ⇒ a.toList)

}


object CinemasRepository extends ZioRepository[CinemasRepository] {

  private val service = ZIO.serviceWithZIO[CinemasRepository]

  def repoLayer = ZLayer.fromFunction(CinemasRepository.apply _)

  def add(cinema: Cinema):Future[Option[Long]] = get(service(_.add(cinema)))

  def all(): Future[List[Cinema]] = get(service(_.all()))

  def getByTitle(title: String): Future[List[Cinema]] = get(service(_.getByTitle(title)))

}
