package service

import api.Model.{Order, Orders}
import zio._


trait OrderService {
  def getOrders(): Task[Unit]
  def submitOrders(order: Order): Task[Unit]
}

//to create the API more ergonomic, it's better to write accessor methods for all of our service methods using ZIO.serviceWithZIO constructor inside the companion object:
object OrderService{
  def getOrders(): ZIO[OrderService, Nothing, Task[Unit]] = ZIO.serviceWith[OrderService](_.getOrders())

  def submitOrder(order: Order): ZIO[OrderService, Nothing, Task[Unit]] = ZIO.serviceWith[OrderService](_.submitOrders(order))
}
object OrderServiceImp {
  val live: ZLayer[OrderService, Nothing, OrderServiceImp.type] = ZLayer {
    for {
      _ <- ZIO.service[OrderService]
    } yield OrderServiceImp
  }
}
case class OrderServiceImp() extends OrderService {
  var ordersSet: Set[Order] = Set.empty
  override def getOrders(): Task[Unit] = for {
   // _ <- Orders(ordersSet.toSeq)
    _ <- ZIO.log(s"${ordersSet.size} orders found.")
  } yield Orders(ordersSet.toSeq)

  override def submitOrders(order: Order)= for {
        _ <- ZIO.log(s"OrderID ${order.id} processed.")
      } yield (ordersSet += order)

}


//  override def submitOrders(order: Order) = {
//    ZIO.acquireRelease {
//      for {
//        queue <- Queue.unbounded[Order]
//        _ <- queue.offer(order).forever.fork
//        _ <- queue.take.flatMap(queuedOrder => ordersSet += queuedOrder).forever.fork
//        _ <- ZIO.log(s"OrderID ${order.id} processed.")
//      } yield ()
//    }
//  }