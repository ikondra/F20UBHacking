package decisionTree

class Graph[A] {

  var head: VertexNode[A] = null

  val currentVertexNode: VertexNode[A] = null

  var vertexNodeList: Array[VertexNode[A]] = Array()
  var edgeList: Array[Edge[A]] = Array()

  def insertVertexNode[A](value: A): Unit = {
    if (head == null) {
      head = new VertexNode(value)
      vertexNodeList = vertexNodeList :+ head
    }

    else {
      vertexNodeList = vertexNodeList :+ new VertexNode[A](value)
    }
  }

  def insertEdge[A, B](prevNode: VertexNode[A], nextNode: VertexNode[A], value: String): Unit = {
      val edge: Edge[A] = new Edge[A](prevNode, nextNode, value)
      edgeList = edgeList :+ edge
      prevNode.incidentEdgeList = prevNode.incidentEdgeList :+ edge
      nextNode.incidentEdgeList = prevNode.incidentEdgeList :+ edge
  }

    // traversing
  def nextVertex(answer: String): VertexNode[A] = {
    // check that there is an edge that matches answer
    if (currentVertexNode.incidentEdgeList.indexWhere(e => e.value == answer) != -1) {
      var i = 0
      while (answer != currentVertexNode.incidentEdgeList(i).value) {
        i = i + 1
      }
      currentVertexNode.incidentEdgeList(i).nextNode
    }
    // otherwise return back the current node
    else {
      currentVertexNode
    }
  }
    // determine end
  def end(vertexNode: VertexNode[A]): Boolean = {
    if (currentVertexNode.incidentEdgeList.length < 2) {
      false
    }
    else {
      true
    }
  }

}
