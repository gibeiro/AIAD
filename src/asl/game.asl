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
	.wait(2000);
	.print("---Negotiation Phase---");
	init(game);
	!doState.
	
+!doState : state(negotiation) <- 
	.wait(5000);
	.print("---Investors Phase---");
	next(phase);
	!doState.
	
+!doState : state(investors) <- 
	.wait(1000);
	.print("---Managers Phase---");
	next(phase);
	!doState.
	
+!doState : state(managers) <- 
	.wait(1000);
	.print("---Payment Phase---");
	next(phase);
	!doState.
	
+!doState : state(payment) <- 
	.wait(1000);
	.print("---Auction Phase---");
	next(phase);
	!doState.
	
+!doState : state(auction) <- 
	.wait(1000);
	.print("---Negotiation Phase---");
	next(phase);
	!doState.
	
+!doState : state(end) <- 
	.print("---Game ended---").
