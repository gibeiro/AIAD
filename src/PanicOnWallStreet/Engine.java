/*
* http://www.iro.umontreal.ca/~dift6802/jade/src/examples/yellowPages/DFSubscribeAgent.java
*
*
* */


package PanicOnWallStreet;

import java.util.Random;

public class Engine {

	public enum Phase{negotiation,investors,managers,payment,auction}
	public static final Integer NEGOTIATION_PERIOD = 120;
	public static final Integer NR_TURNS = 5;
	static final Random RNG = new Random();
	static int diceRoll(){ return (RNG.nextInt()%6)+1;}

	private Phase phase = Phase.negotiation;
	private Integer turn = 0;

	public Engine(){}
	public void addPlayer(Player p){}
	public void start(){
		for(; turn < NR_TURNS; turn++)
			startTurn();
	}

	private void startTurn(){
		switch(phase){
			case negotiation:

				break;
			case investors: break;
			case managers: break;
			case payment: break;
			case auction: break;
		}
	}

	public void reset(){
		turn = 0;
		phase = Phase.negotiation;
	}
	
	
}
