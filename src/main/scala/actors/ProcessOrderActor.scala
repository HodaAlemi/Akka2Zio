package actors

import actors.ProcessOrderActor.{ActionExecuted, ProcessOrder}
import actors.RegisterOrderActor.Order
import akka.actor.{ActorLogging, ActorRef, Props, Actor => untypedActor}

object ProcessOrderActor {
  case class ActionExecuted(description: String)
  case class ProcessOrder(order: Order, flowActorRef:ActorRef)

  def props: Props = Props[ProcessOrderActor]
}

class ProcessOrderActor extends untypedActor with ActorLogging {

   def receive: Receive = {
     case processOrder : ProcessOrder =>
       log.info(s"process order actor received the order ${processOrder.order.id}")
       processOrder.flowActorRef ! ActionExecuted(s"OrderID ${processOrder.order.id} processed.")
   }


}
