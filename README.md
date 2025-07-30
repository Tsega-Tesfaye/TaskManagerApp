# TaskManagerApp

This is a simple Java application for managing tasks and users, using an SQLite database for data storage.

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven (for dependency management and building)

## Setup
1. **Clone the repository**
   ```sh
   git clone <your-repo-url>
   cd TaskManagerApp
   ```

2. **Install dependencies**
   Maven will automatically download the required dependencies (including the SQLite JDBC driver) when you build the project.

3. **Database**
   - The application uses an SQLite database file named `taskmanager.db` in the project root by default.
   - The database tables (`User` and `Task`) are created automatically when you run the application, using the `DataSource.initialize()` method.

## Building the Project
Run the following command in the project root:
```sh
mvn clean package
```
This will compile the code and package it into a JAR file in the `target/` directory.

## Running the Application
You can run the application using the following command:
```sh
java -cp target/TaskManagerApp-1.0-SNAPSHOT.jar;path/to/sqlite-jdbc.jar app.TaskManagerApp
```
Replace `path/to/sqlite-jdbc.jar` with the actual path to the SQLite JDBC driver JAR if it's not included in your Maven dependencies.

## Troubleshooting
- If you encounter `ClassNotFoundException` for the SQLite driver, ensure the dependency is present in your `pom.xml` and your classpath.
- If the database file is not created, check file permissions and the path in `DataSource.java`.

## License
This project is for educational purposes.
