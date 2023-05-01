IMPORTANT: "User launches application" means the user runs the MarketServer class, then runs the Marketplace class.

IMPORTANT 2: "User logs in as (seller/customer)" means the user does the following:
1. User selects "(Seller/Customer)" from dropdown
3. User enters username using keyboard, hits "Ok"
4. User enters password using keyboard, hits "Ok"

<b>Test 1: Create Seller Account</b>
1. User launches application
2. User selects "Create new account"
3. User selects "Ok"
4. User enters username using keyboard
5. User selects "Ok"
6. User enters password using keyboard
7. User selects "Ok"
8. User selects "Seller" from dropdown
9. User selects "Ok"

Expected result: "Success" panel pops up, new user account is created in users.txt as type Seller.
Test Status: Passed

<b>Test 2: Create Customer Account</b>
1. User launches application
2. User selects "Create new account"
3. User selects "Ok"
4. User enters username using keyboard
5. User selects "Ok"
6. User enters password using keyboard
7. User selects "Ok"
8. User selects "Customer" from dropdown
9. User selects "Ok"

Expected result: "Success" panel pops up, new user account is created in users.txt as type Customer.
Test Status: Passed

<b>Test 3: Create Seller Store</b>
1. User launches application
2. User selects "Seller" from dropdown
3. User enters username using keyboard, hits "Ok"
4. User enters password using keyboard, hits "Ok"
5. User selects "Create a store" from dropdown
6. User enters store name using keyboard, hits "Ok"

Expected Result: New store is created in stores.txt under user's username
Test Status: Passed

<b>Test 4: Create Product</b>
1. User launches application
2. User logs in as seller
3. User selects "Create a product" from dropdown
4. User enters name of product with keyboard, hits "Ok"
5. User selects store from dropdown, hits "Ok"
6. User enters description of product with keyboard, hits "Ok"
7. User enters an integer for how many are available using keyboard, hits "Ok"
8. User enters a double for how much it costs using keyboard, hits "Ok"

Expected Result: New product is created in products.txt with all information entered above
Test Status: Passed

<b>Test 5: View Stores</b>
1. User launches application
2. User logs in as seller
3. User selects "View Stores" from dropdown
4. User hits "Ok"

Expected Result: Window pops up listing seller's stores
Test Status: Passed

<b>Test 6: View Marketplace</b>
1. User launches application
2. User logs in as customer
3. User selects "View Marketplace" from dropdown, hits "Ok"

Expected Result: Window pops up with items in marketplace
Test Status: Passed

<b>Test 7: Purchase from marketplace</b>
1. User launches application
2. User logs in as customer
3. User selects "View Marketplace" from dropdown, hits "Ok"
4. User takes note of product name, hits "Ok"
5. User hits "No"
6. User types in name of product using keyboard, hits "Ok"
7. User takes note of quantity, hits "Ok"
8. User hits "Yes"
9. User types an integer less than or equal to quantity available using keyboard, hits "Ok"

Expected Result: Window pops up saying "Success!", and item is added to purchased.txt
Test Status: Passed

<b>Test 8: View Purchase History</b>
1. User launches application
2. User logs in as customer
3. User selects "Review purchases history" from dropdown, hits "Ok"

Expected Result: Window pops up displaying product, price, and quantity bought for the customer's purchase history
Test Status: Passed
