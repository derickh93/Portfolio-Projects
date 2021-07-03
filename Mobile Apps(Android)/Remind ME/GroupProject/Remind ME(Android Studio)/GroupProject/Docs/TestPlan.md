# Test Plan

Author: Sukhjot Singh, Shehan Fernando | Group 6A | Other Team Members: Derick Hansraj, Choun Lee, Yasmeen Munasser 
									  
## 1 Testing Strategy

### 1.1 Overall strategy

Unit-testing is when each of us will go through each individual part of the application to make sure it works. This 
includes: add a reminder, edit a reminder, delete a reminder, add a reminder list, edit a reminder list, and delete a
reminder list. Each of these parts should work on their own without needing any other part of the application and therefore
 needs to be included as part of unit-testing.

Integration-testing is when we actually combine multiple parts of the application. This includes: creating a reminder 
list and adding a reminder to the list, adding a reminder and then checking it off as complete, adding multiple reminders
 to a list, checking them all, and then clearing all checks, setting a reminder with a day and time setting that allows 
 for repeat. Each of these tests require multiple parts of the application to be functioning individually, so the next 
 step would be to test if these parts function together.

System-testing is when we need to evaluate whether all the parts of the application meet the requirements. We would have 
to go through each requirement and test each one to make sure it’s working as needed. Therefore, all the tests on this 
part would naturally be the same as all the listed requirements.

Regression-testing is constantly occurring throughout the development process. Everytime a piece of code is added or 
changed, it should be regression-tested to see if it broke anything before it or broke itself. Test cases for this arise
 as our developers are developing the program.


### 1.2 Test Selection

We plan to use both black-box and white-box techniques, but for different parts of the testing. Our backend developers 
will be handling all of the white-box testing since it will require a good understanding of the code. Unit-testing will 
deploy such techniques because as the developer codes each part of the application, he/she will then need to test it to
 see if it works on its own. It will also be included in the integration-testing, but there will be more functional 
 testing for integration. We will need to test how each part of the application works with the other app without needing 
 to know anything about the code since our users won’t see any of that either. So it’s important to employ a black-box 
 technique for integration-testing. 

Overall, the most important way in selecting our test cases will be to use the requirements and using black-box techniques
 to test each requirement is met.

### 1.3 Adequacy Criterion

Through the use of testing programs such as JUnit, Selenia, and JIRA, we will thoroughly test all possible scenarios that
a user will possibly run into while using the app. We will need to constantly update as necessary as we come across new 
ideas or find bugs.

### 1.4 Bug Tracking

We are planning on using JIRA to track bugs. 

### 1.5 Technology

We are planning on using JUnit as a main testing program because of the ease of use and understanding in how the run the
program. Also, we use Selenium to check different aspects of the UI. 

## 2 Test Cases

![Test Case Diagram 1](https://github.com/qc-se-fall2019/370Fall19Team6a/blob/master/GroupProject/design-images/Test%20Case%20Diagram%201.PNG)

![Test Case Diagram 2](https://github.com/qc-se-fall2019/370Fall19Team6a/blob/master/GroupProject/design-images/Test%20Case%20Diagram%202.PNG)
