package game;

public class Company {
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
	
	private static int globalNAMESs = 1;
    private String name;
    
    private int multiplier;

    private int price;
    private Status status;
    private Color color;

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

	public static int getGlobalNAMESs() {
		return globalNAMESs;
	}

	public static void setGlobalNAMESs(int globalNAMESs) {
		Company.globalNAMESs = globalNAMESs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}