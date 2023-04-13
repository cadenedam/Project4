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
        System.out.println("Dashboard:" + "\n");
        for (int i = 0; i < stores.length; i++) {
            System.out.println("Store:" + stores[i] + "\n");

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
            System.out.println("Customer Data");
            String[] itemsCount = itemsSold.toArray(new String[0]);
            String[] storeProducts = products.toArray(new String[0]);
            boolean printJ = false;
            for (int j = 0; j < customers.length; j++) {
                System.out.println("Customer:" + customers[j] + "Items Purchased: " + itemsCount[j]);
                printJ = true;
            }
            if (!printJ) {
                System.out.println("~No Customer Data~");
            }
            System.out.println("\nProduct Data");
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
                System.out.println("Product:" + storeProducts[k] + "Number of Sales: " + soldCount);
                printK = true;
            }
            if (!printK) {
                System.out.println("~No Product Data~");
            }
            System.out.println("\n");
        }
    }
}
