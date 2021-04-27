/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import School.Dao.Course_dao;
import java.util.ArrayList;

/**
 *
 * @author vicky
 * @since March 2021
 */
public class Course {

    private int courseId;
    private String title;
    private String stream;
    private String type;
    private String start_date;
    private String end_date;
    

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    

    public Course(int courseId, String title, String stream, String type, String start_date, String end_date) {
        this.courseId = courseId;
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    
    
    public Course(String title, String stream, String type, String start_date,
            String end_date) {

        this.title = title;
        this.stream = stream;   
        this.type = type;       
        this.start_date = start_date;
        this.end_date = end_date;
    }



    //prints a list of all the assignments
    public static void listAllCourses() {
        Course_dao c = new Course_dao();
        ArrayList<Course> courseList = c.findAllCourses();
        int listIndex = 1;
        System.out.print("Course List:\n");
        for (Course course : courseList) {
            System.out.println(listIndex + ". " + course);
            listIndex++;
        }
    }

    @Override
    public String toString() {
        return title + ":" + "\n" + stream + " - " + type + " - Start date: " 
                + start_date + " - End date: " + end_date;
    }

        
    
}//end of class course
