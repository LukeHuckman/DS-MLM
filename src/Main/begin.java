package Main;

import java.util.Scanner;

public class begin {
    public static void main(String[] args) {
        ClassFile mlm = new ClassFile(); String mark = "-1";
        Scanner s = new Scanner(System.in);
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
                    mlm.create(newUser);
                    
                    break;
                case "2":
                    System.out.print("Enter the username: ");
                    TreeNode <String> user = new TreeNode (s.nextLine());
                    System.out.println(mlm.retrieve(user));
                        //retrieve method
                    break;
                case "3"://update method
                    
                    break;
                case "4":
                    System.out.print("Enter the username that need to be deleted:");
                    String tempUser = s.nextLine();
                    mlm.delete(tempUser);
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