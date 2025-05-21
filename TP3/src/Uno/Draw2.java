package Uno;

import java.util.AbstractList;
import java.util.List;

public class Draw2 extends Carta{
    private String tipo;
    String color;

    Draw2(String color) {
        this.color = color;
        this.tipo = "draw2";
    }

    public void applyEffect(JuegoUNO juego) {
        juego.getJugadorTurnoYSkip().recibirCartas(juego.levantarDeMazo(2));
        return ;
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra.coincideTipo(this.tipo);
    }

    boolean coincideColor(String color) {
        return color.equals(this.color);
    }

    boolean coincideNumero(int numero) {
        return false;
    }

    boolean coincideTipo(String tipo) {
        return this.tipo.equals(tipo);
    }

    public String toString() {
        return "+2 " + color;
    }

    public String getColor(){ return color;}
}
