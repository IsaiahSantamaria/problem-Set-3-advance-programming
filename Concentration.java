/**
 * Concentration class for problem 2 in problem set 3
 * @author Isaiah Santamaria
 * @version 3/21/2026
 * 
 */
public class Concentration {
    public static void main(String []args){
        Matrix matrix = new Matrix();
        System.out.println(matrix + "\n");
        matrix.start();
        System.out.println(matrix);

    }
    
}

class Matrix {
    private Integer [][] intMatrix;
    private boolean [][] booleanMatrix;
    private boolean [][] completedMatrix;
    private Integer [] values;

    public Matrix(){
        intMatrix = new Integer[6][6];
        booleanMatrix = new boolean[6][6];
        completedMatrix = new boolean[6][6];
        values = new Integer[18];
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
     * returns value of the matrix row and column
     * @param row
     * @param col
     * @return value of the matrix row and column
     */
    public Integer getVal(int row, int col){

        return intMatrix[row][col];
    }

    /**
     * method restarts both intMatrix and booleanMatrix
     */
    public void restart(){
        booleanMatrix = new boolean[6][6];
        intMatrix = new Integer[6][6];
        values = new Integer[18];
    }
    /**
     * method starts the game by assigning all values to t
     * intMatrix
     */
    public void start(){
        int ranRowOne = (int)(Math.random() * 6); //random row coordinate
        int ranColOne = (int)(Math.random() * 6); //random col coordinate
        int ranRowTwo = (int)(Math.random() * 6); //random row coordinate
        int ranColTwo = (int)(Math.random() * 6); //random col coordinate
        setValues();
        //randomly assigning 2 coordinates of int matrix
        for(int i = 0; i < values.length; i ++){
            //ensure that no matrix is overwritten
            //does this loop into it finds a matrix where it can put a new val
            while((ranRowOne == ranRowTwo && ranColOne == ranColTwo) || !isEmpty(ranRowOne,ranColOne) || !isEmpty(ranRowTwo, ranColTwo)){
                if(ranRowOne == ranRowTwo && ranColOne == ranColTwo){
                    //System.out.println("loop 1 is repeated");
                    ranRowOne = (int)(Math.random() * 6); //random row coordinate
                    ranColOne = (int)(Math.random() * 6); //random col coordinate
                    ranRowTwo = (int)(Math.random() * 6); //random row coordinate
                    ranColTwo = (int)(Math.random() * 6); //random col coordinate      
                }else if(!isEmpty(ranRowOne,ranColOne)){
                  //  System.out.println("loop 2 is repeated");
                    ranRowOne = (int)(Math.random() * 6); //random row coordinate
                    ranColOne = (int)(Math.random() * 6); //random col coordinate
                }else if(!isEmpty(ranRowTwo, ranColTwo)){
                   ///System.out.println("loop 3 is repeated");
                    ranRowTwo = (int)(Math.random() * 6); //random row coordinate
                    ranColTwo = (int)(Math.random() * 6); //random col coordinate  
                }
                
            }
            //filling booleanMatrix coordinate to true and 
            //setting up vals in intMatrix coordinates
            booleanMatrix[ranRowOne][ranColOne] = true;
            booleanMatrix[ranRowTwo][ranColTwo] = true;
            intMatrix[ranRowOne][ranColOne] = values[i];
            intMatrix[ranRowTwo][ranColTwo] = values[i];
        }
    }

    /**
     * setting values for the values array to be imported when
     * game starts
     */
    private void setValues(){
        int val = (int)(Math.random() * 99);
        for(int i = 0; i < values.length; i ++){
            while(isValueRepeated(val)){
                val = (int)(Math.random() * 99);
            }
            values[i] = val;
        }

    }

    /**
     * returns if values vals are repeated  
     * @param val
     * @return if values vals are repeated 
     */
    private boolean isValueRepeated(int val){
        for(int i = 0; i < values.length; i ++){
            if(values[i] != null && values[i] == val )return true;
        }
        return false;
    }

    /**
     * when gui is made, this method will check
     * if both values coordinate is pressed
     * @param rowOne
     * @param colOne
     * @param rowTwo
     * @param colTwo
     */
    public void bothPressed(int rowOne, int colOne, int rowTwo, int colTwo){
        if(intMatrix[rowOne][colOne] == intMatrix[rowTwo][colTwo]){
            System.out.println("There is a Match!");
            intMatrix[rowOne][colOne] = null;
            intMatrix[rowTwo][colTwo] = null;
            completedMatrix[rowOne][colOne] = true;
            completedMatrix[rowTwo][colTwo] = true;
        }else{
            System.out.println("These are not a match");
        }
        
    }


    /**
     * @return output of the Box class Matrixs data
     */
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
