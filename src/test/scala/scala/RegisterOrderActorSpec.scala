package scala

import actors.RegisterOrderActor._
import actors.{ProcessOrderActor, RegisterOrderActor}
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.duration._
import scala.language.postfixOps

class RegisterOrderActorSpec extends TestKit(ActorSystem("RegisterOrderActorSpec")) with ImplicitSender with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll{

  override def afterAll(): Unit =
    TestKit.shutdownActorSystem(system)

  private class RegisterOrderTestActor extends RegisterOrderActor {
    override def processOrderActor: ActorRef = TestActorRef(Props(new ProcessOrderActor))
    override def receive: Receive = {
      case GetOrders() =>
        sender() ! Orders(orders.toSeq)

      case SubmitOrder(order) =>
        log.info("registering the order")
        orders += order
    }

  }

    "RegisterOrderActor" must {

    "send back empty list of orders when there is no order" in {
      val registerOrderActor = system.actorOf(RegisterOrderActor.props, "registerOrderActor")
      registerOrderActor ! GetOrders()
      val msg = receiveOne(1 second)
      msg shouldBe Orders(List())
    }

    "return list of orders when there is order" in {
      val registerOrderActor = TestActorRef(Props(new RegisterOrderTestActor))
      val order = Order(1, items = Seq(Item( 12, "pizza", 2, 20.0)) , 20.0, "2023-02-25")

      registerOrderActor ! SubmitOrder(order)
      registerOrderActor ! GetOrders()
      val msg = receiveOne(1 second)
      msg shouldBe Orders(List(order))
    }

  }
}
