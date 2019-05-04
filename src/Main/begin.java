package Main;

import java.util.Scanner;

public class begin {
    public static ClassFile mlm = new ClassFile();
    public static void main(String[] args) { String mark = "-1";
        Scanner s = new Scanner(System.in);
        MainGUI GUI = new MainGUI();
        /**GUI NOTICE:
         * Only create, delete, and display are working for now.
         * -Luqman
         */
        GUI.setLocationRelativeTo(null);
        GUI.setVisible(true);
        System.out.println("Welcome to main page of DreamCorporation!!!");
        while(!mark.equals("0")){
            System.out.print("1 Create new user."
                    + "\n2 Retrieve the data of the user chosen."
                    + "\n3 Update the data of the user chosen."
                    + "\n4 Delete the user chosen."
                    + "\n5 View the tree of the hierachy."
                    + "\n6 Get the revenue of each generations."
                    + "\n7 Change the registration fees."
                    + "\n8 Save and obtain the directory of data file (in txt.file)."
                    + "\n0 Close the program."
                    + "\nEnter the input: ");
            mark = s.nextLine();
            switch (mark) {
                case "1":
                    System.out.print("Enter the new username: ");
                    String newUser = s.nextLine();
                    System.out.print("Enter the user who recommend the user: ");
                    String userParent = s.nextLine();
                    mlm.create(mlm.encrypt(newUser),mlm.encrypt(userParent));
                    
                    break;
                case "2":
                    System.out.print("Enter the username: ");
                    String userName = s.nextLine();
                    TreeNode <String> user = new TreeNode (mlm.encrypt(userName));
                    System.out.println(mlm.retrieve(user));
                    if(!userName.equals("admin")){
                        System.out.print("Enter password to decrypt the parent username: ");
                        int password = s.nextInt();
                        System.out.println("Parent name: " + mlm.decrypt((String)mlm.getNode(mlm.getRoot(),user.encrypteddata).parent.encrypteddata, password));
                        s.nextLine();
                    }
                    break;
                case "3":
                    System.out.print("Enter the username: ");
                    String username = s.nextLine();
                    TreeNode <String> updateUser = new TreeNode (mlm.encrypt(username));
                    mlm.update(updateUser);
                    break;
                case "4":
                    System.out.print("Enter the username that need to be deleted:");
                    String tempUser = s.nextLine();
                    mlm.delete(mlm.encrypt(tempUser));
                    break;
                case "5":
                    mlm.display();
                    break;
                case "6"://get revenue for each generations method
                    break;
                case "7":
                    System.out.println("Enter new fee amount: ");
                    double newFee = s.nextDouble();
                    s.nextLine();
                    mlm.setFee(newFee);
                    break;
                case "8":
                    System.out.println("The data are saved.");
                    mlm.save();
                    System.out.println();
                    break;
                case "0":
                    System.out.println("The server will shut down.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input. Please enter the input again.");
                    break;
            }
        }
    }
}