/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package School.Dao;

import Models.Trainer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;


/**
 *
 * @author vicky
 */
public class Trainer_dao extends GenericDao{
    
    //list of all queries
    private static final String FINDALLTRAINERS = "SELECT * FROM trainers";
    private static final String TRAINERSPERCOURSE = "select courses.title as 'Course title',"
            + " courses.type as 'Course type', trainers.*" +
            "from courses "+
            "inner join courses_trainers on courses.id = courses_trainers.course_id\n" +
            "inner join trainers on courses_trainers.trainer_id = trainers.id "
            + "where courses.id = ?";
    
    private static final String INSERT = "insert into trainers "
            + "(first_name, last_name, subject) values (?, ?, ?)";
    
    private static final String TRAINERSCOURSE = "insert into courses_trainers "
            + "(course_id, trainer_id) values (?, ?)";
    
    private static final String FINDBYMAXID = "select * from trainers "
            + "where trainers.id = (select max(trainers.id) from trainers)";


    
    //method that returns a list of all trainers from DB
    public  ArrayList<Trainer> findAllTrainers() {
        ArrayList<Trainer> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(FINDALLTRAINERS);
            while (rs.next()) {
                int trainerId = rs.getInt("id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String subject = rs.getString("subject");

                Trainer trainer = new Trainer(trainerId, fname, lname, subject);
                list.add(trainer);
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong with the Trainers!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }
    
    
    
     public ArrayList<Trainer> findTrainersPerCourse(int id) {
        ArrayList<Trainer> list = new ArrayList();
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        
        try {
            stmt = conn.createStatement();            
            pstm = conn.prepareStatement(TRAINERSPERCOURSE);
            pstm.setInt(1, id);           
            rs = pstm.executeQuery();

            while (rs.next()) {
                int trainerId = rs.getInt("id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String subject = rs.getString("subject");  
                
                Trainer trainer = new Trainer(trainerId, fname, lname, subject);
                list.add(trainer);
            }
        }
        catch (SQLException ex) {
            System.out.println("Something went wrong with the Trainers!");
        }
        finally {
            closeConnections(rs, stmt, conn);
        }
        return list;
    }
    
        
     
    public void insertTrainer(Trainer trainer) throws ParseException {
        Connection conn = getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(INSERT);
            pstm.setString(1, trainer.getFirstName());
            pstm.setString(2, trainer.getLastName());
            pstm.setString(3, trainer.getSubject());
            int result = pstm.executeUpdate();
            if (result == 1) {
                System.out.println("Trainer successfully created!!");
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong! Trainer's not created");
        } 
        finally {
            closeConnections(pstm, conn);
        }
    }
    
     
     public void insertTrainersCourse(int courseId, int trainerId){
        Connection conn = getConnection();
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(TRAINERSCOURSE);
            pstm.setInt(1, courseId);
            pstm.setInt(2, trainerId);
            int result = pstm.executeUpdate();
            if (result == 1) {
                System.out.println("Trainer successfully added to course!!");
            }
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong! Trainer's not added to course");
        } 
        finally {
            closeConnections(pstm, conn);
        }
    }

     
     public Trainer findMaxId(){         
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Trainer trainer = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(FINDBYMAXID);
            rs.next();
            int trainerId = rs.getInt("id");
            String fname = rs.getString("first_name");
            String lname = rs.getString("last_name");
            String subject = rs.getString("subject");
            trainer = new Trainer(trainerId, fname, lname, subject);
        } 
        catch (SQLException ex) {
            System.out.println("Something went wrong, trainer was not added to"
                    + " the course!");
        } 
        finally {
            closeConnections(rs, stmt, conn);
        }
        return trainer;
     }
     
     

    
    
}//end of class Trainer_dao
