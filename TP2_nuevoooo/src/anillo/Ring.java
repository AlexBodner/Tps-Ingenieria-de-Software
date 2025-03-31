package anillo;

public class Ring {
    private Node current;


    private static class Node {
        Object data;
        Node next;
        Node prev;
        Node(Object cargo) {
            this.data = cargo;
            this.next = this; // Circular por defecto
            this.prev = this;
        }
    }

    public Ring next() {
        if (this.current == null) {throw new RuntimeException("Empty ring");}
        this.current = this.current.next;
        return this;
    }

    public Object current() {
        if (this.current == null){ throw new RuntimeException( "Empty ring" ); }
        return this.current.data;
    }

    public Ring add( Object cargo ) {
        Node n = new Node(cargo);
        if (this.current!=null) { // si ya hay cosas en el ring
            Node prev = this.current.prev;

            n.prev = prev;
            prev.next = n;

            this.current.prev = n;
            n.next = this.current;

            this.current = n;
            return this;
        }
        this.current = n;
        return this;
    }

    public Ring remove() {
        if (this.current == null){ throw new RuntimeException( "Empty ring" ); }
        if (this.current == this.current.next){ // solo un elemento en el anillo
            this.current = null;
            return this;
        }
        Node prev = this.current.prev; // me guardo el prev de current
        this.current = this.current.next; // avanzo al next de current
        this.current.prev = prev; // establezco relacion: prev <- next
        this.current.prev.next = this.current; // establezco relacion: prev -> next
        return this;
    }
}
