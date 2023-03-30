package api

import api.Model.{Item, Order}
import zio.json.{DeriveJsonDecoder, JsonDecoder}

trait JsonDecoders {
  implicit val orderDecoder: JsonDecoder[Order] = DeriveJsonDecoder.gen[Order]
  implicit val itemDecoder: JsonDecoder[Item] = DeriveJsonDecoder.gen[Item]
}
