package Uno;

public class Wildcard extends Carta{
    String color;
    String tipo;

    public Wildcard(String color) {
        this.color = color;
        this.tipo = "wildcard";
    }


    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra.coincideTipo(this.tipo);
    }

    boolean coincideColor(String color) {
        return this.color.equals(color);
    }

    boolean coincideNumero(int numero) {
        return true;
    }

    boolean coincideTipo(String tipo) {
        return this.tipo.equals(tipo);
    }

    public String toString() {
        return  "wildcard " + color;
    }

    public String getColor(){ return color;}
}
