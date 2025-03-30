package anillo;

public class Ring {
    private Node current;

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
        if (this.current == null){
            this.current = new Node(cargo);
            return this;
        }

        Node n = new Node(cargo);
        Node right_node = this.current.next;
        this.current.next = n;
        n.next = right_node;
        this.current = n;
        return this;
    }

    public Ring remove() {
        return null;
    }
}
