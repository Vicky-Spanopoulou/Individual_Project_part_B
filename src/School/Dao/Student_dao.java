/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package School.Dao;

import Models.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 *
 * @author vicky
 */
public class Student_dao extends GenericDao{
    
        
    //list of all queries
    private static final String FINDALLSTUDENTS = "SELECT * FROM students";
    private static final String STUDENTSPERCOURSE = "select courses.id, courses.title as 'Course title', "
        + "courses.type as 'Course type', students.*"
        + "from courses inner join students_courses "
        + "on courses.id = students_courses.courses_id\n" +
        "inner join students on students_courses.students_id = students.id\n" +
        "where courses.id = ?";
    
    private static final String MULTIPLECOURSES = "select students_courses.students_id as 'student_id', "
            + "students.*, count(students_courses.courses_id) as 'Number of courses'\n" 
            + "from students_courses "
            + "inner join students on students_courses.students_id = students.id\n"
            + "group by students_id\n"
            + "having count(students_courses.courses_id) > 1";
    
    private static final String INSERT = "insert into students (first_name, "
            + "last_name, tuition_fees, date_of_birth) values (?, ?, ?, ?)";
    
    private static final String STUDENTSCOURSE = "insert into students_courses "
            + "(students_id, courses_id) values (?, ?)";
    
    private static final String FINDBYMAXID = "select * from students "
            + "where students.id = (select max(students.id) from students)";
    
    

    
    //method that returns a list of all studnets from DB
    public ArrayList<Student> findAllStudents() {
        ArrayList<Student> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(FINDALLSTUDENTS);
            while (rs.next()) {
                int studentId = rs.getInt("id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                Double tuitionFees = rs.getDouble("tuition_fees");
                LocalDate birthDate = rs.getDate("date_of_birth").toLocalDate();
                Student student = new Student(studentId, fname, lname,
                        birthDate.toString(),tuitionFees.toString());
                list.add(student);
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong with the Students!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }
    
    
    //method that returns a list of all the studnets per course from DB
     public ArrayList<Student> findStudentsPerCourse(int id) {
        ArrayList<Student> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        try {
            stmt = conn.createStatement();            
            pstm = conn.prepareStatement(STUDENTSPERCOURSE);
            pstm.setInt(1, id);           
            rs = pstm.executeQuery();
            while (rs.next()) {
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                Double tuitionFees = rs.getDouble("tuition_fees");
                LocalDate birthDate = rs.getDate("date_of_birth").toLocalDate();
                
                Student student = new Student(fname, lname, birthDate.toString(), 
                    tuitionFees.toString());
                list.add(student);
            }
        }
        catch (SQLException ex) {
            System.out.println("Something went wrong with the Students!");
        }
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }
    
    
   //method that returns a list of all the studnets who have more than one courses from DB
   public  ArrayList<Student> findStudentsWithManyCourses() {
        ArrayList<Student> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(MULTIPLECOURSES);
            while (rs.next()) {
                int studentId = rs.getInt("id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                Double tuitionFees = rs.getDouble("tuition_fees");
                LocalDate birthDate = rs.getDate("date_of_birth").toLocalDate();
                Student student = new Student(studentId, fname, lname,
                        birthDate.toString(),tuitionFees.toString());
                list.add(student);
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong with the Students!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }
    
    
     public void insertStudent(Student student) throws ParseException {
        Connection conn = getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(INSERT);
            pstm.setString(1, student.getFirstName());
            pstm.setString(2, student.getLastName());
            Double tuitionFees = Double.valueOf(student.getTuitionFees());
            pstm.setDouble(3, tuitionFees);
            LocalDate dateOfBirth = stringToLocalDate(student.getDateOfBirth());
            pstm.setDate(4, java.sql.Date.valueOf(dateOfBirth));
            int result = pstm.executeUpdate();
            if (result == 1) {
                System.out.println("Student successfully created!!");
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong! Student's not created");
        } 
        finally {
            closeConnections(pstm, conn);
        }
    }
    
     public void insertStudentsCourse(int studentId, int courseId){
        Connection conn = getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(STUDENTSCOURSE);
            pstm.setInt(1, studentId);
            pstm.setInt(2, courseId);
            int result = pstm.executeUpdate();
            if (result == 1) {
                System.out.println("Student successfully added to course!!");
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong! This student already"
                    + "exists in this course");
        } 
        finally {
            closeConnections(pstm, conn);
        }
    }

     
     public Student findMaxId(){         
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Student student = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(FINDBYMAXID);
            rs.next();
            int studentId = rs.getInt("id");
            String fname = rs.getString("first_name");
            String lname = rs.getString("last_name");
            Double tuitionFees = rs.getDouble("tuition_fees");
            LocalDate birthDate = rs.getDate("date_of_birth").toLocalDate();
            student = new Student(studentId, fname, lname,
                    birthDate.toString(),tuitionFees.toString());
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong, student was not added to"
                    + " the course!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return student;
     }


    
    
}//end of class Student_dao
