object Testing extends App {
  println(FinalRegex.fullMatch("aaaaab", "a*b")) // True
  println(FinalRegex.fullMatch("aaaabc", "a*b")) // False
  println(FinalRegex.matchAnywhere("abcde", "cde")) // True
}
