public class Skip extends Carta{

    Skip(String color) {
        super(color);
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra instanceof Skip;
    }

    boolean coincideColor(String color) {
        return this.color.equals(color);
    }

    boolean coincideNumero(int numero) {
        return false;
    }

    public String toString() {
        return color + " Skip";
    }

}
