//Derick Hansraj
//12/02/2018
//Project 1
//Cs211

//included files/headers
#include <iostream>
#include <string>
#include <sstream>
#include "Roster.h"
#include <fstream>
#include <vector>
#include <algorithm>

//function prototype -- splits string vector with specified delimiter.
std::vector<std::string> split(std::string strToSplit, char delimeter);

int main()
{

    //variables
    std::string name,course,instructor,type,line,line2,lastName, firstName,standing, id,type2, username = "", password = "", u = "", p = "";
    Date dateOfBirth, dateOfMat;
    int stuCount = 0, choice = 0, choiceR2 = 0, choiceU = 0, choiceU2 = 0, add = 0, j = 0;
    std::vector<int> vectorStu;
    std::vector<Roster> vectorRoster;
    std::vector<Student> vectorAdd;
    bool loginSuccess = false, firstCont = false, menuCont = true, userLoop = true, adminLoop = true, cont = false;
    Roster tempAddR;

    //Opening the file rosters.txt
    std::ifstream inFile("rosters.txt");
    //reading each line from roster.txt until the end
    while(getline(inFile, line))
    {
        std::vector<std::string> result;
        std::vector<std::string> temp;
        std::istringstream iss(line);

        //if the first line is being read process each item split with a delimiter
        //with help of the split function. A roster is created to populate and push into
        //the vectorRoster.
        if(line.substr(0,7) == "course1")
        {
            for(std::string line; iss >> line; )
            {
                result.push_back(line);
            }
            temp = split(result[0],'|');
            Roster r(temp[1],temp[2],stoi(temp[3]),temp[4]);
            vectorRoster.push_back(r);
        }

        //The same as course1 occurs for all other courses but a problem came about
        //with the counting so this was the only solution with the time i had left.
        if(line.substr(0,6) == "course" && line.substr(0,7) != "course1")
        {
            for(std::string line; iss >> line; )
            {
                result.push_back(line);
            }

            temp = split(result[0],'|');
            Roster r(temp[1],temp[2],stoi(temp[3]),temp[4]);
            vectorRoster.push_back(r);
            vectorStu.push_back(stuCount);
            stuCount = 0;
        }

        //if a line is a student stuCount is incremented. The line is separated using the split function and
        //with the help of some temp variables a Student is created and pushed into vectorAdd.
        if(line.substr(0,7) == "student")
        {
            for(std::string line; iss >> line; )
            {
                result.push_back(line);
            }
            std::vector<std::string> date;
            std::vector<std::string> date2;
            temp = split(result[0],'|');
            date = split(temp[7],'/');
            date2 = split(temp[8],'/');
            Date dob(stoi(date[0]),stoi(date[1]),stoi(date[2]));
            Date dom(stoi(date2[0]),stoi(date2[1]),stoi(date2[2]));
            Student s(temp[2],temp[1],dob,dom,stod(temp[6]),stod(temp[5]),temp[3]);
            vectorAdd.push_back(s);
            stuCount++;
        }
    }

    //the count of students in each roster is pushed into the vector vectorStu to help keep track
    vectorStu.push_back(stuCount);

    //the file is closed so that the rest of the information can be read from the beginning.
    inFile.close();

    //for loop that will add students to their respective rosters using the vectorStu integers obtained earlier
    for(int i = 0; i < vectorStu.size(); i++)
    {
        add += vectorStu[i];
        Roster r;
        for(; j < add; j++)
        {
            r.addStudent(vectorAdd.at(j));

        }
        vectorRoster[i] = r;
    }

    //for loop that will allow the user to stay in the program until the user chooses to exit
    while(menuCont)
    {
        //bool values that are reset so that there is a continuous flow of navigation through the options
        firstCont = false;
        userLoop = true;
        adminLoop = true;
        choice = 0;

        //loop that will keep the user in the main menu and present them with option until they choose to exit the program.
        while(!firstCont)
        {
            choice = 0;
            system("CLS");
            std::cout << "*****Welcome to Roster Management Program*****\nChoose either mode by entering 1 or 2. Enter 3 to exit:"
                << "\n1. User Mode\n2. Supervisor Mode\n3. Exit\n***CHANGES WILL ONLY BE SAVED CLOSING THE PROGRAM FROM THE MAIN MENU***\n";
            std::cin >> choice;

            system("CLS");

            //if the user chooses to enter user mode this loop will exit and the user mode loop will be in control
            if(choice == 1)
            {
                std::cout << "*****Welcome to User Mode*****\n";
                firstCont = true;
            }

            //if the user chooses to enter supervisor mode this loop will exit and the user mode loop will be in control
            else if (choice == 2)
            {
                system("CLS");

                std::cout << "*****Welcome to Supervisor Mode*****\n";
                firstCont = true;
                userLoop = false;
            }

            //if the user chooses to exit all rosters will be written to rosters.txt and the program will then close
            else if (choice == 3)
            {
                //the file is created if not there. if a file exist it is overwritten.
                std::ofstream file;
                file.open("rosters.txt");

                //loop that will print roster information
                for(int i = 0; i < vectorRoster.size(); i++)
                {
                    file << "course" << i+1 << "|" << vectorRoster[i].getCourseName() << "|" << vectorRoster[i].getCourseCode() << "|"<< vectorRoster[i].getNumCredits() << "|"
                         << vectorRoster[i].getInstructorName() << endl;

                        //loop that will print student information
                    for(int j = 0; j < vectorRoster[i].getNum(); j++)
                    {
                        Student temp = Student(vectorRoster[i][j]);
                        file << "student" << j+1 << "|" << temp.getFirst() << "|" << temp.getLast()
                             << "|" << temp.getID() << "|" << temp.getStandings()
                             << "|" << temp.getCredits() << "|" << temp.getGpa()
                             << "|" << temp.getDateOfBirth().getMonthNum() << "/" << temp.getDateOfBirth().getDay() << "/"
                             << temp.getDateOfBirth().getYear() << "|" << temp.getDom().getMonthNum() << "/" << temp.getDom().getDay() << "/"
                             << temp.getDom().getYear() << endl;

                    }
                    //added at the end of each roster to help with file reading
                    file << "end_roster|\n";
                }
                //the file is closed after written
                file.close();
                //the program is exited.
                exit(0);
            }
            else
            {
                //displayed if the input is invalid and loops around again
                std::cout << "*****Invalid Input, Try Again!*****\n";
            }
        }

        //loop that will control the user mode
        while(userLoop)
        {
            //if the user chooses user mode they will be asked to choose from the menu options
            if(choice == 1)
            {
                std::cout <<"1. Insert new Student to Roster\n2. Remove a Student from a Roster\n3. Update a Student in a Roster\n4. Main Menu\n";
                std::cin >> choiceU2;

                //if the user chooses to go to the main menu they are redirected there.
                if(choiceU2 < 5)
                {
                    if(choiceU2 == 4)
                    {
                        std::cout << "Directing to main menu.\n";
                        system("pause");
                        system("CLS");
                    }


                    //if the roster is empty the user is told to speak to a admin because they do not have access to create a roster
                    else if(vectorRoster.size() == 0)
                    {
                        std::cout << "There are no Rosters at this time ask admin to add Roster\n";
                        choiceU2 = 4;
                        system("pause");
                        system("CLS");
                    }
                    else
                    {
                        //the user is asked to choose a roster to edit
                        if(choiceU2 != 4)
                        {
                            system("CLS");

                            std::cout << "Select a Roster from the following:\n";
                            for(int i = 0; i < vectorRoster.size(); i++)
                            {
                                std::cout << i+1 << ". \n" << "**********ROSTER*********\nCurrent Roster Size: " << vectorRoster[i].getNum() << "\nCourse Name : " << vectorRoster[i].getCourseName()
                                          << "\nCourse Code: " << vectorRoster[i].getCourseCode() << "\nCourse Instructor: " << vectorRoster[i].getInstructorName()
                                          << "\nCourse Credits: " << vectorRoster[i].getNumCredits() << "\n\n";
                            }
                            std::cin >> choiceU;
                        }
                    }
                }

                //if the user chose to add a student they will be prompted to enter the information
                // and then the student will be added with the use of a temporary/local variable.
                if(choiceU2 == 1)
                {
                    std::cout << "Enter a student to be added: " << endl;
                    Student tempAdd;
                    std::cin >> tempAdd;
                    Roster tempAddR2;
                    //tempAddR2 = vectorRoster[choiceU-1];
                    //tempAddR2.addStudent(tempAdd);
                    vectorRoster[choiceU-1].addStudent(tempAdd);
                    std::cout << "Student added\n" << vectorRoster[choiceU-1] << endl;

                }

                //if the user chooses to remove a student the roster is check first to see if it is empty or not.
                //if it is not empty they are prompted to choose a student.
                else if(choiceU2 == 2)
                {
                    if(vectorRoster[choiceU-1].getNum() == 0)
                    {
                        std::cout << "\nRoster Empty";
                        system("pause");

                        system("CLS");

                    }
                    else
                    {
                        system("CLS");

                        std::cout << "Select a student to be removed: " << endl;
                        std::cout << vectorRoster[choiceU-1] << endl;
                        int rem = 0;
                        while(rem == 0) {
                            std::cin >> rem;
                        }
                        Student temp = vectorRoster[choiceU-1][rem-1];
                        vectorRoster[choiceU-1].deleteStudent(temp);
                        std::cout << "Student deleted\n" << vectorRoster[choiceU-1] << endl;
                    }
                }

                //if the user chose to update a student they are required to enter the students information and
                //will be prompted to do so if the student is within bounds.
                else if(choiceU2 == 3)
                {
                    system("CLS");

                    std::cout << "Select a student to be updated: " << endl;
                    std::cout << vectorRoster[choiceU-1] << endl;
                    int rem;
                    std::cin >> rem;
                    std::cin >> vectorRoster[choiceU-1][rem-1];

                    std::cout << "Student updated\n" << vectorRoster[choiceU-1] << endl;
                }
                //if the user chooses to go back to the menu the user loop ends
                else if(choiceU2 == 4)
                {
                    userLoop = false;
                }

                //the user entered invalid input and the program will loop over the user menu.
                else
                {
                    std::cout << "*****Invalid Input, Try Again!*****\n";
                }

            }
        }

        //If the user chose to enter supervisor mode they will be prompted to login and the information will be
        //verified against a database.txt file.
        if(choice == 2)
        {
            cont = false;
            loginSuccess = false;
            //user is prompted for login information
            std::cout << "Please login below to access the rosters\n\n";
            //do while loop that will assist in authenticating the supervisor
            do
            {
                std::cout << "\nUsername: ";
                std::cin >> username;
                std::cout << "Password: ";
                std::cin >> password;

                //file is opened to check for a match for the users username and password.
                std::ifstream infile("database.txt");

                //loop through the database.txt file
                while (infile >> u >> p)
                {
                    //if there is a username and password match the loops end.
                    if(username == u && password == p)
                    {
                        cont = true;
                    }
                }

                //if the username/password was correct the user is logged in and the loop is broken
                if(cont)
                {
                    std::cout << "\nSuccessfull Login\n\n";
                    loginSuccess = true;
                    system("CLS");
                }

                //if the username/password was wrong the loop continues
                else
                {

                    system("CLS");
                    std::cout << "Incorrect username and password combo\n";
                    std::cout << "Please try to login again";
                }
            }
            while(!loginSuccess);

            //if successfully logging in the user is taken to supervisor mode
            while(adminLoop)
            {
                //the variables are reset and the vector is resized after each operation
                choiceR2 = 0;
                vectorRoster.resize(vectorRoster.size());
                system("pause");
                system("CLS");

                //menu options for supervisor mode
                std::cout << "*****Welcome to Supervisor Mode*****\n";
                std::cout << "1. Create a new Roster" << endl;
                std::cout << "2. Drop a Roster" << endl;
                std::cout << "3. Display Roster Information" << endl;
                std::cout << "4. Display all Rosters" << endl;
                std::cout << "5. Select a Roster to perform operations" << endl;
                std::cout << "6. Main Menu\n" << endl;

                //the user is prompted to choose a menu option
                std::cin >> choiceR2;

                //if the user chose to create a roster they will be prompted to do so and will get a verification message.
                if(choiceR2 == 1)
                {
                    system("CLS");

                    std::cout << "Enter Roster details to be created: " << endl;
                    std::cin >> tempAddR;
                    vectorRoster.push_back(tempAddR);
                    std::cout << "Roster created" << endl;
                }

                //if the user chose to remove a roster they will be prompted to do so and will get a verification message.
                else if(choiceR2 == 2)
                {

                    //if the roster is empty a message will indicate so
                    if(vectorRoster.size() == 0)
                    {
                        std::cout << "The roster is empty there is nothing to display" << endl;
                        system("pause");

                        system("CLS");

                    }

                    //the user is prompted for a course number which is searched for using a loop.
                    else
                    {
                        system("CLS");
                        std::cout << "Enter a course number to be removed: " << endl;
                        std::string courseRem;
                        std::cin >> courseRem;
                        std::vector<Roster> vectorRoster2;
                        int pos = 0;
                        //loop that will find position of course if it exist
                        for(int i = 0; i < vectorRoster.size(); i++)
                        {
                            if(courseRem == vectorRoster[i].getCourseCode())
                            {
                                pos = i;
                            }
                        }
                        //loop that will remove course from temporary roster
                        for(int i = 0 ; i < vectorRoster.size(); i++)
                        {
                            if(i != pos)
                                vectorRoster2.push_back(vectorRoster.at(i));
                        }
                        //the temporary vector is swapped with the vectorRoster to display course removal
                        vectorRoster.swap(vectorRoster2);
                        system("pause");
                    }
                }

                //if the user chose to display a roster they will be prompted to do so and will get a verification message.
                else if(choiceR2 == 3)
                {

                    //if the roster is empty a message will indicate so
                    if(vectorRoster.size() == 0)
                    {
                        std::cout << "The roster is empty there is nothing to display" << endl;
                        system("pause");

                        system("CLS");

                    }
                    else
                    {
                        //the user enters a course number to display information pertaining to that course if it exist
                        system("CLS");

                        std::cout << "Enter a course number to display information: " << endl;
                        std::string courseRem;
                        std::cin >> courseRem;
                        int pos = 0;

                        //loop that will find position of the course
                        for(int i = 0; i < vectorRoster.size(); i++)
                        {
                            if(courseRem == vectorRoster[i].getCourseCode())
                            {
                                pos = i;
                            }
                        }
                        //displaying the course information if it is within bounds
                        std::cout << "Displaying chosen Rosters information: " << endl;
                        std::cout << "**********ROSTER*********\nCurrent Roster Size: " << vectorRoster[pos].getNum() << "\nCourse Name : " << vectorRoster[pos].getCourseName()
                                  << "\nCourse Code: " << vectorRoster[pos].getCourseCode() << "\nCourse Instructor: " << vectorRoster[pos].getInstructorName()
                                  << "\nCourse Credits: " << vectorRoster[pos].getNumCredits() << "\n\n";
                    }

                }

                //if the user chooses to all rosters are displayed with pertinent information only
                else if(choiceR2 == 4)
                {

                    //if the roster is empty it is indicated so
                    if(vectorRoster.size() == 0)
                    {
                        std::cout << "The roster is empty there is nothing to display" << endl;
                        system("pause");

                        system("CLS");

                    }
                    else
                    {
                        //all rosters are displayed with the help of a loop
                        system("CLS");

                        std::cout << "Displaying all Rosters:\n" << endl;
                        for(int i = 0; i < vectorRoster.size(); i++)
                        {
                            std::cout << i + 1 << ". " << "**********ROSTER*********\nCurrent Roster Size: " << vectorRoster[i].getNum() << "\nCourse Name : " << vectorRoster[i].getCourseName()
                                      << "\nCourse Code: " << vectorRoster[i].getCourseCode() << "\nCourse Instructor: " << vectorRoster[i].getInstructorName()
                                      << "\nCourse Credits: " << vectorRoster[i].getNumCredits() << "\n\n";
                        }
                    }

                }

                //if the user chooses this option they will gain access to another menu to perform
                //operations on their desired roster.
                else if(choiceR2 == 5)
                {

                    //if the roster is empty it is indicated so.
                    if(vectorRoster.size() == 0)
                    {
                        std::cout << "The roster is empty there is nothing to display" << endl;
                        system("pause");

                        system("CLS");

                    }
                    else
                    {
                        //the user chooses a roster to perform operations on
                        system("CLS");

                        std::cout << "Select a Roster from the following:\n";
                        for(int i = 0; i < vectorRoster.size(); i++)
                        {
                            std::cout << i + 1 << ". " << "**********ROSTER*********\nCurrent Roster Size: " << vectorRoster[i].getNum() << "\nCourse Name : " << vectorRoster[i].getCourseName()
                                      << "\nCourse Code: " << vectorRoster[i].getCourseCode() << "\nCourse Instructor: " << vectorRoster[i].getInstructorName()
                                      << "\nCourse Credits: " << vectorRoster[i].getNumCredits() << "\n\n";
                        }
                        std::cin >> choiceU;

                        //the user enters the operation they want to perform
                        std::cout << "1. Insert a new Student to the Roster\n2. Remove a Student from a Roster\n3. Update a Student in a Roster\n4. List all Students in one Roster in sorted order" << endl;

                        std::cin >> choiceU2;

                        //if indicated so the user will add a student to a roster if it is within bounds
                        if(choiceU2 == 1)
                        {
                            system("CLS");

                            Student temp2;
                            std::cout << "Enter a student to be added: " << endl;
                            std::cin >> temp2;
                            Roster tempAddR3;
                            //tempAddR3 = vectorRoster[choiceU-1];
                            //tempAddR3.addStudent(temp2);
                            vectorRoster[choiceU-1].addStudent(temp2);
                            std::cout << "Student added\n" << vectorRoster[choiceU-1] << endl;

                        }

                        //the user will remove a student from a chosen roster if they exist.
                        else if(choiceU2 == 2)
                        {

                            //if the roster is empty it is indicated so.
                            if(vectorRoster[choiceU-1].getNum() == 0)
                            {
                                std::cout << "\nRoster Empty";
                                system("pause");

                                system("CLS");

                            }
                            else
                            {

                                //the student is chosen based on their index and removed if within bounds
                                system("CLS");

                                std::cout << "Select a student to be removed: " << endl;
                                std::cout << vectorRoster[choiceU-1] << endl;
                                int rem;
                                std::cin >> rem;
                                Student temp = vectorRoster[choiceU-1][rem-1];
                                vectorRoster[choiceU-1].deleteStudent(temp);
                                std::cout << "Student deleted\n" << vectorRoster[choiceU-1] << endl;
                            }
                        }

                        //a student can be updated if within bounds.
                        else if(choiceU2 == 3)
                        {

                            //if the roster is empty it is indicated so.
                            if(vectorRoster[choiceU-1].getNum() == 0)
                            {
                                std::cout << "\nRoster Empty";
                                system("pause");

                                system("CLS");

                            }
                            else
                            {

                                //if the student is within bounds/exist it will be updated.
                                system("CLS");
                                std::cout << vectorRoster[choiceU-1] << endl;

                                std::cout << "Select a student to be updated: " << endl;
                                int rem;
                                std::cin >> rem;
                                std::cin >> vectorRoster[choiceU-1][rem-1];

                                std::cout << "Student updated\n" << vectorRoster[choiceU-1] << endl;
                            }
                        }

                        //the students in a chosen roster will be displayed in a sorted fashion
                        else if(choiceU2 == 4)
                        {
                            //if the roster is empty it will indicate so.
                            if(vectorRoster[choiceU-1].getNum() == 0)
                            {
                                std::cout << "\nRoster Empty";
                                system("pause");

                                system("CLS");

                            }
                            else
                            {

                                //the roster is displayed if it exist/is within bounds
                                system("CLS");

                                std::cout << "Sorted list of students in Roster: " << endl;
                                std::cout << vectorRoster[choiceU-1] << endl;
                            }
                        }

                        //the user entered an invalid option and the loop is broken
                        else
                        {
                            std::cout << "Invalid Option";
                            userLoop = false;
                        }
                    }

                }

                //the user desired to go to the main menu and the loop is broken
                else if(choiceR2 == 6)
                {
                    adminLoop = false;
                    std::cout << "Directing to main menu.\n";
                    system("pause");
                    system("CLS");

                }

                //the user entered invalid input and the loop runs again.
                else
                {
                    std::cout << "*****Invalid Input, Try Again!*****\n";
                }
            }
        }
    }
    //the program is paused before exiting
    system("pause");
    return 0;
}

//function definition -- splits string vector with specified delimiter.
std::vector<std::string> split(std::string strToSplit, char delimeter)
{
    //local variables
    std::stringstream ss(strToSplit);
    std::string item;
    std::vector<std::string> splittedStrings;

    //loop that will split string using vector and a delimiter
    while (std::getline(ss, item, delimeter))
    {
        splittedStrings.push_back(item);
    }
    //returns a string vector
    return splittedStrings;
}

