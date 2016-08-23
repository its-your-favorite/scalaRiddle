/**
  * Created by Alex on 8/22/16.
  */
object solver {
  def main(args: Array[String]): Unit = {

    assert(testSolution(2, theSolution))    // test every permutation with N = 2 (e.g. red / black)
    assert(testSolution(4, theSolution))    // test every permutation with N = 4 (e.g. clubs, hearts, spades, diamonds)
    assert(testSolution(5, theSolution))    // test every permutation with N = 5

    assert(testSolution(2, anotherSolution))    // Now demonstrate with XOR 2
    assert(testSolution(4, anotherSolution))    // Xor with N = 4
    //This would Fail:
    //  assert(testSolution(5, anotherSolution))    // N = 5

    print( "Passed all cases" )
  }

  /**
    * Try given "solution"/"strategy" with EVERY possibility to see if it works
    * for n=2 there are four situations to test, for n=4 there are 256, and so on
    *
    *
    * @param n       n represents number of players, as well as number of possible values of each card
    * @param strategy
    */
  def testSolution(n: Int, strategy: (Int, Int, IndexedSeq[Int]) => Int): Boolean = {
    val permutations2 = calculateVariations(n, n)
    for (x <- permutations2.indices) {
      val playerResponses = (0 until n).map(i => (i, strategy(i, n, permutations2(x).slice(0, i) ++ permutations2(x).slice(i+1, n))))
      val correctPlayerResponses = playerResponses.filter(resp => resp._2 == permutations2(x)(resp._1))
      if (correctPlayerResponses.length == 0) { //if NONE of the players guessed their card, then the strategy failed
        print("Failed with test case: " + permutations2(x).toList.toString + ", players answered: " + playerResponses.toString)
        return false
      }
    }
    true
  }

  /**
    * Get all possibilities of [count] different values each ranging from 0-(maxRange-1)
    * [Really just a change-of-base problem]
    *  So for calculateVariations(2, 2) we get [[0,0], [0,1], [1,0], [1,1]]
    *   these would correspond to (black, black), (black, red), (red, black), (red, red) i.e. every possible setup
    *
    *  The code below utilizes the fact that this relationship is basically base conversion
    * @param count
    * @param maxRange (Must be <= 36)
    * @return
    */
  def calculateVariations(count: Int, maxRange: Int): IndexedSeq[IndexedSeq[Int]] = {
     val max = Math.pow(maxRange, count).asInstanceOf[Int]
     for (x <- 0 until max) yield { /*each variation */
         val leftBuffer = "0" * count // to ensure output is exactly $count characters long
         (leftBuffer + Integer.toString(x, maxRange)).takeRight(count).map(chr => Integer.parseInt(""+chr, maxRange))
     }
  }

  /**
    * Get the CORRECT response a player should make given the info they know
    *  Funny how much simpler the solution is than the testing code...
    *
    *  returns the number the player guesses (0 through N-1) where (0 = clubs, 1=hearts, or however you code them)
    *
    * @param playerNumber  Which player is deciding (because each player picks a different offset)
    * @param n             Number of players
    * @param cardsVisible  The cards visible to this player (e.g. he sees hearts, hearts, clubs)
    */
  def theSolution(playerNumber: Int, n: Int, cardsVisible: IndexedSeq[Int]): Int = {
    // visible_cards + my_answer = player_number guarantees uniqueness
    // therefore: my_answer = player_number - visible_cards
    ( playerNumber - cardsVisible.sum + n * n) % n /* Pretty simple solution, except negative modulo is always weird... */
    // if modulo worked with negatives we could simply write (playerNumber - cardsVisible.sum) % n
  }

  // a pseudo-solution
  def anotherSolution(playerNumber: Int, n: Int, cardsVisible: IndexedSeq[Int]): Int = {
    (playerNumber ^ cardsVisible.reduce(_ ^ _)) % n /* XOR is another way to combine the data */
  }
}
