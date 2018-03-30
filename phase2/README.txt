Instructions to run the program:
  1. Run the main method from Restaurant Simulation
  2. Events that simulate button input will be read from events.txt file
  3. Output will be printed on screen.
     -The number that appears before the output is meant to show what line event is being read from events.txt
     -The serving table is what connects the servers to the chefs. The state of a dish (needing to be cooked, currently being cooked, need to be served, need to be rejected can be seen from it.
     -The current events running are a mix of real life button events. To isolate a scenario we have include an event scenarios text file
     -Events are written to simulate real life button events. For example a cook would not accept a dish if there is no dish that needs to be cooked or there is not enough ingredients to make it.
       As such certain actions will only make sense with the inventory use in event scenarios text file.
     -Certain events require a dish index. We made it this way to allow the chef and servers to choose which dish they want to work on as they see most efficient and practical. They will be able to choose based on whats on the serving table that will have its on graphical interface in phase1
  4. Adding more workers, menu, ingredients, etc. Workers, menu, ingredients, are read from their respective .txt file. We have provided the formats to generate each object below

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