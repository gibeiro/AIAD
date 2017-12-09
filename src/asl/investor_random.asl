// Agent investor_random in project pows

/* Initial beliefs */

beggining.

// join the game
!join.

/* Plans */

+!join : beggining <- 
	-beggining;
	join(investor);
	+canNegotiation.

/*Negotiation phase*/

+state(negotiation):canNegotiation <-
	.abolish(selling(_,_));
	.abolish(propose(_,_,_));
	+canAuction;
	-canNegotiation;
	+canInvestors.

//Reaction to the first selling(_,_) it has received

@s[atomic]
+selling(Company,MinPrice,Phase)[source(Manager)] : state(negotiation)<-
	!handleSelling(Manager,Company,MinPrice,Phase);
	.abolish(selling(Company,MinPrice,_)).
	
+!handleSelling(Manager,Company,MinPrice,Phase) : Phase = 1 <-
	.random(N);
	N1 = N*100;
    .random(N2);
    if(N1 < 70){
		Offer = N2 * 20000 + 15000;
		.broadcast(tell,propose(Company,Offer,Phase));
	}
.
+!handleSelling(Manager,Company,MinPrice,Phase) : not Phase = 1 <-
	.random(N);
	N1 = N*100;
	//Chance to offer a proposal to the manager
	if(N1 < 60){
		.random(N2);
		Offer = MinPrice + N2 * 500;
		.broadcast(tell,propose(Company,Offer,Phase));
	}.
	
/*Investors phase*/
	
+state(investors):canInvestors <-
	+canNegotiation;
	-canInvestors;
	//Nothing to be done here
	+canManagers.
	
/*Managers phase*/

+state(managers):canManagers <-
	+canInvestors;
	-canManagers;
	//Nothing
	+canPayment.
/*Payment phase*/

+state(payment):canPayment <-
	+canManagers;
	-canPayment;
	//Nothing to do
	+canAuction.
	
/*Auction phase*/

+state(auction):canAuction <-
	+canPayment;
	-canAuction;
	//Nothing to do
	+canNegotiation.
