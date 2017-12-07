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
	!startEA;
	+canInvestors.

//Send to all investors the companies i'm selling
+!startEA : true <-
	.findall(Name,player(investor,Name,_),LI);
	.my_name(Me);
	.print("I'm open for proposals!");
	for(owns(Me,Comp)){
		.send(LI,tell,selling(Comp,0,1));
	}.

@pb1[atomic]
+propose(Comp,_,Phase) : not didPhase(Comp,Phase) & state(negotiation) <-
	//wait for all proposals
	.wait(500);
	.findall(b(V,A),propose(Comp,V,Phase)[source(A)],List);
	.findall(A,propose(Comp,_,Phase)[source(A)],ListBuyers);
	.length(List,L);
	if(L == 1){
		.max(List,b(V,W));
		.print("Winner of company ", Comp," is ",W, " with an offer of ",V);
		.my_name(Me);
		acceptProposal(W,Me,Comp,V);
	}
	if(not L == 1){
		.print("I received ", L, " proposals for the company ", Comp, ", trying again");
		.max(List,b(V,W));
		//Price has to be bigger than previously biggest offer
		Phase2 = Phase+1;
		.send(ListBuyers,tell,selling(Comp,V,Phase2));
	}
	.abolish(propose(Comp,_,Phase));
	+didPhase(Comp,Phase);
.

/*Investors phase*/

+state(investors):canInvestors <-
	.abolish(didPhase(_,_));
	+canNegotiation;
	-canInvestors;
	//Nothing to do here
	+canManagers.
	
/*Managers phase*/

+state(managers):canManagers <-
	+canInvestors;
	-canManagers;
	//Nothing to do here
	+canPayment.

/*Payment phase*/

+state(payment):canPayment <-
	+canManagers;
	-canPayment;
	!payManag;
	+canAuction.

+!payManag : true <-
	.my_name(Me);
	//Se nao tiver dinheiro para pagar todas as empresas, vender suficientes até ser possível
	while( player(_,Me,Cash) & .findall(Company,owns(Me,Company),List) & .length(List,NC) & Cash < NC * 10000){
		.shuffle(List,List2);
		.nth(0,List2,ToSell);
		sellCompany(Me,ToSell);
		.print("Sold company ",ToSell, " for 5000");
		.wait(.count(ready(env),1),100)
	}
	for(owns(Me,Company)){
		payFee(Me,10000);
		.print("Payed fee for owning the company ",Company);
	}.

/*Auction phase*/

+state(auction):canAuction <-
	+canPayment;
	-canAuction;
	//Code
	+canNegotiation.
	
+aucStart[source(S)] : auction(Company,Color,Mult) & .my_name(Me) & player(_,Me,Cash)<-
	.random(Rand);
	Value = Rand*20000+20000;
	if(Value < Cash){
		.send(S,tell,place_bid(Value))
	}
	.abolish(aucStart).
	