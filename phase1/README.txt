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


Instructions for events.txt
    Server takes an order:
        ServerID | order | TableID | SeatNum | MenuItemID | ExtraName, AmountChanged # ExtraName1, AmountChanged |
        John     | order |       A |       1 |        1.1 |   Lettuce,            12 #     Tomato,            -2 |
    Cook reads an order:
        CookID | read | dishIndex |
        Harsh | read | 0 |
    Cook finishes cooking a dish:
        CookID | cooked | dishIndex |
        Harsh | cooked | 0 |
    Server serve an order:
            ServerID | serve | dishIndex
            John     | serve | 0