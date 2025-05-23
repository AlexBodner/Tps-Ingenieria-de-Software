package Uno;

public class Skip extends Carta{

    Skip(String color) {
        super(color, "skip");
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideTipo(this.getTipo());
    }

    public String toString() {
        return  "skip " + getColor();
    }

    public void applyEffect(JuegoUNO juego) {
        juego.avanzarTurno();
    }

}
