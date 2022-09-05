package com.david.example.ctx

import io.getquill.context.jasync.JAsyncContext

trait CustomEncoders {
  this: JAsyncContext[_, _, _] =>

  case class CustomEncoder[T](sqlType: String)(implicit encoder: BaseEncoder[T])
    extends BaseEncoder[T] {
    override def apply(index: Index, value: T, row: PrepareRow, session: Session) =
      encoder.apply(index, value, row, session)
  }

  def encoder[T](sqlType: String): CustomEncoder[T] =
    encoder(identity[T], sqlType)

  def encoder[T](f: T => Any, sqlType: String): CustomEncoder[T] =
    CustomEncoder[T](sqlType)(new BaseEncoder[T] {
      def apply(index: Index, value: T, row: PrepareRow, session: Session) =
        row :+ f(value)
    })

  implicit def circeEncoder[T](implicit circeEncoder: io.circe.Encoder[T]): CustomEncoder[T] = encoder[T]((value: T) => {
    import io.circe.syntax._
    value.asJson.noSpaces
  }, "json")

}
