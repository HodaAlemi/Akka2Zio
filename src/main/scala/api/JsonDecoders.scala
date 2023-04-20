package api

import api.Model.{ Order, Orders}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait JsonDecoders {
  implicit val orderDecoder: JsonDecoder[Order] = DeriveJsonDecoder.gen[Order]
  //implicit val itemDecoder: JsonDecoder[Item] = DeriveJsonDecoder.gen[Item]
  //implicit val itemsDecoder: JsonDecoder[Items] = DeriveJsonDecoder.gen[Items]
  implicit val ordersDecoder: JsonDecoder[Orders] = DeriveJsonDecoder.gen[Orders]

  implicit val orderEncoder: JsonEncoder[Order] = DeriveJsonEncoder.gen[Order]
  implicit val ordersEncoder: JsonEncoder[Orders] = DeriveJsonEncoder.gen[Orders]
  //implicit val itemEncoder: JsonEncoder[Item] = DeriveJsonEncoder.gen[Item]
  //implicit val itemsEncoder: JsonEncoder[Items] = DeriveJsonEncoder.gen[Items]

}
