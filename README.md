# Online Bookstore Application

This is a simple online bookstore application built using Spring Boot, Hibernate, and MySQL. The application allows users to perform CRUD (Create, Read, Update, Delete) operations on books, authors, and genres. Users can also search for books by title, author, or genre.

## Technologies Used

- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Jacoco (for code coverage)
- SonarQube (for code quality analysis)
- JUnit 5 (for unit testing)

## Getting Started

### Prerequisites

Before running the application, make sure you have the following installed:

- Java Development Kit (JDK) 11 or later
- MySQL database (You can configure the database connection in `application.properties`)
- Maven

### Running the Application

1. Clone the repository to your local machine:

   ```bash
   git clone <repository_url>

### Navigate to the project directory:
cd online-bookstore-app

### Build the application using Maven:
mvn clean install

### Run the application:
java -jar target/online-bookstore-app-1.0.jar

The application will be accessible at http://localhost:8080.

API Endpoints
/api/books: CRUD operations for books.
/api/authors: CRUD operations for authors.
/api/genres: CRUD operations for genres.
/api/books/searchByTitle: Search for books by title.
/api/books/searchByAuthor: Search for books by author.
/api/books/searchByGenre: Search for books by genre.

### Unit Testing
To run unit tests and check code coverage, you can use the following Maven command:

mvn clean test

This will execute the JUnit tests and generate a code coverage report using Jacoco.

### Code Quality Analysis
Code quality analysis can be performed using SonarQube. Ensure you have SonarQube configured and running. Then, you can analyze the code using the following Maven command:

mvn sonar:sonar

### Questions and answers
- Was it easy to complete the task using AI? 
Basically yes, but there were the problems with tests.
- How long did task take you to complete? (Please be honest, we need it to gather anonymized statistics) 
Basic app about 12 minutes, tests and coverage about 40 minutes
- Was the code ready to run after generation? What did you have to change to make it usable? 
Almost. I needed to add some properties for database connection, a few dependencies and getter/setter for entities.
- Which challenges did you face during completion of the task? 
The tests were most complicated part.
- Which specific prompts you learned as a good practice to complete the task? 
Short phrases with specific rules, code in '''

