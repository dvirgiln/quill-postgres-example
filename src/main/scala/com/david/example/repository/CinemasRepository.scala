package com.david.example.repository
import scala.concurrent.{ExecutionContext, Future}

import com.david.example.Model.{Cinema, Film}
import com.david.example.ctx.CustomSqlContext

class CinemasRepository (implicit ctx: CustomSqlContext) {
    import ctx._

  def add(cinema: Cinema)(implicit executionContext: ExecutionContext): Future[Long] = {
    ctx.run(quote {
      query[Cinema].insertValue(lift(cinema)).returningGenerated(_.id)
    }).map(a ⇒ a.get)
  }

  def all()(implicit executionContext: ExecutionContext): Future[List[Cinema]] = {
    ctx.run(quote { query[Cinema]} ).map(a ⇒ a.toList)
  }

  def getByTitle(title: String)(implicit executionContext: ExecutionContext): Future[List[Cinema]] = {
    ctx.run(quote {query[Cinema].filter(_.title == lift(title))}).map(a ⇒ a.toList)
  }
}


object CinemasRepository{
  def apply()(implicit ctx: CustomSqlContext) = new CinemasRepository()
}
