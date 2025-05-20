package Uno;

public class Skip extends Carta{
    private String tipo;
    String color;
    Skip(String color) {
        this.color = color;
        tipo = "skip";
    }

    public JuegoUNO serJugada(JuegoUNO juego, Jugador jugador){
        if(this.acepta(juego.getPozo())) {
            juego.setPozo(this);
            jugador.removerCarta(this);
            juego.getJugadorTurno(); // salteamos uno
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
        return false;
    }

    boolean coincideTipo(String tipo) {
        return this.tipo.equals(tipo);
    }

    public String toString() {
        return  "skip " + color;
    }

}
