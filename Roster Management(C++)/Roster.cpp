//Derick Hansraj
//12/02/2018
//Project 1
//Cs211


//This is the implementation file Roster.cpp of the class Roster
//The interface for this class is in header file Roster.h
#include <iostream>
#include <cstdlib>
#include "Roster.h"

    //Default constructor for the Roster class
	Roster::Roster() {
	    student[rcurr] = new Student[current_Max];
		courseName = "CSC";
		courseCode = "001";
		numCredits = 0;
		instructorName = "Temp";
	}

	//Parameterized constructor for the Roster class
	Roster::Roster(std::string cn, std::string cc, int nc, std::string in) {
        student[rcurr] = new Student[current_Max];
		courseName = cn;
		courseCode = cc;
		numCredits = nc;
		instructorName = in;
	}

	//accessor functions
	std::string Roster::getCourseName(){return courseName;}
	std::string Roster::getCourseCode(){return courseCode;}
	std::string Roster::getInstructorName(){return instructorName;}
	int Roster::getNumCredits(){return numCredits;}
    int Roster::getNum(){return num;}


	//mutator functions
	void Roster::setCourseName(std::string n) {
		courseName = n;
	}

	void Roster::setCourseCode(std::string c) {
		courseCode = c;
	}

	void Roster::setInstructorName(std::string i) {
		instructorName = i;
	}

	void Roster::setNumCredits(int c) {
		numCredits = c;
	}

	//adds a student to the roster if there is space
	void Roster::addStudent(Student s) {
	    //if the roster reaches capacity we double to size
	    if(num > current_Max-1){
            std::cout << "Roster Full!!! Doubling size!!!\n\n";
            grow(student);
	    }
        //if the student exist in the roster we do not duplicate
        if(searchStudent(s) >= 0)
            std::cout << "Student Already Exist";
        else
            //the student is added and the roster is sorted
            Roster::student[rcurr][num++] = s;
        sort(student,num);
	}

	//deletes a student from the roster if they are in it
	void Roster::deleteStudent(Student s) {
        int pos = 0;
        Student s2;

        //checks if the student is in the roster
        if(searchStudent(s) < 0)
            std::cout << "The student is not in the roster";
        else{
            //the student is found and then the roster is adjusted
            pos = searchStudent(s);
            for(int i = pos; i < num-1; i++) {
                Roster::student[rcurr][i] = Roster::student[rcurr][i+1];
            }
            Roster::student[rcurr][num-1] = s2;
            num--;
        }
        //the roster is sorted after deletion
        sort(student,num);
    }

	//searches for a student in the Roster
	int Roster::searchStudent(Student s) {
	    int status = -1;
	    //if the student is found return the position
	    //else -1 is returned
	    for(int i = 0; i < num; i++) {
	        if(s == Roster::student[rcurr][i]) {
                status = i;
	        }
	    }
        return status;
	}

	//outputs the Roster in a proper manner
	void Roster::output() {
	    sort(student,num);
	    std::cout << "**********ROSTER*********\nCurrent Roster Size: " << num << "\nCourse Name : " << getCourseName()
	    << "\nCourse Code: " << getCourseCode() << "\nCourse Instructor: " << getInstructorName()
            << "\nCourse Credits: " << getNumCredits() << "\n\n";

	    for(int i = 0; i < num; i++) {
            std::cout << "Student # " << i+1 << ": \n";
            Roster::student[rcurr][i].output();
	    }
	}

	//Overload of the << operator in a nice manner
    std::ostream& operator <<(std::ostream& out, const Roster& s) {
        out << "**********ROSTER*********\nCurrent Roster Size: " << s.num << "\nCourse Name : " << s.courseName
            << "\nCourse Code: " << s.courseCode << "\nCourse Instructor: " << s.instructorName
                << "\nCourse Credits: " << s.numCredits << "\n\n";

        for(int i = 0; i < s.num; i++) {
            out << "Student # " << i+1 << ": \n";
            std::cout << s.student[0][i] << endl;
        }
        return out;
    }

    //Overload of the >> operator in a nice manner
    std::istream& operator >>(std::istream& in, Roster& r) {

        std::cout << "Enter the Course Name:";
        std::string cn;
        in >> cn;
        r.setCourseName(cn);

        std::cout << "Enter the Course Code:";
        std::string cc;
        in >> cc;
        r.setCourseCode(cc);

        std::cout << "Enter the Number of Credits:";
        int nc;
        in >> nc;
        r.setNumCredits(nc);

        std::cout << "Enter the Instructors Name:";
        std::string insN;
        in >> insN;
        r.setInstructorName(insN);

        return in;
    }

    //swap function that will swap two students
    template<class T>
    void Roster::swap(T& v1, T& v2) {
        T temp;
        temp = v1;
        v1 = v2;
        v2 = temp;
    }

    //sort function that will sort students from least to greatest as defined
    template<class T>
    void Roster::sort(T a[], int numberUsed) {
        int minIndex;
        for(int i = 0; i < numberUsed - 1; i++) {
            minIndex = i;
            for(int j = i + 1; j < numberUsed; j++) {
                if(a[rcurr][j] < a[rcurr][minIndex])
                    minIndex = j;
            }
            swap(a[rcurr][minIndex], a[rcurr][i]);
        }
    }

    //this function doubles the capacity of the roster
    template<class T>
    void Roster::grow(T a[]) {
        current_Max = current_Max * 2;
        Student **stu = new Student*[1];
        stu[0] = new Student[current_Max];

        for(int i = 0; i < num;i++) {
            stu[0][i] = a[0][i];
        }

        //the temporary array is deleted
        a[0] = stu[0];
        delete [] stu;
    }

    //overload of bracket operators that will return the student at the index
    Student& Roster::operator[](int index) {
        if (index >= num) {
            cout << "Array index out of bound, exiting";
            exit(0);
        }
        return student[0][index];
    }

    //Copy Constructor that assigns member values to the parameter
    Roster::Roster(const Roster& r) {

        this->courseName = r.courseName;
        this->courseCode = r.courseCode;
        this->instructorName = r.instructorName;
        this->numCredits = r.numCredits;
        this->num = r.num;
        this->rcurr = r.rcurr;
        this->current_Max = r.current_Max;

        for(int i = 0; i < this->num; i++)
            this->student[i] = r.student[i];
    }

    //Destructor
    Roster::~Roster() {
        delete [] student;
    }


    //The assignment operator
    Roster& Roster::operator=(const Roster& rhs) {
        if(this != &rhs) {
            if(this->num != rhs.num) {
                delete [] student;
                this->student[rcurr] = rhs.student[rcurr];
            }
            this->num = rhs.num;
            for(int i = 0; i < this->num; i++)
                this->student[i] = rhs.student[i];
        }
        return *this;
    }




