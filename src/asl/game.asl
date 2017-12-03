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
	fluctuate(game);
	//print fluctuations
	.print("Doing fluctuation");
	for(fluct(Color,Val)){
		.print(Color," companies fluctuation at ",Val);
	};
	investorsIncome(game);
	for(invests(Investor,Company,_)){
		?company(Company,Color,Mult);
		?fluct(Color,Value);
		Income = Value * Mult;
		investorIncome(Investor,Income);
		.print("Income of ",Income," to ",Investor," for the ",Color," company ",Company);
	};
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
