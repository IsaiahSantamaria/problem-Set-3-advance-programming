

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Concentration class for problem 2 in problem set 3
 * @author Isaiah Santamaria
 * @version 3/21/2026
 * 
 */
public class Concentration extends MouseAdapter implements Runnable {
    private static final int SQUARE_SIZE = 200;
    private static final int PADDING = 20;

    /**box color */
    private Color boxColor = Color.white;

    /**main panel where boxes will be located */
    private JPanel panel;

    /**number of boxes that there will be in the application */
    private int numSquares = 36;
    
    //matrix(back end of the program)
    Matrix matrix;

    public Concentration(){
        matrix = new Matrix();
        matrix.start();
        

    }
    //JPanel with a paintComponent method using an anonymous class. 
	// We override the paintComponent method to draw our squares.
    @Override
    public void run(){
        //setting up the basic gui setup, there will be a jFrame with a Jpanel inside it
        JFrame frame = new JFrame("Concentration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(SQUARE_SIZE * 6 + PADDING*7,SQUARE_SIZE * 6 + PADDING*7));
        frame.setResizable(true);
        frame.addMouseListener(this);

        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                //drawing squares into the gui
                int x = 1;
                int y = 0;
                for(int i = 0; i < 6; i ++){
                    g.setColor(Color.WHITE);
                    g.fillRect(x * PADDING + SQUARE_SIZE * y, PADDING, SQUARE_SIZE, SQUARE_SIZE);
                    x++;
                    y++;
                }
            }
        };
        //Add a mouse listener to the panel to respond to mouse events.
        panel.addMouseListener(this);

        frame.add(panel);

        //Display the window we've created.
        frame.pack();
        frame.setVisible(true);
    
        
    }
    
        /**
     * When the mouse button is pressed, change the color of the
     * square(s) according to the (x, y) location of the
     * mouse press in the JFrame.
     * 
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //Get the (x, y) location of the mouse button press.
        int x = e.getX();
        int y = e.getY();
        boolean isInBox = false; //this will check if mouse was clicked on the box
        for(int i = 0; i < numSquares; i ++){
            if(x >= ( i+ 1) * PADDING + i * SQUARE_SIZE && x <= (i + 1) * PADDING + (i + 1) * SQUARE_SIZE
                && y >= PADDING && y <= PADDING + SQUARE_SIZE && numSquares >= (i+1)){
                System.out.println("box is clicked");
                isInBox = true;
            }
        }
        if(!isInBox){
            System.out.println("box is not clicked");
        }
        panel.repaint();
    }








    



    public static void main(String []args){
        Matrix matrix = new Matrix();
        System.out.println(matrix + "\n");
        matrix.start();
        System.out.println(matrix);
        SwingUtilities.invokeLater(new Concentration());

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
