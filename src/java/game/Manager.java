package game;

import java.util.*;

public class Manager extends Player {
	public Manager(String name) {
		super(name);
		cash = 0;
		companies =  new ArrayList<Company>();
	}
}
