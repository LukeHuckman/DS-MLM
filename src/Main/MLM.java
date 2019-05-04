package Main;

public interface MLM<E> {
    //create new user information
    public void create(String newUser, String parent);
    //retrieve the user information
    public String retrieve(TreeNode<String> user);
    //update the user information
    public void update(TreeNode<String> user);
    //public void update();
    //delete the user information
    public void delete(String tempUser);
    //encrypt the username
    public String encrypt(String name);
    //decrypt the username
    public String decrypt(String encryptedname,int password);
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

