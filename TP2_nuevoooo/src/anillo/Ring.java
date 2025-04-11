package anillo;

public class Ring {
    private Node current;

    public Ring() {
        this.current = new NullNode(this);
    }

    public Ring add(Object cargo) {
        this.current = this.current.add(cargo);
        return this;
    }

    public Ring next() {
        this.current = this.current.next();
        return this;
    }

    public Ring remove() {
        this.current = this.current.remove();
        return this;
    }

    public Object current() {
        return this.current.current();
    }
}



