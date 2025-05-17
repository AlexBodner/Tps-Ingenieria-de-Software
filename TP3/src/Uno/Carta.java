package Uno;

abstract class Carta {
    abstract boolean acepta(Carta otra);
    abstract boolean coincideColor(String color);
    abstract boolean coincideNumero(int numero);
    abstract boolean coincideTipo(String tipo);
}
