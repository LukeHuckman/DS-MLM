package Main;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<E> {
    E encrypteddata;
    int id;
    double amount;
    List<TreeNode<E>> children = new ArrayList();
    TreeNode<E> parent;

    public TreeNode() {
        this.encrypteddata = null;
        this.amount = 0;
        this.id = 0;
    }
    
    public TreeNode(E encrypteddata) {
        this.encrypteddata = encrypteddata;
        this.amount = 0;
        this.id = 0;
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

    public void setEncrypteddata(E encrypteddata) {
        this.encrypteddata = encrypteddata;
    }

    public E getEncrypteddata() {
        return encrypteddata;
    }

    public TreeNode<E> getParent() {
        return parent;
    }

    public void setChildren(List<TreeNode<E>> children) {
        this.children = children;
    }

    public void setParent(TreeNode<E> parent) {
        this.parent = parent;
    }    
}