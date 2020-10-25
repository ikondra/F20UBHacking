
import decisionTree.{Graph, VertexNode}
import org.scalatest.{BeforeAndAfter, FlatSpec}

class TestGraph extends FlatSpec with BeforeAndAfter{

  behavior of "insertVertexNode"

  it should "work with an empty list" in {
    val graph: Graph[String] = new Graph[String]
    val string1 = "Apples or bananas?"
    graph.insertVertexNode(string1)
    assert(graph.head.question == string1)
    assert(graph.vertexNodeList.indexWhere(x => x.question == string1) != -1)
  }

  it should "work multiple times" in {
    val graph: Graph[String] = new Graph[String]
    val string1 = "Apples or bananas?"
    val string2 = "Yee or Haw?"
    val string3 = "PS or XBOX?"
    graph.insertVertexNode(string1)
    graph.insertVertexNode(string2)
    graph.insertVertexNode(string3)
    assert(graph.head.question == string1)
    assert(graph.vertexNodeList.indexWhere(x => x.question == string1) != -1)
    assert(graph.vertexNodeList.indexWhere(x => x.question == string2) != -1)
    assert(graph.vertexNodeList.indexWhere(x => x.question == string3) != -1)
  }

  behavior of "insertEdge"

  it should "connect two nodes" in {
    val graph: Graph[String] = new Graph[String]
    val string1 = "Apples or bananas?"
    val string2 = "Yee or Haw?"
    val string3 = "PS or XBOX?"
    val node1 = graph.insertVertexNode(string1)
    val node2 = graph.insertVertexNode(string2)
    val node3 = graph.insertVertexNode(string3)
    val edge1 = graph.insertEdge(node1, node2, "Yes")
    val edge2 = graph.insertEdge(node1, node3, "No")
    assert(graph.edgeList.contains(edge1))
    assert(graph.edgeList.contains(edge2))
  }

  behavior of "nextVertex"

  it should "return the next node" in {
    val graph: Graph[String] = new Graph[String]
    val string1 = "Apples or bananas?"
    val string2 = "Yee or Haw?"
    val string3 = "PS or XBOX?"
    val node1 = graph.insertVertexNode(string1)
    val node2 = graph.insertVertexNode(string2)
    val node3 = graph.insertVertexNode(string3)
    val edge1 = graph.insertEdge(node1, node2, "Yes")
    val edge2 = graph.insertEdge(node2, node3, "No")
    assert(graph.nextVertex("Yes") == node2)
    assert(graph.currentVertexNode == node2)
    assert(graph.nextVertex("No") == node3)
    assert(graph.currentVertexNode == node3)
  }

  it should "return the node you give it if there is no next node" in {
    val graph: Graph[String] = new Graph[String]
    val string1 = "Apples or bananas?"
    val string2 = "Yee or Haw?"
    val string3 = "PS or XBOX?"
    val node1 = graph.insertVertexNode(string1)
    val node2 = graph.insertVertexNode(string2)
    val node3 = graph.insertVertexNode(string3)
    val edge1 = graph.insertEdge(node1, node2, "Yes")
    val edge2 = graph.insertEdge(node2, node3, "No")
    assert(graph.nextVertex("blah") == node1)
    assert(graph.currentVertexNode == node1)
  }

  behavior of "end"

  it should "return false when there is more than 1 edge attached" in {
    val graph: Graph[String] = new Graph[String]
    val string1 = "Apples or bananas?"
    val string2 = "Yee or Haw?"
    val string3 = "PS or XBOX?"
    val node1 = graph.insertVertexNode(string1)
    val node2 = graph.insertVertexNode(string2)
    val node3 = graph.insertVertexNode(string3)
    val edge1 = graph.insertEdge(node1, node2, "Yes")
    val edge2 = graph.insertEdge(node2, node3, "No")
    assert(!graph.end(node1))
    assert(!graph.end(node2))
  }

  it should "return true when there is less than 2 edges" in {
    val graph: Graph[String] = new Graph[String]
    val string1 = "Apples or bananas?"
    val string2 = "Yee or Haw?"
    val string3 = "PS or XBOX?"
    val node1 = graph.insertVertexNode(string1)
    val node2 = graph.insertVertexNode(string2)
    val node3 = graph.insertVertexNode(string3)
    val edge1 = graph.insertEdge(node1, node2, "Yes")
    val edge2 = graph.insertEdge(node2, node3, "No")
    assert(graph.end(node3))
  }
}
