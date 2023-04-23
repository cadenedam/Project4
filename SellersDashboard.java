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
 * Customer Data - data including the list of customers who have made a purchase at the store, including the quantity of purchases
 * Product Data - data including the list of products for each store, as well as the quantity of items for each store
 */

public class SellersDashboard {
    public String [] stores;

    public SellersDashboard(String[] stores) {
        this.stores = stores;
    }

    //stores - each store index corresponds to a new store

    public void viewDashboard() throws IOException {


        String dashboardString = "";
        for (int i = 0; i < stores.length; i++) {
            String storeList = "";
            String customerData = "";
            String productData = "";
            storeList += "Store: " + stores[i] + "\n";

            BufferedReader br = new BufferedReader(new FileReader("purchased.txt"));
            String line;
            String[] splitLine;
            List<String> customerList = new ArrayList<>();
            List<String> itemsSold = new ArrayList<>();
            List<String> products  = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                splitLine = line.split(";");
                if (splitLine[4].equals(stores[i])) {
                    customerList.add(splitLine[0]);
                    itemsSold.add(splitLine[3]);
                    products.add(splitLine[1]);
                }
            }

            String[] customers = customerList.toArray(new String[0]);
            customerData = "Customer Data\n";
            String[] itemsCount = itemsSold.toArray(new String[0]);
            String[] storeProducts = products.toArray(new String[0]);
            boolean printJ = false;
            for (int j = 0; j < customers.length; j++) {
                customerData += "Customer:" + customers[j] + "Items Purchased: " + itemsCount[j] + "\n";
                printJ = true;
            }
            if (!printJ) {
                customerData += "~No Customer Data~";
            }
            productData = "\nProduct Data\n";
            boolean printK = false;
            for (int k = 0; k < storeProducts.length; k++) {
                int soldCount = 0;
                BufferedReader sr = new BufferedReader(new FileReader("purchased.txt"));
                String countLine;
                while ((countLine = sr.readLine()) != null) {
                    String[] splitLineSC = countLine.split(";");
                    if (storeProducts[k].equals(splitLineSC[1]) && (stores[i].equals(splitLineSC[4]))) {
                        soldCount += parseInt(splitLineSC[3]);
                    }
                }
                productData = "Product:" + storeProducts[k] + "Number of Sales: " + soldCount + "\n";
                printK = true;
            }
            if (!printK) {
                productData += "~No Product Data~\n";
            }
            dashboardString += storeList + customerData + productData + "\n";

        }
        if (dashboardString.isEmpty()) {
            dashboardString = "There is no store data.";
        }
        JOptionPane.showMessageDialog(null, dashboardString,
                "Dashboard" , JOptionPane.INFORMATION_MESSAGE);
    }
}
