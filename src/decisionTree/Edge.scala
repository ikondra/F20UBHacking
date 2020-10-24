package decisionTree

class Edge(prevNode: VertexNode, nextNode: VertexNode, value: String) {
  def matchesAnswer(answer: String): Boolean = {
    answer == value
  }
}
