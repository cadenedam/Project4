import java.util.Arrays;
import java.util.List;

public class CustomerDashboard {

    public String username;
    public String password;

    public CustomerDashboard(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void viewDashboard(String [] storesList, String [] productsList, String [] productsBought) {
        //storesList - each store index corresponds to the productList index
        //productsList - list of products for the store/index, separated by "," plz (no space)
        //productsBought - index separated list of particular products bought by customer (from customers.java)

        //stores by number of products sold (get from sellers.java)
        System.out.println("Stores:");
        for (int i = 0; i < storesList.length; i++) {
            List<String> products = Arrays.asList(productsList[i].split(","));
            String[] storeProducts = products.toArray(new String[0]);
            System.out.println(storesList[i] + " - Number of Products: " +  storeProducts.length);
        }
        //stores by products bought
        System.out.println("Products Bought:");
        boolean print = false;
        for (int i = 0; i < storesList.length; i++) {
            List<String> products = Arrays.asList(productsList[i].split(","));
            String[] storeProducts = products.toArray(new String[0]);
            for (int j = 0; j < storeProducts.length; j++) {
                for (int k = 0; k < productsBought.length; k++) {
                    if (productsBought[k].equals(storeProducts[j])) {
                        print = true;
                        System.out.println(productsBought[k] + " - " + storesList[i]);
                    }
                }
            }
        }
        if (!print) {
            System.out.println("None");
        }
    }
}
