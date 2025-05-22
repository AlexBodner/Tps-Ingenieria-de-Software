package Uno;

public class Reverse extends Carta{

    Reverse(String color) {
        super(color, "reverse");
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideTipo(this.getTipo());
    }

    boolean coincideColor(String color) {
        return color.equals(this.getColor());
    }

    boolean coincideNumero(int numero) {
        return false;
        // tenemos que poner false porque sino cuando querramos jugar
        // una carta especial (no numerica) sobre una numerica
        // hace: coincideNumero || coincideColor y si el numero da true
        // por default, va a aceptar la jugada siempre
    }

    boolean coincideTipo(String tipo) {
        return this.getTipo().equals(tipo);
    }

    public String toString() {
        return "reverse " + getColor();
    }
    
    public  void applyEffect(JuegoUNO juego){
        juego.reverse();
    };

}

