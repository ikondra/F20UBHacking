package findAccent

import akka.actor._
import messages._
import decisionTree._

class AccentActor extends Actor {
  val accentTree: Graph[String] = new Graph[String]
  var currNode: VertexNode[String] = accentTree.currentVertexNode
  var nextQuestion: String = ""

  override def receive: Receive = {
    case FirstQuestion => { //sending the first question b/c no client answer for that
      nextQuestion = currNode.getQuestion()
      sender() ! nextQuestion
    }
    case ans: Response => { //receiving the client's answer to the question
      val clientAnswer: String = ans.answer
      currNode = accentTree.nextVertex(clientAnswer)
      nextQuestion = currNode.getQuestion()

      if(accentTree.end(currNode)) {
        sender() ! Finished(nextQuestion)
      }
      else {
        sender() ! nextQuestion
      }
    }
  }
}
