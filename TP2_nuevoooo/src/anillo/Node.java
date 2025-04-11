package anillo;

abstract class Node {
    abstract void setNext(Node next);
    abstract void setPrev(Node prev);
    abstract Object current();
    abstract Node add(Object cargo);
    abstract Node next();
    abstract Object data();
    abstract Node remove();
}
