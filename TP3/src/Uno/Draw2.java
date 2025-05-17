package Uno;

public class Draw2 extends Carta{
    private String tipo;
    String color;
    Draw2(String color) {
        this.color = color;
        this.tipo = "draw2";
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra.coincideTipo(this.tipo);
    }

    boolean coincideColor(String color) {
        return color.equals(this.color);
    }

    boolean coincideNumero(int numero) {
        return false;
    }

    boolean coincideTipo(String tipo) {
        return this.tipo.equals(tipo);
    }

    public String toString() {
        return color + " +2";
    }
}
