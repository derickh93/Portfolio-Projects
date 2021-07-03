## Reminder App

1. The app has Reminder Interface(UI), User Class, List Class, Reminder 
class and a database
2. The User Interface would be able to handle a list of reminders in 
hierarchical list and ehich shows the the the type of the remider and 
the reminder name
3. I designed a User class becasue the app would be able to personalize 
the app using name of the user and user would be able to login using a 
password
4. List is the parent class of reminder class, list has a name and a 
type, could be multiple lists
4. Reminder class enables to customize the reminders as the user wish, 
all the methods are implemeneted within the class.

>setReminderName(string)
 > getReminderName():string
> setReminderDate(string)
>getReminderDate():string
>setReminderTime(string)
>getReminderTime():string
>isReminderRepeat():booelan
>setReminderRepeat(boolean)
>selectAllReminder()
>hideSelectedReminders()
>checkOffAll()
>ClearAll()
>doesReminderExsist():boolean
>deleteRemInder(string):boolean

6 Then the reminder class has ability to Update, delete and Add data to 
the database.
