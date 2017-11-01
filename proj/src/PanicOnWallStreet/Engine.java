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
	//---- GAME ----

	//Game phases
	public enum Phase{
		negotiation,
		investors,
		managers,
		payment,
		auction
	};
	//All the dices for the game(red,yellow, green and blue)
	private static final Integer[][] DICE = {
			{7,-7,3,-3,2,-2},
			{3,-3,2,-2,1,1},
			{2,-2,1,-1,2,0},
			{1,-1,1,-1,0,0}
	};
	//Fluctuations table
	private static final Integer[][] VALUES = {
			{-20,-10,0,30,40,50,60,70},
			{-10,0,0,30,40,40,60,60},
			{0,10,20,30,30,40,50,60},
			{20,20,20,30,30,30,40,40}
	};
	//Fluctuation indexes at fluctuation table
	private static Integer[] pointers = {3,3,3,3};
	//Time available for negotiation
	private static final Integer NEGOTIATION_PERIOD = 120;
	//Number of turns
	private static final Integer NR_TURNS = 5;
	//RNG
	private static final Random RNG = new Random();
	//Roll dice
	private static Integer diceRoll(Company.Color c){
		int i = RNG.nextInt(6);
		return DICE[c.index()][i];		
	}		
	//List of players
	public static List<Player> players = new ArrayList<Player>();
	//Current phase
	private static Phase phase;
	//Current turn
	private static Integer turn;
	
	
	//----Variables needed for phase 1----
	
	
	//Negotiation proposal
	private static class NProposal {
		public static int globalIDs = 1;
		public final int id;
		
		Manager manager;
		Investor investor;
		Company company;
		int price;
		Boolean accept;
		NProposal(Player manager, Player investor, Company company,int price){
			this.id = globalIDs++;
			this.manager = (Manager)manager;
			this.investor = (Investor)investor;
			this.company = company;
			this.price = price;
			this.accept = false;
		}
	}
	//List of negotation proposals
	private static List<NProposal> proposals = new ArrayList<NProposal>();
	
	
	//--------------GAME CYCLE---------------
	
	
	//Constructor
	public Engine(){}
	//Add new player
	public static void addPlayer(Player p){ players.add(p); }
	//Start/Restart game
	public static void start(){
		turn = 1;
		phase = Phase.negotiation;
		startNegotiation();
	}
	public static void nextPhase() {
		switch(phase){
		case negotiation:
			finishNegotiation();
			phase = Phase.investors;
			//startInvestors();
			break;
		case investors:
			//finishInvestors();
			phase = Phase.managers;
			//startManagers();
			break;
		case managers:
			//finishManagers();
			phase = Phase.payment;	// calculate the managers' income
			//startPayment();
			break;
		case payment:
			//finishPayment();
			phase = Phase.auction;	// managers cost payment to the bank
			//startAuction();
			break;
		case auction:
			//finishAuction();
			phase = Phase.negotiation;	// 
			startNegotiation();
			turn++;
			break;
	}
	}
	
	
	//----Negotioation phase methods----
	
	
	//Start negotiation phase
	private static void startNegotiation(){
		proposals = new ArrayList<NProposal>();
	}
	//Finish negotiation phase, distribute companies
	private static void finishNegotiation(){
		for(NProposal proposal : proposals) {
			if(proposal.accept) {
				proposal.investor.companies.add(proposal.company);
				proposal.company.price = proposal.price;
			}
			
		}
	}
	//Make a new proposal, idm = id of manager,idi = id of investor, idc = id of company
	public static Message makeNProposal(int idm,int idi,int idc,int price) {
		if(phase != Phase.negotiation) {
			return new Message(false,"Not in negotiation phase");
		}
		Investor investor = null;
		Manager manager = null;
		Company company=null;
		for(Player p : players) {
			if(p.id == idm) {
				manager = (Manager) p;
			}
			if(p.id == idi) {
				investor = (Investor) p;
			}
		}
		if(investor == null) {
			return new Message(false,"There is no such investor");
		}
		if(manager == null) {
			return new Message(false,"There is no such manager");
		}
		for(int i = 0;i < manager.companies.size();i++) {
			if(manager.companies.get(i).id == idc) {
				if(manager.companies.get(i).status == Company.Status.closed) {
					return new Message(false,"Company is closed");
				}
				company = manager.companies.get(i);
			}
		}
		if(company == null) {
			return new Message(false,"The manager has no such company");
		}
		NProposal proposal = new NProposal(manager,investor,company,price);
		proposals.add(proposal);
		return new Message(true,"Success");
	}
	//Accept a proposal
	public static Message acceptNProposal(int id) {
		int idc = -1;
		for(NProposal p : proposals) {
			if(p.id == id) {
				p.accept = true;
				idc = p.company.id;
				break;
			}
		}
		//This step is done so that there can't be more than 1 accepted proposal on the same company
		if(idc == -1) {
			return new Message(false,"There is no such company");
		}
		for(NProposal p : proposals) {
			if(p.company.id == idc && p.id != id) {
				p.accept = false;
			}
		}
		return new Message(true,"Success");
	}
	//Delete a proposal
	public static Message deleteNProposal(int id) {
		for(NProposal p : proposals) {
			if(p.id == id) {
				proposals.remove(p);
				return new Message(true,"Success");
			}
		}
		return new Message(false,"There is no such proposal");
	}
	//TODO get proposals
	
	//----Investors phase methods----
	
	private static void fluctuate(){
		for(Company.Color c = Company.Color.red; c.index() < Company.Color.NR_COLORS; c.next()){
			pointers[c.index()] += diceRoll(c);
			pointers[c.index()] %= 8;
		}
	}
	
	public static void investorsIncome(){
		
	}
	
	public static void managersIncome(){
		
	}
	
	public static void startAuction(){
		
	}
	
	public static void managersPayment(){
		
	}
	
	/*private static void doTurn(){
	switch(phase){
		case negotiation:
			startNegotiation();	// starts company negotiation protocol
			finishNegotiation(); // stops company negotiation and distributes companies
			break;
		case investors:				
			fluctuate();		// fluctuate the companies' values
			investorsIncome();	// calculate the investors' income
			break;
		case managers:
			managersIncome();	// calculate the managers' income
			break;
		case payment:
			managersPayment();	// managers cost payment to the bank
			break;
		case auction:
			startAuction();		// starts company auction protocol
			break;
	}
}*/
	
}
