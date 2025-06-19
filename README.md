# Day-14-Practical ICAE02

This project implements a RESTful API for a library management system using Spring Boot. The system manages students, books, and borrowing operations with comprehensive validation rules.

## Table of Contents

- [Day-14-Practical ICAE02](#day-14-practical-icae02)
  - [Table of Contents](#table-of-contents)
  - [Project Overview](#project-overview)
  - [Features](#features)
  - [Technology Stack](#technology-stack)
  - [Setup Instructions](#setup-instructions)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [Database Schema](#database-schema)
    - [Book Entity](#book-entity)
    - [Student Entity](#student-entity)
    - [Borrow Entity](#borrow-entity)
  - [API Documentation](#api-documentation)
    - [Base URL](#base-url)
    - [Endpoints](#endpoints)
      - [Book Endpoints](#book-endpoints)
      - [Borrow Endpoints](#borrow-endpoints)
    - [Request/Response Examples](#requestresponse-examples)
      - [1. Filter books by genre](#1-filter-books-by-genre)
      - [2. Find students who borrowed a book](#2-find-students-who-borrowed-a-book)
      - [3. Borrow a book](#3-borrow-a-book)
  - [Validation Rules](#validation-rules)
  - [Error Handling](#error-handling)
  - [Sample Data](#sample-data)
  - [Running the Application](#running-the-application)
  - [Contributing](#contributing)
  - [License](#license)

## Project Overview

The Library Management System provides RESTful endpoints for:

- Managing books and students
- Filtering books by genre
- Finding students who borrowed a specific book
- Processing book borrowing operations with comprehensive validation
- Handling errors with structured JSON responses

## Features

- **Book Management**: CRUD operations for books
- **Student Management**: CRUD operations for students
- **Book Filtering**: Search books by genre
- **Borrowing System**: Track book borrowing with validation rules
- **Validation**:
  - Ensure student and book exist
  - Limit students to 2 unreturned books
  - Maintain at least one available copy per book
- **Error Handling**: Structured JSON error responses

## Technology Stack

- **Java 17**
- **Spring Boot 3.1**
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**
- **RESTful API Design**

## Setup Instructions

### Prerequisites

- Java 17 JDK
- MySQL 8.0+
- Maven 3.8+

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/library-management-system.git
   cd library-management-system
   ```

2. Create MySQL database:

   ```sql
   CREATE DATABASE library_db;
   ```

3. Update database configuration in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library_db
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Build the application:
   ```bash
   mvn clean package
   ```

## Database Schema

The system uses three main entities:

### Book Entity

```java
@Entity
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int copiesAvailable;
    private String genre;
    private Date publishedDate;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Borrow> borrows;
}
```

### Student Entity

```java
@Entity
public class Student {
    @Id
    private String id;
    private String name;
    private String department;
    private int year;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Borrow> borrows;
}
```

### Borrow Entity

```java
@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Book book;

    private Date borrowDate = new Date();
    private Date returnDate;

    public enum Status {
        NO, YES
    }

    @Enumerated(EnumType.STRING)
    private Status returned = Status.NO;
}
```

## API Documentation

### Base URL

`http://localhost:8080`

### Endpoints

#### Book Endpoints

| Method | Endpoint                    | Description                      |
| ------ | --------------------------- | -------------------------------- |
| GET    | `/books`                    | Get all books                    |
| GET    | `/books?genre={genre}`      | Filter books by genre            |
| GET    | `/books/{bookId}/borrowers` | Get students who borrowed a book |

#### Borrow Endpoints

| Method | Endpoint  | Description   |
| ------ | --------- | ------------- |
| POST   | `/borrow` | Borrow a book |

### Request/Response Examples

#### 1. Filter books by genre

```http
GET /books?genre=Programming
```

**Response:**

```json
[
  {
    "id": "B002",
    "title": "Clean Code",
    "author": "Robert Martin",
    "isbn": "978-0132350884",
    "copiesAvailable": 3,
    "genre": "Programming",
    "publishedDate": "2008-08-01"
  }
]
```

#### 2. Find students who borrowed a book

```http
GET /books/B001/borrowers
```

**Response:**

```json
[
  {
    "id": "S001",
    "name": "John Doe",
    "department": "Computer Science",
    "year": 3
  }
]
```

#### 3. Borrow a book

```http
POST /borrow
Content-Type: application/json

{
  "studentId": "S003",
  "bookId": "B002"
}
```

**Success Response (201 Created):**

```json
{
  "id": 5,
  "student": {
    "id": "S003",
    "name": "Robert Johnson",
    "department": "Mathematics",
    "year": 1
  },
  "book": {
    "id": "B002",
    "title": "Clean Code",
    "author": "Robert Martin",
    "isbn": "978-0132350884",
    "copiesAvailable": 3,
    "genre": "Programming",
    "publishedDate": "2008-08-01"
  },
  "borrowDate": "2023-06-20",
  "returnDate": null,
  "returned": "NO"
}
```

## Validation Rules

1. **Student and Book Validation**:

   - Both studentId and bookId must exist
   - Returns 404 error if not found

2. **Borrowing Limit**:

   - Students can have maximum 2 unreturned books
   - Returns 400 error if limit exceeded

3. **Copy Availability**:
   - At least one copy must remain available after borrowing
   - Calculated as: `availableCopies = book.copiesAvailable - borrowedCopies`
   - Returns 400 error if insufficient copies

## Error Handling

The API returns structured JSON error responses using the `ErrorResponse` class:

```json
{
  "statusCode": 400,
  "errorMessage": "Detailed error message"
}
```

**Common Error Responses:**

- `400 Bad Request`: Validation failures
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Unexpected server errors

## Sample Data

The system initializes with sample data:

```sql
-- Students
INSERT INTO student (id, name, department, year) VALUES
('S001', 'John Doe', 'Computer Science', 3),
('S002', 'Jane Smith', 'Electrical Engineering', 2),
('S003', 'Robert Johnson', 'Mathematics', 1);

-- Books
INSERT INTO book (id, title, author, isbn, copies_available, genre, published_date) VALUES
('B001', 'Introduction to Algorithms', 'Thomas Cormen', '978-0262033848', 5, 'Computer Science', '2009-07-31'),
('B002', 'Clean Code', 'Robert Martin', '978-0132350884', 3, 'Programming', '2008-08-01'),
('B003', 'Design Patterns', 'Erich Gamma', '978-0201633610', 2, 'Software Engineering', '1994-10-21'),
('B004', 'The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 4, 'Fiction', '1925-04-10');

-- Borrow records
INSERT INTO borrow (student_id, book_id, borrow_date, return_date, returned) VALUES
('S001', 'B001', '2023-01-15', NULL, 'NO'),
('S001', 'B002', '2023-02-20', NULL, 'NO'),
('S002', 'B003', '2023-03-10', '2023-04-10', 'YES'),
('S003', 'B004', '2023-05-01', NULL, 'NO');
```

## Running the Application

1. Start MySQL server
2. Update database credentials in `application.properties`
3. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The API will be available at: `http://localhost:8080`

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new feature branch
3. Commit your changes
4. Push to the branch
5. Create a pull request

## License

This project is licensed under the MIT License.
