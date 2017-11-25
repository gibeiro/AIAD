package game;

import jade.core.Agent;
import jade.core.AID;

public class Player{
	public static int globalIDs = 1;
	public final int id;
    public String name;

    public Player(String name){
    	this.id = Player.globalIDs++;
    	this.name = name;
    }

}
