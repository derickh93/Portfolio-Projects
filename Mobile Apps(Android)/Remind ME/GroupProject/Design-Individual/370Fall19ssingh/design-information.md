1. A list consisting of reminders the users want to be aware of. The application must allow
users to add reminders to a list, delete reminders from a list, and edit the reminders in
the list.

To satisfy this requirement, I added a relationship between user and reminders that allowed users to do all the tasks necessary

2. The application must contain a database (DB) of reminders and corresponding data.

For this, I simply created a Reminders class with the corresponding data as attributes

3. Users must be able to add reminders to a list by picking them from a hierarchical list,
where the first level is the reminder type (e.g., Appointment), and the second level is the
name of the actual reminder (e.g., Dentist Appointment).

To satisfy this requirement, in the add reminder box, I have all the information needed for the user to be able to manipulate it as necessary

4. Users must also be able to specify a reminder by typing its name. In this case, the
application must look in its DB for reminders with similar names and ask the user
whether that is the item they intended to add. If a match (or nearby match) cannot be
found, the application must ask the user to select a reminder type for the reminder, or
add a new one, and then save the new reminder, together with its type, in the DB.

To satisfy this, I added a relation between user and reminder to find item with the specified name and such

5. The reminders must be saved automatically and immediately after they are modified.

I have a boolean in editReminder that is automatically set to true when the reminder is modified

6. Users must be able to check off reminders in the list (without deleting them).

Since checking off a reminder is editing it, I added a boolean under editReminder that is originally false, but the user can change to true if necessary

7. Users must also be able to clear all the check-off marks in the reminder list at once.

Similar to checked off, I added clearAll to editReminders since this is also an attribute of editing the list

8. Check-off marks for the reminder list are persistent and must also be saved immediately.

This happens all through the editReminder relation. Once the user edits a reminder, it is automatically saved, even if the user just wanted to check it off

9. The application must present the reminders grouped by type.

This is a part of the GUI that is not needed to be presented in a UML class diagram

10. The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”,
“Kid’s Reminders”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete reminder lists.

Also not part of a UML class diagram

11. The application should have the option to set up reminders with day and time alert. If this
option is selected allow option to repeat the behavior.

Under addReminder, I included date, which includes all the stuff the user wants to add

12. Extra Credit: Option to set up reminder based on location.

Not done

13. The User Interface (UI) must be intuitive and responsive.

Not considered



