Instructions to run the program:
  1. Run the RestaurantApplication from RestaurantApplicationStart.

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