package com.david.example.ctx

import java.sql.Types

import com.david.example.Model.{Cinema, CinemaExtra, Film}
import com.github.jasync.sql.db.RowData
import io.circe.Json
import io.getquill.PostgresJAsyncContext
import io.getquill.Literal
class CustomSqlContext extends PostgresJAsyncContext(Literal, "testPostgresDB")
  with CustomDecoders
  with CustomEncoders
{
  implicit val filmsSchemaMeta = schemaMeta[Film]("films")
  implicit val cinemasSchemaMeta = schemaMeta[Cinema]("cinemas")

}
