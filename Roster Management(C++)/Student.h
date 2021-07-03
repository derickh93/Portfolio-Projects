//Derick Hansraj
//12/02/2018
//Project 1
//Cs211

#ifndef STUDENT_H
#define STUDENT_H


#include <iostream>
#include <iomanip>
#include "Date.h"
#include <cstdlib>


//Define Student class
class Student
{
	//Define private members/variables
	private:
		std::string lastName, firstName,standing, id;
		Date dateOfBirth, dateOfMat;
		double gpa, credits;

	//Define public members/variables
	public:
		Student();

		Student(std::string last, std::string first,Date dob, Date dom, double gp, double credit,std::string stuID);

        //Mutator functions
        void setLast(std::string l);
        void setFirst(std::string f);
		void setDateOfBirth(Date dob);
		void setDom(Date dom);
		void setGpa(double gp);
		void setCredits(double credit);
		void setID(std::string stuID);


        //Accessor functions
		std::string getLast();
		std::string getFirst();
        Date getDateOfBirth();
        Date getDom();
		double getGpa();
		double getCredits();
		std::string getStandings();
		std::string getID();


		//input and output functions
		void input();
		void output();

		//Overloaded Operators as member functions
        bool operator==(const Student& rhs) const;
        bool operator!=(const Student& rhs) const;
        bool operator<(const Student& rhs) const;
        bool operator>(const Student& rhs) const;
        bool operator<=(const Student& rhs) const;
        bool operator>=(const Student& rhs) const;

		//Overloaded Operators as friends
		friend std::ostream& operator <<(std::ostream& out, const Student& s);
		friend std::istream& operator >>(std::istream& in, Student& s);


};

#endif

