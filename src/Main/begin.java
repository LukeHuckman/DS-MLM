package Main;

import java.util.Scanner;

public class begin {
    public static void main(String[] args) {
        ClassFile mlm = new ClassFile(); String mark = "-1";
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to main page of MLM!!!");
        while(!mark.equals("0")){
            System.out.print("1 Create new user."
                    + "\n2 Retrieve the data of the user chosen."
                    + "\n3 Update the data of the user chosen."
                    + "\n4 Delete the user chosen."
                    + "\n5 View the tree of the hierachy."
                    + "\n6 Get the revenue of each generations."
                    + "\n7 Change the registration fees."
                    + "\n0 Close the program."
                    + "\nEnter the input: ");
            mark = s.nextLine();
            switch (mark) {
                case "1":
                    mlm.create();
                    break;
                case "2":
                    System.out.print("Enter the username: ");
                    Node <String> user = new Node (s.nextLine());
                    System.out.println(mlm.retrieve(user));
                        //retrieve method
                    break;
                case "3"://update method
                    break;
                case "4":
                    mlm.delete();
                    break;
                case "5":
                    mlm.display();
                    break;
                case "6"://get revenue for each generations method
                    break;
                case "7":
                    System.out.println("Enter new fee amount: ");
                    double newFee = s.nextDouble();
                    mlm.setFee(newFee);
                    break;
                case "0":
                    System.out.println("The server will shut down.");
                    break;
                default:
                    System.out.println("Invalid input. Please enter the input again.");
                    break;
            }
        }
    }
}