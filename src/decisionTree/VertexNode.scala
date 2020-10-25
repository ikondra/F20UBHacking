package decisionTree

class VertexNode[A](val question: A) {
  var incidentEdgeList: Array[Edge[A]] = Array()
}
