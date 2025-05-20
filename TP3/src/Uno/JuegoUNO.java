package Uno;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import static java.lang.StringUTF16.length;

public class JuegoUNO {
    ArrayList<Carta> mazo;
    ArrayList<Jugador> jugadores;
    Carta cartaPozo = new CartaVacia();


    public JuegoUNO(ArrayList<Jugador> jugadores, ArrayList<Carta> mazo, ArrayList<Integer> cantidadPorJugador) {
        this.mazo = mazo;
        int i = 0;
        int index_cant = 0;
        for (Jugador jugador : jugadores) {
            int cantidad = cantidadPorJugador.get(index_cant);
            jugador.recibirCartas(mazo.subList(i, i+cantidad));
            i += cantidad;
            index_cant += 1;
        }
    }

    public JuegoUNO() {}

    public Carta getPozo(){
        return cartaPozo;
    }

    public void repartirCartas(){
        for (Jugador jugador : jugadores) {
            for (Carta carta : mazo) {

            }
        }
    }

    public JuegoUNO jugar(String nombreJugador, Carta cartaJugada){
        // nunca recibe un tipo wildcardNoJugada
        Jugador jugador = jugadores.stream()
                        .filter(j->j
                        .getNombre()
                        .equals(nombreJugador))
                        .findFirst()
                        .orElse(null);

        if ( jugador.puedeJugar(cartaJugada) && cartaPozo.acepta(cartaJugada)){
            cartaPozo = cartaJugada;
            jugador.removerCarta(cartaJugada);
        }
        else{
            Carta agarrada = mazo.removeLast();
            jugador.recibirCarta(agarrada);
        }

        return this;
    }
}
