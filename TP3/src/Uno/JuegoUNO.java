package Uno;

import java.util.ArrayList;
import java.util.Collections;

public class JuegoUNO {
    ArrayList<Carta> mazo;
    ArrayList<Jugador> jugadores;
    Carta cartaPozo = new CartaVacia();

    public JuegoUNO(ArrayList<Jugador> jugadores, ArrayList<Carta> mazo, ArrayList<Integer> cantidadPorJugador) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        int i = 0;
        int index_cant = 0;
        for (Jugador jugador : jugadores) {
            int cantidad = cantidadPorJugador.get(index_cant);
            jugador.recibirCartas(mazo.subList(i, i + cantidad));
            i += cantidad;
            index_cant += 1;
        }
    }

    public JuegoUNO() {
    }

    public Carta getPozo() {
        return cartaPozo;
    }

    public Jugador getJugador(String nombre) {
        return jugadores.stream().filter(j -> j.getNombre().equals(nombre)).findFirst().orElse(null);
    }

    public Jugador getJugadorTurno() {
        // Tomamos el primero y lo ponemos ultimo en la lista
        Jugador jugador = jugadores.removeFirst();
        jugadores.add(jugador);
        return jugador;
    }

    public JuegoUNO reverse(){
        Collections.reverse(jugadores);
        Jugador jugador = jugadores.removeFirst();
        jugadores.add(jugador);
        return this;
    }

    public JuegoUNO jugar(Jugador jugador, String carta) {
        Carta cartaAJugar = jugador.getCarta(carta);
        if (cartaAJugar.acepta(cartaPozo)){
            cartaPozo = cartaAJugar;
            jugador.removerCarta(cartaAJugar);
        }
        return this;
    }

}


