import java.io.*;
import java.net.*;
import java.util.*;

public class Marketplace {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean validUser = true;
        
        do {
            validUser = true;
            System.out.println("Welcome to the Marketplace!\nAre you a customer or a seller?\n1. Customer\n2. Seller\n3. Create Account");
            int user = scan.nextInt();
            scan.nextLine();

            if (user == 1) {
                System.out.println("What would you like to do?");
                System.out.println("1. View marketplace");
                System.out.println("2. Search for a product");
                System.out.println("3. Logout");
                int selection = scan.nextInt();
                scan.nextLine();

                switch(selection) {
                    case 1:
                    break;
                    case 2:
                    break;
                    case 3:
                    break;
                    default:
                    System.out.println("Please enter a valid input");
                }

            } else if (user == 2) {
                System.out.println("What would you like to do?");
                System.out.println("1. Create a product");
                System.out.println("2. Edit/Delete a product");
                System.out.println("3. Create a store");
                System.out.println("4. View stores");
                int selection = scan.nextInt();
                scan.nextLine();

                switch(selection) {
                    case 1:
                    break;
                    case 2:
                    break;
                    case 3:
                    break;
                    case 4:
                    break;
                    default:
                    System.out.println("Please enter a valid input");
                }

            } else if (user == 3) {
                System.out.println("Please enter a username:");
                String username = scan.nextLine();
                System.out.println("Please enter a password:");
                String password = scan.nextLine();
                System.out.println("Are you a...\n1. Customer\n2. Seller");
                int userType = scan.nextInt();
                scan.nextLine();

                if (userType == 1) {
                    Customers newCustomer = new Customers(username, password);
                    try {
                        addUser("Customer", username, password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (userType == 2) {
                    Sellers newSeller = new Sellers(username, password);
                    try {
                        addUser("Seller", username, password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            } else {
                System.out.println("That's not a valid input!");
                validUser = false;
            }
        } while (!validUser);
    }

    public String[] printMarket() {
        String[] market = null;
        return market;
    }

    public static void addUser(String userType, String username, String password) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("users.txt", true), true);
        pw.write(userType + ";" + username + ";" + password);
        pw.println();
        pw.close();
    }
}