/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Create_Data;

import Models.Assignment;
import Models.Course;
import Models.Student;
import Models.Trainer;
import School.Dao.Assignment_dao;
import School.Dao.Course_dao;
import School.Dao.Student_dao;
import School.Dao.Trainer_dao;
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 * @author vicky
 */
public class Create_Data {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";


    //creates new Student by user input
    public void createStudent() throws ParseException {
        String inputFirstName;
        String inputLastName;
        String inputDateOfBirth;
        String inputTuitionFees;
        String exitInput = "";
        boolean exitLoop = true;
        Scanner scanner = new Scanner(System.in);
        DateValidatorUsingDateFormat validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");

        while (exitLoop) {

            System.out.println(ANSI_CYAN + "Enter the student's details:" + ANSI_RESET);

            System.out.println("First name:");
            inputFirstName = stringValidation(scanner);

            System.out.println("Last name:");
            inputLastName = stringValidation(scanner);

            System.out.println("Date of birth DD/MM/YYYY:");
            inputDateOfBirth = scanner.next();
            while (!validator.isValid(inputDateOfBirth)) {
                System.out.println(ANSI_RED + "Please give a valid date format: DD/MM/YYYY" + ANSI_RESET);
                inputDateOfBirth = scanner.next();
                validator.isValid(inputDateOfBirth);
            }
            System.out.println("Tuition fees:");
            inputTuitionFees = Integer.toString(giveOnlyNumbers());

            Student student = new Student(inputFirstName, inputLastName,
                    inputDateOfBirth, inputTuitionFees);
            Student_dao st = new Student_dao();
            
            //insert student to database
            st.insertStudent(student);
            
            boolean exit = true;
            while (exit) {
                System.out.println("Course:");
                
                //get the id from the last student we created
                int lastStudentId = st.findMaxId().getStudentId();
                 
                //add a course to this student
                st.insertStudentsCourse(lastStudentId, usersCourseChoice());
                
                System.out.println(ANSI_CYAN + "\nDo you want to add this student to another course?" + ANSI_RESET);
                System.out.println("Press Yes to add more or No to continue");
                exit = Menu.yesOrNoMenu();
            }

            System.out.println(ANSI_CYAN + "\nWhat do you want to see?" + ANSI_RESET);
            Menu.programMenu();

            exitLoop = Menu.exitMenu(exitInput, " students");

        }//end of while loop

    }//end of createStudent
    

