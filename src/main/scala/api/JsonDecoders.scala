package api

import api.Model.{Item, Order, Orders}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

trait JsonDecoders {
  implicit val orderDecoder: JsonDecoder[Order] = DeriveJsonDecoder.gen[Order]
  implicit val itemDecoder: JsonDecoder[Item] = DeriveJsonDecoder.gen[Item]

  implicit val orderEncoder: JsonEncoder[Order] = DeriveJsonEncoder.gen[Order]
  implicit val ordersEncoder: JsonEncoder[Orders] = DeriveJsonEncoder.gen[Orders]
  implicit val itemEncoder: JsonEncoder[Item] = DeriveJsonEncoder.gen[Item]


}
