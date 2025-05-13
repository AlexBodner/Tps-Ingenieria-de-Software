public class Draw2 extends Carta{

    Draw2(String color) {
        super(color);
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra instanceof Draw2;
    }

    boolean coincideColor(String color) {
        return color.equals(this.color);
    }

    boolean coincideNumero(int numero) {
        return false;
    }

    public String toString() {
        return color + " +2";
    }
}
