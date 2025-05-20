package Uno;

public class WildcardNoJugada extends Carta{
    String tipo = "wildcardNoJugada";

    boolean acepta(Carta otra) {
        return true;
    }

    public Carta asignarColor(String color){
        return new Wildcard(color);
    }

    public JuegoUNO serJugada(JuegoUNO juego, Jugador jugador){
        throw new RuntimeException("WildCard sin color asignado");
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
