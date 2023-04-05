import java.io.*;
import java.net.*;
import java.util.*;

public class Marketplace {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean validUser = true;
        
        do {
            validUser = true;
            System.out.println("Welcome to the Marketplace!\nAre you a customer or a seller?\n1. Customer\n2. Seller");
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
}