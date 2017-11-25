package game;

public class Company {
	public static int globalIDs = 1;
	public final int id;
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
        public Color next(){
        	return values()[++i];
        }
	};	
    public enum Multiplier{
        one(1),two(2);
        private int mult;
        private Multiplier(int m) {
            this.mult = m;
        }
        public int value() {
        	return mult;
        }
    };

    public int price;
    public Status status;
    public Color color;
    public Multiplier multiplier;

    public Company(String name, Color c, Multiplier m){
    	this.id = Company.globalIDs++;
    	this.name = name;
    	this.status = Status.open;
    	this.price = 0;
        this.color = c;
        this.multiplier = m;
    }

}