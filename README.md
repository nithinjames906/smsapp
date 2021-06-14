Problem Description 
 
 
Develop a survey management system using microservices architecture using rest API. 
Surveys are question and Answers (Supports options(multiple/single options) and/or free form response).
 
Functional requirements
 
Administrator Use Case:
 
The system should allow the administrator to create (setup) surveys. Support for different versions of same Surveys(Same survey will have different versions at the same time).
 
Customer Use Case (can be valid login as well as anonymous) : 
 
a) The system should allow users to access and answers the survey Questionaire. 
b) The system should allow users to resume surveys, where they have left before (Not mandatory for anonymous users)
c) The users should be given provision to continue on a version if they have started responding to survey but not finished.
d) New users with no in-progress surveys will be given the latest version of survey.
 
Analytics User
 
Business Analyst should be able to display the statistics around the surveys(Can use visualization techniques)
 
a) How many user attempted a specific survey?
 
b) How may user completed a specific survey?
 
The Survey can be integrated to other customer applications.
 
Create an JS Library, which can embed the Survey in client application. (Can develop a sample dummy application to integrate the Surveys into different pages.)
 
Few non-functional requirements are
 
• There should be sufficient test coverage (unit / integration tests) and code quality/security checks. 
• Service(s) should be observable, it should have sufficient logs and metrics 
• Service(s) should be containerized.
• Automate all stages within a CI/CD pipeline 
• Architecture diagram and sequence diagram. 
. Survey UI as described in the Functional spec above, [creation/design of survey . 
. Build & deployment script for for the application as microservice.
. Basic UI of Dummy UI , where the Survey can be integrated.
 
Required Artifacts
 
Source code 
Unit /Integration tests 
Docker/K8 files 
Build scripts
 
