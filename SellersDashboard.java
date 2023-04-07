import java.io.IOException;

public class SellersDashboard {

    public String username;
    public String password;

    public SellersDashboard(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void printSellersDashboard() throws IOException {
        Sellers seller = new Sellers(username, password);
        String stores = seller.getStores();
        System.out.println("Dashboard:" + "\n");
        String [] storesList = stores.split( "[\\s,]+" );

        for (int i = 0; i < storesList.length; i++) {
            System.out.println("Store:" + storesList[i] + "\n");
            //get customers from certain store
            String customers = storeCustomers[i]; // get from sellers
            String [] customerList = customers.split( "[\\s,]+" );

            System.out.println("Customer Data");
            for (int j = 0; j < customerList.length; j++) {
                int itemCount = 0; // update later
                System.out.println("Customer:" + customerList[i] + "Items Purchased: " + itemCount + "\n");
            }

            // String [] storeProducts = get from sellers.java
            System.out.println("Product Data");
            for (int k = 0; k < storeProducts.length; k++) { // get from sellers
                int soldCount = 0; // update later
                System.out.println("Product:" + storeProducts[i] + "Number of Sales: " + soldCount + "\n");
            }
        }
    }
}
