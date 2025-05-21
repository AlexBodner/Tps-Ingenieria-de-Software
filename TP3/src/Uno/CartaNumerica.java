package Uno;

class CartaNumerica extends Carta {
    int numero;
    private String tipo;
    String color;

    CartaNumerica(String color, int numero) {
        this.color = color;
        this.numero = numero;
        tipo = "numerica";
    }

    public JuegoUNO serJugada(JuegoUNO juego){
        if(this.acepta(juego.getPozo())) {
            juego.setPozo(this);
        }
        return juego;
    }

    boolean acepta(Carta otra) {
        return otra.coincideColor(this.color) || otra.coincideNumero(this.numero);
    }

    boolean coincideColor(String color) {
        return this.color.equals(color);
    }

    boolean coincideNumero(int numero) {
        return this.numero == numero;
    }

    boolean coincideTipo(String tipo) {
        return this.tipo.equals(tipo);
    }

    public String toString() {
        return color + " " + numero;
    }

    public String getColor() {return color;}
}