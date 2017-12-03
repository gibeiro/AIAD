/*
* http://www.iro.umontreal.ca/~dift6802/jade/src/examples/yellowPages/DFSubscribeAgent.java
*
*
* */

package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Engine {
	//---- GAME ----

	//Game phases
	public enum Phase{
		start,
		negotiation,
		investors,
		managers,
		payment,
		auction,
		end
	};
	//Player type
	public enum PlayerType{
		manager,
		investor
	};
	//All the dices for the game(red,yellow, green and blue)
	private  final Integer[][] DICE = {
			{7,-7,3,-3,2,-2},
			{3,-3,2,-2,1,1},
			{2,-2,1,-1,2,0},
			{1,-1,1,-1,0,0}
	};
	//Fluctuations table
	public  final Integer[][] VALUES = {
			{-20,-10,0,30,40,50,60,70},
			{-10,0,0,30,40,40,60,60},
			{0,10,20,30,30,40,50,60},
			{20,20,20,30,30,30,40,40}
	};
	//Fluctuation indexes at fluctuation table
	public  Integer[] pointers = {3,3,3,3};
	//Number of turns
	private  final Integer NR_TURNS = 5;
	//RNG
	private  final Random RNG = new Random();
	//Roll dice
	private  Integer diceRoll(Company.Color c){
		int i = RNG.nextInt(6);
		return DICE[c.index()][i];		
	}		
	//List of players
	public  List<Investor> investors;
	public  List<Manager> managers;
	public  List<Company> reserve;
	//Current phase
	private  Phase phase;
	//Current turn
	private  Integer turn;
	
	//Auctioned company
	private  Company auction;
	//--------------GAME CYCLE---------------
	
	
	//Constructor
	public Engine(){
		this.phase = Phase.start;
		this.turn = 0;
		investors = new ArrayList<Investor>();
		managers = new ArrayList<Manager>();
		reserve = new ArrayList<Company>();
	}
	//Add new player
	public  void addManager(Manager p){
		managers.add(p);
	}
	
	public  void addInvestor(Investor p){
		investors.add(p);
	}
	//Init game
	public  void init() {
		this.phase = Phase.negotiation;
		this.turn = 1;
		
		reserve = new ArrayList<Company>();
		/* add regular 1x companies */
		for(int i = 0; i < 11; i++) {
			reserve.add(new Company("",Company.Color.yellow,1));
			reserve.add(new Company("",Company.Color.green,1));
			reserve.add(new Company("",Company.Color.blue,1));
		}
		for(int i = 0; i < 6; i++) {
			reserve.add(new Company("",Company.Color.red,1));
		}	
		
		/* give players 120k cash */
		for(Manager p : managers) p.addCash(120000);
		for(Investor p : investors) p.addCash(120000);
		
		/* give managers 3 random companies from the reserve */
		Random r = new Random();
		for(Manager m : managers) {
			for(int i = 0; i < 3; i++) {
				Company c = reserve.get(r.nextInt(reserve.size()));
				m.addCompany(c);
				reserve.remove(c);
			}
		}
		
		/* add remaining 2x companies */
		for(int i = 0; i < 2; i++) {
			reserve.add(new Company("",Company.Color.yellow,2));
			reserve.add(new Company("",Company.Color.green,2));
			reserve.add(new Company("",Company.Color.blue,2));
		}
		reserve.add(new Company("",Company.Color.red,2));
		/* shuffle the companies -> useful coz u don't have to calc a random index
		 * each time u access the list of companies and u can 'reserve.remove(reserve.size() - 1)'
		 * to get a random company in constant time	
		 */
		//Collections.shuffle(reserve);
		
		startNegotiation();
	}
	
	public  void nextPhase() {
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
				phase = Phase.end;
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
		case end:
			return;
		}
	}
	
	public String getPhase() {
		if(phase == Phase.negotiation) {
			return "negotiation";
		}
		switch(phase){
		case start:
			return "start";
		case negotiation:
			return "negotiation";
		case investors:
			return "investors";
		case managers:
			return "managers";
		case payment:
			return "payment";
		case auction:
			return "auction";
		case end:
			return "end";
		}
		return "";
	}
	
	
	//----Negotioation phase methods----
	
	
	//Start negotiation phase
	private void startNegotiation(){
		//NOTHING
	}
	//Finish negotiation phase, distribute companies
	private void finishNegotiation(){
		//NOTHING
	}
	//Add a company to a investor
	public Message investCompany(String idi,String idm,String idc,int price) {
		if(phase != Phase.negotiation) {
			return new Message(false,"Not in negotiation phase");
		}
		Investor investor = null;
		Manager manager = null;
		Company company=null;
		for(Manager p : managers) {
			if(p.name.equals(idm)) {
				manager = (Manager) p;
				for(Company c : manager.companies) {
					if(c.name.equals(idc)) {
						company = c;
					}
				}
			}
		}
		for(Investor p : investors) {
			if(p.name.equals(idm)) {
				investor = (Investor) p;
			}
		}
		if(investor == null) {
			return new Message(false,"There is no such investor");
		}
		if(manager == null) {
			return new Message(false,"There is no such manager");
		}
		if(company == null) {
			return new Message(false,"Manager doesn't have this company");
		}
		investor.addCompany(company);
		company.price = price;
		return new Message(true,"");
	}
	
	//----Investors phase methods----
	
	//Start investors phase
	private  void startInvestors(){
		//NOTHING
	}
	//Finish investors phase
	private  void finishInvestors(){
		//NOTHING
	}
	//Fluctuate prices
	public  void fluctuate(){
		for(Company.Color c : Company.Color.values()){
			pointers[c.index()] += diceRoll(c);
			if(pointers[c.index()] < 0) {
				pointers[c.index()] = 0;
			}else if(pointers[c.index()] > 7) {
				pointers[c.index()] = 7;
			}
		}
	}
	//Give income to investors
	public  void giveInvestorsIncome() {
		for(Investor p:investors) {
			for(Company c:p.companies) {
				p.cash += VALUES[c.color.index()][pointers[c.color.index()]] * c.multiplier;
			}
		}
	}
	
	//----Managers phase methods----
	
	//Start managers phase
	private  void startManagers(){
		//NOTHING
	}
	//Finish investors phase
	private  void finishManagers() {
		//NOTHING
	}
	//Give income to managers
	public  Message giveManagersIncome(String idm, String idi, int ammount) {
		if(phase != Phase.managers) {
			return new Message(false,"Not in negotiation phase");
		}
		Investor investor = null;
		Manager manager = null;
		Company company=null;
		for(Manager p : managers) {
			if(p.name.equals(idm)) {
				manager = (Manager) p;
			}
		}
		for(Investor p : investors) {
			if(p.name.equals(idm)) {
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
	
	//----Payment phase methods----
	
	//Start payment phase
	private  void startPayment() {
		//...
	}
	//Finish payment phase
	private  void finishPayment() {
		//NOTHING
	}
	//Pay company fee
	public  Message payFee(String idm) {

		if(phase != Phase.payment) {
			return new Message(false,"Not in negotiation phase");
		}
		Manager manager = null;
		for(Manager p : managers) {
			if(p.name.equals(idm)) {
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
	public  Message removeCompany(String idm,String idc) {
		if(phase != Phase.payment) {
			return new Message(false,"Not in negotiation phase");
		}
		Manager manager = null;
		Company company = null;
		for(Manager p : managers) {
			if(p.name.equals(idm)) {
				manager = (Manager) p;
			}
		}
		if(manager == null) {
			return new Message(false,"There is no such manager");
		}
		for(Company c:manager.companies) {
			if(c.name.equals(idc)) {
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
	private  void startAuction() {
		
	}
	//Start auction phase
	private  void finishAuction() {
		
	}
	//Remove company from reserve to auction
	public  void toAuction() {
		int random = new Random().nextInt(reserve.size());
		Company c = reserve.get(random);
		reserve.remove(random);
	}
	//Buy company
	public  Message buyCompany(int idm) {
		if(phase != Phase.payment) {
			return new Message(false,"Not in negotiation phase");
		}
		Manager manager = null;
		for(Manager p : managers) {
			if(p.name.equals(idm)) {
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
