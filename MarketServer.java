import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MarketServer {

    public static HashMap <String, Sellers> sellers = new HashMap<String, Sellers>();
    public static HashMap <String, Customers> customers = new HashMap<String, Customers>();

    public static void main(String[] args) throws IOException {
        populateHashMaps();
        boolean validUser = false;
        ServerSocket serverSocket = new ServerSocket(4242);

        System.out.println("Waiting for client to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);

        String connected = "Connected to server!";
        socketWriter.write(connected);
        socketWriter.println();
        socketWriter.flush();

        do {
            String user = socketReader.readLine();
            int userNum = Integer.parseInt(String.valueOf(user.charAt(0)));
            socketWriter.write(userNum);
            socketWriter.println();
            socketWriter.flush();

            if (userNum == 1) {
                boolean loggedIn = false;
                BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                do {
                    //User authentication (goes through users.txt file, validates password)
                    String userInfo = socketReader.readLine();
                    String[] parsedInfo = userInfo.split(";");
                    String username = parsedInfo[0];
                    String password = parsedInfo[1];
                    String line;
                    try {
                        line = br.readLine();
                        while (line != null) {
                            String[] lineSplit = line.split(";");
                            if (lineSplit[1].equals(username)) {
                                if (lineSplit[2].equals(password)) {
                                    loggedIn = true;
                                    validUser = true;
                                }
                            }
                            line = br.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace(); // don't know if I need to do an error message?
                    }

                    if (loggedIn) {
                        socketWriter.write("loggedIn");
                        socketWriter.println();
                        socketWriter.flush();
                    } else {
                        socketWriter.write("fail");
                        socketWriter.println();
                        socketWriter.flush();
                    }

                    //Customer homepage
                    if (loggedIn) {
                        int selectionNum = 0;
                        do {
                            String selectionInfo = socketReader.readLine();
                            selectionNum = Integer.parseInt(selectionInfo);

                            switch (selectionNum) {
                                //View marketplace
                                case 1:
                                    ArrayList<String> market = new ArrayList<String>();
                                    market = printMarket();
                                    String marketplace = null;

                                    for (int i = 0; i < market.size(); i++) {
                                        marketplace += market.get(i) + "\n";
                                    }
                                    if (marketplace == null) {
                                        marketplace = "There is nothing currently in the marketplace.";
                                    }

                                    socketWriter.write(marketplace);
                                    socketWriter.println();
                                    socketWriter.flush();

                                    int sort = Integer.parseInt(socketReader.readLine());

                                    if (sort == 0) {
                                        int sortingNum = Integer.parseInt(socketReader.readLine());

                                        if (sortingNum == 1) {
                                            ArrayList<String> sortedMarket = (customers.get(username)).sortByPrice(market);
                                            marketplace = null;
                                            for (int i = 0; i < sortedMarket.size(); i++) {
                                                marketplace += sortedMarket.get(i) + "\n";
                                            }
                                            if (marketplace == null) {
                                                marketplace = "There is nothing currently in the marketplace.";
                                            }
                                            socketWriter.write(marketplace);
                                            socketWriter.println();
                                            socketWriter.flush();

                                        } else if (sortingNum == 2) {
                                            ArrayList<String> sortedMarket = (customers.get(username)).sortByQuantity(market);
                                            marketplace = null;
                                            for (int i = 0; i < sortedMarket.size(); i++) {
                                                marketplace += sortedMarket.get(i) + "\n";
                                            }
                                            if (marketplace == null) {
                                                marketplace = "There is nothing currently in the marketplace.";
                                            }
                                            socketWriter.write(marketplace);
                                            socketWriter.println();
                                            socketWriter.flush();

                                        }
                                    } else {

                                    }

                                    String selectedProduct = socketReader.readLine();
                                    String viewProduct = viewProduct(selectedProduct);
                                    socketWriter.write(viewProduct);
                                    socketWriter.println();
                                    socketWriter.flush();
                                    int purchasing = 0;

                                    if (!viewProduct.isEmpty()) {
                                        purchasing = Integer.parseInt(socketReader.readLine());

                                        if (purchasing == 1) {
                                            int availableQuantity = checkQuantity(selectedProduct);
                                            int quantity = Integer.parseInt(socketReader.readLine());
                                            String available = "no";

                                            if (availableQuantity - quantity > 0) {
                                                productPurchased(selectedProduct, username, quantity);
                                                double price = checkPrice(selectedProduct);
                                                String store = checkStore(selectedProduct);
                                                String description = checkDescription(selectedProduct);
                                                (customers.get(username)).updatePurchaseHistory(selectedProduct, store, description, quantity, price);
                                                available = "yes";
                                                socketWriter.write(available);
                                                socketWriter.println();
                                            } else {
                                                socketWriter.write(available);
                                                socketWriter.println();
                                            }
                                        }
                                    }

                                    break;
                                //Search for product
                                case 2:
                                    ArrayList<String> newMarket = new ArrayList<String>();
                                    newMarket = printMarket();
                                    int choiceNum = Integer.parseInt(socketReader.readLine());

                                    if (choiceNum == 1) {
                                        String product = socketReader.readLine();
                                        (customers.get(username)).searchProductName(newMarket, product);
                                        //Write it back to client
                                    } else if (choiceNum == 2) {
                                        String store = socketReader.readLine();
                                        (customers.get(username)).searchProductStore(newMarket, store);
                                    } else if (choiceNum == 3) {
                                        String description = socketReader.readLine();
                                        (customers.get(username)).searchProductDescription(newMarket, description);
                                    }
                                    break;
                                case 3:
                                    String purchaseHistory = (customers.get(username)).getPurchaseHistory();
                                    socketWriter.write(purchaseHistory);
                                    socketWriter.println();
                                    break;
                                //Logout
                                case 4:
                                break;
                                default:
                            }
                        } while (selectionNum != 4);
                    } else {
                    }
                } while (!loggedIn);

                //Seller section LOGIN SECTION NEEDS TO BE I/O
            } else if (userNum == 2) {
                boolean loggedIn = false;
                do {
                    BufferedReader br = new BufferedReader(new FileReader("users.txt"));

                    String username = JOptionPane.showInputDialog(null, "Enter username:",
                            "Log In", JOptionPane.QUESTION_MESSAGE);
                    String password = JOptionPane.showInputDialog(null, "Enter password:",
                            "Log In", JOptionPane.QUESTION_MESSAGE);


                    //User authentication (goes through users.txt file, validates password)
                    String line;
                    try {
                        line = br.readLine();
                        while (line != null) {
                            String[] lineSplit = line.split(";");
                            if (lineSplit[1].equals(username)) {
                                if (lineSplit[2].equals(password)) {
                                    loggedIn = true;
                                    validUser = true;
                                }
                            }
                            line = br.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (loggedIn) {
                        String selection;
                        int selectionNum = 0;
                        //Seller homepage
                        do {
                            selection = socketReader.readLine();
                            selectionNum = Integer.parseInt(selection);

                            switch (selectionNum) {
                                //Create product
                                case 1:
                                    String product = socketReader.readLine();

                                    String[] storesArray = (sellers.get(username).getStores()).split(", ");

                                    for (String store : storesArray) {
                                        socketWriter.print(store + ",");
                                    }
                                    socketWriter.println();
                                    socketWriter.flush();

                                    String productStore = socketReader.readLine();
                                    boolean storeExists = storeExists(productStore);
                                    if (storeExists) {
                                        socketWriter.write("yes");
                                    } else {
                                        socketWriter.write("no");
                                    }

                                    if (storeExists) {
                                        //Get all info for product, add product
                                        String description = socketReader.readLine();
                                        int quantity = Integer.parseInt(socketReader.readLine());
                                        double price = Double.parseDouble(socketReader.readLine());

                                        sellers.get(username).addProduct(product, productStore, description, quantity, price);
                                    }
                                    break;
                                //Edit/Delete product
                                case 2:
                                    String choice = socketReader.readLine();
                                    int choiceNum = Integer.parseInt(choice);
                                    
                                    // Edit product (deletes, then adds with changes)
                                    if (choiceNum == 1) {
                                        String[] productsArray = (sellers.get(username).getProducts()).split(", ");
                                    
                                        for (String products : productsArray) {
                                            socketWriter.print(products + ", ");
                                        }
                                        socketWriter.println();
                                        socketWriter.flush();
                                    
                                        String editProduct = socketReader.readLine();
                                        String newProduct = socketReader.readLine();
                                    
                                        String[] storesArrays = (sellers.get(username).getStores()).split(", ");
                                    
                                        for (String store : storesArrays) {
                                            socketWriter.print(store + ", ");
                                        }
                                        socketWriter.println();
                                        socketWriter.flush();
                                    
                                        String newProductStore = socketReader.readLine();
                                        boolean newStoreExists = storeExists(newProductStore);
                                        if (newStoreExists) {
                                            socketWriter.write("yes");
                                        } else {
                                            socketWriter.write("no");
                                        }
                                    
                                        if (newStoreExists) {
                                            // Get all info for product, add product
                                            String newDescription = socketReader.readLine();
                                            int newQuantity = Integer.parseInt(socketReader.readLine());
                                            double newPrice = Double.parseDouble(socketReader.readLine());
                                    
                                            sellers.get(username).deleteProduct(editProduct);
                                            sellers.get(username).addProduct(newProduct, newProductStore, newDescription, newQuantity, newPrice);
                                        } else {
                                            socketWriter.println("That store doesn't exist!");
                                            socketWriter.flush();
                                        }
                                        // Deletes product
                                    } else if (choiceNum == 2) {
                                        String[] productsArray = (sellers.get(username).getProducts()).split(", ");
                                    
                                        for (String products : productsArray) {
                                            socketWriter.print(products + ", ");
                                        }
                                        socketWriter.println();
                                        socketWriter.flush();
                                    
                                        String unwantedProduct = socketReader.readLine();
                                        sellers.get(username).deleteProduct(unwantedProduct);
                                    }
                                    break;
                                
                                //Create store
                                case 3:
                                    String storeName = socketReader.readLine();
                                    (sellers.get(username)).addStore(storeName);
                                    break;
                                //View Stores
                                case 4:
                                    String allStores = (sellers.get(username)).getStores();
                                    socketWriter.write(allStores);
                                    socketWriter.println();
                                    break;
                                //View Seller Dashboard NEEDS TO BE CONVERTED TO I/O
                                case 5:
                                    String sellerStores = (sellers.get(username)).getStores();
                                    String[] stores = sellerStores.split(", ");
                                    SellersDashboard sellersDashboard = new SellersDashboard(stores);
                                    sellersDashboard.viewDashboard();
                                    break;
                                //Logout
                                case 6:
                                break;
                                default:
                            }
                        } while (selectionNum != 6);

                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } while (!loggedIn);

                //User creation system
                //Add new user authentication?
            } else if (userNum == 3) {
                boolean userTaken;
                String username;
                do {
                    username = JOptionPane.showInputDialog(null, "Please enter a username:",
                            "Create Account", JOptionPane.QUESTION_MESSAGE);

                    BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                    String line = br.readLine();
                    userTaken = false;

                    //Makes sure username is not taken
                    while (line != null) {
                        String[] splitLine = line.split(";");
                        if (splitLine[1].equals(username)) {
                            userTaken = true;
                            JOptionPane.showMessageDialog(null, "This username is taken!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        line = br.readLine();
                    }
                } while (userTaken);

                String password = JOptionPane.showInputDialog(null, "Please enter a password:",
                        "Create Account", JOptionPane.QUESTION_MESSAGE);
                String[] userTypeArray = {"1. Customer", "2. Seller"};
                String userType = (String) JOptionPane.showInputDialog(null, "\"Are you a...",
                        "Create Account", JOptionPane.QUESTION_MESSAGE, null, userTypeArray, userTypeArray[0]);
                int userTypeNum = Integer.parseInt(String.valueOf(userType.charAt(0)));
                if (userTypeNum == 1) {
                    try {
                        addUser("Customer", username, password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (userTypeNum == 2) {
                    try {
                        addUser("Seller", username, password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "That's not a valid input!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                validUser = false;
            }
        } while (!validUser);
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

        public static int checkQuantity(String product) throws IOException {
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
                if (newQuantity > 0 && !product.isEmpty()) {
                    (sellers.get(splitLine[0])).deleteProduct(product);
                    (sellers.get(splitLine[0])).addProduct(splitLine[2], splitLine[1], splitLine[3], newQuantity, Double.parseDouble(splitLine[5]));
                    pw.write(username + ";" + product + ";" + splitLine[1] + ";" + splitLine[5] + ";" + splitLine[4]);
                    pw.println();
                    pw.close();
                }
                line = br.readLine();
            }
        }

        public static String checkDescription(String product) throws IOException{
            String description = "";
            BufferedReader br = new BufferedReader(new FileReader("products.txt"));
            String line = br.readLine();
    
            while (line != null) {
                String[] splitLine = line.split(";");
                if (splitLine[2].equals(product)) {
                    description = splitLine[3];
                }
                line = br.readLine();
            }
            return description;
        }

        public static double checkPrice(String product) throws IOException{
            double price = 0;
            BufferedReader br = new BufferedReader(new FileReader("products.txt"));
            String line = br.readLine();
    
            while (line != null) {
                String[] splitLine = line.split(";");
                if (splitLine[2].equals(product)) {
                    price = Double.parseDouble(splitLine[5]);
                }
                line = br.readLine();
            }
            return price;
        }

        public static String checkStore(String product) throws IOException{
            String store = "";
            BufferedReader br = new BufferedReader(new FileReader("products.txt"));
            String line = br.readLine();
    
            while (line != null) {
                String[] splitLine = line.split(";");
                if (splitLine[2].equals(product)) {
                    store = splitLine[1];
                }
                line = br.readLine();
            }
            return store;
        }

        public static boolean storeExists(String store) throws IOException{
            boolean storeExists = false;
            BufferedReader br = new BufferedReader(new FileReader("stores.txt"));
            String line = br.readLine();
    
            while (line != null) {
                String[] splitLine = line.split(";");
                if (splitLine[1].equals(store)) {
                    storeExists = true;
                }
                line = br.readLine();
            }
            return storeExists;
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
}