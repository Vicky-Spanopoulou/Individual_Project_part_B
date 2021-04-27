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
 * @since March 2021
 */
public class Trainer {
    
    private int trainerId;
    private String firstName;
    private String lastName;
    private String subject;
    


    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public Trainer(int trainerId, String firstName, String lastName, String subject) {
        this.trainerId = trainerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }
    
    public Trainer(String firstName, String lastName, String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }
    
    
    //prints a list of all trainers
    public static void listAllTrainers() {
        Trainer_dao t = new Trainer_dao();
        ArrayList<Trainer> trainersList = t.findAllTrainers();
        int listIndex = 1;
        System.out.println("Trainer List:");
        for(Trainer trainer : trainersList) {
            System.out.println(listIndex + ". " + trainer);
            listIndex++;
        } 
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - Subject: " + subject;
    }
    
    
    
}//end of class trainer
