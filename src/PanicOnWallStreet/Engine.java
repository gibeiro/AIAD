package PanicOnWallStreet;

import java.util.Random;

public class Engine {

	static final Random RNG = new Random();
	static int diceRoll(){ return (RNG.nextInt()%6)+1;}	
	
	
}
