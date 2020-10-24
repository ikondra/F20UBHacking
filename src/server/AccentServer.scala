package server

import akka.actor.{Actor, ActorRef, Props}
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

  server.addEventListener("startDecision", classOf[String], new StartDecisionListener(this))
  server.addEventListener("questionAnswered", classOf[String], new AnswerListener(this))


  server.start()

  override def receive(): Receive = {
    case ans: ClientAnswer => {

    }
    case nextQ: NextQuestion => {

    }
  }

}


class StartDecisionListener(server: AccentServer) extends DataListener[String] {
  override def onData(client: SocketIOClient, username: String, ackSender: AckRequest): Unit = {
    val person: ActorRef = server.context.actorOf(Props(classOf[AccentActor]))
    server.actorToSocket += (person -> client)
    server.socketToActor += (client -> person)
    //client.sendEvent()
  }
}

class AnswerListener(server: AccentServer) extends DataListener[String] {
  override def onData(client: SocketIOClient, clientAns: String, ackSender: AckRequest): Unit = {
    val actorForSend: ActorRef = server.socketToActor.apply(client)
    actorForSend ! ClientAnswer(clientAns)
  }
}
