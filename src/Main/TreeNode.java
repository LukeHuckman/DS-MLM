package Main;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<E> {
    E data;
    double amount;
    List<TreeNode<E>> children = new ArrayList();
    TreeNode<E> parent;

    public TreeNode() {
        this.data = null;
        this.amount = 0;
    }
    
    public TreeNode(E data) {
        this.data = data;
        this.amount = 0;
    }
    
    public TreeNode<E> addChild(TreeNode<E> child){
        child.setParent(this);
        this.children.add(child);
        return child;
    }
    
    public void addChildren(List<TreeNode<E>> children){
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }
    
    public List<TreeNode<E>> getChildren() {
        return children;
    }

    public E getData() {
        return data;
    }

    public TreeNode<E> getParent() {
        return parent;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setChildren(List<TreeNode<E>> children) {
        this.children = children;
    }

    public void setParent(TreeNode<E> parent) {
        this.parent = parent;
    }    
}