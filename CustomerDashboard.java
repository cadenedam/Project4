import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * CustomerDashboard
 *
 * CustomerDashboard is the interface in which a customer can view much of the data involving:
 * Stores - List of stores and how many products are for sale
 * Products Bought - List of products and the store the customer bought the item at.
 */

public class CustomerDashboard {

    public String username;
    public String password;

    //Constructor for CustomerDashboard
    public CustomerDashboard(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //Note that this constructor is never used

    public void viewDashboard(String[] storesList, String[] productsList, String[] productsBought) {
        //storesList - each store index corresponds to the productList index
        //productsList - list of products for the store/index
        //productsBought - index separated list of particular products bought by customer (from customers.java)

        //Stores by number of products sold (from sellers.java)
        String dashboardMessage = "Stores: \n";
        StringBuilder storeMessage = new StringBuilder();
        for (int i = 0; i < storesList.length; i++) {
            List<String> products = Arrays.asList(productsList[i].split(","));
            String[] storeProducts = products.toArray(new String[0]);
            storeMessage.append(storesList[i]).append(" - Number of Products: ").append(storeProducts.length).append("\n");
        }
        if (storeMessage.length() == 0) {
            storeMessage = new StringBuilder("No stores exist.\n");
        }
        dashboardMessage += storeMessage + "\n";

        //Stores by products bought
        dashboardMessage += "Products Bought: \n";
        boolean print = false;
        StringBuilder productNums = new StringBuilder();
        for (int i = 0; i < storesList.length; i++) {
            List<String> products = Arrays.asList(productsList[i].split(","));
            String[] storeProducts = products.toArray(new String[0]);
            for (int j = 0; j < storeProducts.length; j++) {
                for (int k = 0; k < productsBought.length; k++) {
                    if (productsBought[k].equals(storeProducts[j])) {
                        print = true;
                        productNums.append(productsBought[k]).append(" - ").append(storesList[i]).append("\n");
                    }
                }
            }
        }
        if (!print) {
            productNums = new StringBuilder("None\n");
            //This is for situations where no products have been bought
        }
        dashboardMessage += productNums;
        JOptionPane.showMessageDialog(null, dashboardMessage,
                "Dashboard", JOptionPane.INFORMATION_MESSAGE);
        //This displays the dashboardMessage
    }
    //Note that this method is never used

    //end of the class
}

