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
	
	
+endSale(_) : .count(owns(Me,_),N) & .count(endSale(_),N2) & N = N2 <-
	.my_name(Me);
	.send(game,tell,doneSelling(Me)).
	
+!doState : state(negotiation) <- 
	.wait(10000);
	.print("---Investors Phase---");
	next(phase);
	!doState.
	
+!doState : state(investors) <- 
	//print fluctuations
	!doFluct;
	!doIncome;
	.wait(500);
	.print("---Managers Phase---");
	next(phase);
	!doState.
	
+!doFluct : true <-
	.print("Doing fluctuation...");
	fluctuate(game);
	.wait(.count(fluct(Color,_),4),1000);
	for(fluct(Color,Val)){
		.print(Color," companies fluctuation at ",Val);
	}.
	
+!doIncome : true <-
	for(invests(Investor,Company,_)){
		?company(Company,Color,Mult);
		?fluct(Color,Value);
		Income = Value * Mult * 1000;
		investorIncome(Investor,Income);
		.wait(.count(ready(env),1),100);
		.print("Income of ",Income," to ",Investor," for the ",Color," company ",Company);
	}.
	
+!doState : state(managers) <- 
	.wait(500);
	.print("---Payment Phase---");
	next(phase);
	!doState.
	
+!doState : state(payment) <- 
	.wait(500);
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
	.findall(Name,player(manager,Name,_),LI);
	.send(LI,tell,aucStart);
	.wait(place_bid(_),2500);
	.wait(500);
	if(.count(place_bid(_),NN) & NN > 0){
		.findall(offer(V,M),place_bid(V)[source(M)],Offers);
		.max(Offers,offer(Val,Man));
		.print("Winner is ",Man, " with an offer of ",Val);
		sellTo(Man,Val);
	}else{
		.print("No one wanted to buy this company");
	}
	clean(auction);
	.abolish(offer(_,_));
	.abolish(place_bid(_)).
	

//Do nothing if there are no more companies to auction
+!doAuction : not auction(_,_,_).
	
	
+!doState : state(end) <- 
	.print("---Game ended---").
