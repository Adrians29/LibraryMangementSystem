# Library Management System

## Author: Adrian Eduardo Guzman Loya 2538954
**Course:** Object-Oriented Programming  
**Language:** Java  

---

# Overview

The Library Management System is a Java application designed to manage users such as Students, Teachers, Admins and library items such as books, DVDs, and magazines.

The system apply the different concepts OOP including:

- Inheritance
- Polymorphism
- Abstract classes
- Interfaces
- Exception handling
- File I/O
- CSV persistence
- Java Collections
- Streams API
- Recursion
- Generics

The application allows users to:

- Borrow items
- Return items
- Search items
- Generate reports
- Load and export CSV data
- Sort users and items

---

# Features

## User Management

The system supports three types of users:

- Student
- Teacher
- Admin

Each user has:

- Unique ID
- Name
- Borrowed items list

### Borrow Limits

| User Type | Borrow Limit  |
|-----------|---------------|
|  Student  | 5 books only  |
|  Teacher  | 10 items      |
|  Admin    | Cannot borrow |

---

# Library Item Management

The system supports three types of items:

## Book

Attributes:
- ID
- Title
- Status
- ISBN
- Author
- Genre

## DVD

Attributes:
- ID
- Title
- Status
- Director
- Duration

## Magazine

Attributes:
- ID
- Title
- Status
- Publisher
- Issue Number

---

# Item Status

The system uses an enum called `ItemStatus`.

Possible values:

```java
BORROWED
IN_STORE
LOST
```

---

# Collections Used

| Collection |         Usage        |
|------------|----------------------|
| ArrayList  | Store items          |
| HashMap    | Store users          |
| Queue      | Waiting list         |
| Stack      | Transaction history  |
| Set        | Unique search titles |

---

# CSV

The application loads and exports data using CSV files:
- items.csv
- users.csv


---

# Demo users.csv

```csv
Teacher,T0001,Jose
Admin,A0001,George
Student,S0001,Juan
```

---

# Demo items.csv

```csv
I0001,Python for Beginners,IN_STORE,1234567890123,Kevin Bruce,Education
I0002,Project Hail Mary,IN_STORE,2345678909876,Andy Weir,Fiction
I0003,Batman Begins,BORROWED,Christopher Nolan,140
I0004,National Geographic,IN_STORE,Polo,9
```

---

# Search Functionalities

The system includes:

- Stream search
- Recursive search
- Search by author

---
