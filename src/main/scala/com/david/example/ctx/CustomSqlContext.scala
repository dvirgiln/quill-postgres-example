package com.david.example.ctx

import com.david.example.Model.{Cinema, Film}
import com.github.jasync.sql.db.RowData
import io.getquill.PostgresJAsyncContext
import io.getquill.Literal
class CustomSqlContext extends PostgresJAsyncContext(Literal, "testPostgresDB"){

  implicit val filmsSchemaMeta = schemaMeta[Film]("films")
  implicit val cinemasSchemaMeta = schemaMeta[Cinema]("cinemas")

}
