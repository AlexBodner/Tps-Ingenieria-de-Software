package anillo;

import java.util.Stack;

public class NullNode extends Node {

    public NullNode() {

    }
    @Override
    Node add(Object cargo) {
        RegularNode node = new RegularNode(cargo);
        node.setNext(node);
        node.setPrev(node);
        return node;
    }

    @Override
    Node next() {
        throw new RuntimeException("Cannot call next, Ring is empty");
    }

    @Override
    Object data() {
        throw new RuntimeException("Cannot get data, Ring is empty");
    }


    @Override
    Node remove() {
        throw new RuntimeException("Cannot remove on Empty Ring");
    }
    Node  rollback(Node node){
        return new NullNode();
    }

}

