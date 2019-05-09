package Main;

public interface MLM<E> {
    //create new user information
    public void create(String newencryptUser,String encryptuserParent);
    //retrieve the user information
    public String retrieveAdmin(String userid);
    //update the user information
    public void update(String userid);
    //public void update();
    //delete the user information
    public void delete(String userid);
    //encrypt the username
    public String encrypt(String name,String key);
    //decrypt the username
    public String decrypt(String encryptedname,String key);
    //save the users data in txt file
    public void saveUserData();
    //see the entire tree of users
    public void display();
    //see the revenue of each generation
    public double getGenerationRevenue(int indexPlusONE,TreeNode<String> current,double companyrevenue);
    //change the registration fee
    public void setFee(double fee);
    
}

