package dsassignmenttesting;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class ClassFile<E> implements MLM<E> {
    private int COMPANY_REVENUE;
    private double fee;
    private String username;
    private ArrayList<Node> usernames;
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
        Node<String> newNode = new Node<String>(newUser);
        for(int a = 0;a<usernames.size()-1;a++){
            if(usernames.get(a).equals(newUser)){
                System.out.println("The user already exist.");
                break;
            }
            usernames.add(newNode);
        }
        
        //encrypted.add(encrypt(newUser));
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
        if(userParent.equals("admin")){
            root.addChild(newNode);
        }
        else{
            if(usernames.contains(newPar)){
                newPar.addChild(newNode);
                //how to link the parent node name with the child
                
            }
            else{
                System.out.println("There is no such user.");
            }
        }
        
        //System.out.println(newNode.data);
        //System.out.println(userParent);
        
        
    }

    @Override
    public String retrieve(Node user) {
        String temp = "RM ";
        temp += useramount.get(useramount.indexOf(user));
        return temp;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each -> print(each, appender + appender));
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
