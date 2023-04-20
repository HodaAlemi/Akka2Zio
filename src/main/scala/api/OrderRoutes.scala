package api

import api.Model._
import zhttp.http.{Response, _}
import zio._
import zio.json._
import service.OrderService
import zhttp.http.Method.POST

trait OrderRoutes extends JsonDecoders {

  val httpApp: Http[OrderService, Throwable, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "orders" =>
      OrderService.getOrders().map(r => Response.json(r.toJson))


    case req @ Method.POST -> !! / "order" =>
//        for {
//          body <- req.bodyAsString
//          order <- ZIO.fromEither(body.fromJson[Order].left.map(e => DecodeError(e)))
//          _ <- OrderService.submitOrder(order)
//        } yield (Response.status(Status.Created))

      for {
        order <- req.bodyAsString.map(_.fromJson[Order])
        response <- order match {
                    case Left(e) =>
                      Console.printLine(s"Failed to parse the input: $e").as(Response.text(e).setStatus(Status.BadRequest))
                    case Right(o) =>
                      println("*********** right: order is: " + o)
                      OrderService.submitOrder(o).map(_ => Response.status(Status.Created))
                  }

      } yield response

  }

//  def httpResponseHandler[R, A](rio: RIO[R, A], onSuccess: A => Response) : URIO[R, Response] =  rio.fold(
//    {
//      case DecodeError(msg) => Response.fromHttpError(HttpError.BadRequest(msg))
//      case NotFoundError(msg) => Response.fromHttpError(HttpError.UnprocessableEntity(msg))
//      case e => Response.fromHttpError(HttpError.InternalServerError(e.getMessage))
//    }, onSuccess
//  )

}