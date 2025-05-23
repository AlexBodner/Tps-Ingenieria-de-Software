package Uno;

public class Wildcard extends Carta{

    public Wildcard() {
        super("multicolor", "wildcard");
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideTipo(this.getTipo());
    }

    boolean coincideColor(String color) {
        return super.coincideColor(this.getColor()) || this.getColor().equals("multicolor");
    }

    public String toString() {
        return  "wildcard";
    }

    public Wildcard as(String color) {
        this.setColor(color);
        return this;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (((Carta) obj).coincideTipo(this.getTipo())) {
            return true;
        }
        return false;
    }
}
