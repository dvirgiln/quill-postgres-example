package com.david.example.zio


import scala.concurrent.Await
import scala.concurrent.duration.Duration

import com.david.example.Model.{Cinema, CinemaExtra}
import io.getquill.JsonbValue
import repository.CinemasRepository


object MainZio extends App {

  val curzonExtra = CinemaExtra("London", "shoreditch Park 22", "+443433333232", 4)
  val curzonShoreditch = Cinema(None, "Curzon Shoreditch", JsonbValue(curzonExtra))

  val future = CinemasRepository.add(curzonShoreditch)

  Await.result(future, Duration.Inf)
}
