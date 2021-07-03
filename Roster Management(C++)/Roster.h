//Derick Hansraj
//12/02/2018
//Project 1
//Cs211

#ifndef ROSTER_H
#define ROSTER_H

#include <iostream>
#include "Student.h"
#include "Date.h"

class Roster {

private:
	//member variables
	std::string courseName, courseCode, instructorName;
	int numCredits,num = 0, rcurr = 0, current_Max = 10;
	Student **student = new Student*[1];
	template<class T>
    void swap(T& v1, T& v2);
    template<class T>
    void sort(T a[], int numberUsed);
    template<class T>
    void grow(T a[]);


public:

	//accessor functions
	std::string getCourseName();
	std::string getCourseCode();
	std::string getInstructorName();
	int getNumCredits();
	int getNum();

	//mutator functions
	void setCourseName(std::string n);
	void setCourseCode(std::string c);
	void setInstructorName(std::string i);
	void setNumCredits(int c);

	//Students functions
	void addStudent(Student s);
	void deleteStudent(Student s);
	int searchStudent(Student s);
	void output();

	//Constructors
	Roster();
	Roster(std::string cn, std::string cc, int nc, std::string in);

    //Overloaded Operators
    friend std::ostream& operator <<(std::ostream& out, const Roster& s);
    friend std::istream& operator >>(std::istream& in, Roster& s);
    Student& operator[] (int);

    //****The big three****
    //Copy Constructor
	Roster(const Roster& da);
	//Destructor
	~Roster();
    //assignment operator
    Roster& operator= (const Roster &roster);


};

#endif


