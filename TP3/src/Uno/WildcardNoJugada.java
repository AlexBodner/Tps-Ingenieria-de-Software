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
        Wildcard cartaAJugar = new Wildcard(juego.getPozo().getColor());
        jugador.removerCarta(this);
        return cartaAJugar.serJugada(juego, jugador);
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
