import java.io.*;
import java.net.*;
import java.util.*;

public class Marketplace {
    //Hashmaps are temporarily fucked up cuz im not good at them
    //Creates hashmaps for sellers and customers.
    //Basically think of an ArrayList, where each element has a title (the string)
    //And each title is associated with a sellers/customers account
    public static HashMap <String, Sellers> sellers = new HashMap<String, Sellers>();
    public static HashMap <String, Customers> customers = new HashMap<String, Customers>();

    public static void main(String[] args) throws IOException {
        populateHashMaps();
        Scanner scan = new Scanner(System.in);
        boolean validUser = true;
        
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
                boolean loggedIn = false;
                do {
                    BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                    System.out.println("Enter username:");
                    String username = scan.nextLine();
                    System.out.println("Enter password:");
                    String password = scan.nextLine();
    
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
                        int selection = 0;
                        do {
                            System.out.println("What would you like to do?");
                            System.out.println("1. Create a product");
                            System.out.println("2. Edit/Delete a product");
                            System.out.println("3. Create a store");
                            System.out.println("4. View stores");
                            System.out.println("5. Logout");
                            selection = scan.nextInt();
                            scan.nextLine();
            
                            switch(selection) {
                                //Create product
                                case 1:
                                    System.out.println("What's the name of the product?");
                                    String product = scan.nextLine();
                                    System.out.println("Which store will it go in?");
                                    //print stores
                                    System.out.print(" (" + (sellers.get(username)).getStores() + ")" );
                                    System.out.println("(case sensitive)");
                                    String productStore = scan.nextLine();
                                    
                                    System.out.println("What's the product description?");
                                    String description = scan.nextLine();
                                    System.out.println("How many are available?");
                                    int quantity = scan.nextInt();
                                    scan.nextLine();
                                    System.out.println("How much does it cost?");
                                    double price = scan.nextDouble();
                                    scan.nextLine();

                                    (sellers.get(username)).addProduct(product, productStore, description, quantity, price);
                                break;
                                //Edit/Delete product
                                case 2:
                                break;
                                //Create store
                                case 3:
                                    System.out.println("What's the name of the store?");
                                    String storeName = scan.nextLine();
                                    (sellers.get(username)).addStore(storeName);
                                break;
                                //View Stores
                                case 4:
                                    String allStores = (sellers.get(username)).getStores();
                                    System.out.println(allStores);
                                break;
                                case 5:
                                System.out.println("Bye!");
                                break;
                                default:
                                System.out.println("Please enter a valid input");
                            }
                        } while (selection != 5);
                        
                    } else {
                        System.out.println("Login failed!");
                    }
                } while (!loggedIn);
                
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
            Sellers newSeller = new Sellers(username, password);
            sellers.put(username, newSeller);
        } else {
            Customers newCustomer = new Customers(username, password);
            customers.put(username, newCustomer);
        }
    }

    public static void populateHashMaps() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
        String line = br.readLine();

        while (line != null) {
            String[] splitLine = line.split(";");

            if (splitLine[0].equals("Seller")) {
                sellers.put(splitLine[1], new Sellers(splitLine[1], splitLine[2]));
            } else {
                customers.put(splitLine[1], new Customers(splitLine[1], splitLine[2]));
            }
            line = br.readLine();
        }
    }
}