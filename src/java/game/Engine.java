/*
* http://www.iro.umontreal.ca/~dift6802/jade/src/examples/yellowPages/DFSubscribeAgent.java
*
*
* */

package game;

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
	//Player type
	public enum PlayerType{
		manager,
		investor
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
	public static List<Investor> investors = new ArrayList<Investor>();
	public static List<Manager> managers = new ArrayList<Manager>();
	public static List<Company> reserve = new ArrayList<Company>();
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
	
	//----Variables needed for phase 3
	//Debt for when an investor cant pay his manager
	private static class Debt{
		public Player payer;
		public Player receiver;
		public int ammount;
		public Debt(Player payer,Player receiver, int ammount) {
			this.payer = payer;
			this.receiver = receiver;
			this.ammount = ammount;
		}
	}
	
	private static List<Debt> debts = new ArrayList<Debt>();
	
	//----Variables needed for phase 5
	//Auctioned company
	private static Company auction;
	//--------------GAME CYCLE---------------
	
	
	//Constructor
	public Engine(){}
	//Add new player
	public static void addManager(Manager p){
		managers.add(p);
	}
	
	public static void addInvestor(Investor p){
		investors.add(p);
	}
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
			startInvestors();
			break;
		case investors:
			finishInvestors();
			phase = Phase.managers;
			startManagers();
			break;
		case managers:
			finishManagers();
			phase = Phase.payment;	// calculate the managers' income
			startPayment();
			break;
		case payment:
			finishPayment();
			phase = Phase.auction;	// managers cost payment to the bank
			if(turn == NR_TURNS) {  //There is no auction in the last turn
				turn++;
				break;
			}
			startAuction();
			break;
		case auction:
			finishAuction();
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
		for(Manager p : managers) {
			if(p.id == idm) {
				manager = (Manager) p;
			}
		}
		for(Investor p : investors) {
			if(p.id == idm) {
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
		return new Message(true,"");
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
		return new Message(true,"");
	}
	//Delete a proposal
	public static Message deleteNProposal(int id) {
		for(NProposal p : proposals) {
			if(p.id == id) {
				proposals.remove(p);
				return new Message(true,"");
			}
		}
		return new Message(false,"There is no such proposal");
	}
	//TODO GETTERS
	
	//----Investors phase methods----
	
	//Start investors phase
	private static void startInvestors(){
		fluctuate();
		giveInvestorsIncome();
	}
	//Finish investors phase
	private static void finishInvestors(){
		//NOTHING
	}
	//Fluctuate prices
	private static void fluctuate(){
		for(Company.Color c = Company.Color.red; c.index() < Company.Color.NR_COLORS; c.next()){
			pointers[c.index()] += diceRoll(c);
			if(pointers[c.index()] < 0) {
				pointers[c.index()] = 0;
			}else if(pointers[c.index()] > 7) {
				pointers[c.index()] = 7;
			}
		}
	}
	//Give income to investors
	private static void giveInvestorsIncome() {
		for(Investor p:investors) {
			for(Company c:p.companies) {
				p.cash += VALUES[c.color.index()][pointers[c.color.index()]] * c.multiplier.value();
			}
		}
	}
	
	//----Managers phase methods----
	
	//Start managers phase
	private static void startManagers(){
		//NOTHING
	}
	//Finish investors phase
	private static void finishManagers() {
		//NOTHING
	}
	//Give income to managers
	public static Message giveManagersIncome(int idm, int idi, int ammount) {
		if(phase != Phase.managers) {
			return new Message(false,"Not in negotiation phase");
		}
		Investor investor = null;
		Manager manager = null;
		Company company=null;
		for(Manager p : managers) {
			if(p.id == idm) {
				manager = (Manager) p;
			}
		}
		for(Investor p : investors) {
			if(p.id == idm) {
				investor = (Investor) p;
			}
		}
		if(investor == null) {
			return new Message(false,"There is no such investor");
		}
		if(manager == null) {
			return new Message(false,"There is no such manager");
		}
		boolean transf = investor.transfer(manager, ammount);
		if(transf) {
			return new Message(true,"");
		}else {
			return new Message(false,"Not enough money");
		}
	}
	//Debts
	public static Message createDebt(int idm, int idi, int ammount) {
		if(phase != Phase.managers) {
			return new Message(false,"Not in negotiation phase");
		}
		Investor investor = null;
		Manager manager = null;
		Company company=null;
		for(Manager p : managers) {
			if(p.id == idm) {
				manager = (Manager) p;
			}
		}
		for(Investor p : investors) {
			if(p.id == idm) {
				investor = (Investor) p;
			}
		}
		if(investor == null) {
			return new Message(false,"There is no such investor");
		}
		if(manager == null) {
			return new Message(false,"There is no such manager");
		}
		debts.add(new Debt(investor,manager,ammount));
		return new Message(true,"");
	}
	
	//----Payment phase methods----
	
	//Start payment phase
	private static void startPayment() {
		//...
	}
	//Finish payment phase
	private static void finishPayment() {
		//NOTHING
	}
	//Pay company fee
	public static Message payFee(int idm) {

		if(phase != Phase.payment) {
			return new Message(false,"Not in negotiation phase");
		}
		Manager manager = null;
		for(Manager p : managers) {
			if(p.id == idm) {
				manager = (Manager) p;
			}
		}
		if(manager == null) {
			return new Message(false,"There is no such manager");
		}
		boolean success = manager.removeCash(10000);
		if(success) {
			return new Message(true,"");
		}
		else return new Message(false,"Not enough cash");
	}
	//Remove a company and get 5k in return
	public static Message removeCompany(int idm,int idc) {
		if(phase != Phase.payment) {
			return new Message(false,"Not in negotiation phase");
		}
		Manager manager = null;
		Company company = null;
		for(Manager p : managers) {
			if(p.id == idm) {
				manager = (Manager) p;
			}
		}
		if(manager == null) {
			return new Message(false,"There is no such manager");
		}
		for(Company c:manager.companies) {
			if(c.id == idc) {
				company = c;
			}
		}
		if(company == null) {
			return new Message(false,"There is no such company");
		}
		manager.addCash(5000);
		manager.companies.remove(company);
		reserve.add(company);
		return new Message(true,"");
	}

	//----Auction----
	
	//Start auction phase
	private static void startAuction() {
		
	}
	//Start auction phase
	private static void finishAuction() {
		
	}
	//Remove company from reserve to auction
	public static void toAuction() {
		int random = new Random().nextInt(reserve.size());
		Company c = reserve.get(random);
		reserve.remove(random);
	}
	//Buy company
	public static Message buyCompany(int idm) {
		if(phase != Phase.payment) {
			return new Message(false,"Not in negotiation phase");
		}
		Manager manager = null;
		for(Manager p : managers) {
			if(p.id == idm) {
				manager = (Manager) p;
			}
		}
		if(manager == null) {
			return new Message(false,"There is no such manager");
		}
		if(manager.cash < 5000) {
			return new Message(false,"Not enough money");
		}
		manager.removeCash(5000);
		manager.companies.add(auction);
		auction = null;
		return new Message(true,"");
	}
	
}
