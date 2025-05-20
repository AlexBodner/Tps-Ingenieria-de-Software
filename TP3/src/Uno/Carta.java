package Uno;

abstract class Carta {
    abstract boolean acepta(Carta otra);
    abstract boolean coincideColor(String color);
    abstract boolean coincideNumero(int numero);
    abstract boolean coincideTipo(String tipo);

    public JuegoUNO serJugada(JuegoUNO juego, Jugador jugador){
        if(this.acepta(juego.getPozo())) {
            juego.setPozo(this);
            jugador.removerCarta(this);
        }
        return juego;
    }
}
