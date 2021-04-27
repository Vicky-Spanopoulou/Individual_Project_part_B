/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import School.Dao.Student_dao;
import java.util.ArrayList;

/**
 *
 * @author vicky
 */
public class StudentsPerCourse {
    

   
   //prints an arraylist of students per course
   public static void printStudentsPerCourse(int id) {
       Student_dao s = new Student_dao();
       ArrayList<Student> studentList = s.findStudentsPerCourse(id);
       int listIndex = 1;
       System.out.println("\nStudent's list:\n");
       for (Student student : studentList) {
           System.out.println(listIndex + ". " + student.getFirstName() + " " 
                   + student.getLastName() + " - Date of birth: " + student.getDateOfBirth());
           listIndex++;
       }
       if (s.findStudentsPerCourse(id).size() <1){
           System.out.println("There are no students for this course!");
       }
       
   }

   
    
    
}//end of class
