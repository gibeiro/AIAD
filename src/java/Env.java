// Environment code for project pows

import jason.asSyntax.*;
import jason.environment.*;
import jason.environment.grid.Location;

import java.awt.EventQueue;
import java.util.logging.*;

import game.Engine;
import game.Investor;
import game.Manager;

public class Env extends Environment {
	private Engine game;
	private Interface gui;
	
	public static final Term jg = Literal.parseLiteral("join(game)");
	public static final Term ig = Literal.parseLiteral("init(game)");
	
    private Logger logger = Logger.getLogger("pows."+Env.class.getName());

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        
        game = new Engine();
        gui = new Interface();
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        try {
        	//If action is to join the game
        	if(action.equals(jg)) {
        		if(agName.contains("investor")) {
        			game.addInvestor(new Investor(agName));
        		}else if(agName.contains("manager")) {
        			game.addManager(new Manager(agName));
        		}
        		logger.info(agName + " joined game");
        	}
        	//If action is to init the game
        	else if(action.equals(ig)) {
        		game.init();
        		logger.info("Inited game");
        	}
        }catch(Exception e) {
        	
        }
        
        updatePercepts();
        
        return true; // the action was executed with success
    }
    
    public void updatePercepts() {
    	//Clear global percepts(not individual ones)
    	clearPercepts();
    	//List of players in the game
    	for(Investor i:game.investors) {
    		Literal inv = Literal.parseLiteral("player(inv," + i.name +")");
    		addPercept(inv);
    	}
    	for(Manager m:game.managers) {
    		Literal man = Literal.parseLiteral("player(man," + m.name + ")");
    		addPercept(man);
    	}
    	
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
