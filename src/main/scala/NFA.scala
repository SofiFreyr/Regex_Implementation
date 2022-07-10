object NFA {
  def regexToNFA(regex: RegexExpr): State =
    regexToNFA(regex, Match())

  private def regexToNFA(regex: RegexExpr,
                         andThen: State): State = {
    regex match {
      case Literal(c) => new Consume(c, andThen)
      case Concat(first, second) => {
        // Convert first to an NFA. The "out" of that is
        // the result of converting second to an NFA.
        regexToNFA(first, regexToNFA(second, andThen))
      }
      case Or(l, r) => new Split(
        regexToNFA(l, andThen),
        regexToNFA(r, andThen)
      )

      case Repeat(r) =>
        val placeholder = new Placeholder(null)
        val split = new Split(
          // One path goes to the placeholder,
          // the other path goes back to r
          regexToNFA(r, placeholder),
          andThen
        )
        placeholder.pointingTo = split
        placeholder

      case Plus(r) =>
        regexToNFA(Concat(r, Repeat(r)), andThen)
    }
  }
}