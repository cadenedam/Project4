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

    public MarketServer(int port) {
        this.server = this;
    }

    public static void main(String[] args) throws IOException {
        int port = 4242;
        ServerSocket serverSocket = new ServerSocket(port);
        //This creates the serverSocket

        //Creates a new instance of MarketServer class, connecting to serverSocket
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