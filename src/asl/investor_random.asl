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

//Negotiation phase

+state(negotiation):canNegotiation <-
	+canAuction;
	-canNegotiation;
	+firstOne;
	+canInvestors.

//Indicator that its the first selling(_,_) it has received, so after receiving it, it shall wait 1s for all other sales, then choose which one(s) to take
+selling(_,_) : firstOne <- -firstOne;.wait(500);!chooseCompanies.

+!chooseCompanies <- 
	.findall([M,C],selling(M,C),LS);
	.shuffle(LS,LS2);
	.nth(0,LS2,[ToSend,Company]);
	.my_name(Me);
	?player(_,Me,Cash);
	.random(Rand);
	Value = (Cash*Rand)/2;
	.send(ToSend,tell,propose(Me,Company,Value)).
	
//Investors phase

+state(investors) <-
	.abolish(selling(_,_)).
	
+state(investors):canInvestors <-
	+canNegotiation;
	-canInvestors;
	//Nothing to be done here
	+canManagers.
	
/*Managers phase*/

+state(managers):canManagers <-
	+canInvestors;
	-canManagers;
	//Nothing to be done here
	+canPayment.
	
+payInc(Manager,Ammount) <-
	-payInc(Manager,Ammount);
	.my_name(Me);
	managerIncome(Manager,Me,Ammount);
	.print("Payed ",Manager, " an ammount of ",Ammount).
	
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
