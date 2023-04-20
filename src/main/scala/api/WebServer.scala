package api


import api.Model.Order
import service.OrderServiceImp
import zhttp.service.Server
import zio._

object WebServer extends ZIOAppDefault with OrderRoutes {

  println(s"Server online at http://localhost:9001/")
  def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server
      .start(
        port = 9001,
        http = httpApp
      )
      .provide(
        OrderServiceImp.live
      )

}