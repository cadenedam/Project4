<b>How to compile and run:</b>
1. Clone code into preferred IDE (optional for Project 4, it should also run in vocareum)
2. Go to Marketplace.java and hit the run button if in IDE, otherwise type [java Marketplace] into vocareum's terminal
3. The program will now allow you to test all features by accepting input through the terminal

<b>Who submitted where:</b>
Vocareum:
Brightspace (report):

<b>Class descriptions:</b>
Marketplace.java: This class contains the main method for the program. It allows the users to interact with the marketplace, and is where all other classes are implemented and tested. Testing done to make sure it works has been constantly running the main method to make sure there are no errors, it outputs the correct outputs, writes and reads files correctly, etc. 

CustomerDashboard.java: This class creates a "dashboard" that shows customers the products they've bought and the stores they've bought from. Testing is done in marketplace.java, where it's methods are called. It's related to the customers class because it takes info from the customer's purchase history in order to show them their dashboard.

Customers.java: This class contains all the information for each individual customer. Testing is done by creating new instances of the class in the marketplace main method, and calling it's methods throughout marketplace.java in order to get information to the user. It's used mostly in the marketplace.java class, however it's also heavily related to customerdashboard since that's where the dashboard class gets all the info from, and shoppingcart.java, since that keeps track of what they're planning on purchasing.

Selection.java: This class allows the user to import and export files, whether it be their purchases, their stores, or anything else that's stored in files. Testing for this class is also done in marketplace.java, since that's where it's methods are called. It's related to pretty much every other class, since it's main purpose is to export or import the information that's used/created by all other classes.

Sellers.java: This class contains all the information for each seller. Testing is done by creating new sellers in marketplace.java when users create seller accounts, and then try to create stores, products, view their dashboard, etc. It's related to sellerdashboard, since sellerdashboard uses it's information to display it all to the user. 

SellersDashboard.java: This class displays statistics and information to the seller. It's tested in the main method of marketplace.java when a seller chooses the option to view their seller dashboard. It's related to the sellers class, since it uses it's information to create a dashboard for the user. 

ShoppingCart.java: This class keeps track of the products that a customer is planning on purchasing. It's tested in the main method of marketplace.java, when a customer adds a product to their shopping cart. It's related to the customers class, since it's keeping track of all the products they want, which could be called upon in the customerdashboard class as well.
