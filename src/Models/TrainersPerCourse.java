/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import School.Dao.Trainer_dao;
import java.util.ArrayList;

/**
 *
 * @author vicky
 */
public class TrainersPerCourse {
    
        
    //prints an Arraylist of trainers per course    
    public static void printTrainersPerCourse(int id) {
        Trainer_dao t = new Trainer_dao();
        ArrayList<Trainer> trainersList = t.findTrainersPerCourse(id);
        int listIndex = 1;
        for (Trainer trainer : trainersList) {
            System.out.println(listIndex + ". " + trainer);
            listIndex++;
        }
        if (t.findTrainersPerCourse(id).size() < 1){
            System.out.println("There are no trainers for this course!");
        }
       
   }
    
    

    

    
    
}//end of class
