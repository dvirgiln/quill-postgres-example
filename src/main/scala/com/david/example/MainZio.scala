package com.david.example
import scala.concurrent.ExecutionContext.Implicits.global

import com.david.example.Model.{Cinema, CinemaExtra}
import com.david.example.repository.zio.CinemasRepository
import io.getquill.{JsonbValue, Literal}
import io.getquill.jdbczio.Quill
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}
import zio.{ZIO, ZIOAppDefault, ZLayer}


object IdiomaticAppWithEncoders extends ZIOAppDefault {


  implicit val cinemaExtraEncoder: JsonEncoder[CinemaExtra] = DeriveJsonEncoder.gen[CinemaExtra]
  implicit val cinemaExtraDecoder: JsonDecoder[CinemaExtra] = DeriveJsonDecoder.gen[CinemaExtra]

  case class App(quill: Quill.Postgres[Literal]) {
    val cinemasRepository = CinemasRepository(quill)

  }

  object App {
    def add(cinema: Cinema) = ZIO.serviceWithZIO[App](_.cinemasRepository.add(cinema))

  }

  val dsLive = Quill.DataSource.fromPrefix("testPostgresDBZio")
  implicit val pgLive = Quill.Postgres.fromNamingStrategy(Literal)
  val appLive = ZLayer.fromFunction(App.apply _)


  val curzonExtra = CinemaExtra("London", "shoreditch Park 22", "+443433333232", 4)
  val curzonShoreditch = Cinema(None, "Curzon Shoreditch", curzonExtra)

  override def run =
    (for {
      _ <- App.add(curzonShoreditch)
     // _ <- printLine(ent)
    } yield ()).provide(dsLive, pgLive, appLive)
}
