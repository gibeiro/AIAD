// Environment code for project pows

import jason.asSyntax.*;
import jason.environment.*;
import jason.environment.grid.Location;

import java.awt.EventQueue;
import java.util.logging.*;

import game.Company;
import game.Company.Color;
import game.Engine;
import game.Player;

public class Env extends Environment {
	private Engine game;
	private Interface gui;
	
	public static final Term jg = Literal.parseLiteral("join(game)");
	public static final Term ig = Literal.parseLiteral("init(game)");
	public static final Term np = Literal.parseLiteral("next(phase)");
	
	public static final String prop = "acceptProposal";
	
	public static final Term flu = Literal.parseLiteral("fluctuate(game)");
	public static final String gii = "investorIncome";
	
	public static final String mii = "managerIncome";
	public static final String bnk = "bankrupt";
	
	public static final String sec = "sellCompany";
	public static final String fee = "payFee";
	
	public static final Term fet = Literal.parseLiteral("pop(auction)");
	
    private Logger logger = Logger.getLogger("pows."+Env.class.getName());

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        
        game = new Engine();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        try {
        	//If action is to join the game
        	if(action.equals(jg)) {
        		//TODO dont let join after game starts
        		if(agName.contains("investor")) {
        			game.addInvestor(new Player(agName));
        		}else if(agName.contains("manager")) {
        			game.addManager(new Player(agName));
        		}
        		logger.info(agName + " joined game");
        	}
        	//Initiate game
        	else if(action.equals(ig)) {
                gui = new Interface(game);
                gui.setVisible(true);
        		game.init();
        		logger.info("Initiated game");
        	}
        	//Next phase
        	else if(action.equals(np)) {
        		game.nextPhase();
        	}
        	//Accept proposal in phase 1
        	else if(action.getFunctor().equals(prop)){
        		Double value = Double.parseDouble(action.getTerm(3).toString());
        		game.investCompany(action.getTerm(0).toString(),action.getTerm(1).toString(),action.getTerm(2).toString(),value.intValue());
        	}
        	//Do fluctuation
        	else if(action.equals(flu)) {
        		game.fluctuate();
        	}
        	//Give income to investor
        	else if(action.getFunctor().equals(gii)) {
        		Integer income = Integer.parseInt(action.getTerm(1).toString());
        		game.giveInvestorIncome(action.getTerm(0).toString(),income);
        	}
        	//Give income to manager
        	else if(action.getFunctor().equals(mii)) {
        		Integer income = Integer.parseInt(action.getTerm(2).toString());
        		game.giveManagerIncome(action.getTerm(0).toString(), action.getTerm(1).toString(), income);
        	}
        	//Bankrupt investor
        	else if(action.getFunctor().equals(bnk)) {
        		game.bankruptInvestor(action.getTerm(0).toString());
        	}
        	//Sell company
        	else if(action.getFunctor().equals(sec)) {
        		game.sellCompany(action.getTerm(0).toString(),action.getTerm(1).toString());
        	}
        	//Pay fee for company
        	else if(action.getFunctor().equals(fee)) {
        		Integer value = Integer.parseInt(action.getTerm(1).toString());
        		game.payFee(action.getTerm(0).toString(),value);
        	}
        	//Pay fee for company
        	else if(action.equals(fet)) {
        		game.toAuction();
        	}
        }catch(Exception e) {
        	
        }
        
        updatePercepts();
        if(gui != null) {
        	gui.update(game);
        }
        return true; // the action was executed with success
    }
    
    public synchronized void updatePercepts() {
    	//Clear global percepts(not individual ones)
    	clearPercepts();
    	//Game state
    	Literal state = Literal.parseLiteral("state(" + game.getPhase() + ")");
    	addPercept(state);
    	//List of players in the game
    	for(Player i:game.investors) {
    		if(!i.isBankrupt()) {
    			Literal inv = Literal.parseLiteral("player(investor," + i.getName() +"," + i.getCash() + ")");
        		addPercept(inv);
    		}
    	}
    	for(Player m:game.managers) {
    		if(!m.isBankrupt()) {
    			Literal man = Literal.parseLiteral("player(manager," + m.getName() + "," + m.getCash() + ")");
    			addPercept(man);
    		}
    	}
    	//List of companies owned by managers
    	for(Player m:game.managers) {
    		for(Company c:m.getCompanies()) {
    			Literal com = Literal.parseLiteral("company(" + c.getName() + "," + c.getColor().toString() + "," + c.getMultiplier() + ")");
    			addPercept(com);
    			Literal own = Literal.parseLiteral("owns(" + m.getName() + "," + c.getName() + ")");
    			addPercept(own);
    		}
    	}
    	//List of companies invested
    	for(Player i:game.investors) {
    		for(Company c:i.getCompanies()) {
    			Literal inv = Literal.parseLiteral("invests(" + i.getName() + "," + c.getName() + "," + c.getPrice() + ")");
    			addPercept(inv);
    		}
    	}
    	//List of fluctuations
    	for(Company.Color c : Company.Color.values()){
			Literal inv = Literal.parseLiteral("fluct(" + c.toString() + "," + game.VALUES[c.index()][game.pointers[c.index()]] + ")");
			addPercept(inv);
		}
    	//Auctioned company
    	if(game.auction != null) {
    		Literal auc = Literal.parseLiteral("auction(" + game.auction.getName() + "," + game.auction.getColor().toString() + "," + game.auction.getMultiplier() + ")");
    		addPercept(auc);
    	}
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
