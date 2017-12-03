package game;

import java.util.ArrayList;
import java.util.List;

public class Player{
    public String name;
    public boolean bankrupt;
    public int cash;
	public List<Company> companies;

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
    	if(this.cash < cash)
    		return false;
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
    
    public void addCompany(Company c) {companies.add(c);}

}
