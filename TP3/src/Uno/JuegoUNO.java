package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JuegoUNO {
    ArrayList<Carta> mazo;
    ArrayList<Jugador> jugadores;
    Carta cartaPozo = new CartaVacia();
    boolean unoCantado = false;
    Jugador ultimoJugador;

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

    public JuegoUNO avanzarTurno(){
        Jugador jugador = jugadores.removeFirst();
        jugadores.add(jugador);
        return this;
    }

    public boolean esSuTurno(Jugador jugador){
        return jugadores.getFirst().getNombre().equals(jugador.getNombre());
    }

    public Jugador verJugadorTurno() {
        return jugadores.getFirst();
    }

    public JuegoUNO reverse(){
        Collections.reverse(jugadores);
        this.avanzarTurno();
        return this;
    }

    public JuegoUNO jugar(String jugador, Carta carta) {
        Jugador currentJugador = this.getJugador(jugador);
        if(this.verJugadorTurno() == currentJugador) {
            assert currentJugador.tieneCarta(carta) : "El jugador no posee esa carta.";

            if (carta.acepta(this.getPozo())) {
                currentJugador.removerCarta(carta);
                this.setPozo(carta);
                this.avanzarTurno();
                carta.applyEffect(this);
                ultimoJugador = currentJugador;
            }
        }
        return this;
    }

    public JuegoUNO cantarUno(Jugador jugador){
        if ( ultimoJugador == jugador ){
            unoCantado = true;
            return this;
        }
        else{
            if (!unoCantado){
                if(ultimoJugador.cantidadCartas()==1){
                    ultimoJugador.recibirCartas(levantarDeMazo(7));
                    unoCantado = true;
                }
                else{
                    jugador.recibirCartas(levantarDeMazo(7));
                }
            } else {
                jugador.recibirCartas(levantarDeMazo(7));
            }
        }
        return this;
    }
}


