package decisionTree

class Edge[A](val prevNode: VertexNode[A], val nextNode: VertexNode[A], val value: String) {
  def matchesAnswer(answer: String): Boolean = {
    answer == value
  }
}
