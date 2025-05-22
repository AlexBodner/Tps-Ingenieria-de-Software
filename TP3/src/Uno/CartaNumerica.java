package Uno;

class CartaNumerica extends Carta {
    int numero;

    public CartaNumerica(String color, int numero) {
        super(color, "numerica");
        this.numero = numero;
    }
    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideNumero(this.numero);
    }

    boolean coincideColor(String color) {
        return this.getColor().equals(color);
    }

    boolean coincideNumero(int numero) {
        return this.numero == numero;
    }

    boolean coincideTipo(String tipo) {
        return this.getTipo().equals(tipo);
    }

    public String toString() {
        return getColor() + " " + numero;
    }

}