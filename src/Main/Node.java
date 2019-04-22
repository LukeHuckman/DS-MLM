package Main;

import java.util.ArrayList;
import java.util.List;

public class Node<E> {
    E data;
    double amount;
    List<Node<E>> children = new ArrayList();
    Node<E> parent;

    public Node() {
        this.data = null;
        this.amount = 0;
    }
    
    public Node(E data) {
        this.data = data;
        this.amount = 0;
    }
    
    public Node<E> addChild(Node<E> child){
        child.setParent(this);
        this.children.add(child);
        return child;
    }
    
    public void addChildren(List<Node<E>> children){
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }
    
    public List<Node<E>> getChildren() {
        return children;
    }

    public E getData() {
        return data;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setChildren(List<Node<E>> children) {
        this.children = children;
    }

    public void setParent(Node<E> parent) {
        this.parent = parent;
    }    
}