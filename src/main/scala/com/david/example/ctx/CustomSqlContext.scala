package com.david.example.ctx

import com.david.example.Model.Film
import io.getquill.PostgresJAsyncContext
import io.getquill.Literal
class CustomSqlContext extends PostgresJAsyncContext(Literal, "testPostgresDB"){

  implicit val filmsSchemaMeta = schemaMeta[Film]("films")
}
