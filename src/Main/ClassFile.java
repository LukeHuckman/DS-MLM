package Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassFile<E> implements MLM<E> {
    private int COMPANY_REVENUE;
    private double fee;
    private String username;
    private ArrayList<String> usernames;
    private ArrayList<Double> useramount;
    private ArrayList<String> encrypted;
    private ArrayList<Integer> id;
    private Node<String> root;

    public ClassFile() {
        this.COMPANY_REVENUE = 0;
        this.fee = 50;
        this.root = new Node<String>("admin");
        this.usernames = new ArrayList();
        this.useramount = new ArrayList();
        this.encrypted = new ArrayList();
        this.id = new ArrayList();
    }
    
    @Override
    public void create() {
        System.out.print("Enter the new username: ");
        Scanner s1 = new Scanner(System.in);
        String newUser = s1.nextLine();
        boolean mark1 = false;
        boolean mark2 = false;
        Node<String> newNode = new Node<String>(newUser);
        if(usernames.isEmpty()){
            usernames.add(newUser);
                if(id.isEmpty()){
                id.add(1);
                }
                else{
                    id.add((id.get(id.size()-1))+1);
                }
                System.out.println();
        
        //link to its parents
                System.out.print("Enter the user who recommend the user: ");
                String userParent = s1.nextLine();
                Node<String> newPar = new Node<String>(userParent);
                for(int a = 0;a<usernames.size();a++){
                    String temp = usernames.get(a);
                    mark2 = false;
                    if(temp.equals(userParent)){
                        mark2 = true;
                        break;
                    }
                }
                if(userParent.equals("admin")){
                    root.addChild(newNode);
                }
                else{
                    if(search(root,userParent)){
                        insert(root,newNode,userParent);
                    }
                    else{
                        System.out.println("There is no such user.");
                    }
                }
        }
        else{
        for(int a = 0;a<usernames.size();a++){
            String temp = usernames.get(a);
            mark1 = false;
            if(temp.equals(newUser)){
                mark1 = true;
                break;
            }
        }
            //System.out.println();
            if(mark1){
                System.out.println("The username already exist.");

            }
            else{
                usernames.add(newUser);
                if(id.isEmpty()){
                id.add(1);
                }
                else{
                    id.add((id.get(id.size()-1))+1);
                }
                System.out.println();
        
        //link to its parents
                System.out.print("Enter the user who recommend the user: ");
                String userParent = s1.nextLine();
                Node<String> newPar = new Node<String>(userParent);
                for(int a = 0;a<usernames.size();a++){
                    String temp = usernames.get(a);
                    mark2 = false;
                    if(temp.equals(userParent)){
                        mark2 = true;
                        break;
                    }
                }
                if(userParent.equals("admin")){
                    root.addChild(newNode);
                }
                else{
                    if(mark2){
                        insert(root,newNode,userParent);
                    }
                    else{
                        System.out.println("There is no such user.");
                    }
                }
            }
        //normal create done, left calculation of revenue and encryption
        }
        //encrypted.add(encrypt(newUser));
    }

    public boolean search(Node<String> current,String data){
        for(int a=0;a<current.getChildren().size();a++){
            if(current.getChildren().get(a).data.equals(data)){
                return true;
            }
        }
        current.getChildren().forEach(each -> search(each,data));
        return false;
    }
    
    public void insert(Node<String> current,Node<String> newUser,String data){
        for(int a=0;a<current.getChildren().size();a++){
            if(current.getChildren().get(a).data.equals(data)){
                current.getChildren().get(a).addChild(newUser);
                break;
            }
        }
        current.getChildren().forEach(each -> insert(each,newUser,data));
    }
    
    public void takeout(Node<String> current,Node<String> newUser,String data){
        List<Node<String>> list;
        List<Node<String>> list2 = new ArrayList();
        int b = current.getChildren().size();
        //while(current.getChildren()!=null){
        for(int a=0;a<b;a++){
            if(current.getChildren().get(a).data.equals(data)){
                list2 = current.getChildren().get(a).parent.getChildren();
                list = current.getChildren().get(a).getChildren();
                list2.remove(newUser);
                current.getChildren().get(a).parent.setChildren(list2);
                
                root.addChildren(list);
                a++;
            }
            //takeout(current.children.get(a),newUser,data);
        }
        current.getChildren().forEach(each -> takeout(each,newUser,data));
        //}
    }
    
    @Override
    public String retrieve(Node user) {
        String temp = "RM ";
        temp += useramount.get(useramount.indexOf(user));
        return temp;
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
    public void delete() {
        System.out.print("Enter the username that need to be deleted:");
        Scanner s3 = new Scanner(System.in);
        String temp1 = s3.nextLine();
        Node<String> target = new Node(temp1);
        boolean mark = false;
        int position = usernames.indexOf(temp1);
        for(int a = 0;a<usernames.size();a++){
            String temp = usernames.get(a);
            mark = false;
            if(temp.equals(temp1)){
                mark = true;
                break;
            }
        }
        if(mark){
            takeout(root,target,temp1);
        }
        usernames.remove(position);
        id.remove(position);
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
        print(root," ");
    }

    public void print(Node<String> node,String appender){
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
