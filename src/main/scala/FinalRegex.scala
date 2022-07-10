object FinalRegex {
  def fullMatch(input: String, pattern: String) = {
    val parsed = RegexParser(pattern).getOrElse(
      throw new RuntimeException("Failed to parse regex")
    )
    val nfa = NFA.regexToNFA(parsed)
    NFAEvaluator.evaluate(nfa, input)
  }

  def matchAnywhere(input: String, pattern: String) =
    fullMatch(input, ".*(" + pattern + ").*")
}
