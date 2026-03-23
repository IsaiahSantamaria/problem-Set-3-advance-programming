/**
 * Interface for gradeable items.
 * @author Isaiah Santamaria
 * @version 3/23/2026
 */
interface Gradeable{

    /**
     * Calculates the grade for the gradeable item.
     * @return the calculated grade as an integer
     */
    double calculateGrade();

    /**
     * Mechanism for allowing feedback for the gradeable item.
     * @param feedback - a string containing feedback for the gradeable item
     */
    void giveFeedback(String feedback);

}