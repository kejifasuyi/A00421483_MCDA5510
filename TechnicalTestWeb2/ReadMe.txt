This solution is a simple web application that allows users to have a 
set of shipments associated with their customer account.

Please make a copy of this git repo in your repo called TechnicalTestWeb2
Please make updates in this file and to the code and push when complete( or end of class) 

Item 1: Setup..
There is script file located in the "SQL Script", directory.
Create a database and using SQL Management Console exedcute the script to populate the data.
Note: You will have to update the Web.config to reference your DB name/credentials

Item 2: 
There is one compilation error in the project fix it.

Please Answer:
What was it?  How did you fix?
ERROR: Error	CS1061	'Shipment' does not contain a definition for 'ServiceType' and no extension method 'ServiceType' accepting a first argument of type 'Shipment' could be found (are you missing a using directive or an assembly reference?)	TechnicalTestWeb2	C:\Users\Dunnyfashion\Documents\Visual Studio 2017\Projects\TechnicalTestWeb2\TechnicalTestWeb2\Models\DataModel.cs	69	Active

SOLUTION:  public string ServiceType { get; set; } (It said ServiceTypes first)


Item 3:
The Shipments link in the menu bar of the app is not working, 
it should like to the index page of the ShipmentsController 

Please Answer:
What was it?  How did you fix?
It said shipppments - In layouts  <li>@Html.ActionLink("Shipments", "Index", "Shipments")</li> 
and add  public ActionResult Shipments()
        {
            return View();
        } 
to the home controller

Item 4:
Make the name label for the Customer on the shipment object say "Customer Name" in all places

Please Answer:
What was required to fix?
Add annotation [Display(Name = "Customer Name")] to the firstName field in Customer.cs


Item 5:
There is a bug in the code.
The estimated ship date must be at least 24 hours after the Date Ordered.  Fix

Please Answer:
What was wrong and what was required to fix?
I added the function add days to make the compare function compare, the shipdate and a day after and then say it cant be sooner if it is lesser than a day after. 
int result = DateTime.Compare(estShipDate, orderPlacedDate.AddDays(1));

Item 6:
When Editing customer Nitin, the province appear as Quebec.  Not Ontario - the default for create.

Explain why it was not Ontario or NS?

Item 7:
Add a button to the right of the customer dropdown to open the add a new customer.
Hint: you can use @Html.ActionLink ( does not have to be a button)

Item 8:
Convert Text dates to date picker