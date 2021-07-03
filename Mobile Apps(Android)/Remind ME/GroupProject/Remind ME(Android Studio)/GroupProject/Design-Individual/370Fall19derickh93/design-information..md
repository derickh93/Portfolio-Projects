# Design Assignment 5(Reminder)
### 1. A list consisting of reminders the users want to be aware of. The application must allow users to add reminders to a list, delete reminders from a list, and edit the reminders in the list.
	-To meet this requirement, I created the class Reminder with the methods
	add, delete, and edit. They each accept a Reminder as the parameter. The add
	method adds the reminder to the current list. The delete methods takes in a reminder
	and removes it from the list if it exist. The edit method takes in a reminder and 
	allows you to edit that reminder if it exist.
	
### 2. The application must contain a database (DB) of ​reminders ​ and corresponding ​data.
	-To meet this requirement, I created the class Database which contain methods search, sync
	and init.The method search will search the db for a reminder and gives the user an option
	to create it if not found. The method sync will synchronize the database with the current list
	of reminders. The init method will initialize and connect to the database.
	
### 3. Users must be able to add reminders to a list by picking them from a hierarchical list,where the first level is the reminder type (e.g., Appointment), and the second level is the name of the actual reminder (e.g., Dentist Appointment). 
	-To meet this requirement, I have an array of LinkedList, similar to a hash table. The index stores
	the actual reminder type and the linked list in each has the reminders created.
	
### 4. Users must also be able to specify a reminder by typing its name. In this case, the application must look in its DB for reminders with similar names and ask the user whether that is the item they intended to add. If a match (or nearby match) cannot be  found, the application must ask the user to select a reminder type for the reminder, or add a new one, and then save the new reminder, together with its type, in the DB.
	-To meet this requirement,I created the methods search and init in the Database class.
	The search method will search for a reminder and return it. If not found it will give the user
	an option to add it by use of the add method. The sync method will synchronize the changes to the db.
	
### 5. The reminders must be saved automatically and immediately after they are modified. 
	-To meet this requirement, the method sync will be called from the Database class. 
	This will sync the database with the current changes in the reminders.
	
### 6. Users must be able to check off reminders in the list (without deleting them). 
	-To meet this requirement, each reminder contains a checkOff variable and a checkOff method.
	checkOff will be true if the reminder is checked and false if not. The method checkOff will
	flip the value upon each click/method call.
	
### 7. Users must also be able to clear all the check-off marks in the reminder list at once. 
	-To meet this requirement, the checkOffAll method will go through the array of LinkedList
	and change each Reminders checkOff value to false.
	
### 8. Check-off marks for the reminder list are persistent and must also be saved immediately. 
	-To meet this requirement,after each checkOff change the array of linked list is synchronized
	 with the db.
	
### 9. The application must present the reminders grouped by type. 
	-To meet this requirement,the array of LinkedList is used. The reason for this is that each index 
	holds a different reminder type. Then as each reminder is added it goes to the correpsonding index
	which results in the reminders being grouped together.
	
### 10. The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”,             
	“Kid’s Reminders”). Therefore, the application must provide the users with the ability to             
	create, (re)name, select, and delete reminder lists. 
	-To meet this requirement,there is a Reminder_List class. The class contains an array of Reminder_List. 
	The method create allows you to create a new list. The method rename allows you to change the name of 
	the list. The method select allows you to view a selected list of reminders. The delete method deletes
	an entire list of reminders.
	
### 11. The application should have the option to set up reminders with day and time alert. If this 
	option is selected allow option to repeat the behavior. 
	-To meet this requirement,there is a parameterized constructor to initalize reminders with day and time.
	There is also a boolean variable repeat that will repeat the behaviour if it is true. There is also method
	dateEdit which will allow the user to add a day and time alert after a reminder has already been created. 
	
### 12. Extra Credit:​ Option to set up reminder based on location. 
	-To meet this requirement,there is a parameterized constructor to initalize reminders with location.
	There is also method dateEdit which will allow the user to add a location alert after a reminder has already 
	been created. 
	
### 13. The User Interface (UI) must be intuitive and responsive. 
	-To meet this requirement, there will be serveral activitys that use the classes created.
	This will allow organization and seperation of different task that need to be accomplished in 
	an efficient manner.
