/* Useful logic */

firstN(_,[],0).
firstN([E1|R1],[E1|R2],N):-
	N2 = N-1 & first3(R1,R2,N2).

// Agent investor_random in project pows

/* Initial goals */

beggining.

// join the game
!join.

/* Plans */

+!join : true <- join(game);+canNegotiation.

/*Negotiation phase*/

+state(negotiation):canNegotiation <-
	+canAuction;
	-canNegotiation;
	+firstOne;
	+canInvestors.

//Indicator that its the first selling(_,_) it has received, so after receiving it, it shall wait 1s for all other sales, then choose which one(s) to take
+selling(Company,_,Phase)[source(Manager)] : Phase = 1 & state(negotiation) <-
	.random(N);
	N1 = N*100;
	//Chance to offer a proposal to the manager
	if(N1 < 25){
		.random(N2);
		Offer = N2 * 30000;
		.send(Manager,tell,propose(Company,Offer,Phase));
	}
	.abolish(selling(Company,MinPrice,_))
.

+selling(Company,MinPrice,Phase)[source(Manager)] : not Phase = 1 & state(negotiation) <-
	.random(N);
	N1 = N*100;
	//Chance to offer a proposal to the manager
	if(N1 < 50){
		.random(N2);
		Offer = MinPrice + N2 * 10000;
		.send(Manager,tell,propose(Company,Offer,Phase));
	}
	.abolish(selling(Company,MinPrice,_))
.
	
/*Investors phase*/
	
+state(investors):canInvestors <-
	.abolish(selling(_,_));
	+canNegotiation;
	-canInvestors;
	//Nothing to be done here
	+canManagers.
	
/*Managers phase*/

+state(managers):canManagers <-
	+canInvestors;
	-canManagers;
	!payIncome;
	+canPayment.
	
+!payIncome : true <-
	.my_name(Me);
	for(invests(Me,Company,Price) & owns(Manager,Company)){
		?player(investor,Me,Cash);
		if(Cash >= Price){
			.print("Im paying ",Manager," ",Price," for investing on the company ",Company);
			managerIncome(Manager,Me,Price);
			.wait(.count(ready(env),1),100);
		}else{
			.send(Manager,tell,noMoney(Company))
		}
	}.

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
