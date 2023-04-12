# Gamification-App
This is a gamification app that allows users to earn tokens in multiple ways. Each task has a form of a quiz. By solving a task correctly, the user gains ``all
the tokens`` associated to a it. By contributing to a task creation, the user receives ``10 tokens`` and if he has bad luck and gets a wrong answer, he still gains ``5 tokens``
for participation. The user can pick up from a variety of categories such as Programming, Economy, Geography, Music, Movies, and Sports.

The app also has a ranking system where it lists the users in three categories:
- most tokens gained
- most contributions
- most correct solved tasks

## Functionality
The Gamification App offers three sets of operations:

- Authentication

  - Register
  
  - Login

- Task

  - Create a task
  
  - Show available tasks to a user
  
  - Solve a task
  
- Application

  - Show user profile
  
  - Check rankings by the number of tokens
  
  - Check rankings by the number of contributions
  
  - Check rankings by the number of correct solved tasks
  
 ## Useability 
 In order for the application flow to run correctly, register the user first and then ``login``!
 
 After the user logins, he can perform the opperations mentioned above.
 
 For the operations regarding the tasks, the user can:
 
  - ```Create a task```. Here he has to specify the description of the task (quiz), 4 options of answers, the number of the correct answer (from 1-4), number of
  tokens (must be between 10 and 50) and pick a category from Programming, Economy, Geography, Music, Movies, or Sports. If the user won't respect these rules, a
  specific error message will occur and warn him.
  
  - ```Show available tasks to a user```. Here the user can see all the available tasks that he can solve from a specified category. The app ensures the user that 
  no tasks that were already solved or tasks that the user created will appear. If the user search for a category that not exists or if no available tasks are 
  available for a certain category, a specific error message will appear.
  
  - ```Solve a task```. Here the user can solve a task by specifying the category and the number of the answer (from 1-4). Note that the user will solve 
  each time ```the first task``` from a category. If his answer is correct, he will be awarded the tokens associated with the task, otherwise, he will gain 
  only 5 tokens. Again, if the user chooses a wrong category that not exists/ there are no available tasks or the response is incorrect, a specific 
  error message will appear.
  
   For the operations regarding the application, the user can:
   
   - ```Check his profile```. Here he will be able to see pieces of information such as username, the list of tasks that he contributed to or solved, the number 
   of tokens gained, the number of correctly solved tasks, and the number of wrong answers that he provided for a task as well.
   
   - ```Check rankings by the number of tokens```
  
  - ```Check rankings by the number of contributions```
  
  - ```Check rankings by the number of correct solved tasks``` 
 
 ## Running App
Build the project following the ```./gradlew build``` command.

You can run the application (a REST server) in your IDE by running class ```GamificationAppApplication``` as Java Application or on the command line ```gradle wrapper run```.
 
 ## Documentation
 The documentation for each Gamification APP endpoint was done via **Swagger springdoc-openapi** and it can be found after running the application at the following
 url ```https://gamification-app.herokuapp.com/swagger-ui/index.html```
 
 ## Technology
 
 - Java 17
 
 - Spring Boot (3.0.5)
 
 - Hibernate 
 
 - Spring Data JPA
 
 - Documentation: Swagger springdoc-openapi
 
 - Deployment: Heroku
 
 - Endpoints testing: Postman
 
 - Build Tool: Gradle
 
 - Database: PostgreSql
 
 - IDE: IntelliJ
