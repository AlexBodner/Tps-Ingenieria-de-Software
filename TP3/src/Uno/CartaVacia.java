package Uno;

public class CartaVacia extends Carta {

    boolean acepta(Carta otra) {
        return true;
    }

    boolean coincideColor(String color) {
        return true;
    }

    boolean coincideNumero(int numero) {
        return true;
    }

    boolean coincideTipo(String tipo) {
        return true;
    }

    public String toString() {
        return "carta vacia";
    }
}
