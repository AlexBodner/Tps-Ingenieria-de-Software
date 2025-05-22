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

    public boolean puedeJugar(Carta cartaAJugar){
        return mano.contains(cartaAJugar);
    }

    public Carta getCarta(Carta carta){
        return mano.stream()
                .filter(c->c.toString().equals(carta.toString()))
                .findFirst().orElse(new CartaInvalida());
    }

    public void removerCarta(Carta cartaJugada) {
        mano.stream()
                .filter(c-> c.coincideTipo(cartaJugada.getTipo())&&c.coincideColor(cartaJugada.getColor()))
                .findFirst().orElse(new CartaInvalida());
    }
}