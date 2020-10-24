package findAccent

import akka.actor._
import messages._
import decisionTree._

class AccentActor extends Actor {
  val accentTree: Graph[String] = new Graph[String]
  var nextQuestion: String = ""

  override def receive: Receive = {
    case ans: Response => { //receiving the client's answer to the question
      //find edge where ans.answer == edge value
      //then get the next question from there
      //if(notDoneTraversing) {sender() ! nextQuestion}
      //else {sender() ! Finished}
    }
  }
}
