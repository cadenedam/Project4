import java.util.Arrays;
import java.util.List;

public class SellersDashboard {
    public String [] stores;
    public String [] storeCustomers;
    public String [] itemsSold;
    public String [] products;
    public String [] sales;

    public SellersDashboard(String[] stores, String[] storeCustomers, String[] itemsSold, String [] products, String [] sales) {
        this.stores = stores;
        this.storeCustomers = storeCustomers;
        this.itemsSold = itemsSold;
        this.products = products;
        this.sales = sales;
    }

    //stores - each store index corresponds to the storeCustomers index
    //storeCustomers - list of customers for the store/index, separated by "," plz (no space)
    //itemsSold - index still corresponds to each store - list of number of items bought by customer, separated by "," where the number corresponds to "index" of the customer
    //products - index still corresponds to each store - list of products by that store separated by ","
    //sales - index still corresponds to each store - list of number of sales for each product separated by "," where the number corresponds to "index" of the product

    public void viewDashboard() {
        System.out.println("Dashboard:" + "\n");
        for (int i = 0; i < stores.length; i++) {
            System.out.println("Store:" + stores[i] + "\n");

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
