package game;

import java.util.ArrayList;
import java.util.List;

public class Player{
    private String name;
    private boolean bankrupt;
    private int cash;
	private List<Company> companies;

    public Player(String name){
    	this.name = name;
    	bankrupt = false;
    	cash = 0;
		companies  = new ArrayList<Company>();
    }
    
    public boolean transfer(Player p, int cash) {
    	if(cash > this.cash) {
    		p.cash += this.cash;
    		this.cash -= this.cash;
    		return true;
    	}
    	p.cash += cash;
    	this.cash -= cash;
    	return true;
    }
    
    public boolean removeCash(int cash) {
    	if(this.cash < cash) {
    		this.cash = 0;
    	}
    	this.cash -= cash;
    	return true;
    }
    public void addCash(int cash) {
    	this.cash += cash;
    }
    
    public boolean hasCompany(Company c) {
    	for(Company i:companies) {
    		if(i == c)
    			return true;
    	}
    	return false;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBankrupt() {
		return bankrupt;
	}

	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public List<Company> getCompanies() {
		if(!this.bankrupt)
			return companies;
		else 
			return new ArrayList<Company>();
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	
	public void clearCompanies() {
		this.companies.clear();
	}
	
	 public void addCompany(Company c) {companies.add(c);}
	 
	 public void removeCompany(Company c) {
		 companies.remove(c);
	 }

}
