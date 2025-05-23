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

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (((Carta) obj).coincideTipo(this.getTipo())
                && ((Carta) obj).coincideColor(this.getColor())) {
            return true;
        }
        return false;
    }
}
