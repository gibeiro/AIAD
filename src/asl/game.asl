// Agent game in project pows

/* Initial beliefs and rules */

state(start).

/* Initial goals */

//Start game
!doState.

/* Plans */

//When game starts, wait for players, then start negotiation phase
+!doState : state(start) <- 
	.print("Waiting for players");
	.wait(2000);
	init(game);
	-state(start);
	+state(negotiation);
	!doState.
	
+!doState : state(negotiation) <- 
	.print("in negotiation").
	
