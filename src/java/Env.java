// Environment code for project pows

import jason.asSyntax.*;
import jason.environment.*;
import jason.environment.grid.Location;

import java.awt.EventQueue;
import java.util.logging.*;

import game.Company;
import game.Engine;
import game.Investor;
import game.Manager;

public class Env extends Environment {
	private Engine game;
	private Interface gui;
	
	public static final Term jg = Literal.parseLiteral("join(game)");
	public static final Term ig = Literal.parseLiteral("init(game)");
	public static final Term np = Literal.parseLiteral("next(phase)");
	public static final String prop = "acceptProposal";
	
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
        	//Initiate game
        	else if(action.equals(ig)) {
        		game.init();
        		logger.info("Initiated game");
        	}
        	//Next phase
        	else if(action.equals(np)) {
        		game.nextPhase();
        	}
        	//Accept proposal in phase 1
        	else if(action.toString().contains(prop)){
        		String s = action.toString();
        		s = s.substring(s.indexOf("("));
        		s = s.substring(0,s.indexOf(")"));
        		String[] parts = s.split(",");
        		Double value = Double.parseDouble(parts[3]);
        		game.investCompany(parts[0],parts[1],parts[2],value.intValue());
        	}
        }catch(Exception e) {
        	
        }
        
        updatePercepts();
        
        return true; // the action was executed with success
    }
    
    public synchronized void updatePercepts() {
    	//Clear global percepts(not individual ones)
    	clearPercepts();
    	//Game state
    	Literal state = Literal.parseLiteral("state(" + game.getPhase() + ")");
    	addPercept(state);
    	//List of players in the game
    	for(Investor i:game.investors) {
    		Literal inv = Literal.parseLiteral("player(investor," + i.name +"," + i.cash + ")");
    		addPercept(inv);
    	}
    	for(Manager m:game.managers) {
    		Literal man = Literal.parseLiteral("player(manager," + m.name + "," + m.cash + ")");
    		addPercept(man);
    	}
    	//List of companies owned by managers
    	for(Manager m:game.managers) {
    		for(Company c:m.companies) {
    			Literal com = Literal.parseLiteral("company(" + c.name + "," + c.color.toString() + "," + c.multiplier.value() + ")");
    			addPercept(com);
    			Literal own = Literal.parseLiteral("owns(" + m.name + "," + c.name + ")");
    			addPercept(own);
    		}
    	}
    	//List of companies invested
    	for(Investor i:game.investors) {
    		for(Company c:i.companies) {
    			Literal inv = Literal.parseLiteral("invests(" + i.name + "," + c.name + "," + c.price + ")");
    			addPercept(inv);
    		}
    	}
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
