package dsassignmenttesting;

public interface MLM<E> {
    //create new user information
    public void create();
    //retrieve the user information
    public String retrieve(Node user);
    //update the user information
    public void update();
    //delete the user information
    public void delete();
    //encrypt the username
    public String encrypt(String name);
    //decrypt the username
    public String decrypt(String name);
    //save the users data in txt file
    public void save();
    //get the company revenue
    public double getRevenue();
    //see the entire tree of users
    public void display();
    //see the revenue of each generation
    public double getRevenue(int gen);
    //check the user revenue
    public double getUserRevenue(String encryptname);
    //change the registration fee
    public void setFee(double fee);
    
}

