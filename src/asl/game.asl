// Agent game in project pows

/* Initial beliefs and rules */

state(start).

/* Initial goals */

//Start game
!start.

/* Plans */

//When game starts, start game
+!start : true <- 
	.print("Waiting for players");
	.wait(2000).
