/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package School.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


/**
 *
 * @author vicky
 */
public class GenericDao {
    
    //queries
    private final String url = "jdbc:mysql://localhost:3306/spanopoulouVicky";
    private final String user = "root";
    private final String password = "root";
    
    
    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("Oops! Something went wrong with the connection!");
        }
        return connection;
    }
    

    protected void closeConnections(ResultSet rs, Statement stmt, Connection conn) {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Oops! Something went wrong with the connection!");
        }
    }
    

    protected void closeConnections(PreparedStatement pstm, Connection conn) {
        try {
            pstm.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Oops! Something went wrong with the connection!");
        }
    }
    
    
    
    //takes a string as an input and turns it into local date  
    public static LocalDate stringToLocalDate(String date) throws ParseException {
        Date dateD = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        LocalDate dateLD = dateD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return dateLD;
    }
    
    

    
}//end of class GenericDao
