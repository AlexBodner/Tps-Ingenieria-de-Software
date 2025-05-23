package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.io.IO.print;

public class JuegoUNO {
    ArrayList<Carta> mazo;
    ArrayList<Jugador> jugadores;
    Carta cartaPozo = new CartaVacia();
    boolean unoCantado = false;
    Jugador ultimoJugador;
    boolean juegoTerminado = false;

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

    public Jugador verJugadorTurno() {
        return jugadores.getFirst();
    }

    public JuegoUNO reverse(){
        Collections.reverse(jugadores);
        this.avanzarTurno();
        return this;
    }

    public boolean isJuegoTerminado(){ return this.juegoTerminado;}

    public JuegoUNO jugar(String jugador, Carta carta) {
        assert !juegoTerminado : "Juego terminado";

        Jugador currentJugador = this.getJugador(jugador);
        if(this.verJugadorTurno() == currentJugador) {
            assert currentJugador.tieneCarta(carta) : "El jugador no posee esa carta.";
            if (carta.acepta(this.getPozo())) {
                currentJugador.removerCarta(carta);
                this.setPozo(carta);
                this.avanzarTurno();
                carta.applyEffect(this);

                if (carta.estaCantandoUno()){
                    if(currentJugador.cantidadCartas()!=1){
                        currentJugador.recibirCartas(levantarDeMazo(2));
                    }
                }
                if (currentJugador.cantidadCartas()==0){
                    juegoTerminado = true;
                }
            }
        }
        return this;
    }
}


