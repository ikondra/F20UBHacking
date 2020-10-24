package decisionTree

class VertexNode(question: String) {
  var prevEdge: Edge = null
  var nextEdge: Edge = null

  def isBeginning(): Boolean = {
    prevEdge == null
  }

  def isLast(): Boolean = {
    nextEdge == null
  }
}
