package assignment1;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Component;
public class Student extends Person {
    private String studentId; // instance variable
    String name;// instance variable
    private String department;
    private int totalCreditUnits;

    Student(String studentId, String name, String department) {

        this.studentId = studentId;
        this.name = name;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public String setDepartment(String newDepartment) {
        department = newDepartment;
        return department;
    }
//Is finalizing check if the course validating is the final verifiation before closing the portal
//(the finish students' registration button
    public boolean validateCourseRegistration(Component parentFrame) {
        if (!hasCompletedCourseRegistration()) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Warning: This student has only registered for " + courses.size() + " of 5 minimum required courses.",
                    "Incomplete Course Registration",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            //System.out.println("Your Course Registration was Successful");
           return true;

        }
    }

    ArrayList<Course> courses = new ArrayList<>();


    public boolean hasCompletedCourseRegistration() {
        return this.courses.size() >= 5; // <--- The ONLY place in the entire codebase where 5 is defined!
    }
    public double calculateCGPA(Component parentFrame) {
        validateCourseRegistration(parentFrame);
        int twgp = 0;
        this.totalCreditUnits = 0;

        for (Course course : courses) {
            try {
                int wgp = course.getCreditUnit() * course.getGradePoint();//wgp means weighted grade points
                this.totalCreditUnits += course.getCreditUnit();
                twgp += wgp;//twgp means total weight grade points
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid Grade", course.getCourseCode() + "has an invalid grade assigned to it", JOptionPane.WARNING_MESSAGE);
                break;
            }
        }
        double cgpa = (double) twgp / this.totalCreditUnits;

        return cgpa;


    }

    public String getStudentId() {
        return studentId;
    }

    public String generateProfileReport(Component parentFrame) {
        StringBuilder sb = new StringBuilder();
        sb.append("===============================================\n");
        sb.append("UNIVERSITY OF IBADAN - FACULTY OF COMPUTING\n");
        sb.append("STUDENT PROFILE\n");
        sb.append("===============================================\n");
        sb.append(String.format("%-20s : %s\n", "Student ID", studentId));
        sb.append(String.format("%-20s : %s\n", "Name", name));
        sb.append(String.format("%-20s : %s\n", "Department", department));
        sb.append(String.format("%-20s : %d\n", "Courses Registered", courses.size()));
        sb.append("---------------------------------------------------------------\n");
        sb.append(String.format("%-12s %-25s %-5s\s %s\n", "Course Code", "Course Title", "CU", "Grade"));
        sb.append("---------------------------------------------------------------\n");
        for (Course course : courses) {
            sb.append(String.format("%-12s %-25s %-5d %s\n",
                    course.getCourseCode(),
                    course.getCourseTitle(),
                    course.getCreditUnit(),
                    course.getGrade()));
        }
        sb.append("---------------------------------------------------------------\n");
        double result = calculateCGPA(parentFrame);
        sb.append(String.format("%-20s : %d\n", "Total Credit Units", this.totalCreditUnits));
        sb.append(String.format("%-20s : %.2f\n", "CGPA", result));
        sb.append("===============================================\n");

        return sb.toString();

    }

    @Override
    public String toString() {
        return name + " (" + studentId + ") ";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Student other = (Student) obj;
        return this.studentId.equalsIgnoreCase(other.studentId);

    }
}



