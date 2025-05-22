package Uno;

public class Skip extends Carta{

    Skip(String color) {
        super(color, "skip");
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideTipo(this.getTipo());
    }

    boolean coincideColor(String color) {
        return this.getColor().equals(color);
    }

    boolean coincideNumero(int numero) {
        return false;
    }

    boolean coincideTipo(String tipo) {
        return this.getTipo().equals(tipo);
    }

    public String toString() {
        return  "skip " + getColor();
    }

    public void applyEffect(JuegoUNO juego) {
        juego.avanzarTurno();
    }

}
