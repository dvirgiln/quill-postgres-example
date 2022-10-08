package com.david.example.repository.zio


import com.david.example.Model.{Cinema}
import io.getquill.Literal
import io.getquill.jdbczio.Quill

class CinemasRepository (ctx: Quill.Postgres[Literal]) {
  import ctx._
  implicit val cinemasSchemaMeta = schemaMeta[Cinema]("cinemas")

  def add(cinema: Cinema)= run(quote {
      query[Cinema].insertValue(lift(cinema)).returningGenerated(_.id)
    })

  def all()= run(quote { query[Cinema]} ).map(a ⇒ a.toList)

  def getByTitle(title: String) = run(quote {query[Cinema].filter(_.title == lift(title))}).map(a ⇒ a.toList)
}


object CinemasRepository{
  def apply(ctx: Quill.Postgres[Literal]) = new CinemasRepository(ctx)
}
