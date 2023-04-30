import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

/**
 * SellersDashboard
 *
 * SellersDashboard is the interface in which a seller can view much of the data involving each of their stores including:
 * Customer Data - data including the list of customers who have made a purchase at the store,
 * including the quantity of purchases, and
 * Product Data - data including the list of products for each store, as well as the quantity of items for each store.
 *
 * Calling the viewDashboard method will present the seller with a list of the store(s), customer username(s),
 * the number of items the customer(s) purchased, the name of the product(s),
 * and the number of those products sold.
 */


public class SellersDashboard {
    public String [] stores;

    //Constructor for SellersDashboard
    public SellersDashboard(String[] stores) {
        this.stores = stores;
    }
    //stores - each store index corresponds to a new store

    public String viewDashboard() throws IOException {
        StringBuilder dashboardString = new StringBuilder();
        //String for output of dashboard via GUI
        for (int i = 0; i < stores.length; i++) {
            String storeList;
            StringBuilder customerData = new StringBuilder();
            StringBuilder productData = new StringBuilder();

            storeList = "Store: " + stores[i];
            //String with the name of the name of the store in the
            //ith position of Array stores.

            BufferedReader br = new BufferedReader(new FileReader("purchased.txt"));
            String line;
            String[] splitLine;
            List<String> customerList = new ArrayList<>();
            List<String> itemsSold = new ArrayList<>();
            List<String> products  = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                splitLine = line.split(";");
                if (splitLine[1].equals(stores[i])) {

                    customerList.add(splitLine[0]);
                    //This adds the username of the customer

                    itemsSold.add(splitLine[4]);
                    //This adds the quantity of items sold

                    products.add(splitLine[2]);
                    //This adds the product purchased
                }
            }

            String[] customers = customerList.toArray(new String[0]);
            customerData = new StringBuilder("Customer Data\n");
            String[] itemsCount = itemsSold.toArray(new String[0]);
            String[] storeProducts = products.toArray(new String[0]);
            boolean printJ = false;
            for (int j = 0; j < customers.length; j++) {
                customerData.append("Customer:").append(customers[j]).append("Items Purchased: ").append(itemsCount[j]).append("\n");
                //This String contains the customer username,
                // along with the number of items purchased by the customer from purchased.txt.
                printJ = true;
            }
            if (!printJ) {
                customerData = new StringBuilder("~No Customer Data~");
            }
            productData = new StringBuilder("\nProduct Data\n");
            boolean printK = false;
            for (int k = 0; k < storeProducts.length; k++) {
                int soldCount = 0;
                BufferedReader sr = new BufferedReader(new FileReader("purchased.txt"));
                String countLine;
                while ((countLine = sr.readLine()) != null) {
                    String[] splitLineSC = countLine.split(";");
                    if (storeProducts[k].equals(splitLineSC[2]) && (stores[i].equals(splitLineSC[1]))) {
                        soldCount += Integer.parseInt(splitLineSC[4]);
                    }
                }
                sr.close();
                productData.append("Product:").append(storeProducts[k]).append("Number of Sales: ").append(soldCount).append("\n");
                //String with the name of the product, along with the quantity of that product sold.
            }
            if (!printK) {
                productData = new StringBuilder("~No Product Data~\n");
            }
            dashboardString.append(storeList).append(customerData).append(productData).append("\n");

        }
        if (dashboardString.length() == 0) {
            dashboardString = new StringBuilder("There is no store data.");
        }
        return(dashboardString.toString());
        //This returns the string of the SellersDashboard to be viewed in Marketplace
    }
    //End of the class
}
