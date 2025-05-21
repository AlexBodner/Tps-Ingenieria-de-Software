package Uno;

public class WildcardNoJugada extends Carta{

    boolean acepta(Carta otra) {
        return true;
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

    public String toString() {return "wildcard";}

    public String getColor(){ throw new RuntimeException("Wildcard no jugada. Aun no tiene color");}
}
