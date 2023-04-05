import java.util.*;
import java.io.*;
import java.net.*;

public class Sellers {

    public String username;
    public String password;
    public ArrayList<String> stores = new ArrayList<String>();

    public Sellers(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addStore(String store) {
        stores.add(store);
    }

    public String getStores() {
        String sellerStores = "";
        for (int i = 0; i < stores.size(); i++) {
            sellerStores = sellerStores + stores.get(i) + ", ";
        }
        return sellerStores;
    }

    public String getUsername() {
        return username;
    }
}