    //creates new Trainer by user input
    public void createTrainer() throws ParseException {
        String inputFirstName;
        String inputLastName;
        String inputSubject;
        String exitInput = "";
        boolean exitLoop = true;
        Scanner scanner = new Scanner(System.in);

        while (exitLoop) {

            System.out.println(ANSI_CYAN + "Enter the trainer's details" + ANSI_RESET);

            System.out.println("First name:");
            inputFirstName = stringValidation(scanner);

            System.out.println("Last name:");
            inputLastName = stringValidation(scanner);

            System.out.println("Subject?");
            inputSubject = stringValidation(scanner);
            
            //create a new trainer
            Trainer trainer = new Trainer(inputFirstName, inputLastName, inputSubject);
            
            //insert trainer to database
            Trainer_dao tr = new Trainer_dao();
            tr.insertTrainer(trainer);
            
            boolean exit = true;
            while (exit) {
                System.out.println("Course:");

                //get the id from the last trainer we created
                int lastTrainerId = tr.findMaxId().getTrainerId();

                //add a course to this trainer
                tr.insertTrainersCourse(usersCourseChoice(), lastTrainerId);
                
                System.out.println(ANSI_CYAN + "\nDo you want to add this student to another course?" + ANSI_RESET);
                System.out.println("Press Yes to add more or No to continue");
                exit = Menu.yesOrNoMenu();
            }
            
            System.out.println(ANSI_CYAN + "What do you want to see?" + ANSI_RESET);
            Menu.programMenu();

            exitLoop = Menu.exitMenu(exitInput, " trainers");

        }//end of while loop

    }//end of createTrainer

    
    
    
    //creates new Course by user input
    public void createCourse() throws ParseException {
        String inputTitle;
        String inputStream;
        String inputType;
        String start_date;
        String end_date;
        String exitInput = "";
        boolean exitLoop = true;
        Scanner scanner = new Scanner(System.in);
        DateValidatorUsingDateFormat validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");

        while (exitLoop) {

            System.out.println(ANSI_CYAN + "Enter the course's details:" + ANSI_RESET);

            System.out.println("Title:");
            inputTitle = scanner.nextLine();

            System.out.println("Stream:");
            inputStream = scanner.nextLine();

            System.out.println("Type:");
            inputType = scanner.nextLine();

            System.out.println("Start date DD/MM/YYYY:");
            start_date = scanner.next();
            while (!validator.isValid(start_date)) {
                System.out.println(ANSI_RED + "Please give a valid date format: "
                        + "DD/MM/YYYY" + ANSI_RESET);
                start_date = scanner.next();
                validator.isValid(start_date);
            }

            System.out.println("End date DD/MM/YYYY:");
            end_date = scanner.next();
            while (!validator.isValid(end_date)) {
                System.out.println(ANSI_RED + "Please give a valid date format: "
                        + "DD/MM/YYYY" + ANSI_RESET);
                end_date = scanner.next();
                validator.isValid(end_date);
            }

            //create a new course and add it to the list
            Course course = new Course(inputTitle, inputStream, inputType,
                    start_date, end_date);
            
            //add the course to the database
            Course_dao c = new Course_dao();
            c.insertCourse(course);

            System.out.println(ANSI_CYAN + "What do you want to see?" + ANSI_RESET);
            Menu.programMenu();

            exitLoop = Menu.exitMenu(exitInput, " courses");

        }//end of while loop

    }//end of createCourse

    
    
    
    //creates new Assignment by user input
    public void createAssignment() throws ParseException {
        String inputTitle;
        String inputDescription;
        String inputSubDateTime;
        double inputOralMark;
        double inputTotallMark;
        String exitInput = "";
        boolean exitLoop = true;
        Scanner scanner = new Scanner(System.in);
        DateValidatorUsingDateFormat validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");

        outerLoop:
        while (exitLoop) {

            System.out.println(ANSI_CYAN + "Enter assignment's details:" + ANSI_RESET);

            System.out.println("Title:");
            inputTitle = scanner.nextLine();

            System.out.println("Description:");
            inputDescription = scanner.nextLine();

            System.out.println("Submission date:");
            inputSubDateTime = scanner.nextLine();

            while (!validator.isValid(inputSubDateTime)) {
                System.out.println(ANSI_RED + "Please give a valid date format: "
                        + "DD/MM/YYYY" + ANSI_RESET);
                inputSubDateTime = scanner.nextLine();
                validator.isValid(inputSubDateTime);
            }

            System.out.println("Oral mark:");
            inputOralMark = giveOnlyNumbers();
            

            System.out.println("Total mark:");
            inputTotallMark = giveOnlyNumbers();
            
            //create a new assignment
            Assignment assignment = new Assignment(inputTitle, inputDescription,
                    inputSubDateTime, inputOralMark, inputTotallMark);
            //add assignment to the database
            Assignment_dao a = new Assignment_dao();
            a.insertAssignment(assignment);
            
            boolean exit = true;
            while (exit) {
                System.out.println("Course?");
                        
                //get the id from the assignment created above
                int lastAssignmentId = a.findMaxId().getAssignmentId();

                //add assignment to a course
                a.insertAssignmentsCourse(usersCourseChoice(), lastAssignmentId);
                
                System.out.println(ANSI_CYAN + "\nDo you want to add this student to another course?" + ANSI_RESET);
                System.out.println("Press Yes to add more or No to continue");
                exit = Menu.yesOrNoMenu();
            }
            
            System.out.println(ANSI_CYAN + "What do you want to see?" + ANSI_RESET);
            Menu.programMenu();

            exitLoop = Menu.exitMenu(exitInput, " assignments");

        }//end of while loop

    }//end of createAssignment
    
    
    
    //*-------------------------- METHOD TOOLS --------------------------------*
   
    //checks if a string contains only digits
    public static int giveOnlyNumbers() {
        Scanner scanner = new Scanner(System.in);
        int resultNum;
        while (true) {
            try {
                String input = scanner.next();
                int number = Integer.parseInt(input);
                resultNum = number;
                break;
            }
            catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Please give only numbers" + ANSI_RESET);
            }
        }
    return resultNum;
    }
    
    //checks if the number given is in range
    public static int checkNumbersRange(int min, int listMax) {
        int num = giveOnlyNumbers();
        while (num < min || num > listMax) {
            System.out.println(ANSI_RED + "Please give a valid number" + ANSI_RESET);
            num = giveOnlyNumbers();
        }
        return num;
    }   

    //checks if a string contains only letters
    public boolean checkIfLetters(String str) {
        return (str.matches("[a-zA-Z]+"));
    }

    //checks if the string given contains only letters
    public String stringValidation(Scanner scan) {
        String userInput = scan.nextLine();
        while (!checkIfLetters(userInput)) {
            System.out.println(ANSI_RED + "Input is invalid, type only letters" + ANSI_RESET);
            userInput = scan.nextLine();
        }
        return userInput;
    }

    public int usersCourseChoice() {
        //list all courses
        Course_dao course = new Course_dao();
        Course.listAllCourses();
        int inputCourseId = checkNumbersRange(1, course.findAllCourses().size());
        return inputCourseId;
    }
    
                   

    
}//end of class create_data


