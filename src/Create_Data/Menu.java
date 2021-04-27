/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Create_Data;

import static Create_Data.Create_Data.ANSI_CYAN;
import static Create_Data.Create_Data.ANSI_RED;
import static Create_Data.Create_Data.ANSI_RESET;
import static Create_Data.Create_Data.checkNumbersRange;
import Models.Assignment;
import Models.AssignmentsPerCourse;
import Models.Course;
import Models.Student;
import Models.StudentsPerCourse;
import Models.Trainer;
import Models.TrainersPerCourse;
import School.Dao.Course_dao;
import School.Dao.Student_dao;
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 * @author vicky
 */
public class Menu {
    
    
    
    //*--------------------------- PROGRAM MENU -------------------------------*
        
    //exits the create new data returning to the main menu
    public static boolean exitMenu(String exitInput, String objName) {
        Scanner scan = new Scanner(System.in);
        System.out.println(ANSI_CYAN + "\nDo you want to add more" + objName + "?" + ANSI_RESET);
        System.out.println("Press: Yes to continue or No to exit");

        return yesOrNoMenu();
    }

    //shows a menu of program's choices
    public static void programMenu() throws ParseException {
        DateValidatorUsingDateFormat validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");
        Scanner scanner = new Scanner(System.in);
        listOfProgramChoices();
        
        outerLoop:
        while(true) {
            String inputChoiceNumber = scanner.next();

            switch (inputChoiceNumber) {
                case "1":
                    Student.listAllStudents();
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose a "
                            + "number from the list" + Create_Data.ANSI_RESET);
                    break;
                case "2":
                    Trainer.listAllTrainers();
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose a "
                            + "number from the list" + Create_Data.ANSI_RESET);                   
                    break;
                case "3":
                    Assignment.listAllAssignments();
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose a "
                            + "number from the list" + Create_Data.ANSI_RESET);
                    break;
                case "4":
                    Course.listAllCourses();
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose a"
                            + " number from the list" + Create_Data.ANSI_RESET);
                    break;
                case "5":
                    System.out.println("Choose a number from list:");
                    Course.listAllCourses();
                    Course_dao c = new Course_dao();
                    int choiceNumber = checkNumbersRange(1, c.findAllCourses().size());
                    StudentsPerCourse.printStudentsPerCourse(choiceNumber);
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose "
                            + "a number from the list" + Create_Data.ANSI_RESET);
                    break;
                case "6":
                    System.out.println("Choose a number from list:");
                    Course.listAllCourses();
                    c = new Course_dao();
                    choiceNumber = checkNumbersRange(1, c.findAllCourses().size());
                    TrainersPerCourse.printTrainersPerCourse(choiceNumber);
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose "
                            + "a number from the list" + Create_Data.ANSI_RESET);
                    break;
                case "7":
                    System.out.println("Choose a number from list:");
                    Course.listAllCourses();
                    c = new Course_dao();
                    choiceNumber = checkNumbersRange(1, c.findAllCourses().size());
                    AssignmentsPerCourse.printAssignmentsPerCourse(choiceNumber);
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose a"
                            + " number from the list" + Create_Data.ANSI_RESET);
                    break;
                case "8":
                    System.out.println("Choose a number from list:");
                    Student.listAllStudents();
                    Student_dao s = new Student_dao();
                    choiceNumber = checkNumbersRange(1, s.findAllStudents().size());
                    Student.listAllStudentsAssignments(choiceNumber);
                    
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose a "
                            + "number from the list" + Create_Data.ANSI_RESET);
                    break;
                case "9":
                    System.out.println("The students who have more than one course are:");
                    Student.listStudentsWithMoreCourses();
                    System.out.println(Create_Data.ANSI_CYAN + "\nTo see more choose a "
                            + "number from the list" + Create_Data.ANSI_RESET);
                    break;
                case "0":
                    break outerLoop;
                default:
                    System.out.println(ANSI_RED + "Wrong input" + ANSI_RESET);
            }
        }
    }

    //lists all the programm's output choices
    public static void listOfProgramChoices() {
        System.out.println("1. A list of all the students");
        System.out.println("2. A list of all the trainers");
        System.out.println("3. A list of all the assignments");
        System.out.println("4. A list of all the courses");
        System.out.println("5. All the students per course");
        System.out.println("6. All the trainers per course");
        System.out.println("7. All the assignments per course");
        System.out.println("8. All the assignments per student");
        System.out.println("9. A list of students that belong to\n   "
                + "more than one courses");
//        System.out.println("10. Students who need to submit an assignment");
        System.out.println("0 . ----------Go Back----------");
    }
    
        //welcome menu
    public static void welcomeMenu() {
        System.out.println(ANSI_CYAN + "*-------- Welcome --------*" + ANSI_RESET);
        System.out.println("Press:");
        System.out.println(" 1.   To add your own data");
        System.out.println(" 2.   To use synthetic data");
        System.out.println("Exit. To stop the program");
    }

    //menu to create new data
    public static void createUserData() throws ParseException {
        Create_Data data = new Create_Data();
        Scanner scan = new Scanner(System.in);
        boolean exit = true;
        while (exit) {
            System.out.println(ANSI_CYAN + "Choose a number from list:" + ANSI_RESET);
            System.out.println("1. Create a new Course");
            System.out.println("2. Create a new Trainer");
            System.out.println("3. Create a new Student");
            System.out.println("4. Create a new Assignment");
            String userInput = scan.next();
            switch (userInput) {                
                case "1":
                    data.createCourse();
                    break;
                case "2":
                    data.createTrainer();
                    break;
                case "3":
                    data.createStudent();
                    break;
                case "4":
                    data.createAssignment();
                    break;
                default:
                    System.out.println(ANSI_RED + "Wrong input, try again" + ANSI_RESET);
                    continue;
            }
            System.out.println(ANSI_CYAN + "\nDo you want to add more?" + ANSI_RESET);
            System.out.println("Press Yes to continue or No to exit");

            exit = yesOrNoMenu();
        }
    }
    
    //yes or no menu for the user to choose if he wants to continue or exit
    public static boolean yesOrNoMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = true;
        String inputChoice = scanner.next();
        
        while ((!inputChoice.equalsIgnoreCase("yes")) && (!inputChoice.equalsIgnoreCase("no"))) {
            System.out.println(ANSI_RED + "Wrong input, please try again" + ANSI_RESET);
            inputChoice = scanner.next();
        }
        if (inputChoice.equalsIgnoreCase("yes")) {
            exit = true;
        } else if (inputChoice.equalsIgnoreCase("no")) {
            exit = false;
        }
        return exit;
    }
    
    
    
    
    
    
    
}//end of lass Menu
