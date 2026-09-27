package assignment1;
public class Course{

    private String courseCode;
    private String courseTitle;
    private int creditUnit;
    private String grade;

    Course(String courseCode, String courseTitle, int creditUnit, String grade){
        boolean validGrade = validateGrade(grade);
        if (validGrade == true){
            this.courseCode=courseCode;
            this.courseTitle = courseTitle;
            this.creditUnit = creditUnit;
            this.grade = grade;
        }
        else{
            throw new IllegalArgumentException("Invalid Grade");
        }

    }
    public boolean validateGrade(String grade){
        if (grade.equals("A")||grade.equals("B")||grade.equals("C")||grade.equals("D")||grade.equals("E")||grade.equals("F")){
            return true;
        }
        else{
            return false;
        }
    }
    public int getGradePoint(){
        int gradePoint = -1;
        switch (grade){
            case "A":
                gradePoint = 5;
                break;
            case "B":
                gradePoint = 4;
                break;
            case "C":
                gradePoint = 3;
                break;
            case "D":
                gradePoint = 2;
                break;
            case "E":
                gradePoint = 1;
                break;
            case "F":
                gradePoint = 0;
                break;
            default:
                throw new IllegalArgumentException("Invalid Grade");
        }
        return gradePoint;
    }
    public int getCreditUnit(){
        return creditUnit;
    }
    public String getCourseCode(){
        return courseCode;
    }
    public String getCourseTitle(){
        return courseTitle;
    }
    public String getGrade(){
        return grade;
    }
}