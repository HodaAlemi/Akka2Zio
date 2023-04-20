package api


object Model {
  //case class Order(id: Int, items: Ist[Item], totalPrice: Double, date: String)
  case class Order(id: Int, item: String, price: Double)
  case class Orders(orders: Seq[Order])

  //case class Item(id: Int, name: String, amount: Int, price: Double)


  case class DecodeError(str: String) extends Error

  final case class InternalServerError(msg: String) extends Error

  final case class NotFoundError(msg: String) extends Error

}
