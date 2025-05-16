public class Skip extends Carta{
    private String tipo;

    Skip(String color) {
        super(color);
        tipo = "skip";
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
        return color + " Skip";
    }

}
