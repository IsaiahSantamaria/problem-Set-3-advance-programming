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
    String name;
    String feedback;
    Map<Integer,GradePair> questionPoints;

    /**
     * initialize name with parameter input,
     * questinoPoints with new HashMap object
     * and also feedback with an empty String
     * @param name 
     */
    public Assignment(String name){

        this.name = name;
        this.questionPoints = new HashMap<Integer, GradePair>();
        this.feedback = "";

    }

    /**
     * adds questions to the assignment and stores its
     * question number, points earned and the total points
     * of the question
     * @param questionNumber 
     * @param pointsEarned
     * @param totalPoints
     * 
     */
    public void addQuestion(int questionNumber, int pointsEarned, int totalPoints){
        //System.out.println("was able to go through loop");
        GradePair points = new GradePair(pointsEarned, totalPoints);
        questionPoints.put(questionNumber, points);
        
        
    }

    /**
     * calculates all earnedPoints out of total points of the assignment
     * in a percentage value
     * @return grade
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
     * replace this.feedback with string parameter feedback input
     * @param changes the this.feedback value to feedback parameter
     */
    public void giveFeedback(String feedback){
        this.feedback = feedback;

    }
    /**
     * returns the name
     * @return name
     */
    public String getName(){
        return name;

    }

    /**
     * Returns the output
     * @return output
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