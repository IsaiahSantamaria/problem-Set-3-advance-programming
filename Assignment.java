import java.util.HashMap;
import java.util.Map;
/**
 * Assignment class represents a the assignment data,
 * like points earned, lost, feedback and its name
 *
 * @author Isaiah Santamaria 
 * @version 3/14/2026
 */
public abstract class Assignment implements Gradeable {
    private String name;
    private String feedback;
    private Map<Integer,GradePair> questionPoints;

    /**
     * Constructs an Assignment object with the specified name.
     * @param name 
     */
    public Assignment(String name){

        this.name = name;
        this.questionPoints = new HashMap<Integer, GradePair>();
        this.feedback = "";

    }

    /**
     * Adds a question to the assignment with the specified question
     * number, points earned, and total possible points.
     * @param questionNumber - the number of the question 
     * @param pointsEarned - the points earned for the question
     * @param totalPoints - the total possible points for the question
     */
    public void addQuestion(int questionNumber, int pointsEarned, int totalPoints){
        //System.out.println("was able to go through loop");
        GradePair points = new GradePair(pointsEarned, totalPoints);
        questionPoints.put(questionNumber, points);
        
        
    }

    /**
     * Calculates the grade for the assignment based 
     * on the points earned and total possible points.
     * @return grade - the calculated grade as an integer percentage
     */
    public double calculateGrade(){
        if(questionPoints == null) return 0.0;
        double totalGrade = 0.0;
        double earnedGrade = 0.0;
        for(Map.Entry<Integer,GradePair> entry : questionPoints.entrySet()){
            GradePair value = entry.getValue();
            earnedGrade+= value.getPointsEarned();
            totalGrade += value.getTotalPoints();
        }
        double grade = (earnedGrade/totalGrade)*100;

        return grade;

    }

    /**
     * Mechanism for allowing feedback for the assignment.
     * @param feedback - a string containing feedback for the assignment
     */
    public void giveFeedback(String feedback){
        this.feedback = feedback;

    }
    /**
     * Returns the name of the assignment.
     * @return the name of the assignment
     */
    public String getName(){
        return name;

    }

    /**
     * Returns a string representation of the assignment, 
     * including its name, calculated grade, and feedback.
     * @return the calculated grade as an integer percentage
     */
    @Override
    public String toString(){
        String output = "Name: " + 
        name +  ", Grade: " + 
        calculateGrade() + ", Feedback: "
         + feedback;

        return output;
    }

}