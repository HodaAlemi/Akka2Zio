package api

import api.Model._
import service.OrderService
import zio._
import zio.http.model.{Method, Status}
import zio.http._
import zio.json._

trait OrderRoutes extends JsonDecoders {

  val httpApp: Http[OrderService, Nothing, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "orders" =>
      OrderService.getOrders.map(response => Response.json(response.toJson)).orDie


    case req @ Method.POST -> !! / "order" => (for {
        order <- req.body.asString.map(_.fromJson[Order])
        response <- order match {
                    case Left(error) =>
                      ZIO
                        .debug(s"Failed to parse the input: $error")
                        .as(Response.text(error).setStatus(Status.BadRequest))
                    case Right(order) =>
                      OrderService.submitOrder(order)
                        .map(id => Response.text(Status.Created + id))
                  }

      } yield response).orDie

  }

//  def httpResponseHandler[R, A](rio: RIO[R, A], onSuccess: A => Response) : URIO[R, Response] =  rio.fold(
//    {
//      case DecodeError(msg) => Response.fromHttpError(HttpError.BadRequest(msg))
//      case NotFoundError(msg) => Response.fromHttpError(HttpError.UnprocessableEntity(msg))
//      case e => Response.fromHttpError(HttpError.InternalServerError(e.getMessage))
//    }, onSuccess
//  )

}