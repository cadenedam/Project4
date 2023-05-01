import java.io.*;
import java.util.*;

public class Selection {

    private String username;
    private String password;
    private String filename;
    private List<String> products;


    File fileProducts = new File("products.txt");
    File filePurchased = new File("purchases.txt");

    public ArrayList<String> readFile(String filename) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    //I deleted the methods that wrote products and purchased files since these tasks
    //were accomplished within the Sellers, and Customers classes respectively.
    //updatePurchaseHistory (Customers) writes to the purchased.txt file and updates it in accordance with
    //the products the customer buys.
    //The purchased file comes with the Customer username, the store selling the product,
    //the product name, a description of the product, the quantity available of the product,
    //and the price of the product.

    //addProduct (Sellers) writes to the products.txt file with new information about the
    //Seller username, the store selling the product, the product name, a description of the product,
    //the quantity available of the product, and the price of the product.

    public boolean csvChecker(String fileName) {
        //the number of rows in the file
        int rowCount = 0;

        //the number of valid rows in the file
        int validCount = 0;

        //the number of invalid rows in the file
        int invalidCount = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null) {
                rowCount++;
                String[] elements = line.split(",");

                //check if the array has exactly 6 elements
                if (elements.length == 6) {
                    //check if the first four elements are strings
                    boolean isString = true;
                    for (int i = 0; i < 3; i++) {
                        try {

                            //try to parse the element as a double
                            Double.parseDouble(elements[i]);
                            //if no exception is thrown, then the element is not a string
                            isString = false;
                            break;
                        } catch (NumberFormatException e) {
                            //if an exception is thrown, then the element is a string
                            //this helps to guarantee the file rows are formatted correctly
                        }
                    }
                    //check if the fifth element is an integer
                    boolean isInt = false;
                    try {
                        //try to parse the element as an integer
                        Integer.parseInt(elements[4]);
                        //if no exception is thrown, then the element is an integer
                        isInt = true;
                        //this helps to guarantee the file rows are formatted correctly
                    } catch (NumberFormatException e) {
                        //if an exception is thrown, then the element is not an integer
                    }
                    //check if the sixth element is a double
                    boolean isDouble = false;
                    try {
                        //try to parse the element as a double
                        Double.parseDouble(elements[5]);
                        //if no exception is thrown, then the element is a double
                        //this helps to guarantee the file rows are formatted correctly
                        isDouble = true;
                    } catch (NumberFormatException e) {
                        //if an exception is thrown, then the element is not a double
                    }

                    //if all the checks are passed, then the row is valid
                    if (isString && isInt && isDouble) {
                        validCount++;
                    } else {
                        invalidCount++;
                    }

                } else {
                    //if the array does not have exactly 6 elements, then the row is invalid
                    invalidCount++;
                }

                //read the next line of the file and store it in a variable
                line = br.readLine();
            }

            //close the buffered reader
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowCount == validCount;
    }

    //End of the class
}


