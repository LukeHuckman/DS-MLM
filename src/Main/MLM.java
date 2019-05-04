package Main;

public interface MLM<E> {
    //create new user information
    public void create(String newencryptUser,String encryptuserParent);
    //retrieve the user information
    public String retrieve(String userid);
    //update the user information
    public void update(String userid);
    //public void update();
    //delete the user information
    public void delete(String userid);
    //encrypt the username
    public String encrypt(String name);
    //decrypt the username
    public String decrypt(String encryptedname,String password);
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

