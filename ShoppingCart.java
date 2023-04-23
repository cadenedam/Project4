import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingCart
 *
 * ShoppingCart is the interface in which a customer can store and view products to check out.
 * It creates a file for the certain user to act as storage to be saved. This file can be read
 * from to add and delete products.
 */

public class ShoppingCart {

    public String username;
    public String password;

    //Constructor for the ShoppingCart
    public ShoppingCart(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //Note that this constructor is never used

    public void createCart() throws IOException {
        // if you create the shopping cart when the customer account is created,
        // it will be simple to prevent calling it multiple times
        File file = new File("shoppingCart." + username + ".txt");
        BufferedWriter newCart = new BufferedWriter(new FileWriter(file));
        //file.createNewFile();
        newCart.close();
    }
    //Note that this method is never used

    //This method adds a product to the user's cart and creates a file named after them with
    //some characteristics of the product, like the store, name of product, quantity bought, and price.
    void addToCart(String store, String product, int quantity, double price) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("shoppingCart." + username + ".txt", true), true);
        pw.write(store + ";" + product + ";" + quantity + ";" + price);
        pw.println();
        pw.close();
    }
    //Note that this method is never used

    //This method will remove an item from the user's shoppingCart
    void removeFromCart(String store, String product, int quantity, double price) throws IOException {
        String remove = String.format("%s;%s;%d;%f", store, product, quantity, price);
        File tempFile = new File("myTempFile.txt");
        File inFile = new File("shoppingCart." + username + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(inFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        String line;
        while((line = br.readLine()) != null) {
            String trimmedLine = line.trim();
            if(!trimmedLine.equals(remove)) {
                bw.write(line + System.getProperty("line.separator"));
            }
        }
        bw.close();
        br.close();
        inFile.delete();
        tempFile.renameTo(inFile);
    }
    //Note that this method is never called

    //This returns a String array of the user's shopping cart.
    public String [] getCart() throws IOException {
        List<String> cart = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("shoppingCart." + username + ".txt"));
        String line;
        while ((line = br.readLine()) != null) {
            cart.add(line);
        }
        return cart.toArray(new String[0]);
    }
    //Note that this method is never used

    //end of the class
}
