// Agent manager_random in project pows

/* Initial beliefs and rules */

beggining.

/* Initial goals */

// join the game
!join.

/* Plans */

+!join : beggining <- 
	-beggining;
	join(game);
	+canNegotiation.

/*Negotiation phase*/

//React to state change to negotiation
+state(negotiation):canNegotiation <-
	+canAuction;
	-canNegotiation;
	.print("Im open for proposals!");
	!startCNP;
	+canInvestors.

//Send to all investors the companies i'm selling
+!startCNP : true <-
	.findall(Name,player(investor,Name,_),LI);
	.my_name(Me);
	for(owns(Me,Comp)){
		.send(LI,tell,selling(Me,Comp))
	};
	+firstOne.
//Process proposals from investors
+propose(_,_,_) : firstOne<-
	-firstOne;.wait(1000);!chooseProposals.
	
+!chooseProposals <- 
	.findall([I,C,V],propose(I,C,V),LS);
	.shuffle(LS,LS2);
	.nth(0,LS2,[Inv,Comp,Val]);
	.my_name(Me);
	acceptProposal(Inv,Me,Comp,Val);
	.print("Accepted proposal for company ", Comp, " by investor ",Inv," for ",Val).

/*Investors phase*/

+state(investors):canInvestors <-
	+canNegotiation;
	-canInvestors;
	//Code
	+canManagers.
	
/*Managers phase*/

+state(managers):canManagers <-
	+canInvestors;
	-canManagers;
	//Code
	+canPayment.
	
/*Payment phase*/

+state(payment):canPayment <-
	+canManagers;
	-canPayment;
	//Code
	+canAuction.
	
/*Auction phase*/

+state(auction):canAuction <-
	+canPayment;
	-canAuction;
	//Code
	+canNegotiation.