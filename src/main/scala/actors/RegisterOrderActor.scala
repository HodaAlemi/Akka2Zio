//package actors
//
//
//import zio._
//
////
//
//trait Actor[-In] {
//  def tell(i: In): UIO[Boolean]
//}
//
//object RegisterOrderActor {
//    case class Order(id: Int, items: Seq[Item], totalPrice: Double, date: String)
//    case class Orders(orders: Seq[Order])
//    case class Item(id: Int, name: String, amount: Int, price: Double)
//
//
//    sealed trait OrderMessage
//    case class SubmitOrder(order: Order) extends OrderMessage
//    case class GetOrders() extends OrderMessage
//    case class ActionExecuted(description: String) extends OrderMessage
//  def make[In](receive: In => UIO[Unit]): ZIO[Scope, Nothing, Actor[In]] =
//    ZIO.acquireRelease {
//      for {
//        queue <- Queue.unbounded[In]
//        fiber <- queue.take.flatMap(receive).forever.fork
//        actor = new Actor[In] {
//          override def tell(i: In): UIO[Boolean] =
//            queue.offer(i)
//        }
//      } yield (actor, fiber)
//    }(_._2.join).map(_._1)
//}
//
////class RegisterOrderActor extends Actor with ActorLogging {
////
////  import RegisterOrderActor._
////
////  var orders: Set[Order] = Set.empty
////  //def processOrderActor: ActorRef = system.actorOf(ProcessOrderActor.props, "processOrderActor")
////
////  def receive: Receive = {
////    case GetOrders() =>
////      sender() ! Orders(orders.toSeq)
////
////    case SubmitOrder(order) =>
////      log.info("registering the order")
////      orders += order
////      sender() ! ActionExecuted(s"OrderID ${order.id} processed.")
////
////      //processOrderActor ! ProcessOrder(order, sender())
////  }
////}
//
////def registerOrders(msg: OrderMessage) = {
////  var orders: Set[Order] = Set.empty
////
////  ZIO.acquireRelease {
////    for {
////      queue <- Queue.unbounded[OrderMessage]
////        _   <- queue.offer(msg).forever.fork
////        _   <- queue.take.flatMap(queuedMessage => handleMessages(queuedMessage, orders)).forever.fork
////    } yield ()
////  }
////}
////
////def handleMessages (msg: OrderMessage, orders: Set[Order]):Product = {
////
////  msg match {
////    case GetOrders() => Orders(orders.toSeq)
////
////    case SubmitOrder(order) =>
////      ZIO.log("Received order ${order.id}")
////      orders += order
////      ActionExecuted(s"Order ${order.id} is processed.")
////  }
////}


