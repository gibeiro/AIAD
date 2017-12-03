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

+!join : true <- join(game).

//Negotiation phase

+state(negotiation) <-
	+firstOne.

//Indicator that its the first selling(_,_) it has received, so after receiving it, it shall wait 1s for all other sales, then choose
+selling(_,_) : firstOne <- -firstOne;.wait(1000);!chooseCompanies.

+!chooseCompanies <- 
	.findall([M,C],selling(M,C),LS);
	.shuffle(LS,LS2);
	.nth(0,LS2,[ToSend,Company]);
	.my_name(Me);
	?player(_,Me,Cash);
	.random(Rand);
	Value = (Cash*Rand);
	.send(ToSend,tell,propose(Me,Company,Value));
	-selling(_,_).
	
