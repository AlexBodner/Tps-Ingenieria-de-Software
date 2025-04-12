package anillo;

import java.util.Stack;

public class Ring {
    private Node current;
    private Stack<Node> stack = new Stack<>();

    public Ring() {
        this.current = new NullNode(this);
        this.stack.push(this.current);
    }

    public Ring add(Object cargo) {
        this.current = this.current.add(cargo);
        this.stack.push(this.current);
        return this;
    }

    public Ring next() {
        this.current = this.current.next();
        return this;
    }

    public Ring remove() {
        Node n = this.stack.pop();
        n.remove(); // Si era el ultimo del anillo falla, sino paso a hacer el remove
        this.current = this.current.remove();
        return this;
    }

    public Object current() {
        return this.current.current();
    }
}



