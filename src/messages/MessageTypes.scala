package messages

// Received by AccentActors
case object FirstQuestion
case class Response(answer: String) //holds the client's answer  what the client's answer was and sends next question back to server
case object Restart //reinitializes the graph and does the whole thing again

// Received by Server
case class NextQuestion(question: String) //sent by AccentActors
case class Finished(accent: String) //sent by AccentActor if the graph has reached a final answer
