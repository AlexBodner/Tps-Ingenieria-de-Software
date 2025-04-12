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
    Node  rollback(Node node){
        return new NullNode();
    }

}

