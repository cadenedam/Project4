import java.util.*;
import java.io.*;
import java.net.*;

public class Sellers {

    public String username;
    public String password;
    public ArrayList<String> stores = new ArrayList<String>();

    public String addStore(String store) {
        stores.add(store);
    }
}