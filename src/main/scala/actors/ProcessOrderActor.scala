//package actors
//
//import actors.ProcessOrderActor.{ActionExecuted, ProcessOrder}
//import actors.RegisterOrderActor.Order
//import akka.actor.{ActorLogging, ActorRef, Props, Actor => untypedActor}
//import zio._
//
//object ProcessOrderActor {
//  case class ActionExecuted(description: String)
//  case class ProcessOrder(order: Order, flowActorRef:ActorRef)
//
//  def props: Props = Props[ProcessOrderActor]
//}
//
//class ProcessOrderActor extends untypedActor with ActorLogging {
//
//   def receive: Receive = {
//     case processOrder : ProcessOrder =>
//       log.info(s"process order actor received the order ${processOrder.order.id}")
//       processOrder.flowActorRef ! ActionExecuted(s"OrderID ${processOrder.order.id} processed.")
//   }
//}
//
//
//def processOrder(order: Order) = {
//  for {
//    _ <- Console.printLine(s"received the order ${order.id}")
//    _ <- ZIO.sleep(1.second)
//    _ <- ActionExecuted(s"OrderID ${order.id} processed.") //Console.printLine(s"processed the order ${order.id}")
//  } yield ()
//}