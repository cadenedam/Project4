import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Runnable;
import java.text.*;

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

        populateHashMaps();

        MarketServer server = new MarketServer(port);

        while (true) {
            Socket newSocket = null;

            try {
                newSocket = serverSocket.accept();
                DataInputStream socketReader = new DataInputStream(newSocket.getInputStream());
                DataOutputStream socketWriter = new DataOutputStream(newSocket.getOutputStream());
                
                MarketRun marketRun = new MarketRun(newSocket, socketReader, socketWriter, server);
                Thread thread = new Thread(marketRun);
                threads.add(thread);
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void populateHashMaps() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
        String line = br.readLine();

        while (line != null) {
            String[] splitLine = line.split(";");

            if (splitLine[0].equals("Seller")) {
                sellers.put(splitLine[1], new Sellers(splitLine[1], splitLine[2]));
            } else {
                customers.put(splitLine[1], new Customers(splitLine[1], splitLine[2]));
            }
            line = br.readLine();
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
}