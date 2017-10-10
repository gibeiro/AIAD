/*
* http://www.iro.umontreal.ca/~dift6802/jade/src/examples/yellowPages/DFSubscribeAgent.java
*
*
* */

package PanicOnWallStreet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {

	public enum Phase{negotiation,investors,managers,payment,auction}
	public enum Color{
		red(0),yellow(1),green(2),blue(3);
		private int i;
		public static final Integer NR_COLORS = 4;
        private Color(int i) {
            this.i = i;
        }
        public Integer index(){return i;}
        public Color next(){
        	return values()[++i];
        }
	};	
	
	private static final Integer[][] DICE = {
			{7,-7,3,-3,2,-2},
			{3,-3,2,-2,1,1},
			{2,-2,1,-1,2,0},
			{1,-1,1,-1,0,0}
	};
	
	private static final Integer[][] VALUES = {
			{-4,-2,0,6,8,10,12,14},
			{-2,0,0,6,8,8,12,12},
			{0,2,4,6,6,8,10,12},
			{4,4,4,6,6,6,8,8}
	};
	
	private static Integer[] pointers = {3,3,3,3};
	
	public static final Integer NEGOTIATION_PERIOD = 120;
	public static final Integer NR_TURNS = 5;
	
	private static final Random RNG = new Random();
	private static Integer diceRoll(Color c){
		int i = RNG.nextInt(6);
		return DICE[c.index()][i];		
	}
	private static void fluctuate(){
		for(Color c = Color.red; c.index() < Color.NR_COLORS; c.next()){
			pointers[c.index()] += diceRoll(c);
			pointers[c.index()] %= 8;
		}
	}

	public static List<Player> players = new ArrayList<Player>();
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
			case investors:
				fluctuate();
				break;
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
