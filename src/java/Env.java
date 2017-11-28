// Environment code for project pows

import jason.asSyntax.*;
import jason.environment.*;
import java.util.logging.*;

import game.Engine;
import game.Investor;
import game.Manager;

public class Env extends Environment {
	private Engine game;
	
	public static final Term jg = Literal.parseLiteral("join(game)");
	
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
        	if(action.equals(jg)) {
        		if(agName.contains("investor")) {
        			game.addInvestor(new Investor(agName));
        		}else if(agName.contains("manager")) {
        			game.addManager(new Manager(agName));
        		}
        		logger.info(agName + " joined game");
        	}
        }catch(Exception e) {
        	
        }
        
        updatePercepts();
        
        return true; // the action was executed with success
    }
    
    public void updatePercepts() {
    	
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
