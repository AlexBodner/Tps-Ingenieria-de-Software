package anillo;

import java.util.Stack;

class RegularNode extends Node {
    private final Object cargo;
    private RegularNode next;
    private RegularNode prev;

    RegularNode(Object cargo) {
        this.cargo = cargo;
    }

    void setNext(RegularNode next) {
        this.next = next;
    }

    void setPrev(RegularNode prev) {
        this.prev = prev;
    }

    public Node getPrev() {
        return this.prev ;
    }

    @Override
    Node add(Object cargo) {
        RegularNode node = new RegularNode(cargo);
        node.setPrev(this.prev);
        node.setNext(this);
        this.prev.setNext(node);
        this.setPrev(node);
        return node;
    }

    @Override
    Node next() {
        return this.next;
    }

    @Override
    Object data() {
        return this.cargo;
    }

    @Override
    Node remove() {
        this.prev.setNext(this.next);
        this.next.setPrev(this.prev);
        return this.next;
    }
    Node  rollback(Node node){
        return node.next();
    }
}
