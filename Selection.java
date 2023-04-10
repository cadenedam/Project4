import java.io.*;
import java.util.*;

public class Selection {

    private String username;
    private String password;
    private String filename;
    private List<String> products;

    private Customers customers = new Customers(username, password);
    //these values are always null?


    File fileProducts = new File("products.txt");
    File filePurchased = new File("purchases.txt");


//    public Selection(List<String> products) {
//        this.products = products;
//    }
    //the constructor above may not be necessary

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

    //allows the seller to export products for their store

    public ArrayList<String> writeProductsFile(String filename) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedWriter productListWriter = new BufferedWriter(new FileWriter(fileProducts));
        String newList = "";
        productListWriter.write(newList);
        productListWriter.close();
        try {
            FileReader fr = new FileReader(fileProducts);
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

    //allows the customer to export a file with their purchase history

    public ArrayList<String> writePurchasedFile(String username,String fileName) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedWriter purchaseListWriter = new BufferedWriter(new FileWriter(filename));
        String newList = "";
        purchaseListWriter.write(newList);
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
                if (Objects.equals(list.get(0), "Customer")) {
                    ArrayList<String> a = readFile("products.txt");
                    if (a.get(0).equals(username)) {
                        purchaseListWriter.write(line);
                    }
                    //I need to add some additional conditions that display the rows of products
                    //that customer has bought
                    //This is a tentative solution
                }
            }
            bfr.close();
            purchaseListWriter.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

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
                    for (int i = 0; i < 4; i++) {
                        try {
                            //try to parse the element as a double
                            Double.parseDouble(elements[i]);
                            //if no exception is thrown, then the element is not a string
                            isString = false;
                            break;
                        } catch (NumberFormatException e) {
                            //if an exception is thrown, then the element is a string
                            //this helps to guarantee the file rows are formatted correctly
                            continue;
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
                        isInt = false;
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
                        isDouble = false;
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
}


