# Gym CLI application
## User Guide
   The Gym App is a comprehensive management system designed for gyms to efficiently manage users, memberships, and workout classes. The application allows administrators to oversee user accounts, trainers to manage workout classes, and members to access their membership information. By streamlining these processes, the Gym App aims to improve the overall gym experience for both staff and members.

### Getting Started with the Gym App System
#### Prerequisites
- Java Development Kit (JDK) installed on your system
- IntelliJ IDEA or Visual Studio Code (VS Code) installed on your system
- Java extensions installed in VS Code (if using)
### How to Run the System
#### VS Code
1. Open VS Code and navigate to the GymApp directory. 
2. Open the Explorer and locate the GymApp.java file in the src/GymApp directory. 
3. Right-click on GymApp.java and select "Run Java" or press the Run/Play button in the top bar. 
4. The system will start, and you can interact with it through the command-line interface.

#### IntelliJ
1. Open IntelliJ IDEA and navigate to the GymApp project. 
2. Locate the GymApp.java file in the src/GymApp directory. 
3. Open GymApp.java and click the Run/Play button near the main method or press Shift+F10. 
4. The system will start, and you can interact with it through the command-line interface.

### How to Seed the Database
Seeding the database is necessary to populate it with initial testing data.
#### VS Code
1. Open VS Code and navigate to the GymApp directory. 
2. In the Explorer, right-click on GymApp.java and choose "Run Java". 
3. Click the small gear icon ⚙️ near the Run/Play button in the top bar. 
4. Select "Configure". 
5. In the launch.json file, add "args": ["--seed"] under the args section. 
6. Save the changes and re-run the program.

#### IntelliJ
1. Open the GymApp class in IntelliJ. 
2. Go to Run > Edit Configurations. 
3. In the "Program Arguments" field, type --seed. 
4. Click Apply, then Run.
Note: Running the system with the --seed argument will only execute the seeding logic and exit. To run the main application, remove the --seed argument from the configuration.
After seeding the database, you can run the system without the --seed argument to interact with the populated database.

### Main Menu
When you start the Gym Management System, you'll see the main menu with the following options:
1. Add a new user: Register a new user with the system.
2. Login as a user: Log in to the system with an existing user account.
3. Exit: Quit the application.

##### Registering a New User
To register a new user, follow these steps:
1. Select option 1. Add a new user from the main menu. 
2. Enter Users email address. It cannot already exist in the system. 
3. Enter Users username. 
4. Enter Users password.
5. Enter Users phone number. 
6. Enter Users address. 
7. Enter Users role (Admin, Trainer, or Member).

#### Logging In
To log in to the system, follow these steps:
1. Select option 2. Login as a user from the main menu. 
2. Enter your email address. 
3. Enter your password.
If your credentials are correct, you'll see a welcome message and be taken to your user-specific menu.

### Accessing the Admin Menu
To access the Admin Menu, you need to log in to the system with an admin account. Once logged in, you will be taken to the Admin Menu.
#### Admin Menu Options
1. View all Users
   This option allows you to view a list of all users in the system.
   The list will include all users.
2. View Users by Role
   This option allows you to view a list of users with a specific role.
   You will be prompted to enter the role (Admin, Trainer, Member).
3. Delete User
   This option allows you to delete a user from the system.
   You will be prompted to enter the ID of the user to delete.
   User ID can be found in the list of all users or 
   Note that you cannot delete yourself.
4. View Memberships
   This option allows you to view a list of all memberships in the system.
5. View total Revenue
   This option allows you to view the total revenue for a specific year.
   You will be prompted to enter the year.
6. Logout
   This option allows you to log out of the system and return to the main menu.

### Accessing the Member Menu
To access the Member Menu, you need to log in to the system with a member account. Once logged in, you will be taken to the Member Menu.
#### Member Menu Options
1. Browse Workout Classes
   This option allows you to view a list of all available workout classes.
   The list will include details such as class ID, name, type, description, status, and trainer.
2. View Total Membership Expenses
   This option allows you to view the total cost of your membership expenses.
   The total cost will be displayed in dollars and cents.
3. Purchase New Gym Membership
   This option allows you to purchase a new gym membership.
   You will be prompted to enter the membership type (basic, standard, premium).
4. Logout
   This option allows you to log out of the system and return to the main menu.

### Accessing the Trainer Menu
To access the Trainer Menu, you need to log in to the system with a trainer account. Once logged in, you will be taken to the Trainer Menu.
#### Trainer Menu Options
1. Add a Workout Class
   This option allows you to add a new workout class.
   You will be prompted to enter the class name, type, description, and capacity. 
   - Class Name: A descriptive name for the workout class (e.g., "Morning Yoga", "Spin Express", "Strength 101", etc.).
   - Class Type: The type of workout class (e.g., "Yoga", "Spin", "Strength", "Pilates", "HIT", "Stretching", etc.).
   - Description: A brief description of the workout class (e.g., "Start your day with a stretch", "High intensity cycling", "Beginner strength training", etc.). 
   - Capacity: The maximum number of participants allowed in the class (e.g., 10, 12, 15, 18, 20, etc.).
