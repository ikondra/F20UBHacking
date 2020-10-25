package decisionTree

import scala.collection.mutable.ArrayBuffer

class Graph[A] {

  var head: VertexNode[A] = null

  var currentVertexNode: VertexNode[A] = head

  var vertexNodeList: ArrayBuffer[VertexNode[A]] = ArrayBuffer()
  var edgeList: ArrayBuffer[Edge[A]] = ArrayBuffer()

  def insertVertexNode(value: A): VertexNode[A] = {
    if (head == null) {
      val node = new VertexNode[A](value)
      head = node
      currentVertexNode = node
      vertexNodeList = vertexNodeList :+ head
      node
    }

    else {
      val node = new VertexNode[A](value)
      vertexNodeList = vertexNodeList :+ new VertexNode[A](value)
      node
    }
  }

  def insertEdge(prevNode: VertexNode[A], nextNode: VertexNode[A], value: String): Edge[A] = {
    val edge: Edge[A] = new Edge[A](prevNode, nextNode, value)
    edgeList = edgeList :+ edge
    prevNode.incidentEdgeList = prevNode.incidentEdgeList :+ edge
    nextNode.incidentEdgeList = prevNode.incidentEdgeList :+ edge
    edge
  }

  // traversing
  def nextVertex(answer: String): VertexNode[A] = {
    // check that there is an edge that matches answer
    if (currentVertexNode.incidentEdgeList.indexWhere(e => e.value == answer) != -1) {
      var i = 0
      while (answer != currentVertexNode.incidentEdgeList(i).value) {
        i = i + 1
      }
      val node = currentVertexNode
      currentVertexNode = node.incidentEdgeList(i).nextNode
      node.incidentEdgeList(i).nextNode
    }
    // otherwise return back the current node
    else {
      currentVertexNode
    }
  }

  // true if end, false if not
  def end(vertexNode: VertexNode[A]): Boolean = {
    for (edge <- vertexNode.incidentEdgeList) {
      if (edge.prevNode == vertexNode) {
        return false
      }
    }
    true
  }

  def build(): Unit = {

  }
}
