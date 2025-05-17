package Uno;

public abstract class Jugada {
    Carta cartaJugada;
    public Jugada(Carta cartaJugada){
        this.cartaJugada = cartaJugada;
    }
    public Carta getCartaJugada() {
        return cartaJugada;
    }
    public abstract jugar(Carta cartaEnPozo);
}
