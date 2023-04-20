package service

import api.Model.{Order, Orders}
import zio._

import java.io.IOException

trait OrderService {
  def getOrders(): Orders
  def submitOrder(order: Order): Task[Unit]
}

//to create the API more ergonomic, it's better to write accessor methods for all of our service methods using ZIO.serviceWithZIO constructor inside the companion object:
object OrderService{
  def getOrders(): ZIO[OrderService, Throwable, Orders] = ZIO.serviceWith[OrderService](_.getOrders())

  def submitOrder(order: Order): ZIO[OrderService, Nothing, Task[Unit]] = ZIO.serviceWith[OrderService](_.submitOrder(order))
}
object OrderServiceImp {
//  val live: ZLayer[OrderService, Nothing, OrderServiceImp.type] = ZLayer {
//    for {
//      _ <- ZIO.service[OrderService]
//    } yield OrderServiceImp
//  }   // this one was giving error in Webserver for provideLayer...

  val live: ZLayer[Any, Nothing, OrderService] = ZLayer.succeed(OrderServiceImp.apply())
}
case class OrderServiceImp() extends OrderService {
  private var ordersSet: Set[Order] = Set.empty
  override def getOrders(): Orders =  {
    Console.printLine(s"${ordersSet.size} orders found.")
    Orders(ordersSet.toSeq)
  }

  override def submitOrder(order: Order)= for {
        _ <- Console.printLine(s"OrderID ${order.id} processed.")
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