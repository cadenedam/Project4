import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingCart
 *
 * ShoppingCart is the interface in which a customer can store and view products to check out.
 * It creates a file for the certain user to act as storage to be saved. This file can be read
 * from as to add products and delete them.
 */

public class ShoppingCart {

    public String username;
    public String password;

    public ShoppingCart(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void createCart() throws IOException {
        // if you create the shopping cart when the customer account is created,
        // it will be simple to prevent calling it multiple times
        File file = new File("shoppingCart." + username + ".txt");
        file.createNewFile();
    }

    void addToCart(String store, String product, int quantity, double price) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("shoppingCart." + username + ".txt", true), true);
        pw.write(store + ";" + product + ";" + quantity + ";" + price);
        pw.println();
        pw.close();
    }

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

    public String [] getCart() throws IOException {
        List<String> cart = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("shoppingCart." + username + ".txt"));
        String line;
        while ((line = br.readLine()) != null) {
            cart.add(line);
        }
        return cart.toArray(new String[0]);
    }
}
