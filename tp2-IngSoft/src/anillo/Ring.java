package anillo;

public class Ring {
    private Node current;

    public Ring() {
        this.current = new EmptyNode();
    }

    public Ring next() {
        this.current = current.getNext();
        return this;
    }

    public Object current() {
        return this.current.getData();
    }

    public Ring add(Object cargo) {
        this.current = this.current.add(cargo);
        return this;
    }

    public Ring remove() {
        this.current = this.current.remove().advance();
        return this;
    }
}

class Node {
    Object data;
    Node next;
    Node prev;
    Node(Object cargo, Node next, Node prev) {
        this.data = cargo;
        this.next = next;
        this.prev = prev;
    }
    Node advance(){
        return this;
    }
    Node add(Object cargo) {
        Node n = new Node(cargo,this.next, this.prev);
        n.setPrev(this.prev) ;
        this.prev.next = n;
        this.prev = n;
        n.setNext(this);
        return n;
    }

    Node remove() {
        Node n1 =  this.prev;
        Node n2 = this.next;
        n1.setNext(new EmptyNode());
        n2.setPrev(new EmptyNode());
        return new EmptyNode(this.prev,this.next);
    }
    Node getNext() {
        return this.next;
    }
    Node getPrev() {
        return this.prev;
    }
    void setNext(Node next) {
        this.next = next;
    }
    void setPrev(Node prev) {
        this.prev = prev;
    }
    Object getData(){
        return this.data;
    }
}
class EmptyNode extends Node{
    EmptyNode() {
        super(null,null,null);
    }
    EmptyNode(Node n1, Node n2) {
        super(null,null,null);

        n1.setNext( n2) ;
        n2.setPrev( n1 );
        this.setNext(n2);
    }
    Node advance(){
        return this.next;
    }
    Node add(Object cargo) {
        Node n = new Node(cargo, null , null);
        n.prev = n;
        n.next = n;
        return n;
    }
    Node remove() {
        throw new RuntimeException("Cannot remove in Empty ring");
    }
    Node getData() {
        throw new RuntimeException("Cannot get data in Empty ring");
    }
    Node getNext() {
        throw new RuntimeException("Cannot get next in Empty ring");
    }

}