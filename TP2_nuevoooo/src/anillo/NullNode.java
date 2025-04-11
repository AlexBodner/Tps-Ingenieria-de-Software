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

    void setNext(Node next) {
        this.next = next;
    }

    void setPrev(Node prev) {
        this.prev = prev;
    }

    Object current() { throw new RuntimeException("Ring is empty"); }

    @Override
    Node add(Object cargo) {
        RegularNode node = new RegularNode(cargo);
        node.setNext(node);
        node.setPrev(node);
        return node;
    }

    @Override
    Node next() {
        throw new RuntimeException("Ring is empty");
    }

    @Override
    Object data() {
        throw new RuntimeException("Ring is empty");
    }

    @Override
    Node remove() {
        return this; // sigue siendo NullNode
    }
}

