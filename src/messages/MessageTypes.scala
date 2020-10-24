package messages

// Received by AccentActors
case class Response(answer: String) //holds the client's answer  what the client's answer was and sends next question back to server

// Received by Server
case class ClientAnswer(answer: String) //sent by client
case class NextQuestion(question: String) //sent by AccentActors
case object Finished //sent by AccentActor if the graph has reached a final answer
