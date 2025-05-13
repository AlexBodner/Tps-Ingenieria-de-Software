class CartaNumerica extends Carta {
    int numero;

    CartaNumerica(String color, int numero) {
        super(color);
        this.numero = numero;
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra.coincideNumero(this.numero);
    }

    boolean coincideColor(String color) {
        return this.color.equals(color);
    }

    boolean coincideNumero(int numero) {
        return this.numero == numero;
    }

    public String toString() {
        return color + " " + numero;
    }
}