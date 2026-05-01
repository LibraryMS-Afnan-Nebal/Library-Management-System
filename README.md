# Library Management System

A Java-based Library Management System that manages users, librarians, admins, media items, borrowing, returns, fines, reminders, and membership-based fine discounts.

---

## Features

### Account Management
- Create user accounts
- Log in and log out
- Change username
- Change password
- Manage different roles:
  - Admin
  - Librarian
  - User

### Media Management
- Add books
- Add CDs
- Search books by:
  - Title
  - Author
  - ISBN
- Search CDs by:
  - Title
  - Artist
- Track media availability
- Support multiple copies of the same media item

### Borrowing and Returning
- Borrow available books or CDs
- Return borrowed media
- Prevent borrowing unavailable items
- Prevent users with unpaid fines from borrowing
- Prevent users with overdue items from borrowing more media

### Fine System
- Automatically calculate overdue fines
- Books have a 28-day loan period
- CDs have a 7-day loan period
- Books use a daily fine rate of 10 NIS
- CDs use a daily fine rate of 20 NIS
- Users can pay fines
- Overdue reports can be generated

### Membership System
The system promotes users based on borrowing history:

- Regular member: default tier
- Silver member: after 5 borrowed items
- Gold member: after 15 borrowed items

Membership affects fine calculation through different fine strategies.

### Reminder System
- Sends reminder messages for overdue items
- Includes email notification support
- Uses environment variables for email credentials

### Testing and Documentation
- Unit tests are included under `src/test/java`
- Javadoc documentation is included in the `DOC/` folder
- Maven is used for building and testing
- JaCoCo and SonarCloud configuration are included in `pom.xml`

---

## Tech Stack

- Java 17
- Maven
- JUnit 5
- Mockito
- Jakarta Mail
- Java Dotenv
- JaCoCo
- SonarCloud

---
## 🧩 Design Highlights

- **Strategy Pattern** for fine calculation (`Regular`, `Silver`, `Gold`)
- **Role-based design** (`Admin`, `Librarian`, `User`)
- **Manager classes** (Singleton-style) to centralize operations
- **Separation of concerns**: accounts, media, loans, fines, notifications

---
## Project Structure

```text
Library-Management-System/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Main.java
│   │       ├── Role.java
│   │       ├── Admin.java
│   │       ├── Librarian.java
│   │       ├── User.java
│   │       ├── AccountManager.java
│   │       ├── AdminManager.java
│   │       ├── LibrarianManager.java
│   │       ├── UserManager.java
│   │       ├── Media.java
│   │       ├── Book.java
│   │       ├── CD.java
│   │       ├── MediaManager.java
│   │       ├── BookManager.java
│   │       ├── CDManager.java
│   │       ├── Loan.java
│   │       ├── LoanManager.java
│   │       ├── FineCalculator.java
│   │       ├── FineStrategy.java
│   │       ├── RegularFineStrategy.java
│   │       ├── SilverFineStrategy.java
│   │       ├── GoldFineStrategy.java
│   │       ├── ReminderService.java
│   │       ├── EmailService.java
│   │       ├── EmailNotifier.java
│   │       ├── EmailSendException.java
│   │       └── UserMessage.java
│   │
│   └── test/
│       └── java/
│           └── unit tests
│
├── DOC/
│   └── generated Javadoc documentation
│
├── pom.xml
├── .env
├── .gitignore
└── README.md
