package Uno;

public class CartaVacia extends Carta {

    public CartaVacia() {
        super(null, null);
    }

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

    public String getColor() {throw new RuntimeException("Carta Vacia no tiene color");}
}
