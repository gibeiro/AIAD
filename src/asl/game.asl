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
	?turn(N);
	.print("----Turn ",N, "----");
	.print("---Negotiation Phase---");
	init(game);
	!doState.

	
+!doState : state(negotiation) <- 
	//Negotiation phase lasts for 5s
	.wait(6000);
	.print("---Investors Phase---");
	next(phase);
	!doState.
	
+!doState : state(investors) <- 
	//print fluctuations
	!doFluct;
	!doIIncomes;
	.wait(500);
	.print("---Managers Phase---");
	next(phase);
	!doState.
	
+!doFluct : true <-
	.print("Doing fluctuation...");
	fluctuate(game);
	.wait(.count(fluct(Color,_,_),4),1000);
	for(fluct(Color,Val,Index)){
		.print(Color," companies fluctuation at ",Val,"(",Index,")");
	}.
@ii[atomic]
+!doIIncomes : true <-
	for(invests(Investor,Company,_)){
		!doIIncome(Investor,Company)
	}.
	
+!doIIncome(Investor,Company):company(Company,Color,Mult) & fluct(Color,Value,_) <-
	Income = Value * Mult * 1000;
	investorIncome(Investor,Income);
	.print("Income of ",Income," to ",Investor," for the ",Color," company ",Company);
	.wait(.count(ready(env),1),100).
+!doIIncome(Investor,Company).
	
+!doState : state(managers) <- 
	!doMIncomes;
	.wait(500);
	.print("---Payment Phase---");
	next(phase);
	!doState.

@mi[atomic]
+!doMIncomes : true <-
	for(player(investor,Inv,_)){
		!doMIncome(Inv);
	}		
.
+!doMIncome(Inv) <-
	for(invests(Inv,Company,Price) & owns(Manager,Company)){
		!tryPay(Inv,Price,Manager,Company);
	}
.
+!tryPay(Inv,Price,Manager,Company) : player(_,Inv,Cash) <-
	if(Cash >= Price){
		.print(Inv," paying ",Manager," ",Price," for investing on the company ",Company);
		managerIncome(Manager,Inv,Price);
	}else{
		bankrupt(Inv);
		.wait(.count(ready(env),1),100);
	}.
//if it fails its because player went bankrupt
+!tryPay(Inv,Price,Manager,Company).
	
+!doState : state(payment) <- 
	.wait(1000);
	.print("---Auction Phase---");
	next(phase);
	!doState.
	
+!doState : state(auction) <- 
	!doAuctions;
	?turn(N);
	.print("----Turn ",N, "----");
	.print("---Negotiation Phase---");
	next(phase);
	!doState.
	
+!doAuctions : true <-
	.count(player(manager,_,_),NMan);
	NAuct = (NMan * 2) - 1; // Number of auctions = 2*NManagers - 1
	+nAuctions(NAuct);
	while(nAuctions(N) & N > 0){
		pop(auction);
		!doAuction;
		-+nAuctions(N-1);
	}.
//Do an auction of a company
+!doAuction : auction(Company,Color,Mult) <-
	.print("Auctioning ", Color ," company ", Company, " with mult of" , Mult);
	.broadcast(tell,aucStart);
	.wait(place_bid(_),2500);
	.wait(200);
	if(.count(place_bid(_),NN) & NN > 0){
		.findall(offer(V,M),place_bid(V)[source(M)],Offers);
		.max(Offers,offer(Val,Man));
		.print("Winner is ",Man, " with an offer of ",Val);
		.send(Man,tell,youWon);
		sellTo(Man,Val);
		.wait(50);
	}else{
		.print("No one wanted to buy this company");
	}
	clean(auction);
	.abolish(offer(_,_));
	.abolish(place_bid(_)).
	

//Do nothing if there are no more companies to auction
+!doAuction : not auction(_,_,_).
	
	
+!doState : state(end) <- 
	.print("---Game ended---");
	.findall(scoreM(C1,N1),player(manager,N1,C1),List1);
	.max(List1,scoreM(C1,W1));
	.print(W1," was the winning manager");
	if(.count(player(investor,_,_),NINV) & NINV > 0){
		.findall(scoreI(C2,N2),player(investor,N2,C2),List2);
		.max(List2,scoreI(C2,W2));
		.print(W2," was the winning investor");
	}else{
		.print("No investor won because all went bankrupt");
	}.
