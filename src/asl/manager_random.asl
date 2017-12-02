// Agent manager_random in project pows

/* Initial beliefs and rules */

beggining.

/* Initial goals */

// join the game
!join.

/* Plans */

+!join : beggining <- 
	-beggining;
	join(game).

//If negotiation state beggins

//React to state change to negotiation
+state(negotiation) <-
	.print("Im open for proposals!");
	!startCNP.

//Send to all investors the companies i'm selling
+!startCNP : true <-
	.findall(Name,player(investor,Name,_),LI);
	.my_name(Me);
	for(owns(Me,Comp)){
		.send(LI,tell,selling(Me,Comp))
	}.
	