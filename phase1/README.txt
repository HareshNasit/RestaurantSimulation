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