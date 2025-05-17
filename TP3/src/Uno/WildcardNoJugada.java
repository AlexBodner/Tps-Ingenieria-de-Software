package Uno;

public class WildcardNoJugada extends Carta{
    String tipo = "wildcard";

    boolean acepta(Carta otra) {
        throw new RuntimeException("Not supported yet.");
    }
    public Carta asignarColor(String color){
        return new Wildcard(color);
    }
    boolean coincideColor(String color) {
        throw new RuntimeException("Not supported yet.");
    }

    boolean coincideNumero(int numero) {
        throw new RuntimeException("Not supported yet.");
    }

    boolean coincideTipo(String tipo) {
        throw new RuntimeException("Not supported yet.");
    }
}
