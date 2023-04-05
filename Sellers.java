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

        while (line != null) {
            String[] splitLine = line.split(";");
            if (splitLine[0].equals(username)) {
                sellerStores = sellerStores + splitLine[1] + ", ";
            }
            line = br.readLine();
        }
        return sellerStores;
    }

    public String getUsername() {
        return username;
    }
}