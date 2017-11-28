// Environment code for project pows

import jason.asSyntax.*;
import jason.environment.*;
import java.util.logging.*;

import game.Engine;

public class Env extends Environment {
	private Engine game;
	
    private Logger logger = Logger.getLogger("pows."+Env.class.getName());

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        
        game = new Engine();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        logger.info("executing: "+action+", but not implemented!");
        if (true) { // you may improve this condition
             informAgsEnvironmentChanged();
        }
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
