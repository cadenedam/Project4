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
public class Customers extends Selection {
    public String username;
    public String password;

    // constructor
    public Customers(String username, String password) {
        this.username = username;
        this.password = password;
    }


    //adds to purchase history when item is purchased
    public void updatePurchaseHistory(String product, String store, String description, int quantity, double price) throws IOException {
        PrintWriter w = new PrintWriter(new FileWriter("purchased.txt", true), true);
        w.write(username + ";" + store + ";" + product + ";" + description + ";" + quantity + ";" + price);
        w.println();
        w.close();
    }

    // return purchase history so customer can view it
    public String getPurchaseHistory() throws IOException {
        String purchaseHistory = null;
        BufferedReader r = new BufferedReader(new FileReader("purchased.txt"));
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
    public String searchProductName(ArrayList<String> list, String product) throws IOException {
        String foundProducts = null;

        for (int i = 0; i < list.size(); i++) {
            String[] splitLine = list.get(i).split(";");
            if (splitLine[2].equals(product)) {
                if (foundProducts != null) {
                    foundProducts += ", " + list.get(i);
                } else {
                    foundProducts = list.get(i);
                }

            }
        }
        return foundProducts;//2
    }

    // search method using store
    public String searchProductStore(ArrayList<String> list, String store) throws IOException {

        String foundProducts = null;

        for (int i = 0; i < list.size(); i++) {
            String[] splitLine = list.get(i).split(";");
            if (splitLine[1].equals(store)) {
                if (foundProducts != null) {
                    foundProducts += ", " + list.get(i);
                } else {
                    foundProducts = list.get(i);
                }

            }
        }
        return foundProducts; //1
    }

    // search method using product description
    public String searchProductDescription(ArrayList<String> list, String description) throws IOException {
        String foundProducts = null;

        for (int i = 0; i < list.size(); i++) {
            String[] splitLine = list.get(i).split(";");
            if (splitLine[3].equals(description)) {
                if (foundProducts != null) {
                    foundProducts += ", " + list.get(i);
                } else {
                    foundProducts = list.get(i);
                }

            }
        }
        return foundProducts;
    }

    // return an array list after sorting the products by quantity
    // will return full lines from product.txt
    public ArrayList<String> sortByQuantity(ArrayList<String> list) throws IOException {
        ArrayList<String> newMarket = null;


            String[] splitLine = list.get(0).split(";");
            int quantity = Integer.parseInt(splitLine[4]);
            newMarket.add(list.get(0));
            boolean added = false;

            for (int j = 1; j < list.size(); j++) {
                for (int i = 0; i < newMarket.size(); i++) {
                    String[] splitProductLine = newMarket.get(i).split(";");
                    int currentQuantity = Integer.parseInt(splitProductLine[4]);
                    if (quantity > currentQuantity) {
                        newMarket.add(i, list.get(j));
                        added = true;
                    }
                }
                if (!added) {
                    newMarket.add(list.get(j));
                }
                added = false;
            }
        return newMarket;
    }

    // return an array list after sorting the products by price
    // will return full lines from product.txt
    public ArrayList<String> sortByPrice(ArrayList<String> list) throws IOException {
        ArrayList<String> newMarket = null;


        String[] splitLine = list.get(0).split(";");
        int price = Integer.parseInt(splitLine[5]);
        newMarket.add(list.get(0));
        boolean added = false;

        for (int j = 1; j < list.size(); j++) {
            for (int i = 0; i < newMarket.size(); i++) {
                String[] splitProductLine = newMarket.get(i).split(";");
                int currentPrice = Integer.parseInt(splitProductLine[5]);
                if (price > currentPrice) {
                    newMarket.add(i, list.get(j));
                    added = true;
                }
            }
            if (!added) {
                newMarket.add(list.get(j));
            }
            added = false;
        }
        return newMarket;
    }


    // returns customer's username
    public String getUsername() {
        return username;
    }

}

