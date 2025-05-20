package Uno;

public class Wildcard extends Carta{
    String color;
    String tipo;

    public Wildcard(String color) {
        this.color = color;
        this.tipo = "wildcard";
    }

    public JuegoUNO serJugada(JuegoUNO juego, Jugador jugador){
        if(this.acepta(juego.getPozo())) {
            juego.setPozo(this);
            jugador.removerCarta(this);
        }
        return juego;
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra.coincideTipo(this.tipo);
    }

    boolean coincideColor(String color) {
        return this.color.equals(color);
    }

    boolean coincideNumero(int numero) {
        return true;
    }

    boolean coincideTipo(String tipo) {
        return this.tipo.equals(tipo);
    }
}
