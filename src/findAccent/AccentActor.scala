package findAccent

import akka.actor._
import messages._
import decisionTree._

class AccentActor extends Actor {
  val accentTree: Graph[String] = new Graph[String]
  var currNode: VertexNode[String] = accentTree.currentVertexNode
  var nextQuestion: String = ""

  override def receive: Receive = {
    case ans: Response => { //receiving the client's answer to the question
      val theAnswer: String = ans.answer
      if(accentTree.end(currNode)) {
        sender() ! Finished(theAnswer)
      }
      else {
        nextQuestion = currNode.getQuestion()
        currNode = accentTree.nextVertex(theAnswer)
        sender() ! nextQuestion
      }
    }
  }
}
