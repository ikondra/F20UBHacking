package findAccent

import akka.actor._
import messages._
import decisionTree._

class AccentActor extends Actor {
  val accentTree: Graph[String] = new Graph[String]
  var currNode: VertexNode[String] = accentTree.currentVertexNode
  var nextQuestion: String = ""

  override def receive: Receive = {
    case FirstQuestion => { //sending the first question b/c no client answer
      nextQuestion = currNode.getQuestion()
      sender() ! nextQuestion
    }
    case ans: Response => { //receiving the client's answer to the question
      val theAnswer: String = ans.answer
      if(accentTree.end(currNode)) {
        sender() ! Finished(theAnswer)
      }
      else {
        currNode = accentTree.nextVertex(theAnswer)
        nextQuestion = currNode.getQuestion()
        sender() ! nextQuestion
      }
    }
  }
}
