/*
 * Program: ChessProject
 * This: Board.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: This JPanel extension is the background of the JFrame which draws
 the chess squares in the traditional alternating pattern. It does not change.
 */
package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class Board extends JPanel {

    private final int SQUARE_SIZE;

    /**
     * Constructs a background JPanel to draw the checkered board pattern. This
     * JPanel, once drawn, does not change.
     * @param squareSize the size in pixels of one square of the board
     */
    public Board(int squareSize) {
        this.SQUARE_SIZE = squareSize;
        setSize(640, 640);
        setPreferredSize(new Dimension(640, 640));
    }

    /**
     * Draws the board in a checkered pattern
     * @param g the graphics context
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 640, 640); //White background
        g.setColor(new Color(80, 67, 33));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    //Brown squares at every other board position
                    g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
        g.dispose();
    }

    /**
     * Gets the size in pixels of a single square on the board
     * @return the size (length/width) of each square in pixels
     */
    public int getBoardSquareSize() {
        return SQUARE_SIZE;
    }
}
