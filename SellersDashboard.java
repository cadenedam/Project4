import java.util.Arrays;
import java.util.List;

/**
 * SellersDahsboard
 *
 * SellersDahsboard is the interface in which a seller can view much of the data involving each of their stores including:
 * Customer Data - data including the list of customers who have made a purchase at the store, including the quantity of purchases
 * Product Data - data including the list of products for each store, as well as the quantity of items for each store
 */

public class SellersDashboard {
    public String [] stores;
    public String [] storeCustomers;
    public String [] itemsSold;
    public String [] products;
    public String [] sales;

    public SellersDashboard(String[] stores, String[] storeCustomers, String[] itemsSold, String[] products, String[] sales) {
        this.stores = stores;
        this.storeCustomers = storeCustomers;
        this.itemsSold = itemsSold;
        this.products = products;
        this.sales = sales;
    }

    //stores - each store index corresponds to the storeCustomers index
    //storeCustomers - list of customers for the store/index
    //itemsSold - index still corresponds to each store - list of number of items bought by customer
    //products - index still corresponds to each store - list of products by that store
    //sales - index still corresponds to each store - list of number of sales for each product

    public void viewDashboard() {
        System.out.println("Dashboard:" + "\n");
        for (int i = 0; i < stores.length; i++) {
            //Each Store
            System.out.println("Store:" + stores[i] + "\n");
            
            //Customer Data
            System.out.println("Customer Data");
            List<String> customerList = Arrays.asList(storeCustomers[i].split(","));
            String[] customers = customerList.toArray(new String[0]);
            List<String> items = Arrays.asList(itemsSold[i].split(","));
            String[] itemsCount = items.toArray(new String[0]);
            List<String> prod = Arrays.asList(products[i].split(","));
            String[] storeProducts = prod.toArray(new String[0]);
            List<String> sale = Arrays.asList(sales[i].split(","));
            String[] soldCount = sale.toArray(new String[0]);
            boolean printJ = false;
            for (int j = 0; j < customers.length; j++) {
                System.out.println("Customer:" + customers[j] + "Items Purchased: " + itemsCount[j]);
                printJ = true;
            }
            if (!printJ) {
                System.out.println("~No Customer Data~");
            }
            
            //Product Data
            System.out.println("\nProduct Data");
            boolean printK = false;
            for (int k = 0; k < products.length; k++) {
                System.out.println("Product:" + storeProducts[k] + "Number of Sales: " + soldCount[k]);
                printK = true;
            }
            if (!printK) {
                System.out.println("~No Product Data~");
            }
            System.out.println("\n");
        }
    }
}
