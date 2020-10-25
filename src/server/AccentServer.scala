package server

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import findAccent.AccentActor
import messages._

class AccentServer extends Actor {

  var actorToSocket: Map[ActorRef, SocketIOClient] = Map()
  var socketToActor: Map[SocketIOClient, ActorRef] = Map()

  val config: Configuration = new Configuration {
    setHostname("localhost")
    setPort(8080)
  }

  val server: SocketIOServer = new SocketIOServer(config)

  server.addEventListener("startDecisions", classOf[String], new StartDecisionListener(this))
  server.addEventListener("questionAnswered", classOf[String], new AnswerListener(this))
  server.addEventListener("restart", classOf[Nothing], new RestartListener(this))
  server.addEventListener("stop", classOf[Nothing], new StopListener(this))

  server.start()

  override def receive(): Receive = {
    case nextQ: NextQuestion => {
      val clientForSend: SocketIOClient = this.actorToSocket.apply(sender())
      clientForSend.sendEvent("nextQuestion", nextQ.question)
    }
    case acc: Finished => {
      val clientForSend: SocketIOClient = this.actorToSocket.apply(sender())
      clientForSend.sendEvent("finished", acc.accent)
    }
  }

  override def postStop(): Unit = {
    server.stop()
  }

}


//starts up this whole guessing thing
//sends an initial Response message to actor to start up everything
class StartDecisionListener(server: AccentServer) extends DataListener[String] {
  override def onData(client: SocketIOClient, username: String, ackSender: AckRequest): Unit = {
    val person: ActorRef = server.context.actorOf(Props(classOf[AccentActor]))
    server.actorToSocket += (person -> client)
    server.socketToActor += (client -> person)
    person ! FirstQuestion
  }
}

//picks up the client's answer and sends it to the corresponding actor
class AnswerListener(server: AccentServer) extends DataListener[String] {
  override def onData(client: SocketIOClient, clientAns: String, ackSender: AckRequest): Unit = {
    val actorForSend: ActorRef = server.socketToActor.apply(client)
    actorForSend ! Response(clientAns)
  }
}

//restarts the decision tree
class RestartListener(server: AccentServer) extends DataListener[Nothing] {
  override def onData(client: SocketIOClient, data: Nothing, ackSender: AckRequest): Unit = {
    val actorForSend: ActorRef = server.socketToActor.apply(client)
    actorForSend ! Restart
  }
}

//stops server
class StopListener(server: AccentServer) extends DataListener[Nothing] {
  override def onData(client: SocketIOClient, data: Nothing, ackSender: AckRequest): Unit = {
    server.postStop()
  }
}



object AccentServer {

  def main(args: Array[String]): Unit = {
    val actorSystem: ActorSystem = ActorSystem()
    val server: ActorRef = actorSystem.actorOf(Props(classOf[AccentServer]))
  }

}