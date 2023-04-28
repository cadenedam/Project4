import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Sellers
 *
 * Sellers allows the seller to add products to a store (with addProduct),
 * delete products from a store (deleteProduct), get a String with all
 * the products in products.txt (getProducts), add a store to the stores.txt file
 * (addStore), and get a String with all the stores in stores.txt (getStores)
 *
 * addProduct - adds a product to the products.txt file with its basic characteristics,
 * such as the seller's name, the store it is sold at, the name of the product,
 * a description of the product, the quantity available of the product,
 * and the price of the product
 *
 * deleteProduct - deletes a product from a store and updates the products.txt file
 * to match the change. Any product that does not match the product parameter will be
 * added to the allProducts ArrayList. The products.txt file updates to match allProducts.
 *
 * getProducts - returns a string with all the products in products.txt
 *
 * addStore - adds a store to the stores.txt file, along with the username of the seller
 * that created the store.
 *
 * getStores - returns a string with all the stores in stores.txt
 */

public class Sellers extends Selection{

    public String username;
    public String password;

    //Constructor for the Sellers
    public Sellers(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //This method adds a new product to a store based on the seller's username,
    //the name of the store, a description of the product, the product quantity,
    //and the price of the product.
    public void addProduct(String product, String store, String description,
                           int quantity, double price) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("products.txt", true), true);
        pw.write(username + ";" + store + ";" + product + ";" + description + ";" + quantity + ";" + price);
        pw.println();
        pw.close();
    }

    //This method deletes products the seller wants to get rid of, and updates the
    //products.txt file to only include the remaining, viable, products.
    public void deleteProduct(String product) throws IOException {
        ArrayList<String> allProducts = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader("products.txt"));
        String line = br.readLine();
        //Read in the contents of the products.txt file
        String useless = "";

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[0].equals(username) && splitLine[2].equals(product)) {
                useless += line;
            } else {
                System.out.println("That product does not exist");
                allProducts.add(line);
            }
            //The product is added to the list of allProducts if there is not a match
            //between the username and the product name. Since this method is meant to
            //delete the existing products, any product that hasn't been selected for deletion
            //would be part of the remaining allProducts list, and ultimately the products.txt file.
            line = br.readLine();
        }

        PrintWriter pw = new PrintWriter(new FileWriter("products.txt"), true);

        for (String allProduct : allProducts) {
            pw.print(allProduct + "\n");
            //This adds the products that haven't been deleted to the products.txt file.
        }
        pw.close();
    }

    //This method returns String with all the products in products.txt
    public String getProducts() throws IOException {
        StringBuilder allProducts = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("products.txt"));
        String line = br.readLine();
        int counter = 0;

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[0].equals(username) && counter > 0) {
                allProducts.append(", ").append(splitLine[2]);
                //This creates a comma separated StringBuilder with the products in products.txt
            } else {
                allProducts.append(splitLine[2]);
                counter++;
                //This adds the product (3rd element of the products.txt line)
                //to the allProducts StringBuilder.
            }
            line = br.readLine();
        }
        return allProducts.toString();
    }

    //This method lets the Seller add a store to the stores.txt file
    public void addStore(String store) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("stores.txt", true), true);
        pw.write(username + ";" + store);
        pw.println();
        pw.close();
    }

    //This method returns a String with all the stores from stores.txt
    public String getStores() throws IOException {
        ArrayList<String> sellerStores = new ArrayList<String>();
        String allStores = "";
        BufferedReader br = new BufferedReader(new FileReader("stores.txt"));
        String line = br.readLine();
        int counter = 0;

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[0].equals(username) && counter > 0) {
                sellerStores.add(", " + splitLine[1]);
                //This creates a comma separated StringBuilder with the stores in stores.txt
            } else if (splitLine[0].equals(username)) {
                sellerStores.add(splitLine[1]);
                counter++;
                //This adds the store (1st element of the stores.txt line)
                //to the sellerStores StringBuilder.
            }
            line = br.readLine();
        }

        for (int i = 0; i < sellerStores.size(); i++) {
            allStores = allStores + sellerStores.get(i);
        }
        return allStores;
    }

    //A getter that relays the username
    public String getUsername() {
        return username;
    }
    //Note that this method is never used

    //end of the class
}