package com.david.example.repository

import scala.concurrent.{ExecutionContext, Future}

import com.david.example.Model.Film
import com.david.example.ctx.CustomSqlContext

class FilmsRepository(implicit ctx: CustomSqlContext) {
    import ctx._

  def add(film: Film)(implicit executionContext: ExecutionContext): Future[Long] = {
    ctx.run(quote {
      query[Film].insertValue(lift(film)).returningGenerated(_.id)
    }).map(a ⇒ a.get)
  }

  def all()(implicit executionContext: ExecutionContext): Future[List[Film]] = {
    ctx.run(quote { query[Film]} ).map(a ⇒ a.toList)
  }

  def getByTitle(title: String)(implicit executionContext: ExecutionContext): Future[List[Film]] = {
    ctx.run(quote {query[Film].filter(_.title == lift(title))}).map(a ⇒ a.toList)
  }
}


object FilmsRepository{
  def apply()(implicit ctx: CustomSqlContext) = new FilmsRepository()
}
