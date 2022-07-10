import scala.util.parsing.combinator.RegexParsers

object RegexParser extends RegexParsers {
  def charLit: Parser[RegexExpr] = ("""\w""".r | ".") ^^ {
    char => Literal(char.head)
  }

  def parenExpr: Parser[RegexExpr] = "(" ~> highExpr <~ ")"
  def lit: Parser[RegexExpr] = charLit | parenExpr
  def repeat: Parser[RegexExpr] = lit <~ "*" ^^ { case l => Repeat(l) }
  def plus: Parser[RegexExpr] = lit <~ "+" ^^ { case p => Plus(p) }
  def lowExpr: Parser[RegexExpr] = repeat | plus | lit
  def concat: Parser[RegexExpr] = rep(lowExpr) ^^ { case list => listToConcat(list)}
  def midExpr: Parser[RegexExpr] = concat | lowExpr
  def or: Parser[RegexExpr] = midExpr ~ "|" ~ midExpr ^^ { case l ~ "|" ~ r => Or(l, r)}
  def highExpr: Parser[RegexExpr] = or | midExpr

  def listToConcat(list: List[RegexExpr]): RegexExpr = list match {
    case head :: Nil => head
    case head :: rest => Concat(head, listToConcat(rest))
  }

  def apply(input: String): Option[RegexExpr] = {
    parseAll(highExpr, input) match {
      case Success(result, _) => Some(result)
      case _ : NoSuccess => None
    }
  }
}