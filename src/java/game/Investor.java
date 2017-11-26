package game;

import java.util.*;

public class Investor extends Player {
	public Investor(String name) {
		super(name);
		cash = 120;
		companies  = new ArrayList<Company>();
	}
}
