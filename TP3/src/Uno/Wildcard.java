package Uno;

public class Wildcard extends Carta{

    public Wildcard() {
        super("multicolor", "wildcard");
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideTipo(this.getTipo());
    }

    boolean coincideColor(String color) {
        return this.getColor().equals(color) || this.getColor().equals("multicolor");
    }

    boolean coincideNumero(int numero) {
        return true;
    }

    boolean coincideTipo(String tipo) {
        return this.getTipo().equals(tipo);
    }

    public String toString() {
        return  "wildcard";
    }

    public Wildcard as(String color) {
        this.setColor(color);
        return this;
    }
}
