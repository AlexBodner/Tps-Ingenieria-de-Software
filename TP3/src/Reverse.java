public class Reverse extends Carta{

    Reverse(String color) {
        super(color);
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra instanceof Reverse;
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

    public String toString() {
        return color + " Reverse";
    }
}
