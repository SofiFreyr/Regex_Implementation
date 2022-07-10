abstract class State

class Consume(val c: Char, val out: State) extends State
class Split(val out1: State, val out2: State) extends State
case class Match() extends State

class Placeholder(var pointingTo: State) extends State