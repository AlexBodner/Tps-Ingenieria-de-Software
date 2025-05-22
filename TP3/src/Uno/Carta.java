package Uno;

abstract public class Carta {
    private String tipo;
    private String color;

    public Carta(String color, String tipo) {
        this.tipo = tipo;
        this.color = color;
    }

    abstract boolean acepta(Carta otra);
    abstract boolean coincideColor(String color);
    abstract boolean coincideNumero(int numero);
    abstract boolean coincideTipo(String tipo);
    public  void applyEffect(JuegoUNO juego){
        return;
    };

    public String getTipo(){return tipo;}
    public String getColor(){return color;}
    public void setColor(String color){this.color = color;}
}
