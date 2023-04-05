import java.util.*;
import java.io.*;
import java.net.*;

public class Sellers {

    public String username;
    public String password;

    public Sellers(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addProduct(String product, String store, String description, int quantity, double price) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("products.txt", true), true);
        pw.write(username + ";" + store + ";" + product + ";" + description + ";" + quantity + ";" + price);
        pw.println();
        pw.close();
    }

    public void addStore(String store) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("stores.txt", true), true);
        pw.write(username + ";" + store);
        pw.println();
        pw.close();
    }

    public String getStores() throws IOException {
        String sellerStores = "";
        BufferedReader br = new BufferedReader(new FileReader("stores.txt"));
        String line = br.readLine();
        int counter = 0;

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[0].equals(username) && counter > 0) {
                sellerStores = sellerStores + ", " + splitLine[1];
            } else if (splitLine[0].equals(username)) {
                sellerStores = sellerStores + splitLine[1];
                counter++;
            }
            line = br.readLine();
        }
        return sellerStores;
    }

    public String getUsername() {
        return username;
    }
}