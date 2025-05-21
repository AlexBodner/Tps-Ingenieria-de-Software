package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JuegoUNO {
    ArrayList<Carta> mazo;
    ArrayList<Jugador> jugadores;
    Carta cartaPozo = new CartaVacia();

    public JuegoUNO(ArrayList<Jugador> jugadores, ArrayList<Carta> mazo, ArrayList<Integer> cantidadPorJugador) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        int index_cant = 0;
        for (Jugador jugador : jugadores) {
            int cantidad = cantidadPorJugador.get(index_cant);
            jugador.recibirCartas(levantarDeMazo (cantidad) );
            index_cant+=1;
        }
    }
    public ArrayList<Carta> levantarDeMazo(int cantidad) {
        ArrayList<Carta> levantadas = new ArrayList<>(mazo.subList(0, cantidad));
        mazo = new ArrayList<>(mazo.subList(cantidad, mazo.size()));
        return levantadas;
    }
    public JuegoUNO() {
    }

    public Carta getPozo() {
        return cartaPozo;
    }

    public void setPozo(Carta cartaPozo) {
        this.cartaPozo = cartaPozo;
    }

    public Jugador getJugador(String nombre) {
        return jugadores.stream().filter(j -> j.getNombre().equals(nombre)).findFirst().orElse(null);
    }

    public Jugador getJugadorTurnoYSkip() {
        // Tomamos el primero y lo ponemos ultimo en la lista
        Jugador jugador = jugadores.removeFirst();
        jugadores.add(jugador);
        return jugador;
    }

    public boolean esSuTurno(Jugador jugador){
        return jugadores.getFirst().getNombre().equals(jugador.getNombre());
    }

    public Jugador verJugadorTurno() {
        return jugadores.getFirst();
    }

    public JuegoUNO reverse(){
        Collections.reverse(jugadores);
        Jugador jugador = jugadores.removeFirst();
        jugadores.add(jugador);
        return this;
    }

    public JuegoUNO jugar(Jugador jugador, String carta) {
        Carta cartaAJugar = jugador.getCarta(carta);
        if(cartaAJugar.acepta(this.getPozo())) {
            jugador.removerCarta(cartaAJugar);
            this.setPozo(cartaAJugar);
            cartaAJugar.applyEffect(this);
        }
        return this;
    }

    public JuegoUNO jugarWildcard(Jugador jugador, String carta, String color) {
        Carta cartaAJugar = jugador.getCarta(carta);
        if (cartaAJugar.acepta(this.getPozo())) {
            jugador.removerCarta(cartaAJugar);
            Wildcard wildcard = new Wildcard(color);
            this.setPozo(wildcard);
        }
        return this;
    }
}


