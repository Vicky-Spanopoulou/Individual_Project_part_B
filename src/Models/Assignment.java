/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import School.Dao.Assignment_dao;
import java.util.ArrayList;

/**
 *
 * @author vicky
 * @since March 2021
 */
public class Assignment {
    
    private int assignmentId;
    private String title;
    private String description;
    private String subDate;
    private double oralMark;
    private double totalMark;
    private Course course;
    


    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubDate() {
        return subDate;
    }

    public void setSubDate(String subDate) {
        this.subDate = subDate;
    }

    public double getOralMark() {
        return oralMark;
    }

    public void setOralMark(double oralMark) {
        this.oralMark = oralMark;
    }

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double totalMark) {
        this.totalMark = totalMark;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    


    public Assignment(int assignmentId, String title, String description, 
            String subDate, double oralMark, double totalMark) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.subDate = subDate;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
    }
    

    public Assignment(String title, String description, String subDateTime, 
            double oralMark, double totalMark) {
        this.title = title;
        this.description = description;
        this.subDate = subDateTime;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
    }

    public Assignment(int assignmentId, String title, String description, 
            String subDate, double oralMark, double totalMark, Course course) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.subDate = subDate;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
        this.course = course;
    }
    
    

    
    //brings all the assignments
    public static void listAllAssignments() {
        Assignment_dao a = new Assignment_dao();
        int listIndex = 1;
        System.out.println("Assignment List:");
        for(Assignment assignment : a.findAllAssignments()) {
            System.out.println(listIndex + ". " + assignment);
            listIndex++;
        } 
    }
       

    @Override
    public String toString() {
        return title + ": " + description + " - Submit until: " + subDate;
    }
    
    
    
    
    
}//end of class assignment
