package Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class ClassFile<E> implements MLM<E> {
    private double COMPANY_REVENUE;
    private double fee;
    private ArrayList<String> usernames;
    private TreeNode<String> root;//check the calculations part
    private int idnumber;
    private Graph graph = new SingleGraph("MLM Graph",false,true);

    public ClassFile() {
        this.COMPANY_REVENUE = 0;
        this.fee = 50;
        this.root = new TreeNode<String>("admin");
        this.usernames = new ArrayList();
        this.idnumber = 0;
        graph.addNode("admin");
        Node graphnode = graph.getNode("admin");
        graphnode.addAttribute("ui.style", "shape:circle;fill-color: red;size: 90px; text-alignment: center;");
        graphnode.addAttribute("ui.label", "Admin");
    }
    
    @Override
    public void create(String newUser) {
        Scanner s1 = new Scanner(System.in);
        boolean mark2 = false;
        TreeNode<String> newNode = new TreeNode<String>(newUser);
        graph.addNode(newUser);
        Node graphnode = graph.getNode(newUser);
        graphnode.addAttribute("ui.style", "shape:circle;fill-color: green;size: 90px; text-alignment: center;");
        graphnode.addAttribute("ui.label", newUser);
        //System.out.println(search(root,newUser));
        if(search(root,newUser)){
            System.out.println("The username already exist.");
        }
        else{
            usernames.add(newUser);                
            System.out.println();
        //link to its parents
            System.out.print("Enter the user who recommend the user: ");
            String userParent = s1.nextLine();
            if(!userParent.equalsIgnoreCase("admin"))
                    graph.addEdge(userParent+"->"+newUser, userParent, newUser,true);
            for(int a = 0;a<usernames.size();a++){
                String temp = usernames.get(a);
                if(temp.equals(userParent)){
                    mark2 = true;
                    break;
                }
            }
            
            if(userParent.equalsIgnoreCase("admin")){
                    graph.addEdge("Admin->"+newUser, "admin", newUser,true);
                    root.addChild(newNode);
                    income(newNode); 
                }
            else{
                if(mark2){
                    insert(root,newNode,userParent);
                    income(newNode); 
                }
                else{
                    System.out.println("There is no such user.");
                }
            }
        }
        //normal create done, left calculation of revenue and encryption
        //encrypted.add(encrypt(newUser));
    }

    public boolean search(TreeNode<String> current,String newUser){//problem
        if(current.data.equals(newUser)){
            return true;
        }
        else{
            for(TreeNode child:current.children){
                boolean sub = search(child,newUser);
                if(sub==true){
                    return sub;
                }
            }
        }
        return false;
    }
    
    public TreeNode getNode(TreeNode<String> current,String newUser){//problem
        if(current.data.equals(newUser)){
            return current;
        }
        else{
            for(TreeNode child:current.children){
                TreeNode sub = getNode(child,newUser);
                if(sub!=null){
                    return sub;
                }
            }
        }
        return null;
    }
    
    public void insert(TreeNode<String> current,TreeNode<String> newUser,String data){
        for(int a=0;a<current.getChildren().size();a++){
            if(current.getChildren().get(a).data.equals(data)){
                current.getChildren().get(a).addChild(newUser);
                break;
            }
        }
        current.getChildren().forEach(each -> insert(each,newUser,data));
    }
    
    public void deleteNode(TreeNode<String> current){
        if(current.parent!=null){
            int index = current.parent.getChildren().indexOf(current);
            current.parent.getChildren().remove(current);
            for(TreeNode<String> each:current.getChildren()){
                each.setParent(root);
            }
            root.getChildren().addAll(index, current.getChildren());
        }
        current.getChildren().clear();;
    }
    
    @Override
    public void delete(String tempUser) {
        if(root.getChildren().isEmpty()){
            System.out.println("There is nothing to deleted.");
        }
        else{
            TreeNode<String> target = new TreeNode(tempUser);
            graph.removeNode(tempUser);
            int position = usernames.indexOf(tempUser);        
            if(search(root,tempUser)){
                System.out.println("have");
                deleteNode(getNode(root,tempUser));
                usernames.remove(position);
            }
            else{
                System.out.println("The user is not-exist.");
            }
        }
        
        //id.remove(position);
    }
    
    @Override//retrieve the revenue of the user only(need what extra??)
    public String retrieve(TreeNode<String> user) {
        if(user.data.equals(root.data)){
            return "RM " + COMPANY_REVENUE;
        }
        else{
            return retrieve2(user,root);
        }
    }

    public String retrieve2(TreeNode<String> user,TreeNode<String> ROOT) {
        String temp = "RM ";
        for(int a=0;a<ROOT.getChildren().size();a++){
            if(ROOT.getChildren().get(a).data.equals(user.getData())){
                temp += ROOT.getChildren().get(a).amount;
                break;
            }
        }
        ROOT.getChildren().forEach(each -> retrieve2(user, each));
        
        return temp;
    }
    
    public void income(TreeNode user){
        double money = 0;
        if(user.parent != null){
            user.parent.amount += fee/2;
            money += fee/2;
            if(user.parent.parent == null || user.parent.parent == root){
                root.amount += fee - money;
            }
            else{
                user.parent.parent.amount += fee/100*12;
                money += fee/100*12;
                if(user.parent.parent.parent == null || user.parent.parent.parent == root){
                    root.amount += fee - money;
                }
                else{
                    user.parent.parent.parent.amount += fee/100*9;
                    money += fee/100*9;
                    if(user.parent.parent.parent.parent == null || user.parent.parent.parent.parent == root){
                        root.amount += fee - money;
                    }
                    else{
                        user.parent.parent.parent.parent.amount += fee/100*6;
                        money += fee/100*6;
                        root.amount += fee - money;
                    }
                }
            }
        }
        else if(user.parent == root){
            root.amount += fee - money;
        }
        COMPANY_REVENUE = root.amount;
    }
    
    @Override
    public void update() {
        /*System.out.println("Enter the username: ");
        Scanner s2 = new Scanner(System.in);
        if(){
            System.out.print("Enter new username: ");
        }*/
    }

    @Override
    public String encrypt(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String decrypt(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save() {
        try{
            Scanner scan = new Scanner(new FileInputStream("data.txt"));
            scan.close();
        }catch(IOException e){
            System.out.println("File output Error");
        }

        
    }

    @Override
    public double getRevenue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        print(root," ");
    }

    public void print(TreeNode<String> node,String appender){
        String appender2 = " ";
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each -> print(each, appender + appender2));
    }
    @Override
    public double getRevenue(int gen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getUserRevenue(String encryptname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFee(double fee) {
        this.fee = fee;
    }
    
    
}
