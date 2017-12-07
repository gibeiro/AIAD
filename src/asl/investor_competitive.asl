/* Beliefs */

beggining.

recommended(red,Index,Ofr):-
	Ofr = 30000 + (Index-3)*10000.
recommended(yellow,Index,Ofr):-
	Ofr = 30000 + (Index-3)*7000.
recommended(green,Index,Ofr):-
	Ofr = 30000 + (Index-3)*6000.
recommended(blue,Index,Ofr):-
	Ofr = 30000 + (Index-3)*2000.
	
	

/* Initial goals */

// join the game
!join.

/* Plans */

+!join : true <- join(game);+canNegotiation.

/*Negotiation phase*/

+state(negotiation):canNegotiation <-
	+canAuction;
	-canNegotiation;
	+canInvestors.

//Indicator that its the first selling(_,_) it has received, so after receiving it, it shall wait 1s for all other sales, then choose which one(s) to take
@s1[atomic]
+selling(Company,_,Phase)[source(Manager)] : Phase = 1 & state(negotiation) <-
	.my_name(Me);
    ?company(Company,Color,Mult);
    ?fluct(Color,_,Index);
    ?recommended(Color,Index,TempOff);
    .count(invests(Me,_,Color),N);
    .count(invests(Me,_,_),N2);
    Offer = TempOff * Mult;
    .send(Manager,tell,propose(Company,Offer,Phase));
	.abolish(selling(Company,MinPrice,_))
.
@s2[atomic]
+selling(Company,MinPrice,Phase)[source(Manager)] : not Phase = 1 & state(negotiation) <-
	.my_name(Me);
    ?company(Company,Color,Mult);
    ?fluct(Color,_,Index);
    ?recommended(Color,Index,TempOff);
    .random(Rand);
    .findall(a,invests(Investor,C,Col) & not Investor = Me & not Color = Col,L1);
    .length(L1,N1);
    //Rand serve para nao competir infinitamente 
    Offer = (TempOff * Mult) + (Rand * 400 * Phase) + (N1*400);
    .print(Offer, " ", MinPrice);
    if(Offer > MinPrice){
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
	//Nothing
	+canPayment.

/*Payment phase*/

+state(payment):canPayment <-
	+canManagers;
	-canPayment;
	//Nothing
	+canAuction.
	
/*Auction phase*/

+state(auction):canAuction <-
	+canPayment;
	-canAuction;
	//Nothing
	+canNegotiation.
