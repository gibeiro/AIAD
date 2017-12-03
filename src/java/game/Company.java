package game;

public class Company {
	public static int globalNAMESs = 1;
    public String name;
    
    public enum Status {open,closed};
    public enum Color{
		red(0),yellow(1),green(2),blue(3);
		private int i;
		public static final Integer NR_COLORS = 4;
        private Color(int i) {
            this.i = i;
        }
        public Integer index(){return i;}
        public String toString() {
        	if(i == 0) {
        		return "red";
        	}else if(i == 1) {
        		return "yellow";
        	}else if(i == 2) {
        		return "green";
        	}else if(i == 3) {
        		return "blue";
        	}
        	//never gets here
        	return "red";
        }
	};	
    public int multiplier;

    public int price;
    public Status status;
    public Color color;

    public Company(String name, Color c, int m){
    	int id = Company.globalNAMESs++;
    	if(name.equals("")) {
    		this.name = Integer.toString(id);
    	}else this.name = name;
    	this.status = Status.open;
    	this.price = 0;
        this.color = c;
        this.multiplier = m;
    }

}