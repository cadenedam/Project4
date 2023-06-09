import java.util.ArrayList;
import java.io.*;
/**
 * Customers
 *
 * Customers can view the overall marketplace listing products for sale,
 * use viewMarketPlace to search for specific products using terms that match
 * the name, store, or description, and sort the marketplace on price or quantity available.
 * Using sortByQuantity and sortByPrice
 * Customers can purchase items from the product page and
 * review a history of their previously purchased items.
 */

public class Customers extends Selection {
    public String username;
    public String password;

    //Constructor for Customers
    public Customers(String username, String password) {
        this.username = username;
        this.password = password;
    }


    //Adds to purchase history when item is purchased
    public void updatePurchaseHistory(String product, String store, String description,
                                      int quantity, double price) throws IOException {
        //Spaced this way just in case Vocareum throws a fit about excessive characters
        PrintWriter w = new PrintWriter(new FileWriter("purchased.txt", true), true);
        w.write(username + ";" + store + ";" + product + ";" + description + ";" + quantity + ";" + price);
        w.println();
        w.close();
    }

    // Return purchase history so customer can view it
    public String getPurchaseHistory() throws IOException {
        StringBuilder purchaseHistory = null;
        BufferedReader r = new BufferedReader(new FileReader("purchased.txt"));
        String line = r.readLine();

        while (line != null) {
            String [] arr = line.split(";");
            if (arr[0].equals(username) && purchaseHistory != null) {
                //makes sure that the username is already in the purchased.txt history
                purchaseHistory.append("\n").append(arr[2] + ", " + arr[5] + ", " + arr[4]);
            } else if (arr[0].equals(username)) {
                //the String purchaseHistory can't be null for this
                purchaseHistory = new StringBuilder(arr[2] + ", " + arr[5] + ", " + arr[4]);
            }
            line = r.readLine();
        }
        r.close();
        assert purchaseHistory != null;
        //Done to prevent a null pointer exception on the toString method
        return purchaseHistory.toString();
    }

    // Search method using product name
    public String searchProductName(ArrayList<String> list, String product) throws IOException {
        StringBuilder foundProducts = null;

        for (String s : list) {
            String[] splitLine = s.split(";");
            if (splitLine[2].equals(product)) {
                if (foundProducts != null) {
                    foundProducts.append(", ").append(s);
                } else {
                    foundProducts = new StringBuilder(s);
                    //in case the products didn't already exist
                }

            }
        }
        assert foundProducts != null;
        return foundProducts.toString();
        //Done to avoid possible NullPointerException error

        //Note that this method is never used
    }

    // Search method using the store
    public String searchProductStore(ArrayList<String> list, String store) throws IOException {

        StringBuilder foundProducts = null;

        for (String s : list) {
            String[] splitLine = s.split(";");
            if (splitLine[1].equals(store)) {
                if (foundProducts != null) {
                    foundProducts.append(", ").append(s);
                } else {
                    foundProducts = new StringBuilder(s);
                    //Creates the foundProducts StringBuilder since it didn't already exist
                }

            }
        }
        assert foundProducts != null;
        return foundProducts.toString();
        //To avoid possible NullPointerException error

        //Note that this method is never used
    }

    // Search method using product description
    public String searchProductDescription(ArrayList<String> list, String description) throws IOException {
        StringBuilder foundProducts = null;

        for (String s : list) {
            String[] splitLine = s.split(";");
            if (splitLine[3].equals(description)) {
                if (foundProducts != null) {
                    foundProducts.append(", ").append(s);
                } else {
                    foundProducts = new StringBuilder(s);
                    //creates the foundProducts StringBuilder since it didn't already exist
                }

            }
        }
        assert foundProducts != null;
        return foundProducts.toString();
        //To avoid possible NullPointerException error

        //Note that this method is never used
    }

    // Return an array list after sorting the products by quantity.
    // Will return full lines from product.txt
    public ArrayList<String> sortByQuantity(ArrayList<String> list) throws IOException {
        ArrayList<String> newMarket = null;
        // Initializes the newMarket String ArrayList as null

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
                        //This checks to make sure that the quantity of next products is
                        //greater than the existing quantity
                    }
                }
                if (!added) {
                    newMarket.add(list.get(j));
                    //This keeps the current quantity as the j position of the newMarket row
                }
                added = false;
                //The new quantity wasn't greater so the order wasn't changed
            }
        return newMarket;
    }

    //Return an array list after sorting the products by price.
    //This will return full lines from product.txt
    public ArrayList<String> sortByPrice(ArrayList<String> list) throws IOException {
        ArrayList<String> newMarket = null;
        //Initializes the newMarket String ArrayList as null

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
                    //This checks to make sure that the price of next products is
                    //greater than the existing price
                }
            }
            if (!added) {
                newMarket.add(list.get(j));
                //This keeps the current price as the j position of the newMarket row
            }
            added = false;
            //The new price wasn't greater so the order wasn't changed
        }
        return newMarket;
    }


    //This returns customer's username
    public String getUsername() {
        return username;
    }
    //Note that this method is called using HashMaps

    //end of the class
}

