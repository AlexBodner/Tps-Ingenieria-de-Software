package anillo;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;

public class Ring {
    Stack<Function<Node, Node>> stack = new Stack<>();
    private Node current;

    public Ring() {
        this.current = new NullNode();
    }


    public Ring add(Object cargo) {
        Node snapshot = this.current; // para q no me pase referencia

        stack.push(node -> snapshot.rollback(node));
        this.current = this.current.add(cargo);
        return this;
    }

    public Ring next() {
        current = this.current.next();
        return this;
    }

    public Ring remove() {
        current.remove();
        current = stack.pop().apply(current);
        return this;
    }

    public Object current() {
        return current.data();
    }


}



