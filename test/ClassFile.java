
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class ClassFile<E> implements MLM<E> {
    private int COMPANY_REVENUE;
    private String username;
    private ArrayList<String> usernames;
    private ArrayList<Double> useramount;
    private ArrayList<String> encrypted;
    private ArrayList<Integer> id;
    private Node root;

    public ClassFile() {
        this.COMPANY_REVENUE = 0;
        this.root = new Node("admin");
        this.usernames = new ArrayList();
        this.useramount = new ArrayList();
        this.encrypted = new ArrayList();
        this.id = new ArrayList();
    }
    
    
    
    @Override
    public void create() {
        System.out.print("Enter the new username: ");
        Scanner s1 = new Scanner(System.in);
        Node newNode = new Node(s1.nextLine());
        System.out.println();
        System.out.print("Enter the user who recommend the user: ");
        String user = s1.nextLine();
        root.getChildren();
        
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
    public String encrypt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String decrypt() {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void setFee(Number fee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
