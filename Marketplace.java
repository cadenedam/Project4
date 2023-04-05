import java.io.*;
import java.net.*;
import java.util.*;

public class Marketplace {
    //Creates hashmaps for sellers and customers.
    //Basically think of an ArrayList, where each element has a title (the string)
    //And each title is associated with a sellers/customers account
    public static Map <String, Sellers> sellers = new HashMap<String, Sellers>();
    public static Map <String, Customers> customers = new HashMap<String, Customers>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        boolean validUser = true;
        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
        
        //Makes sure people enter a correct value for this
        do {
            validUser = true;
            System.out.println("Welcome to the Marketplace!\nAre you a customer or a seller?\n1. Customer\n2. Seller\n3. Create Account");
            int user = scan.nextInt();
            scan.nextLine();

            //Customer section, currently just an outline
            if (user == 1) {
                //Add user authentication
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

            //Seller section
            } else if (user == 2) {
                System.out.println("Enter username:");
                String username = scan.nextLine();
                System.out.println("Enter password:");
                String password = scan.nextLine();
                boolean loggedIn = false;

                //User authentication (goes through users.txt file, validates password)
                String line;
                try {
                    line = br.readLine();
                    while (line != null) {
                        String[] lineSplit = line.split(";");
                        if (lineSplit[1].equals(username)) {
                            if (lineSplit[2].equals(password)) {
                                loggedIn = true;
                            }
                        }
                        line = br.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (loggedIn) {
                    System.out.println("What would you like to do?");
                    System.out.println("1. Create a product");
                    System.out.println("2. Edit/Delete a product");
                    System.out.println("3. Create a store");
                    System.out.println("4. View stores");
                    int selection = scan.nextInt();
                    scan.nextLine();
    
                    switch(selection) {
                        //Create product
                        case 1:
                            System.out.println("What's the name of the product?");
                            String product = scan.nextLine();
                            System.out.println("Which store will it go in?");
                            //print stores
                            System.out.println("(" + (sellers.get(username)).getStores() + ")" );
                        break;
                        //Edit/Delete product
                        case 2:
                        break;
                        //Create store
                        case 3:
                        break;
                        //View Stores
                        case 4:
                        break;
                        default:
                        System.out.println("Please enter a valid input");
                    }
                } else {
                    System.out.println("Login failed!");
                }

            //User creation system
            } else if (user == 3) {
                System.out.println("Please enter a username:");
                String username = scan.nextLine();
                System.out.println("Please enter a password:");
                String password = scan.nextLine();
                System.out.println("Are you a...\n1. Customer\n2. Seller");
                int userType = scan.nextInt();
                scan.nextLine();

                if (userType == 1) {
                    try {
                        addUser("Customer", username, password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (userType == 2) {
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

    //This will eventually print the marketplace for customers
    public String[] printMarket() {
        String[] market = null;
        return market;
    }

    //Creates new users and adds them to hashmaps
    //EVENTUALLY get rid of file system, authenticate with hashmaps instead
    public static void addUser(String userType, String username, String password) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("users.txt", true), true);
        pw.write(userType + ";" + username + ";" + password);
        pw.println();
        pw.close();

        //"put" adds them to the hashmaps, with the specified username, 
        //and the specified user profile
        if (userType.equals("Seller")) {
            sellers.put(username, new Sellers(username, password));
        } else {
            customers.put(username, new Customers(username, password));
        }
    }
}