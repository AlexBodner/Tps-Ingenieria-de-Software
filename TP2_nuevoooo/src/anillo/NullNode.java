package anillo;

class NullNode extends Node {
    private Node next;
    private Node prev;
    private final Ring ring;

    NullNode(Ring ring) {
        this.ring = ring;
        this.next = null;
        this.prev = null;
    }

    void setNext(Node next) { this.next = next;}

    void setPrev(Node prev) {this.prev = prev;}

    Object current() { throw new RuntimeException("Ring is empty"); }

    Node add(Object cargo) {
        RegularNode node = new RegularNode(cargo);
        node.setNext(node);
        node.setPrev(node);
        return node;
    }

    Node next() {
        throw new RuntimeException("Ring is empty");
    }

    Object data() {
        throw new RuntimeException("Ring is empty");
    }

    Node remove() {
        throw new RuntimeException("Ring is empty"); // sigue siendo NullNode
    }
}

