package Uno;

public class CartaVacia extends Carta {

    CartaVacia() {
    }

    @Override
    boolean acepta(Carta otra) {
        return true;
    }

    boolean coincideColor(String color) {
        return true;
    }

    boolean coincideNumero(int numero) {
        return true;
    }

    @Override
    boolean coincideTipo(String tipo) {
        return true;
    }
}
