Our project is an implementation of the Monte Carlo Tree Search algorithm to try to play a simple text game.

The rules of our game:
-You can do one of 5 actions per turn.
	-Fight an enemy that is the same level as you
	-Fight an enemy 1 level higher than you
	-Fight an enemy 2 levels higher than you
	-Fight an enemy 3 levels higher than you
	-Sleep

When you kill an enemy, you get exp equal to the monster's level. E.g. if I am level 4 and fight a monster two levels higher than me (and win), I gain 6 experience.

Fighting enemies will lower your health by 1/4 of your total health, 1/2 of your total health, 3/4  ofyour total health, or your total health - 1, respective of your combat options. If you try to fight an enemy of a higher level you will die, and you get no experience fighting an enemy of a lower level.

Sleeping will fully recover your health but brings you to the next day.

You as the player have 10 days to get to a high enough level to defeat a boss that appears at the end of the 10 days. The boss is level 15.

To summarize, get to the highest level you can in 10 days by fighting enemies in order to beat a boss at the end! Which our bot will attempt to do.

In order to get an understanding of the output that our the bot will produce, it helps to play the game yourself first.
To run the game to play yourself, go to the /src directory and type:
javac *.java
java Play

Once you have a good understanding of how the game plays, you can have the bot attempt to play the game by typing (in the same directory as above):
java MonteCarloTreeSearch

In the current implementation, the bot has been able to reach the boss but not reach the level requirement to win. I believe it can be fixed with a better heuristic, which we've been playing around with but haven't found the right values.