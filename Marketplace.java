import javax.swing.*;
import java.io.*;
import java.net.*;
import java.lang.Runnable;

public class Marketplace {
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4242);
        DataInputStream socketReader = new DataInputStream(socket.getInputStream());
        DataOutputStream socketWriter = new DataOutputStream(socket.getOutputStream());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    boolean validUser = false;
                    do {
                        String [] marketMenuArray = {"Customer", "Seller", "Create Account"};
                        String user = (String) JOptionPane.showInputDialog(null, "Are you a customer or a seller?",
                                "Welcome to the Marketplace!", JOptionPane.QUESTION_MESSAGE,
                                null, marketMenuArray, marketMenuArray[0]);
                        socketWriter.writeUTF(user);
                        socketWriter.flush();
            
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
            
                        switch (userNum) {
                            case 1:
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
            
                                //Customer homepage
                                if (loggedIn) {
                                    int selectionNum = 0;
                                    do {
                                        String [] menuArray = {"1. View marketplace", "2. Search for a product", "3. Review purchase history", "4. Logout"};
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
                                                            marketplace = socketReader.readUTF();
                                                            JOptionPane.showInputDialog(null, marketplace,
                                                            "View Market: (product, price, store)", JOptionPane.INFORMATION_MESSAGE);
        
                                                        } else if (sortingNum == 2) {
                                                            marketplace = socketReader.readUTF();
                                                            JOptionPane.showInputDialog(null, marketplace,
                                                            "View Market: (product, price, store)", JOptionPane.INFORMATION_MESSAGE);
        
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "That is not a valid choice!", "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } else {
                                                        //Write something, continue loop
                                                    }
        
                                                    String selectedProduct = JOptionPane.showInputDialog(null, "Which product would you like to view? (case sensitive)",
                                                    "View Market", JOptionPane.QUESTION_MESSAGE);
                                                    socketWriter.writeUTF(selectedProduct);
        
                                                    String viewProduct = socketReader.readUTF();
                                                    int purchasing = 0;
        
                                                    if (!viewProduct.isEmpty()) {
                                                        JOptionPane.showMessageDialog(null, viewProduct,
                                                        "View Market" , JOptionPane.INFORMATION_MESSAGE);
                                                        purchasing = JOptionPane.showConfirmDialog(null, "Would you like to purchase this product?",
                                                        "View Market", JOptionPane.YES_NO_OPTION);
        
                                                        String purchasingString = String.valueOf(purchasing);
                                                        socketWriter.writeUTF(purchasingString);
        
                                                        if (purchasing == 0) {
                                                            int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many?",
                                                            "View Market", JOptionPane.QUESTION_MESSAGE));

                                                            String quantityString = String.valueOf(quantity);
                                                            socketWriter.writeUTF(quantityString);
        
                                                            String available = socketReader.readUTF();
        
                                                            if (available.equals("yes")) {
                                                                JOptionPane.showMessageDialog(null, "Success!", "Buy Product", JOptionPane.INFORMATION_MESSAGE);
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, "Not enough in stock!", "Error", JOptionPane.ERROR_MESSAGE);
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "Ok!",
                                                            "View Market" , JOptionPane.INFORMATION_MESSAGE);
                                                        }
                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "No products match that name!", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                                
                                                break;
                                            //Search for product
                                            case 2:
                                                String [] searchArray = {"1. Product name", "2. Store name", "3. Product description"};
                                                String choice = (String) JOptionPane.showInputDialog(null, "Do you want to search by: ",
                                                        "Search", JOptionPane.QUESTION_MESSAGE,
                                                        null, searchArray, searchArray[0]);
                                                int choiceNum = Integer.parseInt(String.valueOf(choice.charAt(0)));
                                                socketWriter.writeUTF(choice);
                                                
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
                                            case 3:
                                            String purchaseHistory = socketReader.readUTF();
                                                JOptionPane.showMessageDialog(null, purchaseHistory,
                                                        "Purchase History" , JOptionPane.INFORMATION_MESSAGE);
                                                break;
                                            //Logout
                                            case 4:
                                                JOptionPane.showMessageDialog(null, "Good Bye!",
                                                        "Log Out" , JOptionPane.INFORMATION_MESSAGE);
                                            break;
                                            default:
                                                JOptionPane.showMessageDialog(null, "Please enter a valid input", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                    } while (selectionNum != 4);
                                } else {
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
                                                        String [] menuArray = {"1. Create a product", "2. Edit/Delete a product", "3. Create a store", "4. View stores", "5. View Seller Dashboard", "6. Logout"};
                                                        selection = (String) JOptionPane.showInputDialog(null, "What would you like to do?",
                                                                "Menu", JOptionPane.QUESTION_MESSAGE,
                                                                null, menuArray, menuArray[0]);
                                                        String[] selectionSplit = selection.split(". ");
                                                        String selectionNumber = selectionSplit[0];
                                                        socketWriter.writeUTF(selectionNumber);
                                                        selectionNum = Integer.parseInt(String.valueOf(selection.charAt(0)));
                            
                                                        switch(selectionNum) {
                                                            //Create product
                                                            case 1:
                                                                String product = JOptionPane.showInputDialog(null, "What's the name of the product?",
                                                                        "Create Product", JOptionPane.QUESTION_MESSAGE);
                                                                socketWriter.writeUTF(product);
                                                                // mot sure why this is here
                                                                // System.out.println(" (case sensitive)");
            
                                                                String[] storesArray = (socketReader.readUTF()).split(",");
                                                                String productStore = (String) JOptionPane.showInputDialog(null, "Which store will it go in?",
                                                                        "Create Product", JOptionPane.QUESTION_MESSAGE, null, storesArray, storesArray[0]);
            
                                                                socketWriter.writeUTF(productStore);
                                                                String storeExists = socketReader.readUTF();
                            
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
                                                                String [] optionsArray = {"1. Edit","2. Delete"};
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
            
                                                                    // mot sure why this is here
                                                                    // System.out.println(" (case sensitive)");
                                                                    String[] storesArrays = socketReader.readUTF().split(", ");
                                                                    String newProductStore = (String) JOptionPane.showInputDialog(null, "Which store will it go in?",
                                                                            "Update Product", JOptionPane.QUESTION_MESSAGE, null, storesArrays, storesArrays[0]);
                                                                    socketWriter.writeUTF(newProductStore);
                                                                    
                                                                    String newStoreExists = socketReader.readUTF();
                            
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
                                                                    //Deletes product
                                                                } else if (choiceNum == 2) {
                                                                    String[] productsArray = socketReader.readUTF().split(", ");
                                                                    String unwantedProduct = (String) JOptionPane.showInputDialog(null, "Which product would you like to delete?",
                                                                            "Update Product", JOptionPane.QUESTION_MESSAGE,
                                                                            null, productsArray, productsArray[0]);
                                                                    socketWriter.writeUTF(unwantedProduct);
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, "Pick a valid number please", "Error",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                                }
                                                                break;
                                                            //Create store
                                                            case 3:
                                                                String storeName = JOptionPane.showInputDialog(null, "What's the name of the store?",
                                                                        "Create Store", JOptionPane.QUESTION_MESSAGE);
                                                                socketWriter.writeUTF(storeName);
                                                                break;
                                                            //View Stores
                                                            case 4:
                                                                String allStores = socketReader.readUTF();
                                                                JOptionPane.showMessageDialog(null, allStores,
                                                                        "View Stores", JOptionPane.INFORMATION_MESSAGE);
                                                                break;
                                                            //View Seller Dashboard
                                                            case 5:
                                                                String sellerStores = socketReader.readUTF();
                                                                String [] stores = sellerStores.split(", ");
                                                                SellersDashboard sellersDashboard = new SellersDashboard(stores);
                                                                String dashboard;
                                                                dashboard = sellersDashboard.viewDashboard();
                                                                JOptionPane.showMessageDialog(null, dashboard,
                                                                        "Dashboard", JOptionPane.INFORMATION_MESSAGE);
                                                                break;
                                                            //Logout
                                                            case 6:
                                                                JOptionPane.showMessageDialog(null, "Goodbye!",
                                                                        "Log Out", JOptionPane.INFORMATION_MESSAGE);
                                                                break;
                                                            default:
                                                                JOptionPane.showMessageDialog(null, "Please enter a valid input into the menu", "Error",
                                                                        JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } while (selectionNum != 6);
                            
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Login failed!", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                            } while (!loggedIn);
                            break;
                            case 3:
                            //User creation system NEEDS TO BE TRANSFERRED TO SERVER SIDE
                            //Add new user authentication?
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
                            String userTypeNew = (String) JOptionPane.showInputDialog(null, "\"Are you a...",
                                    "Create Account", JOptionPane.QUESTION_MESSAGE, null, userTypeArray, userTypeArray[0]);
                            int userTypeNum = Integer.parseInt(String.valueOf(userTypeNew.charAt(0)));
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
                            break;
                        }
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
    }

    public static void addUser(String userType, String username, String password) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("users.txt", true), true);
        pw.write(userType + ";" + username + ";" + password);
        pw.println();
        pw.close();

        //"put" adds them to the hashmaps, with the specified username,
        //and the specified user profile
        if (userType.equals("Seller")) {
            Sellers newSeller = new Sellers(username, password);
        } else {
            Customers newCustomer = new Customers(username, password);
        }
    } 
}
