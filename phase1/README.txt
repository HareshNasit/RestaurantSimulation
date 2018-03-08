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

Instructions for events.txt
    Server takes an order: Server Takes a dish order from Table tableID seat SeatNum
      Server| ServerID | order | TableID | SeatNum | MenuItemID | ExtraName, AmountChanged # ExtraName1, AmountChanged |
      Server | John     | order |       A |       1 |        1.1 |   Lettuce,            12 #     Tomato,            -2 |
     *Extras are optional and can be changed only if it is allowed in that MenuItem
    Server passes an order: Server has completed taking all the orders of the table. Orders are passed to the chef
      Server| ServerID | passOrder | TableID |
      Server| John     | order |       A |
    Server cancel an order: Customer descides to cancel the order before cook can pass it on to the chef
      Server| ServerID | cancel | TableID | indexOnTableOrder |
      Server| John     | order |       A | 0|
    Server serve an order: Server takes a dish that a chef has prepared and removes it from the serving table
      Server|ServerID|serve|dishIndex
      Server|John|serve|0|
    Server seats a table Server declare that a table has been occupied
      Server|ServerID|seat|TableID
      Server|John|seat|A
    Server clears a table: Server removes all the orders from
      Server|ServerID|clear|TableID
      Server|John|clear|A
    Server reject a dish for cook
      Server|ServerID|reject|dishIndex
      Server|John|reject|0
    Server returns a dish for cook
      Server|ServerID|return|TableId|dishIndex|comment
       Server|John|return|A|too hot
    Server generates a single bill for table
      Server|ServerID|bill|TableID|single
      Server|John|clear|A
    Server generates a split bill for table seat number
      Server|ServerID|bill|TableID|split|seatnum
      Server|John|bill|A|split|1
    Cook check| cook can check if their is enough ingredients to make desired dish
      Cook|CookID|check|dishID|
      Cook|CookID|check|0|
      *This input will most likely be replaced by a GUI feature as opposed to the chef checking himself
    Cook transfer: Cook moves dish at dishIndex to cooking state without subtracting ingredients
      Cook|CookID|transfer|dishIndex|
    Cook reject: Cook rejects a dish that needs to be cooked
      Cook|CookID|reject|dishIndex|
    Cook accept: Cook accept a dish to cook from the list of dishes that need to be cooked
      Cook|CookID|accept|dishIndex|
    Cook serve: Cook finishes cooking a dish and calls servers to pick it up from the serving table
      Cook|CookID|serve|dishIndex|
    Manager scan stock: Manager chooses a worker to become a receiver and scan ingredients into inventory.
      Manager|ManagerID|scan stock|WorkerID|ingredient|amount
      Manager|Adam|scan stock|Harsh|chocolate|1000
    Manager startup: Manager starts the restaurant and reads the inventory from the previous day
      Manager|ManagerID|startup|
      Manager|Adam|startup|
    manager shutdown: Manager closes the restaurant adn writes the inventory into the file
      Manager|ManagerID|shutdown|
      Manager|Adam|shutdown|

Instructions for menu.txt

Format for a dish in the menu
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

Instructions for inventory.txt
