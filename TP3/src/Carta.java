abstract class Carta {
    String color;

    Carta(String color) {
        this.color = color;
    }

    abstract boolean acepta(Carta otra);
    abstract boolean coincideColor(String color);
    abstract boolean coincideNumero(int numero);
    abstract boolean coincideTipo(String tipo);
}
