package Main;

import java.util.Scanner;

public class lol {

    public static void main(String[] args) {
        ClassFile mlm = new ClassFile();
        int mark = -1;
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to main page of MLM!!!");
        while(mark != 0){
            System.out.println("1 Create new user.");
            System.out.println("2 Retrieve the data of the user chosen.");
            System.out.println("3 Update the data of the user chosen.");
            System.out.println("4 Delete the user chosen.");
            System.out.println("5 View the tree of the hierachy.");
            System.out.println("6 Get the revenue of each generations.");   
            System.out.println("7 Change the registration fees.");  
            System.out.println("0 Close the program.");
            System.out.print("Enter the input: ");
            mark = s.nextInt();
            if(mark == 1){
                mlm.create();
            }
            else if(mark == 2){
                //retrieve method
            }
            else if(mark == 3){
                //update method
            }
            else if(mark == 4){
                mlm.delete();
            }
            else if(mark == 5){
                mlm.display();
            }
            else if(mark == 6){
                //get revenue for each generations method
            }
            else if(mark == 7){
                System.out.println("Enter new fee amount: ");
                double newFee = s.nextDouble();
                mlm.setFee(newFee);
            }
            else if(mark == 0){
                System.out.println("The server will shut down.");
            }
            else{
                System.out.println("Invalid input. Please enter the input again.");
            }
            
        }
        
    }
    

    
}
