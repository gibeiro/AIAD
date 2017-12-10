// Agent manager_safe in project pows

/* Initial beliefs and rules */

beggining.

vals(red,[-20000,-10000,0,30000,40000,50000,60000,70000]).
vals(yellow,[-10000,0,0,30000,40000,40000,60000,60000]).
vals(green,[0,10000,20000,30000,30000,40000,50000,60000]).
vals(blue,[20000,20000,20000,30000,30000,30000,40000,40000]).
maxshift(red,7).
maxshift(yellow,3).
maxshift(green,2).
maxshift(blue,1).

maxProfit(Color,Val):-
	fluct(Color,_,Ind) & maxshift(Color,Shift) & NInd = Ind + Shift & .min([NInd,7],NNInd) & vals(Color,L) & .nth(NNInd,L,Val).
maxProfit(Color,40000).
minProfit(Color,Val):-
	fluct(Color,_,Ind) & maxshift(Color,Shift) & NInd = Ind - Shift & .max([NInd,0],NNInd) & vals(Color,L) & .nth(NNInd,L,Val).
minProfit(Color,20000).
	
avgProfit(Color,Val):-
	maxProfit(Color,Max) & minProfit(Color,Min) & Val = (Max + Min) / 2.
avgProfit(Color,30000).
	
richest(Player) :- 
	.findall([V,I],player(manager,I,V),L) & .max(L,[V,I]) & I = Player & not(player(manager,A,V) & not(A = Player)).
	
poorest(Player) :- 
	.findall([V,I],player(manager,I,V),L) & .min(L,[V,I]) & I = Player & not(player(manager,A,V) & not(A = Player)).
	
goodValue(Color) :-
	fluct(Color,_,Index) & Index > 2.

veryGoodValue(Color) :-
	fluct(Color,_,Index) & Index > 4.

badValue(Color) :-
	fluct(Color,_,Index) & Index < 3.

safe(Color) :-
	Color = blue | Color = green.
	
risky(Color) :-
	Color = yellow | Color = red.

/* Initial goals */

// join the game
!join.

/* Plans */

+!join : beggining <- 
	-beggining;
	join(manager);
	+canNegotiation.

/*Negotiation phase*/

//React to state change to negotiation
+state(negotiation):canNegotiation <-
	.abolish(didPhase(_,_));
	.abolish(propose(_,_,_));
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
+propose(Comp,_,Phase) : not didPhase(Comp,Phase) & state(negotiation) & .my_name(Me) & owns(Me,Comp) <-
	+didPhase(Comp,Phase);
	//wait for all proposals
	.wait(200);
	!handlePropose(Comp,Phase);
	.abolish(propose(Comp,_,Phase));
.
	
+!handlePropose(Comp,Phase) : state(negotiation) <-
	.findall(b(V,A),propose(Comp,V,Phase)[source(A)],List);
	.findall(A,propose(Comp,_,Phase)[source(A)],ListBuyers);
	.length(List,L);
	if(L == 1){
		.max(List,b(V,W));
		if(V > 10000){
			.print("Winner of company ", Comp," is ",W, " with an offer of ",V);
			.my_name(Me);
			acceptProposal(W,Me,Comp,V);
		}
	}
	if(not L == 1){
		.print("I received ", L, " proposals for the company ", Comp, ", trying again");
		.max(List,b(V,W));
		//Price has to be bigger than previously biggest offer
		.send(ListBuyers,tell,selling(Comp,V,Phase+1));
	}.
+!handlePropose(Comp,Phase) : not state(negotiation).

/*Investors phase*/

+state(investors):canInvestors <-
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

+!payManag :.my_name(Me) & player(_,Me,Cash) & .count(owns(Me,_),NC) & Cash < NC * 10000 <-
	.findall([N,Color],owns(Me,Company) & company(Company,Color,_) & maxshift(Color,N),L);
	.max(L,[N,Color]);
	.findall([Index,Company],owns(Me,Company) & company(Company,Color,_) & fluct(Color,_,Index),L2);
	.min(L2,[Index,ToSell]);
	sellCompany(Me,ToSell);
	.print("Sold company ",ToSell, " for 5000");
	.wait(20);
	!payManag;
.
+!payManag :.my_name(Me) & player(_,Me,Cash) & .count(owns(Me,Company),NC) & Cash >= NC * 10000 <-
	for(owns(Me,Company)){
		payFee(Me,10000);
		.print("Payed fee for owning the company ",Company);
	}
.
/*Auction phase*/

+state(auction):canAuction <-
	.abolish(bought(_));
	+bought(0);
	+canPayment;
	-canAuction;
	//Code
	+canNegotiation.

+aucStart[source(S)] <-
	!handleAuc(S);
	.abolish(aucStart).
	
+youWon : bought(N) <-
	-+bought(N+1);
	-youWon.
//nao compra se nao tiver um indice muito bom
+!handleAuc(Game) : auction(Company,Color,Mult) & .my_name(Me) & player(_,Me,Cash) & not veryGoodValue(Color).
//tenta comprar se for o mais rico e tiver comprado menos de 2
+!handleAuc(Game) : auction(Company,Color,Mult) & .my_name(Me) & player(_,Me,Cash) & richest(Me) & bought(N) & N < 2 <-
	?avgProfit(Color,Avg);
	?maxProfit(Color,Max);
	.random(Rand);
	Value = Mult * ((Avg + Max)/2 + Rand * 10000);
	if(Value < Cash & Value < Max){
		.broadcast(tell,place_bid(Value))
	}.
//Nao compra se ja comprou 2 e é o mais rico
+!handleAuc(Game) : auction(Company,Color,Mult) & .my_name(Me) & player(_,Me,Cash) & richest(Me) & bought(N) & N >= 2.
//tenta comprar se nao for o mais rico, mas dá uma oferta menor
+!handleAuc(Game) : auction(Company,Color,Mult) & .my_name(Me) & player(_,Me,Cash) & not richest(Me) & bought(N) & N < 1 <-
	?avgProfit(Color,Avg);
	?maxProfit(Color,Max);
	.random(Rand);
	Value = Mult * ((Avg + Max)/2 + Rand * 7000);
	if(Value < Cash & Value < Max){
		.broadcast(tell,place_bid(Value))
	}.
//Nao compra se ja comprou 2 e é o mais rico
+!handleAuc(Game) : auction(Company,Color,Mult) & .my_name(Me) & player(_,Me,Cash) & richest(Me) & bought(N) & N >= 1.

+!handleAuc(Game).

	