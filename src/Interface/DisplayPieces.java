/*
 * Program: ChessProject
 * This: DisplayPieces.java
 * Date: November 1, 2015
 * Author: Maxwell Jong
 * Purpose: This JPanel extension displays the pieces of one player in the GUI
so players can see the pieces. It draws each piece at a given position from a 
sprite sheet resized for this program.
 * The png image (sprite sheet) used is licensed under the Creative Commons 
Attribution-Share Alike 3.0 Unported license. The source information can be found at
the website source: https://commons.wikimedia.org/wiki/File:Chess_Pieces_Sprite.svg
 */
package Interface;

import Pieces.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class DisplayPieces extends JPanel {
    
    private final Piece[] pieceArr;
    private final BufferedImage pieceSprites;
    private final int ROW, WHICH_PLAYER;
    private static boolean flipped = false, autoFlip = false;
    
    /**
     * Constructs the JPanel which displays the pieces of one color.
     * @param pieces the array of pieces which the JPanel draws
     * @param color a string representing the color of the array of pieces; "White"
     * for white and "Black" for black
     * @throws IOException 
     */
    public DisplayPieces(Piece[] pieces, String color) throws IOException {
        setSize(640, 640);
        setPreferredSize(new Dimension(640, 640));
        setOpaque(false);
        
        //Reads the chess piece sprite sheet
        pieceSprites = ImageIO.read(new File("ChessSpritesMini.png"));
        pieceArr = pieces;
        if (color.equalsIgnoreCase("White")) WHICH_PLAYER = 1;
        else WHICH_PLAYER = -1;
        
        //Determines which row to read from (top is white pieces, bottom is black)
        if (WHICH_PLAYER == 1) ROW = 0;
        else ROW = 1;
    }
    
    /**
     * Draws each piece at its specified position on the board, from the sprite sheet.
     * @param g the graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //Checks if the program needs to automatically flip the board after each turn
        if (autoFlip) {
            if (GUIMain.getTotalMoveCount() % 2 == 0) flipped = false;
            else flipped = true;
        }
        
        //Displaying the pieces from the sprite sheet
        if (WHICH_PLAYER > 0) {
            for (int i = 0; i < GUIMain.getWhite().getPieceCount(); i++) {
                int type = 0;
                //Specifies the column of the sprite by type
                if (pieceArr[i] instanceof King) type = 0;
                else if (pieceArr[i] instanceof Queen) type = 1;
                else if (pieceArr[i] instanceof Bishop) type = 2;
                else if (pieceArr[i] instanceof Knight) type = 3;
                else if (pieceArr[i] instanceof Rook) type = 4;
                else if (pieceArr[i] instanceof Pawn) type = 5;
                
                //Cuts a sub-image and displays it at the piece's position
                if (flipped == false)
                    g.drawImage(cut(pieceSprites, ROW, type), pieceArr[i].getPosX()*80 - 80,
                            pieceArr[i].getPosY()*80 - 80, null);
                else g.drawImage(cut(pieceSprites, ROW, type), (9 - pieceArr[i].getPosX())*80 - 80,
                            (9 - pieceArr[i].getPosY())*80 - 80, null);
            }
        } else {
            for (int i = 0; i < GUIMain.getBlack().getPieceCount(); i++) {
                int type = 0;
                if (pieceArr[i] instanceof King) type = 0;
                else if (pieceArr[i] instanceof Queen) type = 1;
                else if (pieceArr[i] instanceof Bishop) type = 2;
                else if (pieceArr[i] instanceof Knight) type = 3;
                else if (pieceArr[i] instanceof Rook) type = 4;
                else if (pieceArr[i] instanceof Pawn) type = 5;

                if (flipped == false)
                    g.drawImage(cut(pieceSprites, ROW, type), pieceArr[i].getPosX()*80 - 80,
                            pieceArr[i].getPosY()*80 - 80, null);
                else g.drawImage(cut(pieceSprites, ROW, type), (9 - pieceArr[i].getPosX())*80 - 80,
                            (9 - pieceArr[i].getPosY())*80 - 80, null);
            }   
        }
        repaint();
    }
    
    /**
     * Picks the correct piece sprite from the sprite sheet
     * @param spriteSheet the BufferedImage sprite sheet
     * @param row the row of the sprite sheet starting from 0
     * @param column the column of the sprite sheet starting from 0
     * @return a single sprite from the sprite sheet; a sub-image
     */
    private BufferedImage cut(BufferedImage spriteSheet, int row, int column) {
        BufferedImage sprite = spriteSheet.getSubimage
            (column * 80, row * 80, 80, 80);
        return sprite;
    }
    
    /**
     * Flips the visual display of the pieces
     */
    public static void flipBoard() {
        if (flipped == false) flipped = true;
        else flipped = false;
    }
    
    /**
     * Returns whether or not the board is flipped from the standard orientation
     * @return a Boolean, true is the board is flipped and false otherwise
     */
    public static boolean getFlipped() {
        return flipped;
    }
    
    /**
     * Sets the state of boolean that determines if automatic flipping of the board
     * is in effect.
     * @param flip the state to set the boolean controlling automatic flipping
     */
    public static void autoFlip(Boolean flip) {
        autoFlip = flip;
    }
    
    /**
     * Returns whether or not automatic flipping is in effect
     * @return a boolean, true is automatic flipping is in effect and false otherwise
     */
    public static boolean getAutoFlip() {
        return autoFlip;
    }
}
