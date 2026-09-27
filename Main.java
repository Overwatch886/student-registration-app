package assignment1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Main {

    public static void main(String args[]) {
        JFrame frame = new JFrame("Student Registration Portal");
        openMainView(frame);

    }
    public static void openCourseRegistrationDialog(JFrame frame, Student student){

        if (student == null) {
            JOptionPane.showMessageDialog(frame, "Please select a student from the list first!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }


        JDialog courseView = new JDialog(frame,  student.name +" - "+ student.getStudentId() +" Course Registration", true);
        courseView.setSize(520, 420);
        courseView.setLocationRelativeTo(frame);
        // Defining Coulmn Names
        String[] columnNames = {"Course Code", "Course Title", "Credit Units", "Grade"};
        DefaultTableModel courseTableModel = new DefaultTableModel(columnNames, 0);
        // Label Informing User to Double Click to Edit
        JLabel hintLabel = new JLabel("Double Click A Cell to Edit It");
        hintLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        courseView.add(hintLabel, BorderLayout.NORTH);
        // Creating Course Table
        JTable courseTable = new JTable(courseTableModel);
        courseTable.setShowGrid(true);
        courseTable.setGridColor(Color.LIGHT_GRAY);
        courseTable.setRowHeight(25);
        // Changing 5he Grade Colummn to Using Combo Boxes
        String grades[] = {"A", "B", "C", "D", "E", "F"};
        JComboBox<String> gradeDropdown = new JComboBox<>(grades);

        courseTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(gradeDropdown));
        courseView.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        // Adding Control Buttons to add, remove courses save courses and close the pane
        JButton addCourse = new JButton("Add Course");
        addCourse.addActionListener(ev -> {
//            Course newCourse = new Course(code, title, cu, grade);
//            student.courses.add(newCourse);
              courseTableModel.addRow(new Object[]{"", "", "", "A"});

        });

        JButton removeCourse = new JButton("Remove Course");
        removeCourse.addActionListener(evv -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                //student.courses.remove(selectedRow);
                courseTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(courseView, "Please Select the Course to Remove", "Make a Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        JButton saveButton = new JButton("Save and Close");
        saveButton.addActionListener(ev -> {
            if (courseTable.isEditing()) {
                courseTable.getCellEditor().stopCellEditing();
            }
            ArrayList<Course>updatedCourses =new ArrayList<>();

            // Loop through every row in the table
            for (int i = 0; i < courseTableModel.getRowCount(); i++) {
                Object codeObj = courseTableModel.getValueAt(i, 0);
                Object titleObj = courseTableModel.getValueAt(i, 1);
                Object cuObj = courseTableModel.getValueAt(i, 2);
                Object gradeObj = courseTableModel.getValueAt(i, 3);

                String code = (codeObj != null) ? codeObj.toString().trim() : "";
                String title = (titleObj != null) ? titleObj.toString().trim() : "";
                String cuStr = (cuObj != null) ? cuObj.toString().trim() : "";
                String grade = (gradeObj != null) ? gradeObj.toString().trim() : "";

                if (code.isEmpty() && title.isEmpty() && cuStr.isEmpty()) {
                    continue;
                }
                if (code.isEmpty() || title.isEmpty() || cuStr.isEmpty() || grade.isEmpty()) {
                    JOptionPane.showMessageDialog(courseView, "Row " + (i + 1)+ " has missing fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int cu = Integer.parseInt(cuStr);
                    Course course = new Course(code, title, cu, grade);
                    updatedCourses.add(course);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(courseView, "Credit Units in row " + (i + 1) + " must be a number!", "Invalid Input Type", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(courseView, "Grade Value in row " + (i + 1) + " must be a A, B, C, D, E or F!", "Invalid Input Type", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        student.courses = updatedCourses;
        if (student.validateCourseRegistration(frame)) {// Validates minimum courses
            JOptionPane.showMessageDialog(courseView, "Courses Saved Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            courseView.dispose();
            frame.repaint();
        }
                });
        JButton calculateCGPA = new JButton();
        calculateCGPA.addActionListener(e ->{
            double cgpa = student.calculateCGPA(courseView);
            JOptionPane.showMessageDialog(courseView, student.name+ " with matric number "+ student.getStudentId()+" has a CGPA of "+ cgpa, "CGPA Summary", JOptionPane.INFORMATION_MESSAGE);
        });
        // Adding the Buttons to the BottomPanel

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(addCourse);
        bottomPanel.add(removeCourse);
        bottomPanel.add(saveButton);
        bottomPanel.add(calculateCGPA);

        courseView.add(bottomPanel, BorderLayout.SOUTH);

        for (Course course: student.courses){
            Object[] row = {
                    course.getCourseCode(),
                    course.getCourseTitle(),
                    course.getCreditUnit(),
                    course.getGrade()};
            courseTableModel.addRow(row);
        }
        courseView.setVisible(true);
    }
    // The Main Page Layout
    public static void openMainView(JFrame frame){
        // Creating Course Registrationn View View JTable and Card Layout

        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //TextBoxes
        JTextField idInput = new JTextField(15);
        JTextField nameInput = new JTextField(15);
        JTextField departmentInput = new JTextField(15);

        //Storing Students
        ArrayList<Student> studentsList = new ArrayList<>();

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2));

        // Creating Buttons
        JButton clearFormButton = new JButton("Clear Form Entries");
        clearFormButton.addActionListener(ev ->{
            idInput.setText("");
            nameInput.setText("");
            departmentInput.setText("");
        });
        JButton addStudentButton = new JButton("Add Student");
        JButton viewStudentsInfo = new JButton("View Student's Details");
        JButton viewCourseRegistration = new JButton("View Course Registration");
        JButton finishCourseRegistration = new JButton("Finish Students' Course Registration");
        ArrayList<String> incompleteStudents = new ArrayList<>();
        finishCourseRegistration.addActionListener(ev -> {
            for (Student s : studentsList) {
                // true indicates that this is the check to finalize the registrations
                if (!s.hasCompletedCourseRegistration()) {
                    incompleteStudents.add(s.name + " (" + s.getStudentId() + ") - only " + s.courses.size() + " courses");
                }
            }
                if (!incompleteStudents.isEmpty()) {
                    String message = "Cannot finalize registration!\nThe following students have fewer than 5 courses:\n\n"
                            + String.join("\n", incompleteStudents);
                    JOptionPane.showMessageDialog(frame, message, "Incomplete Registrations", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(frame,
                            "All " + studentsList.size() + " students have successfully completed course registration!\nClosing Portal.",
                            "Portal Finalized",
                            JOptionPane.INFORMATION_MESSAGE);

                    frame.dispose(); // Closes the window
                    System.exit(0);
                }
            });

        viewCourseRegistration.setEnabled(false);
        viewStudentsInfo.setEnabled(false);

        // Adding Buttons to Button Panel
        JPanel buttonSlot = new JPanel(new FlowLayout());
        buttonSlot.add(clearFormButton);
        buttonSlot.add(addStudentButton);
        buttonSlot.add(viewStudentsInfo);
        buttonSlot.add(viewCourseRegistration);
        buttonSlot.add(finishCourseRegistration);


        // Labels
        JLabel idInputLabel = new JLabel("Enter you Matric Number");
        JLabel nameInputLabel = new JLabel("Enter you Name ");
        JLabel departmentInputLabel = new JLabel("Enter your department ");
        // Centering the Labels
        idInputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameInputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        departmentInputLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // List of Students
        DefaultListModel<Student> studentListModel = new DefaultListModel<>();
        JList<Student> studentListView = new JList<>(studentListModel);
        JScrollPane scrollPane = new JScrollPane(studentListView);
        scrollPane.setPreferredSize(new Dimension(250, 0));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Students"));
        // Adding Elements to the window
        // Adding Labels and Textboxes
        formPanel.add(idInputLabel);
        formPanel.add(idInput);

        formPanel.add(nameInputLabel);
        formPanel.add(nameInput);

        formPanel.add(departmentInputLabel);
        formPanel.add(departmentInput);

        //Add Student List
        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonSlot, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.EAST);

        // Creating 3 students objcts to pre-exists in our database
        Student israel = new Student("250398", "Israel Olawuyi", "Computer Science");
        Student emmanuel = new Student("250559", "Sunday Emmanuel", "Mathematics");
        Student favour = new Student("250645", "Favour Adetunji", "Physics");
        // Adding 3 pre-existing students to the array list
        studentsList.add(israel);
        studentsList.add(emmanuel);
        studentsList.add(favour);
        // Adding 3 pre-existing students to the JList List Model
        studentListModel.addElement(israel);
        studentListModel.addElement(emmanuel);
        studentListModel.addElement(favour);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        addStudentButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String matricInput = idInput.getText().trim();
                String name = nameInput.getText().trim();
                String department = departmentInput.getText().trim();
                Student student = new Student(matricInput, name, department);
                if (matricInput.isEmpty() || name.isEmpty() || department.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please Fill In All Fields", "Empty Fields", JOptionPane.WARNING_MESSAGE);
                    return;

                }
                else if (studentsList.contains(student)){
                    JOptionPane.showMessageDialog(frame, "A Student With This Matric Number Already Exists in the Database", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else {

                    studentsList.add(student);
                    studentListModel.addElement(student);
                    JOptionPane.showMessageDialog(frame, "Student Added Successfully", "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
                    openCourseRegistrationDialog(frame, student);
                }



            }
        });

        viewStudentsInfo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Student selectedStudent = studentListView.getSelectedValue();
                if (selectedStudent == null) {
                    JOptionPane.showMessageDialog(frame, "Please select a student from the list first!", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JDialog studentInfoView = new JDialog(frame, "Student Profile - "+ selectedStudent.name, true);
                studentInfoView.setSize(520, 420);
                studentInfoView.setLocationRelativeTo(frame);
                JTextArea textArea = new JTextArea(selectedStudent.generateProfileReport(frame));
                textArea.setEditable(false);
                textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                studentInfoView.add(new JScrollPane(textArea), BorderLayout.CENTER);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(ev -> studentInfoView.dispose());

                JPanel bottomPanel = new JPanel();
                bottomPanel.add(closeButton);

                studentInfoView.add(bottomPanel, BorderLayout.SOUTH);
                studentInfoView.setVisible(true);


            }
        });
        viewCourseRegistration.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Student selectedStudent = studentListView.getSelectedValue();
                if (selectedStudent == null) {
                    JOptionPane.showMessageDialog(frame, "Please select a student from the list first!", "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                openCourseRegistrationDialog(frame, selectedStudent);


            }
        });
        studentListView.addListSelectionListener( e -> {
            if (!e.getValueIsAdjusting()) {
                Student selectedStudent = studentListView.getSelectedValue();

                if (selectedStudent != null) {
                    viewCourseRegistration.setEnabled(true);
                    viewStudentsInfo.setEnabled(true);
                }
                else{
                    viewCourseRegistration.setEnabled(false);
                    viewStudentsInfo.setEnabled(false);
                }
            }
        });
        studentListView.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Student) {
                    Student s = (Student) value;
                    if (s.courses.size() >= 5) {
                        c.setForeground(new Color(0, 120, 0)); // Dark Green / Blue (Complete)
                        setText(s.name + " (" + s.getStudentId() + ") - Complete");
                    } else {
                        c.setForeground(Color.RED); // Red (Incomplete)
                        setText(s.name + " (" + s.getStudentId() + ") - " + s.courses.size() + "/5 Courses");
                    }
                }
                return c;
            }
        });
    }

}
