package com.david.example

import java.util.Date

object Model {

  import io.circe.generic.JsonCodec, io.circe.syntax._
  case class Film(id: Option[Long], title: String, did: Int, date_prod: Date, kind: String)

  case class Cinema(id: Option[Long], title: String, extra: CinemaExtra)

  @JsonCodec case class CinemaExtra(city: String, address: String, phoneNumber: String, number_screens: Int)
}
