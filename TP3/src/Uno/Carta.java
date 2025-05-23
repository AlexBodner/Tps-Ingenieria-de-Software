package Uno;

abstract public class Carta {
    private String tipo;
    private String color;

    public Carta(String color, String tipo) {
        this.tipo = tipo;
        this.color = color;
    }

    abstract boolean acepta(Carta otra);
    public  void applyEffect(JuegoUNO juego){
        return;
    };

    public String getTipo(){return tipo;}
    public String getColor(){return color;}
    public void setColor(String color){this.color = color;}
    boolean coincideTipo(String tipo) {
        return this.getTipo().equals(tipo);
    }
    boolean coincideColor(String color) {
        return this.getColor().equals(color);
    }
    boolean coincideNumero(int numero) {
        return false;
    }
}
