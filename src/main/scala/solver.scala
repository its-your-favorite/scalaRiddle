/**
  * Created by Alex on 8/22/16.
  */
object solver {
  def main(args: Array[String]): Unit = {

    assert(testSolution(2, getResponse))    // test every permutation with N = 2
    assert(testSolution(4, getResponse))    // test every permutation with N = 4
    print( "Passed all cases" )
  }

  /**
    * Call the given "solution"/"strategy" with EVERY possibility and see if it checks out
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
      if (correctPlayerResponses.length == 0) {
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
    * @param count
    * @param maxRange
    * @return
    */
  def calculateVariations(count: Int, maxRange: Int): IndexedSeq[IndexedSeq[Int]] = {
     val max = Math.pow(maxRange, count).asInstanceOf[Int]
     for (x <- 0 until max) yield { /*each variation */
         ("0" * count + Integer.toString(x, maxRange)).takeRight(count).map(chr => Integer.parseInt(""+chr, maxRange))
     }
  }

  /**
    * Get the response a player should make given the info they know (i.e. THE SOLUTION TO THE RIDDLE)
    *  Funny how much simpler the solution is that the testing code...
    *
    * @param playerNumber
    * @param n             Number of players
    * @param cardsVisible
    */
  def getResponse(playerNumber: Int, n: Int, cardsVisible: IndexedSeq[Int]): Int = {
    (playerNumber - cardsVisible.reduce(_ + _) % n + n ) % n /* Pretty simple solution, except negative modulo is always weird... */
  }
}
