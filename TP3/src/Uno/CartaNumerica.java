package Uno;

class CartaNumerica extends Carta {
    int numero;

    public CartaNumerica(String color, int numero) {
        super(color, "numerica");
        this.numero = numero;
    }
    boolean acepta(Carta otra) {
        return otra.coincideColor(this.getColor()) || otra.coincideNumero(this.numero);
    }

    boolean coincideNumero(int numero) {
        return this.numero == numero;
    }

    public String toString() {
        return getColor() + " " + numero;
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
           && ((Carta) obj).coincideColor(this.getColor())
           && ((Carta) obj).coincideNumero(this.numero)) {
            return true;
        }
        return false;
    }

}