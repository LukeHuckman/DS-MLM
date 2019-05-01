package Main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class ClassFile<E> implements MLM<E> {
    private double COMPANY_REVENUE;
    private double fee;
    private ArrayList<String> usernames;//for reference (save method)
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
            idnumber++;          
            System.out.println();
        //link to its parents
            System.out.print("Enter the user who recommend the user: ");
            String userParent = s1.nextLine();
            if(!userParent.equalsIgnoreCase("admin"))
                    graph.addEdge(userParent+"->"+newUser, userParent, newUser,true);
            if(userParent.equalsIgnoreCase("admin")){
                newNode.id = idnumber;
                newNode.encrypteddata = encrypt(newUser);
                graph.addEdge("Admin->"+newUser, "admin", newUser,true);  
                root.addChild(newNode); 
                income(newNode); 
                usernames.add(newUser);              
            }
            else{
                if(search(root,userParent)){
                    newNode.id = idnumber;
                    newNode.encrypteddata = encrypt(newUser);
                    insert(root,newNode,userParent);
                    income(newNode); 
                    usernames.add(newUser);   
                }
                else{
                    System.out.println("There is no such user.");
                }
            }
        }
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
            String a = "Username: " + getNode(root,user.data).data + "\n";
            a += "Encrypted name: " + getNode(root,user.data).encrypteddata + "\n";
            a += "ID: " + getNode(root,user.data).id;
            //parent in encrypted
            return a;
            
        }
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
        String encoded = Base64.getEncoder().encodeToString(name.getBytes());
   // Reverse the string
        String reverse = new StringBuffer(encoded).reverse().toString();

        StringBuilder tmp = new StringBuilder();
        final int OFFSET = 4;
        for (int i = 0; i < reverse.length(); i++) {
            tmp.append((char)(reverse.charAt(i) + OFFSET));
        }
        return tmp.toString();
    }

    @Override
    public String decrypt(String encryptedname,int password) {
        if (password == 1234){
            StringBuilder tmp = new StringBuilder();
        final int OFFSET = 4;
        for (int i = 0; i < encryptedname.length(); i++) {
            tmp.append((char)(encryptedname.charAt(i) - OFFSET));
        }

        String reversed = new StringBuffer(tmp.toString()).reverse().toString();
        return new String(Base64.getDecoder().decode(reversed));
        }
        else
            return "The Key is Incorrect!";
    }

    @Override
    public void save() {
        String b = "";
        //savepart2(root,b);
        System.out.println(b);
        try{
            //Scanner scan = new Scanner(new FileInputStream("data.txt"));
            PrintWriter print = new PrintWriter(new FileOutputStream("data.txt"));
            String a = "|ID\t|Username\t|Encrypted Username\t|Revenue (RM)  |";
            print.write(a);
            print.println();
            String c = "|-------+---------------+-----------------------+--------------|";
            print.write(c);
            print.println();
            for(int i=0;i<usernames.size();i++){
                b = "|" + getNode(root,usernames.get(i)).id + "\t|" + getNode(root,usernames.get(i)).data + "\t\t|" + getNode(root,usernames.get(i)).encrypteddata + "\t\t\t|" + getNode(root,usernames.get(i)).amount + "\t\t|";
                print.write(b);
                print.println();
            }
            
            //scan.close();
            print.close();
        }catch(IOException e){
            System.out.println("File output Error");
        }
    }
    
    public void savepart2(TreeNode<String> current,String b){
        
        /*if(current.equals(root)){
            for(TreeNode child:current.children){
                b += child.id + "\t" + child.data + "\t" + child.encrypteddata + "\t" + child.amount + "\n";
                savepart2(child,b);
            }
        }
        else{
            for(TreeNode child:current.children){
                b += child.id + "\t" + child.data + "\t" + child.encrypteddata + "\t" + child.amount + "\n";
                savepart2(child,b);
            }
        }*/
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
