// Agent investor_random in project pows

/* Initial goals */

beggining.

// join the game
!join.

/* Plans */

+!join : true <- join(game).

//Negotiation phase

+state(negotiation) <-
	+firstOne.

//Indicator that its the first selling(_,_) it has received, so after receiving it, it shall wait 1s for all other sales, then choose
+selling(_,_) : firstOne <- -firstOne;.wait(1000);!chooseCompanies.

+!chooseCompanies <- 
	.findall([Man,Comp],selling(Man,Comp),LS);
	.shuffle(LS,RAND).