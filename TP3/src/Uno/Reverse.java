package Uno;

public class Reverse extends Carta{
    private String tipo;
    String color;

    Reverse(String color) {
        this.color = color;
        tipo = "reverse";
    }

    public JuegoUNO serJugada(JuegoUNO juego, Jugador jugador){
        if(this.acepta(juego.getPozo())) {
            juego.setPozo(this);
            jugador.removerCarta(this);
            return juego.reverse();
        }
        return juego;
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra.coincideTipo(this.tipo);
    }

    boolean coincideColor(String color) {
        return color.equals(this.color);
    }

    boolean coincideNumero(int numero) {
        return false;
        // tenemos que poner false porque sino cuando querramos jugar
        // una carta especial (no numerica) sobre una numerica
        // hace: coincideNumero || coincideColor y si el numero da true
        // por default, va a aceptar la jugada siempre
    }

    boolean coincideTipo(String tipo) {
        return this.tipo.equals(tipo);
    }

    public String toString() {
        return "reverse " + color;
    }
}
