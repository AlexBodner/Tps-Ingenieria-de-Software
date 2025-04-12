package anillo;

import java.util.Stack;

abstract class Node {
    //abstract void setNext(Node next);
    //abstract void setPrev(Node prev);
    abstract Node add(Object cargo);
    abstract Node next();
    abstract Object data();
    abstract Node remove();
    abstract Node  rollback(Node node);

}
