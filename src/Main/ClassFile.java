package Main;

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
    private String key;

    public ClassFile() {
        this.COMPANY_REVENUE = 0;
        this.fee = 50;
        this.root = new TreeNode<String>(encrypt("admin"),"000000");
        this.usernames = new ArrayList();
        this.idnumber = 0;
        this.key = "0000";
        graph.addNode("admin");
        Node graphnode = graph.getNode("admin");
        graphnode.addAttribute("ui.style", "shape:circle;fill-color: red;size: 90px; text-alignment: center;");
        graphnode.addAttribute("ui.label", "Admin");
    }
    
    @Override
    public void create(String newencryptUser,String userParentid) {
        Scanner s1 = new Scanner(System.in);
        TreeNode<String> newNode = new TreeNode<String>(newencryptUser);
        graph.addNode(newencryptUser);
        Node graphnode = graph.getNode(newencryptUser);
        graphnode.addAttribute("ui.style", "shape:circle;fill-color: green;size: 90px; text-alignment: center;");
        graphnode.addAttribute("ui.label", newencryptUser);
        if(searchDATA(root,newencryptUser)){
            System.out.println("The username already exist.");
        }
        else{            
            System.out.println();
        //link to its parents
            if(userParentid.equals(root.id)){
                idnumber++;
                newNode.id = idConverter(idnumber) + idnumber;
                graph.addEdge("Admin->"+newencryptUser, "admin", newencryptUser,true);  
                root.addChild(newNode); 
                income(newNode); 
                usernames.add(newencryptUser);
            }
            
            else{
                if(searchID(root,userParentid)){
                    String encryptuserParent = (String) getNodebyID(root,userParentid).encrypteddata;
                    idnumber++;
                    newNode.id = idConverter(idnumber) + idnumber;
                    graph.addEdge(encryptuserParent+"->"+newencryptUser, encryptuserParent, newencryptUser,true);
                    insert(root,newNode,encryptuserParent);
                    income(newNode); 
                    usernames.add(newencryptUser);   
                }
                else{
                    System.out.println("There is no such user.");
                }
            }
        }
    }

    public String idConverter(int number){
        String a = "";
        int b = 100000;
        while(b>number){
            a+="0";
            b/=10;
        }
        return a;
    }
    
    public boolean searchDATA(TreeNode<String> current,String newUser){
        if(current.encrypteddata.equals(newUser)){
            return true;
        }
        else{
            for(TreeNode child:current.children){
                boolean sub = searchDATA(child,newUser);
                if(sub==true){
                    return sub;
                }
            }
        }
        return false;
    }
    
    public boolean searchID(TreeNode<String> current,String userID){
        if(current.id.equals(userID)){
            return true;
        }
        else{
            for(TreeNode child:current.children){
                boolean sub = searchID(child,userID);
                if(sub==true){
                    return sub;
                }
            }
        }
        return false;
    }
    
    public TreeNode getNodebyID(TreeNode<String> current,String userid){//problem
        if(current.id.equals(userid)){
            return current;
        }
        else{
            for(TreeNode child:current.children){
                TreeNode sub = getNodebyID(child,userid);
                if(sub!=null){
                    return sub;
                }
            }
        }
        return null;
    }
    
    public TreeNode getNodebyencryptUser(TreeNode<String> current,String encryptUser){//problem
        if(current.encrypteddata.equals(encryptUser)){
            return current;
        }
        else{
            for(TreeNode child:current.children){
                TreeNode sub = getNodebyencryptUser(child,encryptUser);
                if(sub!=null){
                    return sub;
                }
            }
        }
        return null;
    }
    
    public void insert(TreeNode<String> current,TreeNode<String> newUser,String data){
        for(int a=0;a<current.getChildren().size();a++){
            if(current.getChildren().get(a).encrypteddata.equals(data)){
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
    public void delete(String userid) {
        if(root.getChildren().isEmpty()){
            System.out.println("There is nothing to deleted.");
        }
        else if(!userid.equals(root.id)){            
            if(searchID(root,userid)){
                TreeNode<String> target = getNodebyID(root,userid);
                for(int i=0;i<getNodebyID(root,userid).getChildren().size();i++){
                    graph.addEdge(target.parent.encrypteddata +"->"+ target.getChildren().get(i).encrypteddata, (String) target.parent.encrypteddata , (String) target.getChildren().get(i).encrypteddata, true);
                }
                graph.removeNode(target.encrypteddata);
                int position = usernames.indexOf(target.encrypteddata);
                deleteNode(target);
                usernames.remove(position);
            }
            else{
                System.out.println("The user is not-exist.");
            }
        }
        else{
            System.out.println("The user is not-exist.");
        }
        
        //id.remove(position);
    }
    
    @Override//retrieve the revenue of the user only(need what extra??)
    public String retrieve(String userid) {
        if(userid.equals(root.id)){
            return "RM " + COMPANY_REVENUE;
        }
        else if(searchID(root,userid)){
            String a = "ID: " + getNodebyID(root,userid).id + "\n";
            a += "Revenue: " + getNodebyID(root,userid).amount + "\n";
            a += "Encrypted name: " + getNodebyID(root,userid).encrypteddata + "\n";
            a += "Encrypted parent name: " + getNodebyID(root,userid).parent.encrypteddata + "\n";
            return a;
        }
        else{
            return "The username is not exist.";
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
                        if(user.parent.parent.parent.parent.parent == null || user.parent.parent.parent.parent.parent == root){
                            root.amount += fee - money;
                        }
                        else{
                            user.parent.parent.parent.parent.amount += fee/100*3;
                            money += fee/100*3;
                            root.amount += fee - money;
                        }
                    }
                }
            }
        }
        else if(user.parent == root){
            root.amount += fee - money;
        }
        COMPANY_REVENUE = root.amount;
    }
    
    //@Override, boolean nameChange, String newData, boolean ParentChange, String newParent
    @Override
    public void update(String userid) {
        if(searchID(root,userid)&&!userid.equals(root.id)){
            TreeNode<String> target = getNodebyID(root,userid);
            Scanner s = new Scanner(System.in);
            String option1 = "";
            String option2 = "";
            while(!option1.equalsIgnoreCase("yes")||!option1.equalsIgnoreCase("no")){
            //while(true){
                System.out.print("Do you wish to change the name of the username? (Yes / No): ");
                option1 = s.nextLine();
                if(option1.equalsIgnoreCase("yes")){
                //if(nameChange){
                    System.out.print("Enter new username: ");
                    String newData = s.nextLine();
                    if(!searchDATA(root,encrypt(newData))){
                        usernames.set(usernames.indexOf(target.encrypteddata), encrypt(newData));
                        target.encrypteddata = encrypt(newData);
                    }
                    else{
                        System.out.println("The user already exist.");
                    }
                    break;
                }
                else if(option1.equalsIgnoreCase("no")){
                //else if(nameChange){
                    break;  
                }
                else{
                    System.out.println("Error input.");
                }
            }
            
            while(!option2.equalsIgnoreCase("yes")||!option2.equalsIgnoreCase("no")){
                System.out.print("Do you wish to change the ID number of the user? (Yes / No) ");
                option2 = s.nextLine();
                if(option2.equalsIgnoreCase("yes")){
                    System.out.print("Enter new ID: ");
                    String newID = s.nextLine();
                    if(!searchID(root,newID)||newID.equals(root.id)){
                        target.id = newID;
                    }
                    else{
                        System.out.println("The user already exist.");
                    }
                    break;
                }
                else if(option2.equalsIgnoreCase("no")){
                //else if(nameChange){
                    break;  
                }
                else{
                    System.out.println("Error input.");
                }
            //while(true){
                /*System.out.print("Do you wish to change the parent of the user? (Yes / No): ");
                option2 = s.nextLine();
                if(option2.equalsIgnoreCase("yes")){
                //if(ParentChange){
                    System.out.print("Enter new parent: ");
                    String userParent = s.nextLine();
                    if(search(root,encrypt(userParent))&&!userParent.equalsIgnoreCase("admin")){
                        TreeNode<String> targetParent = getNodebyID(root,encrypt(userParent));
                        target.parent.getChildren().remove(target);
                        targetParent.addChild(target);
                        target.setParent(targetParent);
                        if(targetParent.parent.encrypteddata.equals(target.encrypteddata)){
                            targetParent.setParent(root);
                            target.getChildren().remove(targetParent);
                            root.addChild(targetParent);
                        }
                        
                    }
                    else if(userParent.equalsIgnoreCase("admin")){
                        target.parent.getChildren().remove(target);
                        root.addChild(target);
                        target.setParent(root);
                    }
                    else{
                        System.out.println("The user is not exist.");
                    }
                    break;
                }
                else if(option2.equalsIgnoreCase("no")){
                //else if(!ParentChange){
                    break;  
                }
                else{
                    System.out.println("Error input.");
                }*/
            }
        }
        else{
            System.out.println("There is no such user.");
        }
        
    
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
    public String decrypt(String encryptedname,String password) {
        if (password.equals(key)){
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
        System.out.println(b);
        try{
            PrintWriter print = new PrintWriter(new FileOutputStream("data.txt"));
            String a = "|ID\t|Username\t|Encrypted Username\t|Revenue (RM)  |";
            print.write(a);
            print.println();
            String c = "|-------+---------------+-----------------------+--------------|";
            print.write(c);
            print.println();
            for(int i=0;i<usernames.size();i++){
                b = "|" + getNode(root,usernames.get(i)).id + "\t\t|" + getNode(root,usernames.get(i)).encrypteddata + "\t\t\t|" + getNode(root,usernames.get(i)).amount + "\t\t|";
                print.write(b);
                print.println();
            }
            
            //scan.close();
            print.close();
        }catch(IOException e){
            System.out.println("File output Error");
        }
    }

    public TreeNode<String> getRoot() {
        return root;
    }
    
    @Override
    public double getRevenue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display() {
        Scanner s = new Scanner(System.in);
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        System.out.print("Enter the decrypt key password: ");
        String password = s.nextLine();
        if(!password.equals(key)){
            System.out.println("Wrong password, the server will exit to mainpage.");
        }
        else{
            print(root," ");
        }
    }

    public void print(TreeNode<String> node,String appender){
        String appender2 = " ";
        String decryptdata = "";
        if(!node.encrypteddata.equalsIgnoreCase("admin")){
            decryptdata = decrypt(node.getEncrypteddata(),key);
        }
        else{
            decryptdata = "admin";
        }
        System.out.println(appender + decryptdata);
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

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    
    
    
}
