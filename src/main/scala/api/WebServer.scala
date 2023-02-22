package api

import actors.RegisterOrderActor
import akka.actor.{ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object WebServer extends App with OrderRoutes {

  implicit val system =  ActorSystem("AkkaHttpServer")
  implicit val materializer = ActorMaterializer()

  /*  akka typed */
  //val orderRegistryActor = ActorSystem("AkkaHttpServer")


  /* classic akka */
  val registerOrderActor: ActorRef = system.actorOf(RegisterOrderActor.props, "registerOrderActor")

  lazy val routes: Route = serviceRoute

  Http().bindAndHandle(routes, "localhost", 9001)

  println(s"Server online at http://localhost:9001/")

  Await.result(system.whenTerminated, Duration.Inf)
}