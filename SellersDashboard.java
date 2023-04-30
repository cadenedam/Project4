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

    public void viewDashboard() throws IOException {


        String dashboardString = "";
        for (int i = 0; i < stores.length; i++) {
            String storeList = "";
            String customerData = "";
            String productData = "";
            storeList += "Store: " + stores[i] + "\n";
            //String with the name of the name of the store in the
            // ith position of Array stores.

            BufferedReader br = new BufferedReader(new FileReader("purchased.txt"));
            String line;
            String[] splitLine;
            List<String> customerList = new ArrayList<>();
            List<String> itemsSold = new ArrayList<>();
            List<String> products  = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                splitLine = line.split(";");
                if (splitLine[1].equals(stores[i])) {
                    //Changed from [4] to [1]
                    //[4] is the quantity of the product, [1] is the store name**
                    //This condition looked wrong:
                    /*
                    According to the UpdatePurchaseHistory method in Customers the 2nd element [1]
                    corresponds to the store name. Index 5 [4] is the quantity.
                    Please correct me if we have decided to change the formatting of the file, otherwise
                    I think the wrong index was grabbed. I updated the condition to match my suggestion, but would
                    appreciate a double-check.
                     */

                    customerList.add(splitLine[0]);
                    //This adds the username of the customer

                    itemsSold.add(splitLine[4]);
                    //Changed from [3] - product description, to [4] - quantity
                    //Please double check this**

                    products.add(splitLine[2]);
                    //This adds the name of the store???
                    //[1] is the name of the store
                }
            }

            String[] customers = customerList.toArray(new String[0]);
            customerData = "Customer Data\n";
            String[] itemsCount = itemsSold.toArray(new String[0]);
            String[] storeProducts = products.toArray(new String[0]);
            boolean printJ = false;
            for (int j = 0; j < customers.length; j++) {
                customerData += "Customer:" + customers[j] + "Items Purchased: " + itemsCount[j] + "\n";
                //This String contains the customer username,
                // along with the number of items purchased by the customer from purchased.txt.
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
                    if (storeProducts[k].equals(splitLineSC[2]) && (stores[i].equals(splitLineSC[4]))) {
                        //If the name of the product equals the kth store in the array of stores, and
                        //the ith store in the stores Array equals the quantity from the purchased.txt file.

                        //**Please double-check this, I changed it to match my interpretation, but I may
                        // have miss-read the intention.

                        /*
                        The second part of the condition does not make sense, why does the ith store
                        have to equal the quantity purchased of the item? Can this condition be omitted or should
                        we just amend it. I would really appreciate a second opinion on this.
                         */

                        soldCount += parseInt(splitLineSC[4]);
                        //Changed from [3] to [4] since [3] is the description and [4] is the quantity.
                    }
                }
                productData = "Product:" + storeProducts[k] + "Number of Sales: " + soldCount + "\n";
                //String with the name of the product, along with the quantity of that product sold.
                //**Why do we need the number of sales if the number of "Items Purchased" is already displayed??

                //**Note that this was changed based on my interpretation, this description is only accurate
                //if my interpretation of storeProducts was correct.
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
        //This displays the dashboard, with the list of stores, customer username,
        //the number of items the customer purchased, the name of the product,
        //and the number of those products sold.

        /*
        As mentioned above, I've taken the liberty to rearrange some Array indexing so this may be
        different from what was initially intended. Could someone please take a look through this
        and make sure that this makes sense. Lastly, as noted before, the "Items the customer purchased"
        should match up with the "Number of product[i] sold",
         so I don't understand why we would need to repeat that information.

         */
    }

    //End of the class
}
