package com.david.example.ctx

import io.getquill.util.Messages.fail
import scala.reflect.{ClassTag, classTag}

import io.circe.Json
import io.getquill.context.jasync.JAsyncContext

trait CustomDecoders {
  this: JAsyncContext[_, _, _] =>

  case class CustomDecoder[T](sqlType: String)(implicit decoder: BaseDecoder[T])
    extends BaseDecoder[T] {
    override def apply(index: Index, row: ResultRow, session: Session) =
      decoder(index, row, session)
  }

  def customDecoder[T: ClassTag](
    f:       PartialFunction[Any, T],
    sqlType: String
  ): CustomDecoder[T] =
    CustomDecoder[T](sqlType)(new BaseDecoder[T] {
      def apply(index: Index, row: ResultRow, session: Session) = {
        row.get(index) match {
          case value: T                      => value
          case value if f.isDefinedAt(value) => f(value)
          case value =>
            fail(
              s"Value '$value' at index $index can't be decoded to '${classTag[T].runtimeClass}'"
            )
        }
      }
    })


  /**implicit val jsonDecoder: CustomDecoder[Json] = customDecoder[Json]({
      case v: String  => io.circe.jawn.parse(v).fold(error => throw error, jsValue => jsValue)
    }, "json")
**/

  implicit def circeDecoder[T: ClassTag](implicit circeDecoder: io.circe.Decoder[T]): CustomDecoder[T] = customDecoder[T]({
    case v: String => {
      val json = io.circe.jawn.parse(v).fold(error => throw error, jsValue => jsValue)
      circeDecoder.decodeJson(json).fold(error => throw error, value => value)
    }
  }, "json")


}
