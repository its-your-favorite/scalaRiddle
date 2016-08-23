# A solution to the August NSA riddle

https://www.nsa.gov/news-features/puzzles-activities/puzzle-periodical/2016/puzzle-periodical-05.shtml

If you're curious to solve it on your own, do not read part below as it is a spoiler.

## Two players:

English description of algorithm for two players:
- Alice  Pick opposite color that she sees on Bob's card
- Bob    Pick Same color he sees on Alice's card

The reason this works is because all possibilies can be divided into two subgroups: 
- Either Alice's card color = Bob's card color
- Alice's card color != Bob's card color

Each player's guess accounts for exactly one of these scenarios (note that in no event will both Alice and Bob be right at the same time).


## Generalize to four (or N) players:

It gets a little more complicated here, but nothing you can't handle.

What we saw with two players is that we solved the problem by breaking the event space into N possible groups (N is the number of players) where each player accounted for 1 of these N possibilites with his/her guess.

With N players we simply must find an expression that each player can *uniquely* cover 1/Nth of the possibilities. Uniqueness is essential, if two players would ever be simultoneously correct then the solution as a whole must be invalid.

It turns out one easy expression is: (V0 + V1 + V2 ... V[N-1]) % N where V represents a numerical value (e.g. black = 0, red = 1 or clubs = 0, hearts = 1, spades = 2 ...) 

Each of the N players can cover one of the N possible values of (V0 + .. V[N-1])

So if player 0 can assure that he guesses his card when (V0 + ... V[N-1])%N==0
and if player 1 can assure that he guesses his card when (V0 + ... V[N-1])%N==1
and so on, then we have achieved our goal.

A player can do this, by asking "What value must my card be, for [the expression] to equal the number I'm responsible for?" For example, if Clubs =0, and Alice sees 3 other Clubs at the table, and Alice is responsible for the case where (V0+ ... V[N-1])%N == 0, she should guess clubs.

The answer to this is: (playerNumber - Sum(observedValues)) % N

When you consider this case with N=2, you'll see it reduces to the simple english solution above.

I hope I've made a pretty complex riddle seem a little easier. Or checkout the source code yourself.


