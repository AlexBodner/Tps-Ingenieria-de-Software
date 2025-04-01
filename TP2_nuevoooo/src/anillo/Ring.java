package anillo;

public class Ring {
    private RingState state;

    public Ring() {
        this.state = new EmptyRing();
    }

    public Ring next() {
        this.state = state.next();
        return this;
    }

    public Object current() {
        return this.state.current();
    }

    public Ring add(Object cargo) {
        this.state = this.state.add(cargo);
        return this;
    }

    public Ring remove() {
        this.state = this.state.remove();
        return this;
    }
}

interface RingState { // Interfaz con las funciones que nos piden.
    RingState next();
    Object current();
    RingState add(Object cargo);
    RingState remove();
}

// 2 posibles casos: anillo vacio / anillo no vacio

class EmptyRing implements RingState { // implementa las funciones de la interfaz para el caso de anillo vacio
    public RingState next() {
        throw new RuntimeException("Empty ring");
    }
    public Object current() {
        throw new RuntimeException("Empty ring");
    }
    public RingState add(Object cargo) {
        return new NonEmptyRing(new Node(cargo)); // ya no es mas un EmptyRing, cambia el state
    }
    public RingState remove() {
        throw new RuntimeException("Empty ring");
    }
}

class NonEmptyRing implements RingState { // Implementa las mismas funciones pero de otra forma - para anillo no vacio
    private Node current;
    // Contructor
    NonEmptyRing(Node current){
        this.current = current;
    }

    public RingState next() {
        return new NonEmptyRing(current.next);
    }
    public Object current() {
        return current.data;
    }
    public RingState add(Object cargo) {
        return new NonEmptyRing(current.add(cargo));
    }
    public RingState remove() {
        // Si es el unico nodo, se devuelve un estado EmpryRing,
        // sino se hace remove del nodo actual y se instancia un nuevo NonEmptyRing con el nuevo nodo actual.
        return current == current.next ? new EmptyRing() : new NonEmptyRing(current.remove());
    }
}

class Node {
    Object data;
    Node next;
    Node prev;

    Node(Object cargo) {
        this.data = cargo;
        this.next = this;
        this.prev = this;
    }

    Node add(Object cargo) {
        Node n = new Node(cargo);
        n.prev = this.prev;
        this.prev.next = n;
        this.prev = n;
        n.next = this;
        return n;
    }

    Node remove() {
        this.prev.next = this.next;
        this.next.prev = this.prev;
        return this.next;
    }
}