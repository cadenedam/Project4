import java.util.ArrayList;
import java.io.*;
/**
 * Customers
 *
 * Customers can view the overall marketplace listing products for sale,
 * use viewMarketPlace
 *
 * search for specific products using terms that match the name, store, or description, and
 * sort - the marketplace on price or quantity available.
 * use sortByQuantity and sortByPrice
 * Customers can purchase items from the product page and
 * review a history of their previously purchased items.
 */
public class Customers {
    public String username;
    public String password;

    // constructor
    public Customers(String username, String password) {
        this.username = username;
        this.password = password;
    }


    //adds to purchase history when item is purchased
    public void updatePurchaseHistory(String product, String store, String description, int quantity, double price) throws IOException {
        PrintWriter w = new PrintWriter(new FileWriter("purchasehistory.txt", true), true);
        w.write(username + ";" + store + ";" + product + ";" + description + ";" + quantity + ";" + price);
        w.println();
        w.close();
    }

    // return purchase history so customer can view it
    public String getPurchaseHistory() throws IOException {
        String purchaseHistory = null;
        BufferedReader r = new BufferedReader(new FileReader("purchasehistory.txt"));
        String line = r.readLine();

        while (line != null) {
            String [] arr = line.split(";");
            if (arr[0].equals(username) && purchaseHistory != null) {
                purchaseHistory = purchaseHistory + ", " + arr[2];
            } else if (arr[0].equals(username)) {
                purchaseHistory = arr[2];
            }
            line = r.readLine();
        }
        r.close();
        return purchaseHistory;
    }

    // search method using product name
    public String searchProductName(String product) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("products.txt"));
        String line = r.readLine();
        String foundProducts = null;

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[2].equals(product)) {
                if (foundProducts != null) {
                    foundProducts += ", " + line;
                } else {
                    foundProducts = line;
                }

            }
            line = r.readLine();
        }
        r.close();
        return foundProducts;
    }

    // search method using store
    public String searchProductStore(String store) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("products.txt"));
        String line = r.readLine();
        String foundProducts = null;

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[1].equals(store)) {
                if (foundProducts != null) {
                    foundProducts += ", " + line;
                } else {
                    foundProducts = line;
                }

            }
            line = r.readLine();
        }
        r.close();
        return foundProducts;
    }

    // search method using product description
    public String searchProductDescription(String description) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("products.txt"));
        String line = r.readLine();
        String foundProducts = null;

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[3].equals(description)) {
                if (foundProducts != null) {
                    foundProducts += ", " + line;
                } else {
                    foundProducts = line;
                }

            }
            line = r.readLine();
        }
        r.close();
        return foundProducts;
    }

    // return an array list after sorting the products by quantity
    // will return full lines from product.txt
    public ArrayList<String> sortByQuantity() throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("products.txt"));
        String line = r.readLine();
        ArrayList<String> newMarket = null;

        while (line != null) {
            String[] splitLine = line.split(";");
            int quantity = Integer.parseInt(splitLine[4]);
            boolean added = false;
            if (newMarket != null) {
                for (int i = 0; i < newMarket.size(); i++) {
                    String[] splitProductLine = newMarket.get(i).split(";");
                    int currentQuantity = Integer.parseInt(splitProductLine[4]);
                    if (quantity > currentQuantity) {
                        newMarket.add(i, line);
                        added = true;
                    }
                }
                if (!added) {
                    newMarket.add(line);
                }
            } else {
                newMarket.add(line);
            }

            line = r.readLine();
        }
        return newMarket;
    }

    // return an array list after sorting the products by price
    // will return full lines from product.txt
    public ArrayList<String> sortByPrice() throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("products.txt"));
        String line = r.readLine();
        ArrayList<String> newMarket = null;

        while (line != null) {
            String[] splitLine = line.split(";");
            double price = Double.parseDouble(splitLine[5]);
            boolean added = false;
            if (newMarket != null) {
                for (int i = 0; i < newMarket.size(); i++) {
                    String[] splitProductLine = newMarket.get(i).split(";");
                    double currentPrice = Double.parseDouble(splitProductLine[5]);
                    if (price > currentPrice) {
                        newMarket.add(i, line);
                        added = true;
                    }
                }
                if (!added) {
                    newMarket.add(line);
                }
            } else {
                newMarket.add(line);
            }

            line = r.readLine();
        }
        return newMarket;
    }
    // returns customer's username
    public String getUsername() {
        return username;
    }

}
