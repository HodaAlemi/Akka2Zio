package actors

import actors.ProcessOrderActor.{ActionExecuted, ProcessOrder}
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import api.WebServer.system


object RegisterOrderActor{
  case class Order(id: Int, items: Seq[Item], totalPrice: Double, date: String)

  case class Orders(orders: Seq[Order])

  case class Item(id: Int, name: String, amount: Int, price: Double)

  case class SubmitOrder(order: Order)
  case class GetOrders()
  def props: Props = Props[RegisterOrderActor]

}

class RegisterOrderActor extends Actor with ActorLogging {

  import RegisterOrderActor._

  var orders: Set[Order] = Set.empty
  def processOrderActor: ActorRef = system.actorOf(ProcessOrderActor.props, "processOrderActor")

  def receive: Receive = {
    case GetOrders() =>
      sender() ! Orders(orders.toSeq)

    case SubmitOrder(order) =>
      log.info("registering the order")
      orders += order

      processOrderActor ! ProcessOrder(order, sender())
  }
}
