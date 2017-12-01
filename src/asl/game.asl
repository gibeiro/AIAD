// Agent game in project pows

/* Initial beliefs and rules */

state(start).

/* Initial goals */

//Start game
!start.

/* Plans */

//When game starts, wait for players
+!start : true <- 
	.print("Waiting for players");
	.wait(2000).
	
