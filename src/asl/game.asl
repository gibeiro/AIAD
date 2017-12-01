// Agent game in project pows

/* Initial beliefs and rules */

beggining.

/* Initial goals */

//Do the action associated with current state
!doState.

/* Plans */

//When game starts, wait for players, then start negotiation phase
+!doState : beggining <- 
	-beggining;
	.print("Waiting for players");
	.wait(200);
	init(game);
	!doState.
	
+!doState : state(negotiation) <- 
	.print("---Negotiation Phase---");
	.wait(200);
	next(phase);
	!doState.
	
+!doState : state(investors) <- 
	.print("---Investors Phase---");
	.wait(200);
	next(phase);
	!doState.
	
+!doState : state(managers) <- 
	.print("---Managers Phase---");
	.wait(200);
	next(phase);
	!doState.
	
+!doState : state(payment) <- 
	.print("---Payment Phase---");
	.wait(200);
	next(phase);
	!doState.
	
+!doState : state(auction) <- 
	.print("---Auction Phase---");
	.wait(200);
	next(phase);
	!doState.
	
+!doState : state(end) <- 
	.print("---Game ended---").
