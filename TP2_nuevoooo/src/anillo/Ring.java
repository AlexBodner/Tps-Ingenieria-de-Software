package anillo;

public class Ring {
    private Node current;
    private Node first;
    private Node last;

    private static class Node {
        Object data;
        Node next;

        Node(Object cargo) {
            this.data = cargo;
            this.next = this; // Circular por defecto
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
            this.last.next = n;
            this.last = n;
            n.next = this.first;
            this.current = n;
        }else{
            this.current = n;
            this.first = this.current;
            this.last = this.current;
        }
        return this;
    }

    public Ring remove() {
        return null;
    }
}
