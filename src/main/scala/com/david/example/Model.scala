package com.david.example

import java.util.Date

object Model {
  case class Film(id: Option[Long], title: String, did: Int, date_prod: Date, kind: String)
}