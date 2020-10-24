package decisionTree

class VertexNode[A](question: A) {
  var incidentEdgeList: Array[Edge[A]] = Array()
}
