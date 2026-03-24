import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Concentration class for problem 2 in problem set 3
 * @author Isaiah Santamaria
 * @version 3/23/2026
 * 
 */
public class Concentration extends MouseAdapter implements Runnable {
    private static final int SQUARE_SIZE = 100;
    private static final int PADDING = 10;

    /**this will keep of each button being pressed and compared to one another */
    private ArrayList<Integer> pair;

    private ArrayList<Integer> pairCoordinates;

    /**keeps track if the pair Integer array is full or not */
    private boolean isPairFull = false;

    /**tracks the first clicked box to prevent clicking the same box twice */
    private int firstClickRow = -1;
    private int firstClickCol = -1;

    /**box color */
    private Color boxColor = Color.white;

    /**main panel where boxes will be located */
    private JPanel panel;

    /**display panel that display the current clicked buttons in the grid */
    private JLabel display = new JLabel("Welcome to Concentration", SwingConstants.CENTER);

    /**number of boxes that there will be in the application */
    private int numSquares = 36;

    //matrix(back end of the program)
    Matrix matrix;

    public Concentration(){
        matrix = new Matrix();
        matrix.start();
        display.setPreferredSize(new Dimension(250, 50));
        pair = new ArrayList<Integer>();
        pairCoordinates = new ArrayList<Integer>();
    }

    //JPanel with a paintComponent method using an anonymous class. 
    // We override the paintComponent method to draw our squares.
    @Override
    public void run(){
        //setting up the basic gui setup, there will be a jFrame with a Jpanel inside it
        JFrame frame = new JFrame("Concentration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(SQUARE_SIZE * 6 + PADDING*8, SQUARE_SIZE * 6 + PADDING*10 + 150));
        frame.setResizable(true);
        frame.addMouseListener(this);

        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                FontMetrics fm = g.getFontMetrics();
                int row = PADDING;

                Font newFont = new Font("SansSerif", Font.BOLD, 16);
                display.setFont(newFont);
                g.setFont(newFont);

                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 6; j++){
                        int boxX = (j + 1) * PADDING + SQUARE_SIZE * j;
                        int boxY = row + 30;

                        // Draw the box
                        g.setColor(Color.WHITE);
                        g.fillRoundRect(boxX, boxY, SQUARE_SIZE, SQUARE_SIZE, 30, 30);

                        // Draw centered text
                        String text = "";
                        if(matrix.getVal(i, j) != null){
                            text = String.valueOf(matrix.getVal(i, j)); // or any string
                            
                        }else{
                            text = String.valueOf("x"); // or any string
                        }

                        int textX = boxX + (SQUARE_SIZE - fm.stringWidth(text)) / 2;
                        int textY = boxY + (SQUARE_SIZE - fm.getHeight()) / 2 + fm.getAscent();

                        newFont = new Font("SansSerif", Font.BOLD, 16);
                        g.setFont(newFont);

                        g.setColor(Color.BLACK);
                        g.drawString(text, textX, textY);
                    }
                    row = row + PADDING + SQUARE_SIZE;
                }

                g.setColor(Color.WHITE);
                //drawing the start and reset button
                g.fillRoundRect((SQUARE_SIZE * 6 + PADDING*8)/2 - SQUARE_SIZE - PADDING, (SQUARE_SIZE * 6 + PADDING*7 + 80) - PADDING - SQUARE_SIZE/3, SQUARE_SIZE, SQUARE_SIZE/3, 15, 15);
                g.fillRoundRect((SQUARE_SIZE * 6 + PADDING*8)/2, (SQUARE_SIZE * 6 + PADDING*7 + 80) - PADDING - SQUARE_SIZE/3, SQUARE_SIZE, SQUARE_SIZE/3, 15, 15);

