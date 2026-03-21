/**
 * Concentration class for problem 2 in problem set 3
 * @author Isaiah Santamaria
 * @version 3/21/2026
 * 
 */
public class Concentration {
    public static void main(String []args){
        Box matrix = new Box();
        System.out.print(matrix);

    }
    
}

class Box {
    protected Integer [][] intMatrix;
    protected boolean [][] booleanMatrix;

    public Box(){
        intMatrix = new Integer[6][6];
        booleanMatrix = new boolean[6][6];
    }
    /**
     * this method fills the row and col of the intMatrix 
     * once a intMatrix is filled it will tell turn boolean
     * Matrix to true according to intMatrix location
     * @param row
     * @param col
     * @param val
     */
    public void fill(int val,  int row, int col){
        intMatrix[row][col] = val;
        booleanMatrix[row][col] = true;
    
    }

    /**
     * returns if booleanMatrix[row][col] is empty or not
     * @param row
     * @param col
     * @return returns if booleanMatrix[row][col] is empty or not
     */
    public boolean isEmpty(int row, int col){
        return !booleanMatrix[row][col];
    }

    /**
     * method restarts both intMatrix and booleanMatrix
     */
    public void restart(){
        booleanMatrix = new boolean[6][6];
        intMatrix = new Integer[6][6];
    }

    @Override
    public String toString(){
        String output ="";
        for(int i = 0; i < 6; i ++){
            for(int j = 0; j < 6; j ++){
                output = output + intMatrix[j][i] + "," 
                + booleanMatrix[j][i] +  " ";
            }
            output = output + "\n";
        }
        return output;
    }

}
