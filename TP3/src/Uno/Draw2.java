package Uno;

import java.util.AbstractList;
import java.util.List;

public class Draw2 extends Carta{

    Draw2(String color) {
        super(color, "draw2");
    }

    public void applyEffect(JuegoUNO juego) {
        juego.verJugadorTurno().recibirCartas(juego.levantarDeMazo(2));
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideTipo(this.getTipo());
    }

    boolean coincideColor(String color) {
        return color.equals(this.getColor());
    }

    boolean coincideNumero(int numero) {
        return false;
    }

    boolean coincideTipo(String tipo) {
        return this.getTipo().equals(tipo);
    }

    public String toString() {
        return "+2 " + getColor();
    }
}
