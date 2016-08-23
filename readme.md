# A solution to the August NSA riddle

https://www.nsa.gov/news-features/puzzles-activities/puzzle-periodical/2016/puzzle-periodical-05.shtml



## Solution Two players (SPOILER):

English description of algorithm for two players:
- Alice  Pick _opposite_ color that she sees on Bob's card
- Bob    Pick _same_ color he sees on Alice's card

The reason this works is because all possibilities can be divided into two subgroups:
- Either Alice's card color = Bob's card color
- Alice's card color != Bob's card color

Each player's guess accounts for exactly one of these scenarios (note that in no event will both Alice and Bob be right at the same time).


## Generalize to four (or N) players (SPOILER):

It gets a little more complicated here, but nothing you can't handle.

What we saw with two players is that we solved the problem by breaking the event space into N possible groups (N is the number of players) where each player accounted for 1 of these N possibilites with his/her guess.

With N players we simply must find an expression that each player can *uniquely* cover 1/Nth of the possibilities. Uniqueness is essential, if two players would ever be simultaneously correct then the solution as a whole must be invalid.

It turns out one easy expression is: (V0 + V1 + V2 ... V[N-1]) % N where V maps directly to a card value (e.g. black = 0, red = 1 or clubs = 0, hearts = 1, spades = 2 ...)

Because we mod by N, the expression (V0 + .. V[N-1]) % N, can have N distinct values. They'll all be equally probable too. Hence each possibile value of our expression has a likelihood of 1/N.

So if player 0 can assure that he guesses his card when (V0 + ... V[N-1])%N==0 (we could say player 0 is responsible for when the expression equals 0)
and if player 1 can assure that he guesses his card when (V0 + ... V[N-1])%N==1 (we could say player 1 is responsible for when the expression equals 1)
and so on, then we have achieved our goal, and we will be 100% sure that no matter what, one player must correctly guess his card.

A player decide what to guess by asking "What value must my card be, for [the expression] to equal the number I'm responsible for?" For example, if Hearts=1, and Alice sees 3 other Hearts at the table, and Alice is responsible for the case where (V0+ ... V[N-1])%N == 1, then the current total is 3, and she needs to get it up to 5 (because 5%4 is 1). So she should pick 2=Diamonds.

The answer to this is: (playerNumber - Sum(observedValues)) % N   [See note<sup>1</sup>]

[Using the above example (1 - 3) % 4 = 2 -> Diamonds)]

When you consider this case with N=2, you'll see it reduces to the simple english solution above.

### Afterthought

I mentioned above (V0 + V1 + V2 ... V[N-1]) % N  is "one easy expression." It's not the only one though. Another example
suitable expression can be found by replacing the addition with XOR. Everything else would be the same, but the expression
is (V0 ^ V1 ^ V2 ... V[N-1]) % N, and you would ^ playerNumber. I've implemented this solution in the code too.

It seems to me that the necessary criteria for such an expression are:
- It ranges across 0 through (N-1) with an equal distribution of each
- It is a function of all inputs
- Changing any one input (from values 0 to N-1) can move the expression to every other valid value (0 through N-1) from any valid value.

If you think about that, you may realize that this holds true for the XOR expression at N = 2, or N = 4, but not at N = 3.
(This is because the 3rd criteria isn't met when N isn't a power of 2. Imagine the current XOR is 1, and a player wishes to bump it to 2. The amount to XOR by is 3, which is out-of-range when N=3 [valid values are 0,1,2])

Footnote 1: As you'll see in the code, depending on how your language handles negative modulus, the expression may be more complicated. For whatever reason, Scala (and some other languages) say -1 % 5, for example, equals -1. However, wolfram alpha give the 4 (which seems more intuitive to me) [http://www.wolframalpha.com/input/?i=-1+mod+5].


I hope I've made a pretty complex riddle seem a little easier. Feel free to check out the code.

