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
	//Nothing to do here
	+canManagers.
	
/*Managers phase*/

+state(managers):canManagers <-
	+canInvestors;
	-canManagers;
	!collectIncome;
	+canPayment.
	
+!collectIncome : true <-
	.my_name(Me);
	for(owns(Me,Company) & invests(Investor,Company,Price)){
		?player(investor,Investor,Cash);
		if(Cash >= Price){
			.send(Investor,tell,payInc(Me,Price));
		}else{
			bankrupt(Investor);
		}
	}.
/*Payment phase*/

+state(payment):canPayment <-
	+canManagers;
	-canPayment;
	!payManag;
	+canAuction.
	
+!payManag : true <-
	.my_name(Me);
	//Se nao tiver dinheiro para pagar todas as empresas, vender suficientes até ser possível
	while(.count(owns(Me,_),NC) & player(_,Me,Cash) & Cash < NC * 10000){
		.findall(Company,owns(Me,Company),List);
		.shuffle(List,List2);
		.nth(0,List2,ToSell);
		sellCompany(Me,ToSell);
		.print("Sold company ",ToSell, " for 5000");
	}
	for(owns(Me,Company)){
		payFee(Me,10000);
		.print("Payed 10000 fee for owning the company ",Company);
	}.
	
/*Auction phase*/

+state(auction):canAuction <-
	+canPayment;
	-canAuction;
	//Code
	+canNegotiation.