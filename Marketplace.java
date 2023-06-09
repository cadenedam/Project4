import javax.swing.*;
import java.io.*;
import java.net.*;
import java.lang.Runnable;

/**
 * Marketplace
 *
 * Marketplace is where the majority of our JOptionPanes is implemented,
 * and it is where the information is written to the server and processed back.
 * There are two type of users that can access the marketplace: Sellers and Customers.
 *
 * If the user does not already have an account they can create a new account (so long as the username is unique).
 * That new account is added to the list of either sellers or customers.
 *
 * Customers can view the marketplace where the available products are displayed, along with details about the product,
 * the customer can search based on the product name, store it's sold at or the description of the product for it,
 * review their purchase history until now, or log out.
 *
 * Sellers can create a new product, edit or delete an existing product, create a new store,
 * view the existing stores, view the seller's dashboard where information about the products
 * that'd been sold can be found, or log out.
 *
 * Concurrency allows multiple users to be logged in simultaneously, with real time updates made to the marketplace and the
 * products available for sale.
 */

public class Marketplace {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4242);
        DataInputStream socketReader = new DataInputStream(socket.getInputStream());
        DataOutputStream socketWriter = new DataOutputStream(socket.getOutputStream());
        //Creates the socket object for server socket connections.

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    boolean validUser = false;
                    do {
                        String[] marketMenuArray = {"Customer", "Seller", "Create Account"};
                        String user = (String) JOptionPane.showInputDialog(null, "Are you a customer or a seller?",
                                "Welcome to the Marketplace!", JOptionPane.QUESTION_MESSAGE,
                                null, marketMenuArray, marketMenuArray[0]);
                        socketWriter.writeUTF(user);
                        socketWriter.flush();
                        //Very first thing the user will see, prompts them for information about their account

                        //Customer section
                        int userNum = 0;
                        if (user != null && user.equalsIgnoreCase("Customer")) {
                            userNum = 1;
                        } else if (user != null && user.equalsIgnoreCase("Seller")) {
                            userNum = 2;
                        } else if (user != null && user.equalsIgnoreCase("Create Account")) {
                            userNum = 3;
                        }
                        boolean loggedIn = false;

                        //Proceeds with some logical statements depending on if the user already has an account in the
                        //users.txt file, and if so what type of user they are: Customer or Seller
                        switch (userNum) {
                            case 1:
                                //This is for Customers
                                loggedIn = false;
                                do {
                                    String username = JOptionPane.showInputDialog(null, "Enter username:",
                                            "Log In", JOptionPane.QUESTION_MESSAGE);
                                    String password = JOptionPane.showInputDialog(null, "Enter password:",
                                            "Log In", JOptionPane.QUESTION_MESSAGE);

                                    socketWriter.writeUTF(username);
                                    socketWriter.writeUTF(password);

                                    String authentication = socketReader.readUTF();
                                    if (authentication.equals("loggedIn")) {
                                        loggedIn = true;
                                        validUser = true;
                                    }
                                    //Checks to see if the username and password match what has already been added

                                    //Customer homepage
                                    if (loggedIn) {
                                        int selectionNum = 0;
                                        do {
                                            String[] menuArray = {"1. View marketplace", "2. Search for a product", "3. Review purchases history", "4. Logout"};
                                            String selection = (String) JOptionPane.showInputDialog(null, "What would you like to do?",
                                                    "Menu", JOptionPane.QUESTION_MESSAGE,
                                                    null, menuArray, menuArray[0]);
                                            socketWriter.writeUTF(selection);
                                            String[] selectionSplit = selection.split(". ");
                                            selectionNum = Integer.parseInt(selectionSplit[0]);

                                            switch (selectionNum) {
                                                //View marketplace
                                                case 1:
                                                    String marketplace = socketReader.readUTF();
                                                    JOptionPane.showMessageDialog(null, marketplace,
                                                            "View Market: (product, price, store)", JOptionPane.INFORMATION_MESSAGE);

                                                    boolean marketEmpty = false;
                                                    if (marketplace.equals("There is nothing currently in the marketplace.")) {
                                                        marketEmpty = true;
                                                    }

                                                    if (!marketEmpty) {
                                                        //Asks the customer if they would like to sort the market since it isn't empty
                                                        int sort = JOptionPane.showConfirmDialog(null, "Would you like to sort the market at all?",
                                                                "View Market", JOptionPane.YES_NO_OPTION);
                                                        String sortString = String.valueOf(sort);
                                                        socketWriter.writeUTF(sortString);

                                                        if (sort == 0) {
                                                            String[] sortArray = new String[]{"1. Price", "2. Quantity available"};

                                                            String sorting = (String) JOptionPane.showInputDialog(null, "Would you like to sort by?",
                                                                    "View Market", JOptionPane.QUESTION_MESSAGE,
                                                                    null, sortArray, sortArray[0]);
                                                            int sortingNum = Integer.parseInt(String.valueOf(sorting.charAt(0)));
                                                            socketWriter.writeUTF(sorting);

                                                            if (sortingNum == 1) {
                                                                //Sorts the market by price
                                                                marketplace = socketReader.readUTF();
                                                                JOptionPane.showInputDialog(null, marketplace,
                                                                        "View Market: (product, price, store)", JOptionPane.INFORMATION_MESSAGE);

                                                            } else if (sortingNum == 2) {
                                                                //Sorts the market by quantity available
                                                                marketplace = socketReader.readUTF();
                                                                JOptionPane.showInputDialog(null, marketplace,
                                                                        "View Market: (product, price, store)", JOptionPane.INFORMATION_MESSAGE);

                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "That is not a valid choice!", "Error",
                                                                        JOptionPane.ERROR_MESSAGE);
                                                            }
                                                        }

                                                        String selectedProduct = JOptionPane.showInputDialog(null, "Which product would you like to view? (case sensitive)",
                                                                "View Market", JOptionPane.QUESTION_MESSAGE);
                                                        socketWriter.writeUTF(selectedProduct);

                                                        String viewProduct = socketReader.readUTF();
                                                        int purchasing = 0;

                                                        if (!viewProduct.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, viewProduct,
                                                                    "View Market", JOptionPane.INFORMATION_MESSAGE);
                                                            purchasing = JOptionPane.showConfirmDialog(null, "Would you like to purchase this product?",
                                                                    "View Market", JOptionPane.YES_NO_OPTION);

                                                            String purchasingString = String.valueOf(purchasing);
                                                            socketWriter.writeUTF(purchasingString);

                                                            //The customer wants to purchase an item from the marketplace
                                                            if (purchasing == 0) {
                                                                int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many?",
                                                                        "View Market", JOptionPane.QUESTION_MESSAGE));

                                                                String quantityString = String.valueOf(quantity);
                                                                socketWriter.writeUTF(quantityString);

                                                                String available = socketReader.readUTF();

                                                                //Checks to see if the amount of product requested is available within the store
                                                                if (available.equals("yes")) {
                                                                    JOptionPane.showMessageDialog(null, "Success!", "Buy Product", JOptionPane.INFORMATION_MESSAGE);
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "Not enough in stock!", "Error", JOptionPane.ERROR_MESSAGE);
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Ok!",
                                                                        "View Market", JOptionPane.INFORMATION_MESSAGE);
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "No products match that name!", "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    }

                                                    break;
                                                //Search for a product
                                                case 2:
                                                    String[] searchArray = {"1. Product name", "2. Store name", "3. Product description"};
                                                    String choice = (String) JOptionPane.showInputDialog(null, "Do you want to search by: ",
                                                            "Search", JOptionPane.QUESTION_MESSAGE,
                                                            null, searchArray, searchArray[0]);
                                                    int choiceNum = Integer.parseInt(String.valueOf(choice.charAt(0)));
                                                    socketWriter.writeUTF(choice);

                                                    //This runs through the different ways the customer can search for a product, such as the name of the product,
                                                    //the name of the store that carries it, or the product description.
                                                    if (choiceNum == 1) {
                                                        String product = JOptionPane.showInputDialog(null, "Please type the name of the product you're searching for:",
                                                                "Search", JOptionPane.QUESTION_MESSAGE);
                                                        socketWriter.writeUTF(product);
                                                    } else if (choiceNum == 2) {
                                                        String store = JOptionPane.showInputDialog(null, "Please type the name of the store you're searching for:",
                                                                "Search", JOptionPane.QUESTION_MESSAGE);
                                                        socketWriter.writeUTF(store);
                                                    } else if (choiceNum == 3) {
                                                        String description = JOptionPane.showInputDialog(null, "Please type the part of a description you're searching for:",
                                                                "Search", JOptionPane.QUESTION_MESSAGE);
                                                        socketWriter.writeUTF(description);
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "That's not a valid option!", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    break;
                                                //This allows the customer to view their purchase history
                                                case 3:
                                                    String purchaseHistory = socketReader.readUTF();
                                                    JOptionPane.showMessageDialog(null, purchaseHistory,
                                                            "Purchase History", JOptionPane.INFORMATION_MESSAGE);
                                                    break;
                                                //This logs the customer out of their account
                                                case 4:
                                                    JOptionPane.showMessageDialog(null, "Good Bye!",
                                                            "Log Out", JOptionPane.INFORMATION_MESSAGE);
                                                    break;
                                                default:
                                                    JOptionPane.showMessageDialog(null, "Please enter a valid input", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                        } while (selectionNum != 4);
                                    }
                                    //The customer failed to log in
                                    else {
                                        JOptionPane.showMessageDialog(null, "Login failed!", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (!loggedIn);
                                break;
                            case 2:
                                //Seller section
                                loggedIn = false;
                                do {
                                    String username = JOptionPane.showInputDialog(null, "Enter username:",
                                            "Log In", JOptionPane.QUESTION_MESSAGE);
                                    String password = JOptionPane.showInputDialog(null, "Enter password:",
                                            "Log In", JOptionPane.QUESTION_MESSAGE);

                                    socketWriter.writeUTF(username);
                                    socketWriter.writeUTF(password);

                                    //Checks to see if the Seller was able to log in successfully
                                    String authentication = socketReader.readUTF();
                                    if (authentication.equals("loggedIn")) {
                                        loggedIn = true;
                                        validUser = true;
                                    }

                                    if (loggedIn) {
                                        String selection;
                                        int selectionNum = 0;
                                        //Seller homepage
                                        do {
                                            String[] menuArray = {"1. Create a product", "2. Edit/Delete a product", "3. Create a store",
                                                    "4. View stores", "5. View Seller Dashboard", "6. Logout"};
                                            selection = (String) JOptionPane.showInputDialog(null, "What would you like to do?",
                                                    "Menu", JOptionPane.QUESTION_MESSAGE,
                                                    null, menuArray, menuArray[0]);
                                            String[] selectionSplit = selection.split(". ");
                                            String selectionNumber = selectionSplit[0];
                                            socketWriter.writeUTF(selectionNumber);
                                            selectionNum = Integer.parseInt(String.valueOf(selection.charAt(0)));

                                            switch (selectionNum) {
                                                //Seller creates a product
                                                //When the seller creates a product the products.txt file is updated to accommodate the changes.
                                                case 1:
                                                    String product = JOptionPane.showInputDialog(null, "What's the name of the product?",
                                                            "Create Product", JOptionPane.QUESTION_MESSAGE);
                                                    socketWriter.writeUTF(product);

                                                    String[] storesArray = (socketReader.readUTF()).split(",");
                                                    String productStore = (String) JOptionPane.showInputDialog(null, "Which store will it go in?",
                                                            "Create Product", JOptionPane.QUESTION_MESSAGE, null, storesArray, storesArray[0]);

                                                    socketWriter.writeUTF(productStore);
                                                    String storeExists = socketReader.readUTF();

                                                    //If the selected store already exists the seller is prompted with questions about the product's description,
                                                    //quantity available, and price.
                                                    if (storeExists.equals("yes")) {
                                                        //Get all info for product, add product
                                                        String description = JOptionPane.showInputDialog(null, "What's the product description?",
                                                                "Create Product", JOptionPane.QUESTION_MESSAGE);
                                                        socketWriter.writeUTF(description);

                                                        int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many are available?",
                                                                "Create Product", JOptionPane.QUESTION_MESSAGE));

                                                        String quantityString = String.valueOf(quantity);
                                                        socketWriter.writeUTF(quantityString);

                                                        double price = Double.parseDouble(JOptionPane.showInputDialog(null, "How much does it cost?",
                                                                "Create Product", JOptionPane.QUESTION_MESSAGE));
                                                        socketWriter.writeUTF(String.valueOf(price));
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "That store doesn't exist!", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    break;
                                                //Edit/Delete product
                                                case 2:
                                                    String[] optionsArray = {"1. Edit", "2. Delete"};
                                                    String choice = (String) JOptionPane.showInputDialog(null, "What would you like to do?",
                                                            "Update Product", JOptionPane.QUESTION_MESSAGE,
                                                            null, optionsArray, optionsArray[0]);
                                                    socketWriter.writeUTF(choice);

                                                    int choiceNum = Integer.parseInt(String.valueOf(choice.charAt(0)));

                                                    //Edit product (deletes, then adds with changes)
                                                    if (choiceNum == 1) {
                                                        String[] productsArray = socketReader.readUTF().split(", ");
                                                        String editProduct = (String) JOptionPane.showInputDialog(null, "Which product would you like to edit?",
                                                                "Update Product", JOptionPane.QUESTION_MESSAGE,
                                                                null, productsArray, productsArray[0]);
                                                        socketWriter.writeUTF(editProduct);

                                                        String newProduct = JOptionPane.showInputDialog(null, "What's the name of the product?",
                                                                "Update Product", JOptionPane.QUESTION_MESSAGE);
                                                        socketWriter.writeUTF(newProduct);

                                                        String[] storesArrays = socketReader.readUTF().split(", ");
                                                        String newProductStore = (String) JOptionPane.showInputDialog(null, "Which store will it go in?",
                                                                "Update Product", JOptionPane.QUESTION_MESSAGE, null, storesArrays, storesArrays[0]);
                                                        socketWriter.writeUTF(newProductStore);

                                                        String newStoreExists = socketReader.readUTF();

                                                        //The store information can only be updated if the store chosen already exists
                                                        //All the information necessary to update products.txt is now available
                                                        if (newStoreExists.equals("yes")) {
                                                            //Get all info for product, add product
                                                            String newDescription = JOptionPane.showInputDialog(null, "What's the product description?",
                                                                    "Update Product", JOptionPane.QUESTION_MESSAGE);
                                                            socketWriter.writeUTF(newDescription);

                                                            int newQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many are available?",
                                                                    "Update Product", JOptionPane.QUESTION_MESSAGE));
                                                            socketWriter.writeUTF(String.valueOf(newQuantity));

                                                            double newPrice = Double.parseDouble(JOptionPane.showInputDialog(null, "How much does it cost?",
                                                                    "Update Product", JOptionPane.QUESTION_MESSAGE));
                                                            socketWriter.writeUTF(String.valueOf(newPrice));

                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "That store doesn't exist!", "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                        //Deletes product, if it is valid (already present in products.txt)
                                                    } else if (choiceNum == 2) {
                                                        String[] productsArray = socketReader.readUTF().split(", ");
                                                        String unwantedProduct = (String) JOptionPane.showInputDialog(null, "Which product would you like to delete?",
                                                                "Update Product", JOptionPane.QUESTION_MESSAGE,
                                                                null, productsArray, productsArray[0]);
                                                        socketWriter.writeUTF(unwantedProduct);
                                                    } else {
                                                        //The product selected could not be deleted because it wasn't a valid choice
                                                        JOptionPane.showMessageDialog(null, "Pick a valid number please", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                    break;
                                                //Create a new store
                                                case 3:
                                                    String storeName = JOptionPane.showInputDialog(null, "What's the name of the store?",
                                                            "Create Store", JOptionPane.QUESTION_MESSAGE);
                                                    socketWriter.writeUTF(storeName);
                                                    break;
                                                //View the existing stores
                                                case 4:
                                                    String allStores = socketReader.readUTF();
                                                    JOptionPane.showMessageDialog(null, allStores,
                                                            "View Stores", JOptionPane.INFORMATION_MESSAGE);
                                                    break;
                                                //View Seller Dashboard
                                                case 5:
                                                    String sellerStores = socketReader.readUTF();
                                                    String[] stores = sellerStores.split(", ");
                                                    SellersDashboard sellersDashboard = new SellersDashboard(stores);
                                                    String dashboard;
                                                    dashboard = sellersDashboard.viewDashboard();
                                                    JOptionPane.showMessageDialog(null, dashboard,
                                                            "Dashboard", JOptionPane.INFORMATION_MESSAGE);
                                                    break;
                                                //Logs the seller out of their account
                                                case 6:
                                                    JOptionPane.showMessageDialog(null, "Goodbye!",
                                                            "Log Out", JOptionPane.INFORMATION_MESSAGE);
                                                    break;
                                                default:
                                                    JOptionPane.showMessageDialog(null, "Please enter a valid input into the menu", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                            }
                                        } while (selectionNum != 6);

                                    } //A user with the account type seller was unable to login
                                    else {
                                        JOptionPane.showMessageDialog(null, "Login failed!", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (!loggedIn);
                                break;
                            case 3:
                                //This creates a new account
                                boolean userTaken;
                                String username;
                                //This will keep prompting the user to enter a username until they choose a unique one
                                //The prompt is repeated if the username was already taken (already occurs in users.txt)
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

                                //Creates the password for the new user
                                String password = JOptionPane.showInputDialog(null, "Please enter a password:",
                                        "Create Account", JOptionPane.QUESTION_MESSAGE);
                                String[] userTypeArray = {"1. Customer", "2. Seller"};
                                String userTypeNew = (String) JOptionPane.showInputDialog(null, "\"Are you a...",
                                        "Create Account", JOptionPane.QUESTION_MESSAGE, null, userTypeArray, userTypeArray[0]);
                                int userTypeNum = Integer.parseInt(String.valueOf(userTypeNew.charAt(0)));
                                //This creates the new, unique, customer with the username and password from above
                                if (userTypeNum == 1) {
                                    try {
                                        addUser("Customer", username, password);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    //This creates the new, unique, customer with the username and password from above
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
                                break;
                        }
                        //This is all repeated so long as teh user is not logged in
                    } while (!validUser);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (socketWriter != null) {
                            socketWriter.close();
                        }
                        if (socketReader != null) {
                            socketReader.close();
                        }
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //end of the SwingUtilities.invokeLater method
    }
    //end of the main method

    //This method adds a new user to the users.txt file with the username and password the user enters.
    //The account is then added in the format userType; username; password.
    //After creating the new user the type of account is checked and a new seller or customer is created with
    //the constructors from the respective classes.
    public static void addUser(String userType, String username, String password) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("users.txt", true), true);
        pw.write(userType + ";" + username + ";" + password);
        pw.println();
        pw.close();

        if (userType.equals("Seller")) {
            Sellers newSeller = new Sellers(username, password);
        } else {
            Customers newCustomer = new Customers(username, password);
        }
    }
}
//end of the class
