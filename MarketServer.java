import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Runnable;
import java.text.*;

/**
 * MarketServer
 *
 * The Thread ArrayList threads is created, and it can be used to start,
 * stop or stop all threads. Thread thread uses the MarketRun object marketRun
 * for its parameters. Arraylist threads consists of the thread made with marketRun.
 *
 * The populateHashMaps method will grab the username and password
 * of a user from the users.txt file.
 *
 * A new MarketRun object marketRun is created with Socket newSocket, DataInputStream socketReader,
 * DataOutputStream socketWriter, and MarketServer server.
 */

public class MarketServer {

    private static List<Thread> threads = new ArrayList<>();
    private MarketServer server;
    public static HashMap <String, Sellers> sellers = new HashMap<String, Sellers>();
    public static HashMap <String, Customers> customers = new HashMap<String, Customers>();

    public MarketServer(int port) {
        this.server = this;
    }

    public static void main(String[] args) throws IOException {
        int port = 4242;
        ServerSocket serverSocket = new ServerSocket(port);
        //This creates the serverSocket

        populateHashMaps();
        //This provides information about the sellers and customers, like their username and password
        MarketServer server = new MarketServer(port);

        while (true) {
            Socket newSocket = null;

            try {
                newSocket = serverSocket.accept();
                DataInputStream socketReader = new DataInputStream(newSocket.getInputStream());
                DataOutputStream socketWriter = new DataOutputStream(newSocket.getOutputStream());
                
                MarketRun marketRun = new MarketRun(newSocket, socketReader, socketWriter, server);
                //This creates a new MarketRun object with Socket newSocket, DataInputStream socketReader,
                // DataOutputStream socketWriter, and MarketServer server

                Thread thread = new Thread(marketRun);
                //This creates a new Thread object thread with parameter marketRun

                threads.add(thread);
                //This adds marketRun to the threads ArrayList
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void populateHashMaps() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
        String line = br.readLine();
        //Reads through the users.txt file to grab information about the user(s)

        while (line != null) {
            String[] splitLine = line.split(";");

            if (splitLine[0].equals("Seller")) {
                sellers.put(splitLine[1], new Sellers(splitLine[1], splitLine[2]));
                //This grabs the Seller's username and password
            } else {
                customers.put(splitLine[1], new Customers(splitLine[1], splitLine[2]));
                //This grabs the Customer's username and password
                //The only types of users possible are Seller or Customer, so additional
                //conditional statements are unnecessary
            }
            line = br.readLine();
            br.close();
        }
    }

    public void startThread(Thread thread) {
        threads.add(thread);
        thread.start();
    }

    public void stopThread(Thread thread) {
        threads.remove(thread);
        thread.interrupt();
    }

    public void stopAllThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    //End of the class
}