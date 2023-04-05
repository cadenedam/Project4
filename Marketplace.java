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

            } else if (user == 2) {
    
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