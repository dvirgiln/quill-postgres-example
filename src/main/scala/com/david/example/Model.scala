package com.david.example

import java.util.Date

import io.getquill.{JsonValue, JsonbValue}

object Model {
  case class Film(id: Option[Long], title: String, did: Int, date_prod: Date, kind: String)

  case class Cinema(id: Option[Long], title: String, extra: JsonbValue[CinemaExtra])

  case class CinemaExtra(city: String, address: String, phoneNumber: String, number_screens: Int)
}
