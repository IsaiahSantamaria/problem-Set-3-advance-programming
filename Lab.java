import java.util.HashMap;
import java.util.Map;
/**
 *
 * Lab class extends the assignment class, containing all 
 * of its properity along with additional data called  checkpoints 
 * to see if a section has been completed or not
 * 
 * @author Isaiah Santamaria 
 * @version 3/14/2026
 */

public class Lab extends Assignment{

    private Map<Integer,Boolean> checkPoints;


    /**
     * intialize name with assignment constructor and also 
     * intilize checkPoints as a HasMap<Integer,Boolean>()
     * @param name - the name of the lab
     * 
     */
    public Lab(String name){
        super(name);
        this.checkPoints = new HashMap<Integer, Boolean>();
    }

    /**
     * adds checkpointNumber to the checkpoints varaible 
     * and indicates if it is complete or not
     * @param checkpointNumber - the number of the checkpoint
     * @param isComplete - true if the checkpoint is complete, false otherwise
     */
    public void addCheckPoint(int checkpointNumber, boolean isComplete){
        checkPoints.put(checkpointNumber,isComplete);
    }


    /**
     * returns the output which decalres it's
     * lab assignment and its data values
     * @return a string representation of the Lab object
     */
    @Override
    public String toString(){
    String strCheckPoints = "";
    for (Map.Entry<Integer, Boolean> entry : checkPoints.entrySet()){
        Integer key = entry.getKey();
        Boolean value = entry.getValue();
        strCheckPoints += "Checkpoint " + key + ": " + (value ? "Complete" : "Incomplete");
    }          
        String output = "Lab " + super.toString() + strCheckPoints;
        return output;
    }

}