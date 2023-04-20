package api


import api.Model.Order
import service.OrderServiceImp
import zio._
import zio.http.Server

object WebServer extends ZIOAppDefault with OrderRoutes {
  val port: Int = 9001

  override val run =
  Server
    .serve(httpApp)
    .provide(Server.default)
    .provideLayer(OrderServiceImp.live)

  //println(s"Server online at http://localhost:9001/")

}