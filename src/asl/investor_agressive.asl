// Agent investor_agressive in project pows

/* Rules */

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
	
minProfit(Color,Val):-
	fluct(Color,_,Ind) & maxshift(Color,Shift) & NInd = Ind - Shift & .max([NInd,0],NNInd) & vals(Color,L) & .nth(NNInd,L,Val).
	
avgProfit(Color,Val):-
	maxProfit(Color,Max) & minProfit(Color,Min) & Val = (Max + Min) / 2.
	
richest(Player) :- 
	.findall([V,I],player(investor,I,V),L) & .max(L,[V,I]) & I = Player & not(player(investor,A,V) & not(A = Player)).
	
poorest(Player) :- 
	.findall([V,I],player(investor,I,V),L) & .min(L,[V,I]) & I = Player & not(player(investor,A,V) & not(A = Player)).
	
nInvests(Player,N) :-
	.count(invests(Player,_,_),N).
	
last :-
	.count(player(investor,_,_),1).
	
goodValue(Color) :-
	fluct(Color,_,Index) & Index > 2.

badValue(Color) :-
	fluct(Color,_,Index) & Index < 3.

safe(Color) :-
	Color = blue | Color = green.
	
risky(Color) :-
	Color = yellow | Color = red.


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
+selling(Company,MinPrice,Phase)[source(Manager)] : state(negotiation) <-
	!handleSelling(Manager,Company,MinPrice,Phase);
	.abolish(selling(Company,MinPrice,_)).

//Se o profit maximo for menor do que MinPrice, nao fazer nada
+!handleSelling(Manager,Company,MinPrice,Phase) : company(Company,Color,Mult) & maxProfit(Color,Val) & MinPrice > Val * Mult.

//Se for o ultimo jogador, nao fazer nada
+!handleSelling(Manager,Company,MinPrice,Phase) : last.

//Se já tiver mais de N empresas, nao investir mais
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & nInvests(Me,N) & N > 6.

//Se for a primeira ronda e for o mais pobre, apostar numa cor qualquer
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & company(Company,Color,Mult) & Phase = 1 & poorest(Me) <-
	?avgProfit(Color,Avg);
	Offer = (Avg) * Mult;
	.broadcast(tell,propose(Company,Offer,Phase)).
	
//Se for a primeira ronda e for o mais rico, apostar em todas empresas excepto arriscadas de baixo indice
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & company(Company,Color,Mult) & Phase = 1  & richest(Me) & goodValue(Color) <-
	?avgProfit(Color,Avg);
	Offer = (Avg + 1000) * Mult;
	.broadcast(tell,propose(Company,Offer,Phase)).

//Se for a primeira ronda e nem for o mais rico nem o mais pobre, apostar em qualquer excepto insegura e com mau indice
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & company(Company,Color,Mult) & Phase = 1 & not poorest(Me) & not richest(Me) & not (risky(Color) & badValue(Color))<-
	?avgProfit(Color,Avg);
	Offer = (Avg + 500)*Mult;
	.broadcast(tell,propose(Company,Offer,Phase));
.

//Se nao for o mais pobre, tentar retirar as empresas seguras ao mais pobre
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & company(Company,Color,Mult) & not Phase = 1 & not poorest(Me) & safe(Color) &  propose(Company,_,Phase-1)[source(Player)] & poorest(Player) <-
	?maxProfit(Color,Max);
	.random(N);
	Offer = (MinPrice + N * 1600)*Mult;
	if(Offer < (Max-1000) * Mult){
		.broadcast(tell,propose(Company,Offer,Phase))
	}
.

//Se for o mais rico, tentar retirar empresas de alto indice a todos os outros
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & company(Company,Color,Mult) & not Phase = 1 & richest(Me) & goodValue(Color) &  propose(Company,_,Phase-1)[source(Player)] & not richest(Player) <-
	?maxProfit(Color,Max);
	.random(N);
	Offer = (MinPrice + N * 2000)*Mult;
	if(Offer < (Max+1000) * Mult){
		.broadcast(tell,propose(Company,Offer,Phase))
	}
.

//Se for o mais rico, apenas apostar em cores seguras com indices grandes
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & company(Company,Color,Mult) & not Phase = 1 & richest(Me) & safe(Color) <-
	?avgProfit(Color,Avg);
	.random(N);
	Offer = (MinPrice + N * 800)*Mult;
	if(Offer < (Avg+3000) * Mult){
		.broadcast(tell,propose(Company,Offer,Phase))
	}
.

//Se for o mais pobre, apostar em qualquer cor 
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & company(Company,Color,Mult) & not Phase = 1 & poorest(Me) <-
	?maxProfit(Color,Max);
	.random(N);
	Offer = (MinPrice + N * 1500) * Mult;
	if(Offer < (Max-1000) * Mult){
		.broadcast(tell,propose(Company,Offer,Phase))
	}
.

//Se nem for o mais rico nem o mais pobre, apostar em qualquer cor nao arriscada
+!handleSelling(Manager,Company,MinPrice,Phase) : .my_name(Me) & company(Company,Color,Mult) & not Phase = 1 & not poorest(Me) & not richest(Me) & not (risky(Color) & badValue(Color)) <-
	?maxProfit(Color,Max);
	.random(N);
	Offer = (MinPrice + N * 1200) * Mult;
	if(Offer < (Max-1000) * Mult){
		.broadcast(tell,propose(Company,Offer,Phase))
	}
.

+!handleSelling(Manager,Company,MinPrice,Phase).
	
/*Investors phase*/
	
+state(investors):canInvestors <-
	+canNegotiation;
	-canInvestors;
	//Nothing to do
	+canManagers.
	
/*Managers phase*/

+state(managers):canManagers <-
	+canInvestors;
	-canManagers;
	//Nothing to do
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
