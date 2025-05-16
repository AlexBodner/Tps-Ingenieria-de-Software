class CartaNumerica extends Carta {
    int numero;
    private String tipo;

    CartaNumerica(String color, int numero) {
        super(color);
        this.numero = numero;
        tipo = "numerica";
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

    boolean coincideTipo(String tipo) {
        return this.tipo.equals(tipo);
    }

    public String toString() {
        return color + " " + numero;
    }
}