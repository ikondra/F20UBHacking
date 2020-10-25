package findAccent

import akka.actor._
import messages._
import decisionTree._

class AccentActor extends Actor {

  val accentTree: Graph[String] = new Graph[String]
  val rhotic = accentTree.insertVertexNode("Do you pronounce Rs at the end of words? (rhotic r)")
  val initialH = accentTree.insertVertexNode("Do you pronounce white like \"hwite\"? (initial h)")
  accentTree.insertEdge(rhotic, initialH, "yes")
  val irish = accentTree.insertVertexNode("Irish")
  accentTree.insertEdge(initialH, irish, "yes")
  val intrusiveR = accentTree.insertVertexNode("Do you insert Rs into words that don't have them? Ex. wash is pronounced like \"warsh\" (Intrusive R)")
  accentTree.insertEdge(initialH, intrusiveR, "no")

  val midlands = accentTree.insertVertexNode("Midlands")
  accentTree.insertEdge(intrusiveR, midlands, "yes")
  val joinSyllables = accentTree.insertVertexNode("Do join syllables like: didn't -> didnae or going to -> ginnae?")
  accentTree.insertEdge(intrusiveR, joinSyllables, "no")

  val scottish = accentTree.insertVertexNode("Scottish")
  accentTree.insertEdge(joinSyllables, scottish, "yes")
  val monophthong = accentTree.insertVertexNode("Do your Os have one sound? No gliding up or down. (monophthong)")
  accentTree.insertEdge(joinSyllables, monophthong, "no")

  val upperMidwestern = accentTree.insertVertexNode("Upper Midwestern")
  accentTree.insertEdge(monophthong, upperMidwestern, "yes")
  val vowelShift = accentTree.insertVertexNode("Do you pronounce bus like boss, block like black, or bit like bet? (Northern Cities Vowel Shift)")
  accentTree.insertEdge(monophthong, vowelShift, "no")

  val north = accentTree.insertVertexNode("North")
  accentTree.insertEdge(vowelShift, north, "yes")
  val west = accentTree.insertVertexNode("West")
  accentTree.insertEdge(vowelShift, west, "no")

  val subTH = accentTree.insertVertexNode("Do you sub th for f or v? Ex. think -> fink and northern -> norvern")
  accentTree.insertEdge(rhotic, subTH, "no")
  val cockney = accentTree.insertVertexNode("Cockney")
  accentTree.insertEdge(subTH, cockney, "yes")
  val gdrop = accentTree.insertVertexNode("Do you drop the g off of -ing? Ex. fishing -> fishin")
  accentTree.insertEdge(subTH, gdrop, "no")

  val ydrop = accentTree.insertVertexNode("Do you pronounce the y sound at the beginning of use?")
  accentTree.insertEdge(gdrop, ydrop, "yes")
  val nyc = accentTree.insertVertexNode("NYC/Boston")
  accentTree.insertEdge(gdrop, nyc, "no")

  val south = accentTree.insertVertexNode("Southern")
  accentTree.insertEdge(ydrop, south, "yes")
  val welsh = accentTree.insertVertexNode("Welsh")
  accentTree.insertEdge(ydrop, welsh, "no")


  var currNode: VertexNode[String] = accentTree.currentVertexNode
  var nextQuestion: String = ""

  override def receive: Receive = {
    case FirstQuestion => { //sending the first question b/c no client answer for that
      nextQuestion = currNode.question
      sender() ! nextQuestion
    }
    case ans: Response => { //receiving the client's answer to the question
      val clientAnswer: String = ans.answer
      currNode = accentTree.nextVertex(clientAnswer)
      nextQuestion = currNode.question

      if(accentTree.end(currNode)) {
        sender() ! Finished(nextQuestion)
      }
      else {
        sender() ! nextQuestion
      }
    }
  }
}
