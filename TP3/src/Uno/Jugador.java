package Uno;

import java.util.ArrayList;

class Jugador {
    String nombre;
    ArrayList<Carta> mano = new ArrayList<>();

    Jugador(String nombre) {
        this.nombre = nombre;
    }

    boolean tieneCartas() {
        return !mano.isEmpty();
    }

    void recibirCarta(Carta carta) {
        mano.add(carta);
    }
    public String getNombre() {
        return nombre;
    }
    public boolean puedeJugar(Carta cartaAJugar){
        return mano.contains(cartaAJugar);
    }

}