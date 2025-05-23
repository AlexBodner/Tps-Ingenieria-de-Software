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

    boolean coincideNumero(int numero) {
        return this.numero == numero;
    }

    public String toString() {
        return getColor() + " " + numero;
    }

}