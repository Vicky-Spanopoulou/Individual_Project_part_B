/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import School.Dao.Assignment_dao;
import School.Dao.Student_dao;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author vicky
 * @since March 2021
 */
public class Student {
    
    private int studentId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String tuitionFees;
    private Course course;
    
    

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(String tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public Student(int studentId, String firstName, String lastName, String dateOfBirth, String tuitionFees, Course course) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
        this.course = course;
    }
 
    public Student(int studentId, String firstName, String lastName, String dateOfBirth, String tuitionFees) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }
    
    public Student(String firstName, String lastName, String dateOfBirth, String tuitionFees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }


    //prints a list of all the students
    public static void listAllStudents() {
        int listIndex = 1;
        Student_dao s = new Student_dao();
        ArrayList<Student> studentList = s.findAllStudents();
        System.out.println("Student List:");
        for (Student student : studentList) {
            System.out.println(listIndex + ". " + student);
            listIndex++;
        }
    }
        
    
    //prints a list of all student's assignments per course
    public static void listAllStudentsAssignments(int sId) {
        Assignment_dao assignment = new Assignment_dao();
        System.out.println("Assignments:");
            int listIndex = 1;
            for (Assignment a : assignment.findStudentsAsignmentsPerCourse(sId)) {  
                System.out.println(listIndex + ". " + a + "\n   Course: " 
                        + a.getCourse().getTitle()
                        + " - " + a.getCourse().getStream()
                        + " - " + a.getCourse().getType());
                listIndex++;
            }
            if(assignment.findStudentsAsignmentsPerCourse(sId).size() < 1){
                System.out.println("This student has no assignments yet!");
            } 
        }
    
    
    //prints a list of students who have more than one courses
    public static void listStudentsWithMoreCourses() {
        Student_dao s = new Student_dao();
        int listIndex = 1;
        for (Student student : s.findStudentsWithManyCourses()) {
                System.out.println(listIndex + ". " + student.getFirstName() 
                        + " " + student.getLastName());
                listIndex++;
        }
        if (s.findStudentsWithManyCourses().size() < 1){
            System.out.println("There are no students with multiple courses.");
        }
    }
    

    
    
    //*------------------------METHOD TOOLS----------------------------------*
    
    
    //checks if the date given is in the same week as the assignment's date
    public static boolean isInSameWeek(LocalDate date, LocalDate assignmentDate) {
        Locale locale = Locale.getDefault();
        int givenWeekOfYear = date.get(WeekFields.ISO.of(locale).weekOfYear());
        int givenWeekOfAssignment = assignmentDate.get(WeekFields.ISO.of(locale).weekOfYear());
        return givenWeekOfYear == givenWeekOfAssignment;
    }
        
  
    @Override
    public String toString() {
        return firstName + " " + lastName + " - Date of birth: " 
                + dateOfBirth + " - Tuition Fees: " + tuitionFees;
    }

    
    
}//end of class student
