
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads data from the file "data.csv" and prints
 * the courses with students who have assignments.
 *
 * @author Prof. White
 * @version Spring 2026
 */
public class Driver {

    /**
     * Reads data from the file "data.csv" and prints the required information.
     *
     * @param args No command line arguments are used.
     */
    public static void main(String[] args) {
        Student currentStudent = null;
        Course currentCourse = null;
        Assignment currentAssignment = null;

        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        Scanner inFile = null;

        try {
            inFile = new Scanner(new File("dataTwo.csv"));
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }   

        while (inFile.hasNextLine()) {
            String[] data = inFile.nextLine().split(",");
            
            switch (data[0]) {
                case "Course":
                    currentCourse = new Course(data[1], data[2], data[3]);

                    courses.add(currentCourse);
                    break;
                case "Student":
                    
                    //intializing the student
                    currentStudent = new Student(data[1], data[2]);
                    //for loop to get rest of the assignment data from the file line
                    for(int i = 3; i < data.length; i ++ ){
                        //assignData contains assignments data
                        String[] assignData = data[i].split(" ");
                        if(assignData[0].equals("Lab")){
                            currentAssignment = new Lab(assignData[1]);
                            //adding questions and points
                            //splitting question number, earned points and total points
                            String [] points = assignData[2].split("-");
                            for(int j = 0; j < points.length; j = j + 3){
                                currentAssignment.addQuestion(Integer.parseInt(points[j]), Integer.parseInt(points[j+1]), Integer.parseInt(points[j+2]));
                            }
                            //setting up checkpoints
                            String [] checkPoints = assignData[3].split("-");
                            //adding it to lab object so it can take checkPoints
                            Lab labAssignment = (Lab) currentAssignment;
                            for(int j = 0; j < checkPoints.length; j = j + 2){
                                labAssignment.addCheckPoint(Integer.parseInt(checkPoints[j]), checkPoints[j+1].equals("1"));
                            }
                            //reassigning labAssignment back to current Assignment
                            currentAssignment = labAssignment;
                        }
                        else if(assignData[0].equals("Quest")){
                            currentAssignment = new Quest(assignData[1]);
                            //adding questions and points
                            //splitting question number, earned points and total points
                            String [] points = assignData[2].split("-");
                            for(int j = 0; j < points.length; j = j + 3){
                                currentAssignment.addQuestion(Integer.parseInt(points[j]), Integer.parseInt(points[j+1]), Integer.parseInt(points[j+2]));
                            }
                            
                        }
                        //assigning feed back based on students calculated grade thress hold
                        if(95.0<= currentAssignment.calculateGrade() ){
                            currentAssignment.giveFeedback("Great Job!");
                        }else if(80.0 <= currentAssignment.calculateGrade()){
                            currentAssignment.giveFeedback("good job!");
                        }else if(70 < currentAssignment.calculateGrade()){
                            currentAssignment.giveFeedback("acceptable grade, make sure to study a bit more! ");
                        }else if(60 < currentAssignment.calculateGrade()){
                            currentAssignment.giveFeedback("please consider coming to office hours to talk about your grade");
                        }else if(0 <= currentAssignment.calculateGrade()){
                            currentAssignment.giveFeedback("have you been paying attention to lectures!");
                        }else if(currentAssignment.calculateGrade()< 0){
                            currentAssignment.giveFeedback("how's that even possible");
                        }

                        //System.out.println(assignData[0]);

                        currentStudent.addAssignment(currentAssignment);
                    }
                    
                    

                    //currentAssignment.addQuestion((int)assignData[0], (int)assignData[1],(int)assignData[2]);

                    if(!students.contains(currentStudent)) {
                        students.add(currentStudent);
                        
                    }   
                    currentCourse.addStudent(currentStudent);
                    break;
                default:
                    System.out.println("Unknown type: " + data[0]); 
            }

        }
        inFile.close();
        
        
        


        System.out.println("Courses with Students who have Assignments: ");
        for (Course course : courses) {
            System.out.println(course);
            System.out.println("-----------------------------------------------");
        }

      
    }
}
