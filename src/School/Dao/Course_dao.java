/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package School.Dao;

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
public class Course_dao extends GenericDao{
    
    //list of all queries
    private static final String FINDALLCOURSES = "select * from courses";
    private static final String FINDBYID = "select * from courses WHERE id = ?";
    private static final String INSERT = "insert into courses "
            + "(title, stream, type, start_date, end_date) "
            + "values (?, ?, ?, ?, ?)";
    

    
    //method that returns a list of all courses from DB
    public  ArrayList<Course> findAllCourses() {
        ArrayList<Course> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();  
            rs = stmt.executeQuery(FINDALLCOURSES);
            
            while (rs.next()) {
                int courseId = rs.getInt("id");
                String title = rs.getString("title");
                String stream = rs.getString("stream");
                String type = rs.getString("type");
                LocalDate sDate = rs.getDate("start_date").toLocalDate();
                LocalDate eDate = rs.getDate("end_date").toLocalDate();
                
                Course course = new Course(courseId, title, stream, type, 
                        sDate.toString(), eDate.toString());
                list.add(course);
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong with the Courses!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }
    
    
    
    public Course getCourseById(int id) {
        Connection conn = getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Course course = null;
        try {
            pstm = conn.prepareStatement(FINDBYID);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            rs.next();
            int courseId = rs.getInt("id");
                String title = rs.getString("title");
                String stream = rs.getString("stream");
                String type = rs.getString("type");
                LocalDate sDate = rs.getDate("start_date").toLocalDate();
                LocalDate eDate = rs.getDate("end_date").toLocalDate();
                
                course = new Course(courseId, title, stream, type, 
                        sDate.toString(), eDate.toString());
           
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong with the Courses!");
        } 
        finally {
            closeConnections(rs, pstm, conn);
        }
        return course;
    }

    
    public void insertCourse(Course course) throws ParseException {
        Connection conn = getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(INSERT);
            pstm.setString(1, course.getTitle());
            pstm.setString(2, course.getStream());
            pstm.setString(3, course.getType());
            LocalDate startDate = stringToLocalDate(course.getStart_date());
            pstm.setDate(4, java.sql.Date.valueOf(startDate));
            LocalDate endDate = stringToLocalDate(course.getEnd_date());
            pstm.setDate(5, java.sql.Date.valueOf(endDate)); 
            int result = pstm.executeUpdate();
            if (result == 1) {
                System.out.println("Course successfully created!!");
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong! Course's not created");
        } 
        finally {
            closeConnections(pstm, conn);
        }
    }
    

    
    
}//end of class Course_dao
