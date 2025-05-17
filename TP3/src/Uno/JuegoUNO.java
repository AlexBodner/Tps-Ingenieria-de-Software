package Uno;

import java.util.ArrayList;
import java.util.Stack;

public class JuegoUNO {
    Mazo mazo;
    ArrayList<Jugador> jugadores;
    Carta cartaPozo;
    public JuegoUNO(ArrayList<Jugador> jugadores, Mazo mazo, ) {
        this.mazo = mazo;
        this.jugadores = jugadores;
        this.cartaPozo = new CartaVacia();
    }
    public Carta getPozo(){
        return cartaPozo;
    }
    private repartirCartas(){

    }
    private inicializarPozo(){
        cartaPozo = (mazo.pop());
    }

    public JuegoUNO jugar(String nombreJugador, Carta cartaJugada){
        Jugador jugador = jugadores.stream().filter(j->j.getNombre().equals(nombreJugador)).
                findFirst().orElse(null);
        if ( jugador.puedeJugar(cartaJugada) && cartaPozo.acepta(cartaJugada)){
            pozo.push(cartaJugada);
            jugador.removerCarta(cartaJugada);
        }
        else{
            Carta agarrada = mazo.pop();
            jugador.recibirCarta(agarrada);
        }

        return this;
    }
}