2. Delete a Workout Class
   This option allows you to delete an existing workout class.
   You will be prompted to enter the ID of the class you want to delete.
3. Update a Workout Class
   This option allows you to update an existing workout class.
   You will be prompted to enter the ID of the class you want to update and the new details.
4. View My Workout Classes
   This option allows you to view all the workout classes you are currently teaching.
5. Buy Membership
   This option allows you to purchase a new gym membership.
   You will be prompted to enter the membership type (basic, standard, premium).
6. Logout
   This option allows you to log out of the system and return to the main menu.

--- 

## Cloning and Running the Project from GitHub
### Prerequisites
- Git installed on your system 
- Java Development Kit (JDK) installed on your system 
- IntelliJ IDEA or Visual Studio Code (VS Code) installed on your system 
- Java extensions installed in VS Code (if using)
### Step-by-Step Instructions
1. Clone the Repository:
   - Open a terminal or command prompt. 
   - Navigate to the directory where you want to clone the project. 
   - Run the command git clone https://github.com/Keyin-SD14-S3/fullstack-databases-final-sprint-team-team-jl-ma-sp.git (replace username and repository-name with the actual GitHub username and repository name). 
   - Press Enter to clone the repository. 
2. Open the Project in Your IDE:
   - Open IntelliJ IDEA or VS Code. 
   - Navigate to the cloned project directory. 
   - Open the project in your IDE. 
3. Configure the Project:
   - Ensure that the JDK is properly configured in your IDE. 
   - If necessary, configure any dependencies or libraries required by the project.
4. Seed the Database:
   - Follow the instructions in the "How to Seed the Database" section above.
5. Run the Project:
   - Follow the instructions in the "How to Run the System" section above.
#### Troubleshooting
- If you encounter any issues while cloning or running the project, ensure that Git and JDK are properly installed and configured on your system. 
- If you encounter any dependency or library issues, ensure that they are properly configured in your IDE.

---

## Reference for testing
### Logins
| Role    | Email                     | Password     |
|---------|---------------------------|--------------|
| Admin   | alex.admin@gym.com        | admin123     |
| Trainer | tina.trainer@gym.com      | trainer123   |
| Member  | marley.member@gym.com     | member123    |

### New User
| **Attribute** | **New User**         |
|---------------|----------------------|
| Email         | sophia.admin@gym.com | 
| Username      | sophia_admin         | 
| Password      | admin456             | 
| Phone Number  | 709-555-4001         |
| Address       | 456 Admin St         | 

## New Class
| **Attribute** | **Value**          |
|---------------|--------------------|
| Name          | Yoga for Beginners |
| Type          | Yoga               |
| Description   | Gentle yoga class  |
| Capacity      | 15                 |

---
## Development Documentation
### Project Directory Structure
- src/gymapp: The main application class (GymApp.java) resides here. 
- setup: Contains the database seeder (DemoDatabaseSeeder) for initializing the database. 
- services: Business logic is handled in this directory, with separate classes for users, memberships, gym classes, and menu actions. 
- models: Data structures are defined here, including users (with admin, trainer, and member subclasses), memberships, and gym classes. 
- dao: Database operations are encapsulated in this directory, with separate classes for users, memberships, and gym classes. 
- menu: Role-based menus are defined in this directory, including admin, user, and member menus. 
- database: The database connection is managed from this directory.
This structure separates concerns and promotes modularity, making it easier to maintain and extend the application.

### Build Process:
The project uses Maven (Apache Maven) as its build tool. Maven is a popular build automation tool for Java projects. The build process is defined in the pom.xml file, which specifies the project's structure, dependencies, and build settings.

### Dependencies:
The project has two main dependencies:
- PostgreSQL Driver: The project uses the PostgreSQL JDBC driver (version 42.7.5) to connect to a PostgreSQL database. This dependency allows the application to interact with the database.
- jBCrypt: The project uses jBCrypt (version 0.4) for password hashing. This dependency provides a secure way to store passwords in the database.

---

## Melanie's Notes
### Contributions:
- 
### Challenges:
- 

---
## Jennifer's Notes
### Contributions:
- Worked on "User Branch" consisting of Users, UserDAO and UserService.
- Worked on Admin Menu and needed functions in MenuActions
- was part of the group effort in planning, editing and documentation.

### Challenges:
- Understanding how the DAO's should function and be written in our system.
- Understanding the use of the child classes for USER, as the logic seems to all be based on the role.

---
## Sarah's Notes
### Contributions
- Designing and implementing the **WorkoutClass model**, DAO, and service layer.
- Building the **Trainer menu**, including full CRUD operations for workout classes with appropriate validation and logging.
- Creating the **DemoDatabaseSeeder** for initializing test data and resetting the database.
- Setting up structured logging using Java’s `Logger` across services and menus.
- Assisting with general application flow and menu navigation for trainers.
- Branches: clean_up, trainer_menu, workout_branch (maybe a couple other smaller ones)
- Pill Requests: just so many of them. And so many to the wrong repo. At least 12. 

### Challenges:
- Understanding the flow for error handling.
- We each worked on a model, DAO, and Service. I wonder if it might have been easier if someone did the models, then the DAO, and then the Services?
