# Student Registration Portal

A desktop GUI application built in Java (Swing) for managing student profiles, course registrations, and automatic CGPA calculation.

---

## Requirements

To run this application without an `.exe` file, you only need Java installed on your machine:
- **Java Runtime Environment (JRE)** or **Java Development Kit (JDK)** version 8 or higher (Java 17 or Java 21 recommended).
- To verify if Java is installed, open your terminal / command prompt and run:
  ```bash
  java -version
  ```

---

## How to Run the Application

You can run the application using any of the three methods below.

### Method 1: Run the Executable JAR (Easiest)

If you have Java installed:

1. Open your terminal or Command Prompt in the project folder.
2. Run the following command:
   ```bash
   java -jar StudentRegistrationPortal.jar
   ```
3. *Alternative:* On most Windows and macOS systems, you can simply **double-click** `StudentRegistrationPortal.jar` to open the GUI.

---

### Method 2: Compile & Run from Source Code (Command Line)

If you want to compile and run the source code directly:

1. Open terminal or Command Prompt in the project root directory.
2. Compile the `.java` files into an output folder (`out`):
   - **Windows (Command Prompt / PowerShell):**
     ```powershell
     javac -d out Course.java Main.java Person.java Student.java
     ```
   - **macOS / Linux:**
     ```bash
     javac -d out Course.java Main.java Person.java Student.java
     ```
3. Run the application:
   ```bash
   java -cp out assignment1.Main
   ```

*(Optional)* If you want to re-generate the executable `.jar` file after making changes:
```bash
jar cfm StudentRegistrationPortal.jar manifest.txt -C out assignment1
```

---

### Method 3: Run via an IDE (IntelliJ IDEA, Eclipse, or VS Code)

#### Using IntelliJ IDEA:
1. Open IntelliJ IDEA and select **Open** -> choose this project directory.
2. Ensure Project SDK is set to JDK 8+ (**File** -> **Project Structure** -> **Project** -> **SDK**).
3. Ensure the project root is marked as Sources Root with package prefix `assignment1`, or right-click `Main.java` and click **Run 'Main.main()'**.

#### Using Eclipse / VS Code:
1. Import the folder as a Java project.
2. Locate `Main.java`.
3. Right-click and choose **Run As** -> **Java Application**.

---

## Application Features & Walkthrough

1. **Student Registration:**
   - Enter Matric Number, Student Name, and Department.
   - Click **Add Student** to add them to the portal.
   - Includes duplicate check prevention.

2. **Course Registration Dialog:**
   - Select a student from the list and click **View Course Registration**.
   - Add courses with Course Code, Course Title, Credit Units, and Grade dropdown (A, B, C, D, E, F).
   - Double-click table cells to edit course information.
   - Each student must register for at least **5 courses** to fulfill requirements.

3. **CGPA Calculation & Profile Report:**
   - Calculate CGPA directly from the course registration window.
   - Select a student and click **View Student's Details** to view a full formatted academic profile report.

4. **Finalizing Portal:**
   - Click **Finish Students' Course Registration** when all registrations are done.
   - The system checks all students; if anyone has under 5 courses, it alerts you with the list of incomplete students before allowing finalization.

---

## Project Structure

```text
student-registration-app/
├── Course.java                    # Course model (code, title, units, grade points)
├── Person.java                    # Base person class
├── Student.java                   # Student model (CGPA calculation, reports, validation)
├── Main.java                      # GUI implementation (Swing frames, tables, dialogs)
├── manifest.txt                   # Manifest specifying Main-Class for the JAR
├── StudentRegistrationPortal.jar  # Pre-compiled runnable JAR archive
└── README.md                      # Documentation & instructions
```
