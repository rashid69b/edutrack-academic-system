# EduTrack – Student Academic Management System

A command-line Java application developed as an Object-Oriented Programming course project.

## What the project does

EduTrack provides a small academic management system through a terminal interface. It supports:

- Student registration and validation
- Theory and laboratory course management
- Course enrollment
- Duplicate-enrollment and capacity checks
- Semester credit-limit validation
- Marks and grade calculation
- Credit-weighted CGPA calculation
- Student transcript generation
- Basic academic analytics
- Persistent storage using text files
- Automated tests without external libraries

## Technology

- Java SE
- JDK 17 or newer
- `javac` and `java`
- File I/O
- Git/GitHub

No Maven, Gradle, database server, or third-party JAR is required.

## Project structure

```text
edutrack-original/
├── src/
│   └── com/edutrack/
│       ├── EduTrackApp.java
│       ├── model/
│       ├── exception/
│       ├── repository/
│       ├── service/
│       └── test/
├── data/
├── bin/
├── run.bat
├── run.sh
├── test.bat
├── statement.md
└── README.md
```

## Requirements

Install JDK 17 or newer and verify:

```bash
java -version
javac -version
```

## Run on Windows

Open Command Prompt or PowerShell in the project folder:

```cmd
run.bat
```

## Run on Linux/macOS

```bash
chmod +x run.sh
./run.sh
```

## Manual compilation

```bash
javac -d bin src/com/edutrack/model/*.java src/com/edutrack/exception/*.java src/com/edutrack/repository/*.java src/com/edutrack/service/*.java src/com/edutrack/test/*.java src/com/edutrack/EduTrackApp.java
```

Run:

```bash
java -cp bin com.edutrack.EduTrackApp
```

## Run tests

Windows:

```cmd
test.bat
```

Linux/macOS:

```bash
./run.sh test
```

Or directly:

```bash
java -cp bin com.edutrack.test.EduTrackTestSuite
```

## Data files

The application stores data in:

```text
data/students.txt
data/courses.txt
data/enrollments.txt
```

These files are intentionally simple text files so the application can run without a database.

## Important

Before academic submission, replace any placeholder student information with your own information and test the project yourself. Keep only features and claims that you can demonstrate.
