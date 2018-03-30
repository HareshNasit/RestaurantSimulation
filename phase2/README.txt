Instructions to run the program:
  1. Run the RestaurantApplication from RestaurantApplicationStart. A login window will appear
  2. Refer to worker.txt to login to a selected restaurant worker
      -In order for a cook or server to login, the manager must first start the system in his screen
      which can be seen in the System tab.
  3. Login the other workers as desired
  4. Select a table from a server's window and open that table's order screen
  5. If a table is not occupied, the server will the prompted to seat that table with the number of people
  6. Once in the table's order screen, select the customer number and choose a dish to add to the tables menu
      -If dish does not have enough ingredients, then the server will not be able to add that dish to order
      -A server can add compliments by clicking the compliments menu
  7. Press send when all the dishes have been ordered
  8. The cook will see when the order has appeared on their servingtable screen
  9. Cook can accept a dish to cook. Program will inform cook if they have other dishes waiting for them
  10. Cook can change the state of the dish by pressing and selecting the screens accordingly
  11. When a dish is ready to be served, all servers will be notified
      -If the server was in the main table screen and there is a dish that needs to be served, he/she will not bale to
       open a table's order screen. He must serve that dish by accessing the serving table screen
      -If there are dishes to be served and the server is in the table's order screen already, then he can deliver the dish directly
  12. A dish can be returned once it is served. To return attach a comment to the dish and it will be sent
      -The dish will return to the serving table with a return status
      -This allows the chef to either add extra ingredients or pass it to cooking if no extra ingredient is needed
  13. Once all the dishes have been served the server can bring up the bill menu and generate the bill as a single or separated option
  14. As all of this is occurring, the logs are updated. There is a new low for a new day
  15. Manager can view this log in his log tab
  16. Manager can send someone to receive a stock by clicking a worker from the worker's table
      -This will open a button so that the user can see and click
  17. Manager can bring up a list of undelivered dishes in the undelivered dishes tab



___________

Format for menu.txt

id #Name# Price #IngredientName, BaseAmount, LowerAmount, UpperAmount, IngredientPrice # more Ingredients

BaseAmount: int
LowerAmount: int
UpperAmount: int
Price: Decimal
Name: String
IngredientPrice: Decimal
id: Decimal


id: id should correspond to an item's type and size.
For ex. Small Hamburger's id is 1.0 and Medium Hamburger's id is 1.1. Same item, different size.
This is for making the menu look nice.

____________

Format for inventory.txt in the menu:

name#currentAmount#restockAmount#lowerThreshhold

name: String
currentAmount: int
restockAmount: int
lowerThreshold: int

_____________

Format for workers.txt

type,name

type: Server|Manager|Cook
name: String

---------------

Format for tables.txt

tableID

table: String

---------------


Format for receivedShipments.txt and request.txt

ingredientName: amount

ingredientName: String
amount: int