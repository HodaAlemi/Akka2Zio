package api


import service.OrderServiceImp
import zhttp.service.Server
import zio._

object WebServer extends ZIOAppDefault with OrderRoutes {

  val port: Int = 9001

  val server = for {
    _ <- ZIO.log(s"Starting server on http://localhost:${port}")
    _ <- Server.start(port, httpApp).provideLayer(

      OrderServiceImp.live
    ).catchAll(t => ZIO.succeed(t.printStackTrace()).map(_ => ExitCode.failure))
  } yield ExitCode.success

  //def run: RIO[Scope, Nothing] = Server(routes).withBinding("localhost", 9001).startDefault
  def run = {
    server
  }


  //println(s"Server online at http://localhost:9001/")

}