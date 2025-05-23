package Uno;

public class Reverse extends Carta{

    Reverse(String color) {
        super(color, "reverse");
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideTipo(this.getTipo());
    }

    public String toString() {
        return "reverse " + getColor();
    }
    
    public  void applyEffect(JuegoUNO juego){
        juego.reverse();
    };

}