                g.setFont(newFont);
                g.setColor(Color.BLACK);
                g.drawString("New Game", (SQUARE_SIZE * 6 + PADDING*8)/2 - SQUARE_SIZE - PADDING + 10, (SQUARE_SIZE * 6 + PADDING*7 + 80) + PADDING - SQUARE_SIZE/3);
                g.drawString("reset", (SQUARE_SIZE * 6 + PADDING*8)/2 + SQUARE_SIZE/2 - fm.stringWidth("reset")/2, (SQUARE_SIZE * 6 + PADDING*7 + 80) + PADDING - SQUARE_SIZE/3);
            }
        };

        //setting up start and restart buttons

        //Add a mouse listener to the panel to respond to mouse events.
        panel.addMouseListener(this);
        frame.add(display, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

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
    int x = e.getX();
    int y = e.getY();
    boolean isInBox = false;

    for (int i = 0; i < 6; i++) {
        for (int row = 0; row < 6; row++) {
            int rowTop = PADDING + row * (SQUARE_SIZE + PADDING) + 30;
            int rowBottom = rowTop + SQUARE_SIZE + 30;
            if (x >= (i + 1) * PADDING + i * SQUARE_SIZE
                    && x <= (i + 1) * PADDING + (i + 1) * SQUARE_SIZE
                    && y >= rowTop
                    && y <= rowBottom
                    && numSquares >= (i + 1)) {

                // prevent the same box from being clicked twice
                if (row == firstClickRow && i == firstClickCol) {
                    isInBox = true;
                    
                    continue;
                }

                //display which button is being clicked
                System.out.println("box is clicked (" + row + "," + i + ")");
                display.setText("Pressed " +matrix.getVal(row,i) + " at ("+row + ", " + i + ")"); 
                if (!isPairFull) {
                    pair.add(matrix.getVal(row, i));
                    pairCoordinates.add(row);
                    pairCoordinates.add(i);

                    if (pair.size() == 1) {
                        firstClickRow = row;
                        firstClickCol = i;
                    }

                    if (pair.size() == 2) isPairFull = true; //when pair is full sets isPairFull to true
                } else {
                    // 3rd click — clear and treat this as new first click
                    pair.clear();
                    pairCoordinates.clear();
                    isPairFull = false;

                    pair.add(matrix.getVal(row, i));
                    pairCoordinates.add(row);
                    pairCoordinates.add(i);
                    firstClickRow = row;
                    firstClickCol = i;
                }
                isInBox = true;
            }
        }
    }

    // match checking is now OUTSIDE the loop
    if (isPairFull) {
        int rowOne = pairCoordinates.get(0);
        int colOne = pairCoordinates.get(1);
        int rowTwo = pairCoordinates.get(2);
        int colTwo = pairCoordinates.get(3);

        if (pair.get(0).equals(pair.get(1))) {
            matrix.bothPressed(rowOne, colOne, rowTwo, colTwo);
            pair.clear();
            pairCoordinates.clear();
            isPairFull = false;
            firstClickRow = -1;
            firstClickCol = -1;
        }
    }

    //if New Game button is clicked
    if (x >= (SQUARE_SIZE * 6 + PADDING*8)/2 - SQUARE_SIZE - PADDING && x <= (SQUARE_SIZE * 6 + PADDING*8)/2 - SQUARE_SIZE - PADDING + SQUARE_SIZE
        && y >= (SQUARE_SIZE * 6 + PADDING*7 + 80) - PADDING - SQUARE_SIZE/3 && y <= (SQUARE_SIZE * 6 + PADDING*7 + 80) - PADDING - SQUARE_SIZE/3 + SQUARE_SIZE/3) {
            System.out.println("New Game button is clicked");
            matrix.start();
            pair.clear();
            pairCoordinates.clear();
            isPairFull = false;
            firstClickRow = -1;
            firstClickCol = -1;
    }

    //if Reset button is clicked
    if (x >= (SQUARE_SIZE * 6 + PADDING*8)/2 && x <= (SQUARE_SIZE * 6 + PADDING*8)/2 + SQUARE_SIZE
        && y >= (SQUARE_SIZE * 6 + PADDING*7 + 80) - PADDING - SQUARE_SIZE/3 && y <= (SQUARE_SIZE * 6 + PADDING*7 + 80) - PADDING - SQUARE_SIZE/3 + SQUARE_SIZE/3) {
            display.setText("Welcome To Concentration!");
            System.out.println("Reset Button is clicked");
    }

    if (!isInBox) {
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
    public void fill(int val, int row, int col){
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
        completedMatrix = new boolean[6][6];
    }

    /**
     * only resets the completedMatrix to keeps its current
     * matrix values
     */
    public void reset(){
        completedMatrix = new boolean[6][6];
    }

    /**
     * method starts the game by assigning all values to t
     * intMatrix
     */
    public void start(){
        restart();
        int ranRowOne = (int)(Math.random() * 6); //random row coordinate
        int ranColOne = (int)(Math.random() * 6); //random col coordinate
        int ranRowTwo = (int)(Math.random() * 6); //random row coordinate
        int ranColTwo = (int)(Math.random() * 6); //random col coordinate
        setValues();
        //randomly assigning 2 coordinates of int matrix
        for(int i = 0; i < values.length; i++){
            //ensure that no matrix is overwritten
            //does this loop into it finds a matrix where it can put a new val
            while((ranRowOne == ranRowTwo && ranColOne == ranColTwo) || !isEmpty(ranRowOne, ranColOne) || !isEmpty(ranRowTwo, ranColTwo)){
                if(ranRowOne == ranRowTwo && ranColOne == ranColTwo){
                    ranRowOne = (int)(Math.random() * 6); //random row coordinate
                    ranColOne = (int)(Math.random() * 6); //random col coordinate
                    ranRowTwo = (int)(Math.random() * 6); //random row coordinate
                    ranColTwo = (int)(Math.random() * 6); //random col coordinate
                }else if(!isEmpty(ranRowOne, ranColOne)){
                    ranRowOne = (int)(Math.random() * 6); //random row coordinate
                    ranColOne = (int)(Math.random() * 6); //random col coordinate
                }else if(!isEmpty(ranRowTwo, ranColTwo)){
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
        for(int i = 0; i < values.length; i++){
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
        for(int i = 0; i < values.length; i++){
            if(values[i] != null && values[i] == val) return true;
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
        if(intMatrix[rowOne][colOne].equals(intMatrix[rowTwo][colTwo])){
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
        String output = "";
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                output = output + intMatrix[j][i] + ","
                + booleanMatrix[j][i] + " ";
            }
            output = output + "\n";
        }
        return output;
    }
}