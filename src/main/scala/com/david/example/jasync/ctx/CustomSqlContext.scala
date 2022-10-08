package com.david.example.jasync.ctx

import com.david.example.Model.{Cinema, Film}
import io.getquill.{Literal, PostgresJAsyncContext}
class CustomSqlContext extends PostgresJAsyncContext(Literal, "testPostgresDB"){

  implicit val filmsSchemaMeta = schemaMeta[Film]("films")
  implicit val cinemasSchemaMeta = schemaMeta[Cinema]("cinemas")

}
