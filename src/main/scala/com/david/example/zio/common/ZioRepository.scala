package com.david.example.zio.common

import scala.concurrent.Future

import io.getquill.Literal
import io.getquill.jdbczio.Quill
import zio.{Runtime, Task, Unsafe, ZIO, ZLayer}

trait ZioRepository[X] {
  val dsLive = Quill.DataSource.fromPrefix("testPostgresDBZio")
  implicit val pgLive = Quill.Postgres.fromNamingStrategy(Literal)

  def repoLayer: ZLayer[Quill.Postgres[Literal], Nothing, X]

  def get[T, B<: Throwable](task: ZIO[X, B, T]): Future[T] = {
    val result = (for {
      a <- task
    } yield (a)).provide(dsLive, pgLive, repoLayer)

    asFuture(result)
  }

  private def asFuture[A]: Task[A] => Future[A] = task => Unsafe.unsafe(implicit u => Runtime.default.unsafe.runToFuture(task))
}
