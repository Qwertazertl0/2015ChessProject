/*
 * Program: ChessProject
 * This: Highlights.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: Another JPanel extension which indicates to the both the player and
GUIMain.java whether the player has selected a piece or not. It also provides the
player information on where his or her selected can move.
 */
package Interface;

import Pieces.Piece;
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
public class Highlights extends JPanel {
    
    private int x = 0, y = 0;
    private Piece selectedPiece;
    
    /**
     * Constructs a JPanel that is not opaque and controls the square highlights
     */
    public Highlights() {
        setSize(640, 640);
        setPreferredSize(new Dimension(640, 640));
        setVisible(false);
        setOpaque(false);
    }
    
    /**
     * Draws a red square to show the selected square and any green squares which
     * signify legal moves.
     * @param g the graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int a, b;
        
        g.setColor(Color.RED);
        //Check if the board is flipped
        if (DisplayPieces.getFlipped() == false)
            g.fillRect(x, y, 80, 80);
        else g.fillRect(560 - x, 560 - y, 80, 80);
        
        //If there is a piece on the selected square, draw its legal moves
        if (selectedPiece != null && !GUIMain.getBlack().getGameOver() && 
                !GUIMain.getBlack().getGameOver()) {
            for (int i = 0; i < selectedPiece.canMove().length; i++) {
                if (DisplayPieces.getFlipped() == false) { //Another flip check
                    a = Piece.toPosX(selectedPiece.canMove()[i]);
                    b = Piece.toPosY(selectedPiece.canMove()[i]);
                } else {
                    a = 9 - Piece.toPosX(selectedPiece.canMove()[i]);
                    b = 9 - Piece.toPosY(selectedPiece.canMove()[i]);
                }

                g.setColor(Color.GREEN);
                g.fillRect(a * 80 - 80, b * 80 - 80, 80, 80);
                g.setColor(new Color(80, 67, 33));
                g.drawRect(a * 80 -79, b * 80 - 79, 78, 78);
            }   
        }
        repaint();
    }
    
    /**
     * Sets the coordinates of the red "selection square" shown to users
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets the selected Piece as the given piece
     * @param piece the piece to be set as "currently selected"
     */
    public void setSelectedPiece(Piece piece) {
        selectedPiece = piece;
    }
    
    /**
     * Returns the piece that the user has currently selected (may return null).
     * @return the Piece that is selected
     */
    public Piece getSelectedPiece() {
        return selectedPiece;
    }
}
