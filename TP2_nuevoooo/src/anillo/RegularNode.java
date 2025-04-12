package anillo;

class RegularNode extends Node {
    private final Object cargo;
    private Node next;
    private Node prev;

    RegularNode(Object cargo) {
        this.cargo = cargo;
    }

    void setNext(Node next) {
        this.next = next;
    }

    void setPrev(Node prev) {
        this.prev = prev;
    }

    Object current(){ return this.cargo; }

    Node add(Object cargo) {
        RegularNode node = new RegularNode(cargo);
        node.setPrev(this.prev);
        node.setNext(this);
        this.prev.setNext(node);
        this.setPrev(node);
        return node;
    }

    Node next() {
        return this.next;
    }

    Object data() {
        return this.cargo;
    }

    Node remove() {
        this.prev.setNext(this.next);
        this.next.setPrev(this.prev);
        return this.next;
    }
}
