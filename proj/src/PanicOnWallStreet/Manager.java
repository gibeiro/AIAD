package PanicOnWallStreet;

import java.util.*;

public class Manager extends Player {
	public int cash;
	public List<Company> companies;
	
	public Manager(String name) {
		super(name);
		cash = 0;
		companies =  new ArrayList<Company>();
	}
}
