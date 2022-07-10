import  scala.util.parsing.combinator.RegexParsers

abstract class RegexExpr

// ., a, b
case class Literal(c: Char) extends RegexExpr

// a|b
case class Or(expr1: RegexExpr, expr2: RegexExpr) extends RegexExpr

// ab -> Concat(a,b); abc -> Concat(a, Concat(b, c))
case class Concat(first: RegexExpr, second: RegexExpr) extends RegexExpr

// a*
case class Repeat(expr: RegexExpr) extends RegexExpr

// a+
case class Plus(expr: RegexExpr) extends RegexExpr