//Derick Hansraj
//12/02/2018
//Project 1
//Cs211

#include "Student.h"
#include <iostream>
#include <iomanip>
#include <cstdlib>




Student::Student() {
    lastName = "Doe";
    firstName = "John";
    gpa = 1.0;
    setCredits(1.0);
    id = "00000000";
}

Student::Student(std::string last, std::string first,Date dob, Date dom, double gp, double credit,std::string stuID) {
    lastName = last;
    firstName = first;
    dateOfBirth = dob;
    dateOfMat = dom;
    gpa = gp;
    setCredits(credit);
    id = stuID;

}


//accessor function for lastName
std::string Student::getLast()
{
    return lastName;
}

//mutator function for lastName
void Student::setLast(std::string l)
{
    lastName = l;
}

//accessor function for firstName
std::string Student::getFirst()
{
    return firstName;
}

//mutator function for firtName
void Student::setFirst(std::string f)
{
    firstName = f;
}

//accessor function for dateOfBirth
Date Student::getDateOfBirth()
{
    return dateOfBirth;
}


//mutator function for dateOfBirth
void Student::setDateOfBirth(Date dob)
{
    dateOfBirth = dob;
}

//accessor function for dateOfMat
Date Student::getDom()
{
    return dateOfMat;
}

//mutator function for dateOfMat
void Student::setDom(Date dom)
{
    dateOfMat = dom;
}

//accessor function for gpa
double Student::getGpa()
{
    return gpa;
}

//mutator function for gpa
void Student::setGpa(double gp)
{
    gpa = gp;
}

//accessor function for credits
double Student::getCredits()
{
    return credits;
}

//mutator function for credits, this will also determine the standing of the Student
void Student::setCredits(double credit)
{
    if(credits < 0) {
        std::cout << "Illegal number of credits\n";
        exit(0);
    }
    credits = credit;
    if(credits < 15)
        standing = "Lower Freshman";
	else if(credits < 30)
        standing = "Upper Freshman";
    else if(credits < 45)
        standing = "Lower Sophmore";
    else if(credits < 60)
        standing = "Upper Sophmore";
    else if(credits < 75)
        standing = "Lower Junior";
    else if(credits < 90)
        standing = "Upper Junior";
    else if(credits < 105)
        standing = "Senior";
    else
        standing = "Upper Senior";
}

//accessor function for standing
std::string Student::getStandings()
{
    return standing;
}

//accessor function for id
std::string Student::getID()
{
    return id;
}

//mutator function for id
void Student::setID(std::string stuID)
{
    id = stuID;
}

//input function that will ask the user for values that will be used with the mutator functions
//for each private member .
void Student::input()
{
    std::cout << "Enter the students last name:";
    std::cin >> lastName;

    std::cout << "Enter the students first name:";
    std::cin >> firstName;


    std::cout << "Enter the students date of birth:";
    Date dob;
    std::cin >> dob;
    setDateOfBirth(dob);

    std::cout << "Enter the students date of matriculation:";
    Date dom;
    std::cin >> dom;
    setDom(dom);

    std::cout << "Enter the students ID number:";
    std::cin >> id;


    while(true)
	{
		try
		{
			std::cout << "Enter the student's credit number: ";
			std::cin >> credits;

			if(credits <= 0) throw credits;

			break;
		}
		catch(int e)
		{
			std::cout	<< "You entered \"" << e << "\" for your credit value. "
						<< "Please enter a positive integer instead." << std::endl;
		}
	}
	setCredits(credits);

	while(true)
	{
		try
		{
			std::cout << "Enter the student's GPA: ";
			std::cin >> gpa;

			if(gpa < 0 || gpa > 4) throw gpa;

			break;
		}
		catch(double e)
		{
			std::cout	<< "You entered \"" << e << "\" for your gpa value. "
						<< "Please enter a value from 0 to 4.0 instead." << std::endl;
		}
	}
}

//output function that will output a Student by displaying all of its private members
void Student::output()
{
    std::cout << "Last Name: " << lastName << std::endl
    << "First Name: " << firstName << std::endl
    << "Date of Birth: " << dateOfBirth << std::endl
    << "Date of Matriculation: " << dateOfMat << std::endl
    << "GPA: " << std::setprecision(2) << std::fixed << gpa << std::endl
    << "Credits: " << credits << std::endl
    << "Standing: " << standing << std::endl << "ID Number: " << id << "\n" << std::endl;
}

bool Student::operator ==(const Student& s) const
{
	return ((this->lastName == s.lastName) && (this->firstName == s.firstName) && (this->id == s.id));
}
bool Student::operator !=(const Student& s) const
{
	return !(*this == s);
}
bool Student::operator <(const Student& s) const
{
	if(this->lastName != s.lastName)
	{
		return this->lastName < s.lastName;
	}
	else if(this->firstName != s.firstName)
	{
		return this->firstName < s.firstName;
	}
	else if(this->id != s.id)
	{
		return this->id < s.id;
	}
	else return false;
}
bool Student::operator >(const Student& s) const
{
	return (!(*this == s) && !(*this < s));
}
bool Student::operator <=(const Student& s) const
{
	return !(*this > s);
}
bool Student::operator >=(const Student& s) const
{
	return !(*this < s);
}

//Overload of the << operator in a nice manner
std::ostream& operator <<(std::ostream& out, const Student& s) {
	out << "Last Name: " << s.lastName << std::endl
    << "First Name: " << s.firstName << std::endl
    << "Date of Birth: " << s.dateOfBirth
    << "Date of Matriculation: " << s.dateOfMat
    << "GPA: " << std::setprecision(2) << std::fixed << s.gpa << std::endl
    << "Credits: " << s.credits << std::endl
    << "Standing: " << s.standing << std::endl << "ID Number: " << s.id << "\n" << std::endl;

	return out;
}

//Overload of the >> operator in a nice manner
std::istream& operator >>(std::istream& in, Student& s) {
	std::cout << "Enter the students last name:";
    std::string ln;
    in >> ln;
    s.setLast(ln);

    std::cout << "Enter the students first name:";
    std::string fn;
    in >> fn;
    s.setFirst(fn);

    std::cout << "Enter the students date of birth:";
    Date dob;
    in >> dob;
    s.setDateOfBirth(dob);

    std::cout << "Enter the students date of matriculation:";
    Date dom;
    in >> dom;
    s.setDom(dom);

    std::cout << "Enter the students GPA:";
    double gp;
    in >> gp;
    s.setGpa(gp);

    std::cout << "Enter the students number of credits:";
    double cr;
    in >> cr;
    s.setCredits(cr);

    std::cout << "Enter the students ID number:";
    std::string stuID;
    in >> stuID;
    s.setID(stuID);

	return in;
}

