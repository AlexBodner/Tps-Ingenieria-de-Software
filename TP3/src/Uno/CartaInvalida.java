package Uno;

public class CartaInvalida extends Carta{

    public CartaInvalida() {
        super(null, null);
    }

    boolean acepta(Carta otra) {
        throw new RuntimeException("Jugada invalida");
    }

    boolean coincideColor(String color) {
        throw new RuntimeException("Jugada invalida");
    }

    boolean coincideNumero(int numero) {
        throw new RuntimeException("Jugada invalida");
    }

    boolean coincideTipo(String tipo) {
        throw new RuntimeException("Jugada invalida");
    }

    public String toString() {
        return "carta invalida";
    }

    public String getColor() {throw new RuntimeException("Jugada invalida");}

    public void applyEffect(JuegoUNO juego) {
        throw new RuntimeException("Jugada invalida");
    }
}
