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
            System.out.println("Welcome to the Marketplace!" +
                    "\nAre you a customer or a seller?" +
                    "\n1. Customer" +
                    "\n2. Seller" +
                    "\n3. Create Account");
            int user = scan.nextInt();
            scan.nextLine();

            //Customer section, currently just an outline
            if (user == 1) {
                boolean loggedIn = false;
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
                //Customer homepage
                if (loggedIn) {
                    System.out.println("What would you like to do?");
                    System.out.println("1. View marketplace");
                    System.out.println("2. Search for a product");
                    System.out.println("3. Logout");
                    int selection = scan.nextInt();
                    scan.nextLine();
    
                    switch(selection) {
                        //View marketplace
                        case 1:
                            ArrayList<String> market = new ArrayList<String>();
                            market = printMarket();
                            System.out.println("Market: (product, price, store)\n");
                            for (int i = 0; i < market.size(); i++) {
                                System.out.println(market.get(i));
                            }
                            System.out.print("\n");
                            System.out.println("Would you like to sort the market at all? (yes/no)");
                            String sort = scan.nextLine();
                            sort = sort.toLowerCase();
                            if (sort.equals("yes")) {
                                System.out.println("Would you like to sort by\n1. Price\n2. Quantity available");
                                int sorting = scan.nextInt();
                                scan.nextLine();

                                if (sorting == 1) {
                                    ArrayList<String> sortedMarket = (customers.get(username)).sortByPrice();
                                    for (int i = 0; i < sortedMarket.size(); i++) {
                                        System.out.println(sortedMarket.get(i));
                                    }
                                } else if (sorting == 2) {
                                    ArrayList<String> sortedMarket = (customers.get(username)).sortByQuantity();
                                    for (int i = 0; i < sortedMarket.size(); i++) {
                                        System.out.println(sortedMarket.get(i));
                                    }
                                } else {
                                    System.out.println("That is not a valid choice!");
                                }
                            }
                            System.out.println("Which product would you like to view? (case sensitive)");
                            String selectedProduct = scan.nextLine();
                            String viewProduct = viewProduct(selectedProduct);
                            System.out.println("\n" + viewProduct);
                            System.out.println("\nWould you like to purchase this product? (yes/no)");
                            String purchasing = scan.nextLine();
                            if (purchasing.equals("yes")) {
                                System.out.println("How many?");
                                int quantity = scan.nextInt();
                                scan.nextLine();
                                int availableQuantity = checkQuantity(selectedProduct);
    
                                if (availableQuantity - quantity > 0) {
                                    productPurchased(selectedProduct, username, quantity);
                                } else {
                                    System.out.println("There aren't that many available!");
                                }
                            } else {
                                System.out.println("Ok!");
                            }
    
                        break;
                        //Search for product
                        case 2:
                            System.out.println("Do you want to search by\n1.Product name\n2. Store name\n3. Product description");
                            int choice = scan.nextInt();
                            scan.nextLine();
                            if (choice == 1) {
                                System.out.println("Please type the name of the product you're searching for:");
                                String product = scan.nextLine();
                                (customers.get(username)).searchProductName(product);
                            } else if (choice == 2) {
                                System.out.println("Please type the name of the store you're searching for:");
                                String store = scan.nextLine();
                                (customers.get(username)).searchProductStore(store);
                            } else if (choice == 3) {
                                System.out.println("Please type part of a description you're searching for:");
                                String description = scan.nextLine();
                                (customers.get(username)).searchProductDescription(description);
                            } else {
                                System.out.println("That's not a valid option!");
                            }
                        break;

                        //Logout
                        case 3:
                        System.out.println("Bye!");
                        break;
                        default:
                        System.out.println("Please enter a valid input");
                    }
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
                        //Seller homepage
                        do {
                            System.out.println("What would you like to do?");
                            System.out.println("1. Create a product");
                            System.out.println("2. Edit/Delete a product");
                            System.out.println("3. Create a store");
                            System.out.println("4. View stores");
                            System.out.println("5. View Seller Dashboard");
                            System.out.println("6. Logout");
                            selection = scan.nextInt();
                            scan.nextLine();
            
                            switch(selection) {
                                //Create product
                                case 1:
                                    System.out.println("What's the name of the product?");
                                    String product = scan.nextLine();
                                    System.out.print("Which store will it go in?");
                                    //print stores
                                    System.out.print(" (" + (sellers.get(username)).getStores() + ")" );
                                    System.out.println(" (case sensitive)");
                                    String productStore = scan.nextLine();
                                    
                                    //Get all info for product, add product
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
                                System.out.println("What would you like to do?\n1. Edit\n2. Delete");
                                int choice = scan.nextInt();
                                scan.nextLine();

                                //Edit product (deletes, then adds with changes)
                                if (choice == 1) {
                                    System.out.println("Which product would you like to edit?");
                                    String products = (sellers.get(username)).getProducts();
                                    System.out.println(products);
                                    String editProduct = scan.nextLine();

                                    System.out.println("What's the name of the product?");
                                    String newProduct = scan.nextLine();
                                    System.out.print("Which store will it go in?");
                                    //print stores
                                    System.out.print(" (" + (sellers.get(username)).getStores() + ")" );
                                    System.out.println(" (case sensitive)");
                                    String newProductStore = scan.nextLine();
                                    
                                    System.out.println("What's the product description?");
                                    String newDescription = scan.nextLine();
                                    System.out.println("How many are available?");
                                    int newQuantity = scan.nextInt();
                                    scan.nextLine();
                                    System.out.println("How much does it cost?");
                                    double newPrice = scan.nextDouble();
                                    scan.nextLine();

                                    (sellers.get(username)).deleteProduct(editProduct);
                                    (sellers.get(username)).addProduct(newProduct, newProductStore, newDescription, newQuantity, newPrice);
                                //Deletes product
                                } else if (choice == 2) {
                                    System.out.println("Which product would you like to delete?");
                                    String products = (sellers.get(username)).getProducts();
                                    System.out.println(products);
                                    String unwantedProduct = scan.nextLine();
                                    (sellers.get(username)).deleteProduct(unwantedProduct);
                                } else {
                                    System.out.println("Pick a valid number bitch");
                                }
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
                                //View Seller Dashboard
                                case 5:
                                String[] stores = ((sellers.get(username)).getStores()).split(", ");
                                String[] storeCustomers = 
                                String[] itemsSold =
                                String[] products = 
                                String[] sales = 
                                SellersDashboard newDash = new SellersDashboard
                                (String[] stores, String[] storeCustomers, String[] itemsSold, String [] products, String [] sales);
                                newDash.viewDashboard();
                                break;
                                //Logout
                                case 6:
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
            //Add new user authentication?
            } else if (user == 3) {
                boolean userTaken;
                String username;
                do {
                    System.out.println("Please enter a username:");
                    username = scan.nextLine();
                    BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                    String line = br.readLine();
                    userTaken = false;
    
                    //Makes sure username is not taken
                    while (line != null) {
                        String[] splitLine = line.split(";");
                        if (splitLine[1].equals(username)) {
                            userTaken = true;
                            System.out.println("This username is taken!");
                        }
                        line = br.readLine();
                    }
                } while (userTaken);
                
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
                    System.out.println("Please enter a valid number");
                }
            } else {
                System.out.println("That's not a valid input!");
                validUser = false;
            }
        } while (!validUser);
    }

    //This will eventually print the marketplace for customers
    public static ArrayList<String> printMarket() throws IOException {
        ArrayList<String> market = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader("products.txt"));
        String line = br.readLine();

        while (line != null) {
            String[] splitLine = line.split(";");
            
            if (Integer.parseInt(splitLine[4]) > 0) {
                market.add(splitLine[2] + " | $" + splitLine[5] + " | " + splitLine[1]);
            }
            line = br.readLine();
        }
        return market;
    }

    public static String viewProduct(String product) throws IOException {
        String productView = "";
        BufferedReader br = new BufferedReader(new FileReader("products.txt"));
        String line = br.readLine();

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[2].equals(product)) {
                productView = splitLine[2] + "\nPrice: $" + splitLine[5] + "\nQuantity Available: " + splitLine[4] + "\nDescription: " + splitLine[3] + "\nStore: " + splitLine[1];
            }
            line = br.readLine();
        }
        return productView;
    }

    public static int checkQuantity(String product) throws IOException{
        int quantity = 0;
        BufferedReader br = new BufferedReader(new FileReader("products.txt"));
        String line = br.readLine();

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[2].equals(product)) {
                quantity = Integer.parseInt(splitLine[4]);
            }
            line = br.readLine();
        }
        return quantity;
    }
    
    public static void productPurchased(String product, String username, int quantity) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("products.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("purchased.txt", true), true);
        String line = br.readLine();
        int newQuantity = 0;

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[2].equals(product)) {
                newQuantity = Integer.parseInt(splitLine[4]) - quantity;
            }
            if (newQuantity > 0) {
                (sellers.get(splitLine[0])).deleteProduct(product);
                (sellers.get(splitLine[0])).addProduct(splitLine[2], splitLine[1], splitLine[3], newQuantity, Double.parseDouble(splitLine[5]));
                pw.write(username + ";" + product + ";" + splitLine[5] + ";" + splitLine[4]);
                pw.println();
                pw.close();
            }
            line = br.readLine();
        }
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