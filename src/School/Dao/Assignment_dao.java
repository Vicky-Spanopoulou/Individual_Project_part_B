/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package School.Dao;

import Models.Assignment;
import Models.Course;
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
public class Assignment_dao extends GenericDao {

    //list of all queries
    private static final String FINDALLASSIGNMENTS = "select * from assignments";
    private static final String ASSIGNMENTSPERCOURSE = "select courses.title as 'Course title', "
            + "courses.type as 'Course type', assignments.*\n"
            + "from courses inner join courses_assignments on courses.id = courses_assignments.crs_id\n"
            + "inner join assignments on courses_assignments.assignment_id = assignments.id\n"
            + "where courses.id = ?";

    private static final String STUDENTSASSIGNMENTSPERCOURSE = "select courses.id as 'course_id', "
            + "courses.title as 'Course title', "
            + "courses.type as 'Course type', assignments.*,\n"
            + "students.last_name as 'Student Last Name', students.first_name as 'Student First Name'\n"
            + "from courses \n"
            + "inner join courses_assignments on courses.id = courses_assignments.crs_id\n"
            + "inner join assignments on courses_assignments.assignment_id = assignments.id\n"
            + "inner join students_courses on courses.id = students_courses.courses_id\n"
            + "inner join students on students.id = students_courses.students_id\n"
            + "where students.id = ?";
    
    private static final String INSERT = "insert into assignments "
            + "(title, description, sub_date, oral_mark, total_mark) "
            + "values (?, ?, ?, ?, ?)";
    
    private static final String ASSIGNMENTSCOURSE = "insert into courses_assignments "
            + "(crs_id, assignment_id) "
            + "values (?, ?)";
    
    private static final String FINDBYMAXID = "select * from assignments "
            + "where assignments.id = (select max(assignments.id) from assignments)";

    
    
    //method that returns a list of all assignments from DB
    public ArrayList<Assignment> findAllAssignments() {
        ArrayList<Assignment> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(FINDALLASSIGNMENTS);
            while (rs.next()) {
                int assignmentId = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                Double oralMark = rs.getDouble("oral_mark");
                Double totalMark = rs.getDouble("total_mark");
                LocalDate subDate = rs.getDate("sub_date").toLocalDate();

                Assignment assignment = new Assignment(assignmentId, title, description,
                        subDate.toString(), oralMark, totalMark);
                list.add(assignment);
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong with the assignments!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }
    

    public ArrayList<Assignment> findAssignmentsPerCourse(int id) {
        ArrayList<Assignment> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        try {
            stmt = conn.createStatement();
            pstm = conn.prepareStatement(ASSIGNMENTSPERCOURSE);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                int assignmentId = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                Double oralMark = rs.getDouble("oral_mark");
                Double totalMark = rs.getDouble("total_mark");
                LocalDate subDate = rs.getDate("sub_date").toLocalDate();

                Assignment assignment = new Assignment(assignmentId, title, description,
                        subDate.toString(), oralMark, totalMark);
                list.add(assignment);
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong with the assignments!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }

    
    //method that returns a list of all the studnets assignments per course from DB
    public ArrayList<Assignment> findStudentsAsignmentsPerCourse(int id) {
        ArrayList<Assignment> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        try {
            stmt = conn.createStatement();
            pstm = conn.prepareStatement(STUDENTSASSIGNMENTSPERCOURSE);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                int assignmentId = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDate subDate = rs.getDate("sub_date").toLocalDate();
                Double oralMark = rs.getDouble("oral_mark");
                Double totalMark = rs.getDouble("total_mark");
                Course_dao c = new Course_dao();
                Course course = c.getCourseById(courseId);
                Assignment assignment = new Assignment(assignmentId, title, description,
                        subDate.toString(), oralMark, totalMark, course);
                list.add(assignment);
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong with the Student's Assignments!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }
    
    public void insertAssignment(Assignment assignment) throws ParseException {
        Connection conn = getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(INSERT);
            pstm.setString(1, assignment.getTitle());
            pstm.setString(2, assignment.getDescription());  
            LocalDate subDate = stringToLocalDate(assignment.getSubDate());
            pstm.setDate(3, java.sql.Date.valueOf(subDate));
            pstm.setDouble(4, assignment.getOralMark());
            pstm.setDouble(5, assignment.getTotalMark());
            int result = pstm.executeUpdate();
            if (result == 1) {
                System.out.println("Assignment successfully created!!");
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong! Assignment's not created");
        } 
        finally {
            closeConnections(pstm, conn);
        }
    }
    
    

    public void insertAssignmentsCourse(int courseId, int assignmentId){
        Connection conn = getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(ASSIGNMENTSCOURSE);
            pstm.setInt(1, courseId);
            pstm.setInt(2, assignmentId);
            int result = pstm.executeUpdate();
            if (result == 1) {
                System.out.println("Assignment successfully added to course!!");
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong! Assignment's not added to course");
        } 
        finally {
            closeConnections(pstm, conn);
        }
    }
    
    
    
     public Assignment findMaxId(){         
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Assignment assignment = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(FINDBYMAXID);
            rs.next();
            int assignmentId = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            Double oralMark = rs.getDouble("oral_mark");
            Double totalMark = rs.getDouble("total_mark");
            LocalDate subDate = rs.getDate("sub_date").toLocalDate();

            assignment = new Assignment(assignmentId, title, description,
                        subDate.toString(), oralMark, totalMark);
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong, trainer was not added to"
                    + " the course!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return assignment;
     }
    
    
    

}//end of class Assignment_dao
