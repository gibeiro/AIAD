package PanicOnWallStreet;

import java.util.*;

public class Investor extends Player {
	public int cash;
	public List<Company> companies;
	
	public Investor(String name) {
		super(name);
		cash = 120;
		companies  = new ArrayList<Company>();
	}
}
