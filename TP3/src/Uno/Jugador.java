package Uno;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    String nombre;
    ArrayList<Carta> mano = new ArrayList<>();

    Jugador(String nombre) {
        this.nombre = nombre;
    }

    boolean tieneCartas() {
        return !mano.isEmpty();
    }

    int cantidadCartas() {  return mano.size(); }

    public void recibirCartas(List<Carta> cartas) {
        for (Carta carta : cartas) {
            mano.add(carta);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public boolean tieneCarta(Carta cartaAJugar) { return mano.contains(cartaAJugar); }

    public void removerCarta(Carta cartaJugada) {
        mano.remove(cartaJugada);
    }
}