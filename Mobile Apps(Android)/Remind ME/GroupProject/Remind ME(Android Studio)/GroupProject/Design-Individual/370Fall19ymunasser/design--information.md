1. A list consisting of reminders the users want to be aware of. The application must allow users to add reminders to a list, delete reminders from a list, and edit the reminders in the list. 

I created a class List which will hold a list of items. I also created a class for the item objects which will be able to be deleted or edited. There is a method to add an item.

2.	The application must contain a database (DB) of reminders and corresponding data.

The database portion wasn't included since it wasn't required for the diagram.

3.	Users must be able to add reminders to a list by picking them from a hierarchical list, where the first level is the reminder type (e.g., Appointment), and the second level is the name of the actual reminder (e.g., Dentist Appointment). 

Class created for item type and another class after for the specific item. 

4.	Users must also be able to specify a reminder by typing its name. In this case, the application must look in its DB for reminders with similar names and ask the user whether that is the item they intended to add. If a match (or nearby match) cannot be found, the application must ask the user to select a reminder type for the reminder, or add a new one, and then save the new reminder, together with its type, in the DB.

Method was included to allow the user to pick out a specific item. 

5.	The reminders must be saved automatically and immediately after they are modified. 

This feature is not needed for this diagram.

6.	Users must be able to check off reminders in a list (without deleting them).

Every item has a method that checks off.

7.	Users must also be able to clear all the check-off marks in the reminder list at once.

Lists have a method to check off all items at once.

8.	Check-off marks for the reminder list are persistent and must also be saved immediately.

This isn’t necessary for the diagram.

9.	The application must present the reminders grouped by type.

Since this is specific to the UI, so this portion will not be necessary for the diagram.

10.	The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”, “Kid’s Reminders”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete reminder lists. 

When creating a user, the user will be able to create as many lists as they want with createList().

11.	The application should have the option to set up reminders with day and time alert. If this option is selected allow option to repeat the behavior. 

Did not do. 

12.	Extra Credit: Option to set up reminder based on location. 

Did not do. 

13.	The User Interface (UI) must be intuitive and responsive.

This portion is not needed for this diagram.
