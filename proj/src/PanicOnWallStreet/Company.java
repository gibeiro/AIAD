package PanicOnWallStreet;

public class Company {

    public enum Status {open,closed};
    public enum Color {red,yellow,green,blue};
    public enum Multiplier{
        one(1),two(2);
        private int mult;
        private Multiplier(int m) {
            this.mult = m;
        }
    };

    public int price = 0;
    public Status status = Status.open;
    public Color color;
    public Multiplier multiplier;

    public Company(Color c, Multiplier m){
        this.color = c;
        this.multiplier = m;
    }

}