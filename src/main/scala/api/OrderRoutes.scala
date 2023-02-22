package api

import actors.ProcessOrderActor.ActionExecuted
import actors.RegisterOrderActor.{GetOrders, Order, Orders, SubmitOrder}
import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{onSuccess, _}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

trait OrderRoutes extends JsonProtocol {

  implicit def system: ActorSystem
  implicit lazy val exec: ExecutionContext = system.dispatcher
  implicit lazy val timeout = Timeout(5.seconds)
  lazy val log = Logging(system, classOf[OrderRoutes])
  def registerOrderActor: ActorRef

  val getOrdersRoute: Route = path("orders") {
      pathEnd {
        get {
          val orders: Future[Orders] =
            (registerOrderActor ? GetOrders()).mapTo[Orders]
          complete(orders)
        }
      }
    }


  val submitOrderRoute: Route = path("order") {
      pathEnd {
        post {
          entity(as[Order]) { order =>
            val orderCreated: Future[ActionExecuted] =
              (registerOrderActor ? SubmitOrder(order)).mapTo[ActionExecuted]

            onSuccess(orderCreated) { executedAction =>
              log.info("Submitted order ID [{}]: {}", order.id, executedAction.description)
              complete((StatusCodes.Created, executedAction))
            }
          }
        }
      }
    }

  val serviceRoute: Route = getOrdersRoute ~ submitOrderRoute

}