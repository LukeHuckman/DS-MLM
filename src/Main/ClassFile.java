package Main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class ClassFile<E> implements MLM<E> {
    private double COMPANY_REVENUE;//recruit revenue
    private double salesRevenue;
    private double TOTALdeletedNodeRevenue;
    private double fee;
    private ArrayList<Double> recruitcommission;
    private ArrayList<Double> salescommission;
    private ArrayList<String> usernames;//for reference (save method)
    private TreeNode<String> root;//check the calculations part
    private int idnumber;
    private int goodssold;
    private Graph graph = new SingleGraph("MLM Graph",false,true);
    private String decryptkey;

    public ClassFile() {
        this.COMPANY_REVENUE = 0;
        this.fee = 50.0;
        this.recruitcommission = new ArrayList();
        recruitcommission.add(0.5);
        recruitcommission.add(0.12);
        recruitcommission.add(0.09);
        recruitcommission.add(0.06);
        recruitcommission.add(0.03);
        this.salescommission = new ArrayList();
        salescommission.add(0.30);
        salescommission.add(0.15);
        salescommission.add(0.05);
        this.TOTALdeletedNodeRevenue = 0;
        this.goodssold = 0;
        this.root = new TreeNode<String>(encrypt("admin",decryptkey),"000000");
        this.usernames = new ArrayList();
        this.idnumber = 0;
        this.decryptkey = "0000";
        graph.addNode("admin");
        Node graphnode = graph.getNode("admin");
        graphnode.addAttribute("ui.style", "shape:circle;fill-color: red;size: 90px; text-alignment: center;");
        graphnode.addAttribute("ui.label", "Admin");
    }
    
    @Override
    public void create(String newencryptUser,String userParentid) {
        Scanner s1 = new Scanner(System.in);
        TreeNode<String> newNode = new TreeNode<String>(newencryptUser);
        /*graph.addNode(newencryptUser);
        Node graphnode = graph.getNode(newencryptUser);
        graphnode.addAttribute("ui.style", "shape:circle;fill-color: green;size: 90px; text-alignment: center;");
        graphnode.addAttribute("ui.label", newencryptUser);*/
        if(searchDATA(root,newencryptUser)){
            System.out.println("The username already exist.");
        }
        else{            
            System.out.println();
        //link to its parents
            if(userParentid.equals(root.id)){
                idnumber++;
                newNode.id = idConverter(idnumber) + idnumber;
                newNode.generation = 1;
                //graph.addEdge("Admin->"+newencryptUser, "admin", newencryptUser,true);  
                root.addChild(newNode); 
                recruitIncome(newNode); 
                usernames.add(newencryptUser);
                //GUI
                graph.addNode(decrypt(newencryptUser,decryptkey));
                Node graphnode = graph.getNode(decrypt(newencryptUser,decryptkey));
                graphnode.addAttribute("ui.style", "shape:circle;fill-color: green;size: 90px; text-alignment: center;");
                graphnode.addAttribute("ui.label", decrypt(newencryptUser,decryptkey));
                graph.addEdge("Admin->"+decrypt(newencryptUser,decryptkey), "admin", decrypt(newencryptUser,decryptkey),true);  
            }
            
            else{
                if(searchID(root,userParentid)){
                    String encryptuserParent = (String) getNodebyID(root,userParentid).encrypteddata;
                    idnumber++;
                    newNode.id = idConverter(idnumber) + idnumber;
                    newNode.generation = getNodebyID(root,userParentid).generation + 1;
                    //graph.addEdge(encryptuserParent+"->"+newencryptUser, encryptuserParent, newencryptUser,true);
                    insert(root,newNode,encryptuserParent);
                    recruitIncome(newNode); 
                    usernames.add(newencryptUser);   
                    //GUI
                    graph.addNode(decrypt(newencryptUser,decryptkey));
                    Node graphnode = graph.getNode(decrypt(newencryptUser,decryptkey));
                    graphnode.addAttribute("ui.style", "shape:circle;fill-color: green;size: 90px; text-alignment: center;");
                    graphnode.addAttribute("ui.label", decrypt(newencryptUser,decryptkey));
                    graph.addEdge(decrypt(encryptuserParent,decryptkey)+"->"+decrypt(newencryptUser,decryptkey), decrypt(encryptuserParent,decryptkey), decrypt(newencryptUser,decryptkey),true);
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
        current.getChildren().clear();
    }
    
    @Override
    public void delete(String userid) {
        if(root.getChildren().isEmpty()){
            System.out.println("There is nothing to deleted.");
        }
        else if(!userid.equals(root.id)){            
            if(searchID(root,userid)){
                TreeNode<String> target = getNodebyID(root,userid);
                //GUI not change yet first child node directly connected to root
                for(int i=0;i<getNodebyID(root,userid).getChildren().size();i++){
                    graph.addEdge(target.parent.encrypteddata +"->"+ target.getChildren().get(i).encrypteddata, (String) target.parent.encrypteddata , (String) target.getChildren().get(i).encrypteddata, true);
                }
                graph.removeNode(target.encrypteddata);
                int position = usernames.indexOf(target.encrypteddata);
                deleteNode(target);
                setGeneration(root);
                usernames.remove(position);
                TOTALdeletedNodeRevenue += target.companyrecruitamount;
                TOTALdeletedNodeRevenue += target.companysalesamount;
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
    
    public void setGeneration(TreeNode<String> current){
        if(current.parent==root){
            current.generation = 1;
        }
        else if(current.parent!=null){
            current.generation = current.parent.generation + 1;
        }
        if(!current.children.isEmpty()){
            current.getChildren().forEach(each -> setGeneration(each));
        }
    }
    
    @Override//retrieve the revenue of the user only(need what extra??)
    public String retrieveAdmin(String userid) {
        if(userid.equals(root.id)){
            double a = COMPANY_REVENUE + salesRevenue;
            String b = "Recruit revenue: RM " + COMPANY_REVENUE + "\n";
            b += "Sales revenue: RM " + salesRevenue + "\n";
            b += "Total revenue gained from deleted users: " + TOTALdeletedNodeRevenue + "\n";
            b += "Total revenue: RM " + a + "\n";
            b += "Total goods sold: RM " + goodssold + "\n";
            b += "Current numbers of registered users: " + usernames.size() + "\n";
            return b;
        }
        else if(searchID(root,userid)){
            String a = "ID: " + getNodebyID(root,userid).id + "\n";
            a += "Generation: " + getNodebyID(root,userid).generation + "\n";
            a += "Recruit revenue: RM " + getNodebyID(root,userid).recruitamount + "\n";
            a += "Sales revenue: RM " + getNodebyID(root,userid).salesamount + "\n";
            a += "Amount of goods sold: " + getNodebyID(root,userid).goodssell + "\n";
            a += "Company's recruit revenue that gained from this user: RM " + getNodebyID(root,userid).companyrecruitamount + "\n";
            a += "Company's recruit revenue that gained from this user: RM " + getNodebyID(root,userid).companysalesamount + "\n";
            a += "Encrypted name: " + getNodebyID(root,userid).encrypteddata + "\n";
            a += "Encrypted parent name: " + getNodebyID(root,userid).parent.encrypteddata + "\n";
            a += "Total number of users recruited: " + getNodebyID(root,userid).children.size() + "\n";
            return a;
        }
        else{
            return "The username is not exist.";
        }
    }
    
    public String retrieveUser(String userid) {
        if(searchID(root,userid)){
            String a = "ID: " + getNodebyID(root,userid).id + "\n";
            a += "Generation: " + getNodebyID(root,userid).generation + "\n";
            a += "Recruit revenue: RM " + getNodebyID(root,userid).recruitamount + "\n";
            a += "Sales revenue: RM " + getNodebyID(root,userid).salesamount + "\n";
            a += "Amount of goods sold: " + getNodebyID(root,userid).goodssell + "\n";
            a += "Encrypted name: " + getNodebyID(root,userid).encrypteddata + "\n";
            a += "Encrypted parent name: " + getNodebyID(root,userid).parent.encrypteddata + "\n";
            a += "Total number of users recruited: " + getNodebyID(root,userid).children.size() + "\n";
            return a;
        }
        else{
            return "The username is not exist.";
        }
    }
    public void sales(String id, int goods, double sales){
        goodssold += goods;
        if(id.equals("000000")){
            root.salesamount += sales;
        }
        else if(getNodebyID(root,id)!=null){
            salesIncome(getNodebyID(root,id), sales);
        }
        else{
            System.out.println("The user is not exist.");
        }
    }
    
    public void salesIncome(TreeNode user, double sales){
        int cnt = 1;
        double money = sales*(salescommission.get(0));
        user.salesamount += sales*(salescommission.get(0));
        TreeNode<String> temp = user;
        while(temp.parent!=null){
            if(temp.parent==root){
                root.salesamount += sales - money;
                user.companysalesamount = sales - money;
                break;
            }
            else{
                if(cnt>=1&&cnt<salescommission.size()){
                    temp.parent.salesamount += sales*(salescommission.get(cnt));
                    money += sales*salescommission.get(cnt);
                    temp = temp.parent;
                    cnt++;
                }
                else{
                    root.salesamount += sales - money;
                    user.companysalesamount = sales - money;
                    break;
                }
            }
        }
        salesRevenue = root.salesamount;
    }
    
    public void recruitIncome(TreeNode user){
        double money = 0;
        int cnt = 0;
        TreeNode<String> temp = user;
        while(temp.parent!=null){
            if(temp.parent==root){
                root.recruitamount += fee - money;
                user.companyrecruitamount = fee - money;
                break;
            }
            else{
                if(cnt>=0&&cnt<recruitcommission.size()){
                    temp.parent.recruitamount += fee*(recruitcommission.get(cnt));
                    money += fee*recruitcommission.get(cnt);
                    temp = temp.parent;
                    cnt++;
                }
                else{
                    root.recruitamount += fee - money;
                    user.companyrecruitamount = fee - money;
                    break;
                }
            }
        }
        COMPANY_REVENUE = root.recruitamount;
    }
    
    @Override
    public double getGenerationRevenue(int indexPlusONE,TreeNode<String> current,double companyrevenue){
        if(current.generation==indexPlusONE){
            companyrevenue += current.companyrecruitamount;//recruit only
        }
        for(TreeNode child:current.children){
            companyrevenue = getGenerationRevenue(indexPlusONE,child,companyrevenue);
        }
        return companyrevenue;
    }

    @Override
    public void update(String userid) {
        if(searchID(root,userid)&&!userid.equals(root.id)){
            TreeNode<String> target = getNodebyID(root,userid);
            Scanner s = new Scanner(System.in);
            String option1 = "";
            String option2 = "";
            while(!option1.equalsIgnoreCase("yes")||!option1.equalsIgnoreCase("no")){
                System.out.print("Do you wish to change the name of the username? (Yes / No): ");
                option1 = s.nextLine();
                if(option1.equalsIgnoreCase("yes")){
                    System.out.print("Enter new username: ");
                    String newData = s.nextLine();
                    if(!searchDATA(root,encrypt(newData,decryptkey))){
                        usernames.set(usernames.indexOf(target.encrypteddata), encrypt(newData,decryptkey));
                        target.encrypteddata = encrypt(newData,decryptkey);
                    }
                    else{
                        System.out.println("The user already exist.");
                    }
                    break;
                }
                else if(option1.equalsIgnoreCase("no")){
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
                    if(!searchID(root,newID)&&!newID.equals(root.id)){
                        target.id = newID;
                    }
                    else{
                        System.out.println("The user already exist.");
                    }
                    break;
                }
                else if(option2.equalsIgnoreCase("no")){
                    break;  
                }
                else{
                    System.out.println("Error input.");
                }
            }
        }
        else{
            System.out.println("The admin data cannot be altered.");
        } 
    }
    @Override
    public String encrypt(String name,String key) {
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
    public String decrypt(String encryptedname,String key) {
        if (key.equals(key)){
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

    @Override//2 files
    //userdata and companypersonal data(password,revenue)
    public void save() {
        String b = "";
        System.out.println(b);
        try{
            PrintWriter print = new PrintWriter(new FileOutputStream("data.txt"));
            String a = "ID\tUsername\tEncryptedUsername\tRevenue (RM)";
            print.write(a);
            print.println();
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
    public void display() {
        Scanner s = new Scanner(System.in);
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        System.out.print("Enter the decrypt key password: ");
        String password = s.nextLine();
        if(!password.equals(decryptkey)){
            System.out.println("Wrong password, the server will exit to mainpage.");
        }
        else{
            print(root," ");
        }
    }

    public void print(TreeNode<String> node,String space){
        String space2 = " ";
        String decryptdata = "";
        if(!node.encrypteddata.equalsIgnoreCase("admin")){
            decryptdata = decrypt(node.getEncrypteddata(),decryptkey);
        }
        else{
            decryptdata = "admin";
        }
        System.out.println(space + decryptdata);
        node.getChildren().forEach(each -> print(each, space + space2));
    }
    
    @Override
    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setdecryptkey(String key) {
        this.decryptkey = key;
    }

    public String getdecryptkey() {
        return decryptkey;
    }
    
    public double getRecruitCommission(int index){
        return this.recruitcommission.get(index);
    }
    
    public double getSalesCommission(int index){
        return this.salescommission.get(index);
    }
    
    public int getRecruitCommissionSize(){
        return recruitcommission.size();
    }
    
    public int getSalesCommissionSize(){
        return salescommission.size();
    }
    
    public void setRecruitCommission(int gen, double newCommission){
        this.recruitcommission.set(gen, newCommission);
    }
    
    public void setSalesCommission(int gen, double newCommission){
        this.salescommission.set(gen, newCommission);
    }
}
