/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import School.Dao.Assignment_dao;

/**
 *
 * @author vicky
 */
public class AssignmentsPerCourse {


    //prints the Arraylist of the assignments per course
    public static void printAssignmentsPerCourse(int id) {
        Assignment_dao a = new Assignment_dao();
        int listIndex = 1;
        for (Assignment assignment : a.findAssignmentsPerCourse(id)){
            System.out.println(listIndex + ". " + assignment.getTitle() + ": "
                    + assignment.getDescription() + " - Submission date: "
                    + assignment.getSubDate());
            listIndex++;
        }
        if (a.findAssignmentsPerCourse(id).size() < 1){
            System.out.println("There are no assignments for this course!");
        }

    }

}//end of class
