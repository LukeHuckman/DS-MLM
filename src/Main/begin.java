package Main;

import java.util.Scanner;

public class begin {
    public static String password = "0000";
    public static String mark = "-1";
    public static ClassFile mlm = new ClassFile();
    public static void main(String[] args) { 
        Scanner s = new Scanner(System.in);
        MainGUI GUI = new MainGUI();
        /**GUI NOTICE:
         * Only create, delete, and display are working for now.
         * -Luqman
         */
        GUI.setLocationRelativeTo(null);
        GUI.setVisible(true);
        //load the data from file
        String option = " ";
        while(!option.equals("user")||!option.equals("admin")||!option.equals("exit")){
            System.out.println("Welcome to main page of DreamCorporation!!!");
            System.out.print("Are you is user or admin? (Enter {'user' / 'admin' / 'exit' to end the program}): ");
            option = s.nextLine();
            if(option.equals("user")){
                String username = " ";                
                while(!username.equals("exit")){
                    System.out.print("Enter the username (Enter 'exit' to end the program.): ");
                    username = s.nextLine();
                    if(username.equals("exit")){
                        break;
                    }
                    else if(mlm.searchDATA(mlm.getRoot(), mlm.encrypt(username))&&!username.equals("admin")){
                        System.out.println(mlm.retrieve(mlm.getNodebyencryptUser(mlm.getRoot(), mlm.encrypt(username)).id));
                        System.out.println("Press enter to exit.");
                        s.nextLine();
                    }
                    else{
                        System.out.println("The username is not exist.");
                        break;
                    }
                }
                
            }
            else if(option.equals("admin")){
                String pass = "";
                while(!pass.equals(password)){
                    System.out.print("Enter the password: ");
                    pass = s.nextLine();
                    if(pass.equals(password)){
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
                                System.out.print("Enter the user ID who recommend the user (Admin ID is 000000): ");
                                String userParentid = s.nextLine();
                                mlm.create(mlm.encrypt(newUser),userParentid);
                    
                                break;
                            case "2":
                                System.out.print("Enter the username ID: ");
                                String userNameid = s.nextLine();
                                System.out.println(mlm.retrieve(userNameid));
                                if(mlm.searchID(mlm.getRoot(), userNameid)&&!userNameid.equals(mlm.getRoot().id)){
                                    System.out.print("Enter password to decrypt both the username and the parent's username: ");
                                    String password = s.nextLine();
                                    if(password.equals(mlm.getKey())){
                                        System.out.println("Username: " + mlm.decrypt((String)mlm.getNodebyID(mlm.getRoot(),userNameid).encrypteddata, password));
                                        System.out.println("Parent name: " + mlm.decrypt((String)mlm.getNodebyID(mlm.getRoot(),userNameid).parent.encrypteddata, password));
                                    }
                                    else{
                                        System.out.println("The key is incorrect.");
                                    }
                                }
                                break;
                            case "3":
                                System.out.print("Enter the username ID: ");
                                String usernameID = s.nextLine();
                                mlm.update(usernameID);
                                break;
                            case "4":
                                System.out.print("Enter the user ID that need to be deleted:");
                                String userID = s.nextLine();
                                mlm.delete(userID);
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
                                System.out.println("The server will back to homepage.");
                                
                                //System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid input. Please enter the input again.");
                                break;
                            }
                        
                        }
                        mark = "";
                    }
                    else{
                        System.out.println("Wrong password. The system will automatically shutdown.");
                        break;
                    }
                    break;
                }
                
            }
            else if(option.equals("exit")){
                System.exit(0);
            }
            else{
                System.out.println("Incorrect input. Please enter again.");
            }
        }
        
        
    }
}