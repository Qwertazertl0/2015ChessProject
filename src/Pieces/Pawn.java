/*
 * Program: ChessProject
 * This: Pawn.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: The Pawn class codes the specific movement and game logic for the chess
piece called a pawn.
 */
package Pieces;

import Engine.Main;
import Interface.GUIMain;
import TextInterface.TextUIMain;
import java.util.Scanner;
import javax.swing.JOptionPane;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class Pawn extends Piece {

    private final Scanner userInput = new Scanner(System.in);
    private int enPassantMove;
    private int dir;
    
    /**
     * Constructs a Pawn piece for a specified color
     * @param posX the starting x-coordinate
     * @param posY the starting y-coordinate
     * @param isWhite the color specifier, true if white and false if black
     */
    public Pawn(int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite);
    }

    /**
     * Returns an integer array of the legal moves this Pawn can make
     * @return an array of legal moves (integers)
     */
    @Override
    public int[] canMove() {
        int[] normalMoveArr = new int[4];
        int x = getPosX(), y = getPosY();
        if (isWhite) dir = 1;
        else dir = -1;
        
        //Generates the three or four possible squares for checking
        normalMoveArr[0] = toPos(x, y - dir);
        normalMoveArr[1] = toPos(x, y - 2 * dir);
        if (x == 1) {
            normalMoveArr[3] = toPos(x + 1, y - dir);
        } else if (x == 8) {
            normalMoveArr[2] = toPos(x - 1, y - dir);
        } else {
            normalMoveArr[2] = toPos(x - 1, y - dir);
            normalMoveArr[3] = toPos(x + 1, y - dir);
        }
        
        int[] canMoveArr = new int[4]; 
        if (Main.getPlayGraphics() == 0) {
            //Case-by-case verification of each possible square
            if (!(GUIMain.getWhite().hasPieceAt(toPos(x, y - dir)) ||
                    GUIMain.getBlack().hasPieceAt(toPos(x, y - dir))))
                canMoveArr[0] = normalMoveArr[0];
            if (hasMoved == false && !(GUIMain.getWhite().hasPieceAt(toPos(x, y - 2 * dir))
                    || GUIMain.getBlack().hasPieceAt(toPos(x, y - 2 * dir))
                    || GUIMain.getWhite().hasPieceAt(toPos(x, y - dir))
                    || GUIMain.getBlack().hasPieceAt(toPos(x, y - dir))))
                canMoveArr[1] = normalMoveArr[1];
                //Diagonal captures
            if (isWhite == true && GUIMain.getBlack().hasPieceAt(toPos(x - 1, y - 1)))
                canMoveArr[2] = normalMoveArr[2];
            if (isWhite == true && GUIMain.getBlack().hasPieceAt(toPos(x + 1, y - 1)))
                canMoveArr[3] = normalMoveArr[3];
            if (isWhite == false && GUIMain.getWhite().hasPieceAt(toPos(x - 1, y + 1)))
                canMoveArr[2] = normalMoveArr[2];
            if (isWhite == false && GUIMain.getWhite().hasPieceAt(toPos(x + 1, y + 1)))
                canMoveArr[3] = normalMoveArr[3];

            //En Passant code (case-by-case checking)
            if (isWhite && getPosY() == 4 && (GUIMain.getBlack().hasPieceAt(pos + 1) ||
                    GUIMain.getBlack().hasPieceAt(pos - 1)) && toPosY(pos + 1) != 5
                    && toPosY(pos - 1) != 3) {
                if (GUIMain.getBlack().pieceAt(pos + 1) instanceof Pawn && 
                        GUIMain.getWhite().getMoveCount() == ((Pawn) GUIMain.getBlack().pieceAt(pos + 1)).enPassantMove + 1)
                    canMoveArr[3] = normalMoveArr[3];
                if (GUIMain.getBlack().pieceAt(pos - 1) instanceof Pawn && 
                        GUIMain.getWhite().getMoveCount() == ((Pawn) GUIMain.getBlack().pieceAt(pos - 1)).enPassantMove + 1)
                    canMoveArr[2] = normalMoveArr[2];
            }
            if (!isWhite && getPosY() == 5 && (GUIMain.getWhite().hasPieceAt(pos + 1) ||
                    GUIMain.getWhite().hasPieceAt(pos - 1)) && toPosY(pos + 1) != 6
                    && toPosY(pos - 1) != 4) {
                if (GUIMain.getWhite().pieceAt(pos + 1) instanceof Pawn && 
                        GUIMain.getBlack().getMoveCount() == ((Pawn) GUIMain.getWhite().pieceAt(pos + 1)).enPassantMove)
                    canMoveArr[3] = normalMoveArr[3];
                if (GUIMain.getWhite().pieceAt(pos - 1) instanceof Pawn && 
                        GUIMain.getBlack().getMoveCount() == ((Pawn) GUIMain.getWhite().pieceAt(pos - 1)).enPassantMove)
                    canMoveArr[2] = normalMoveArr[2];
            }
        } else {
            //Code replication for the TextUI classes
            if (!(TextUIMain.getWhite().hasPieceAt(toPos(x, y - dir)) ||
                    TextUIMain.getBlack().hasPieceAt(toPos(x, y - dir))))
                canMoveArr[0] = normalMoveArr[0];
            if (hasMoved == false && !(TextUIMain.getWhite().hasPieceAt(toPos(x, y - 2 * dir))
                    || TextUIMain.getBlack().hasPieceAt(toPos(x, y - 2 * dir))
                    || GUIMain.getWhite().hasPieceAt(toPos(x, y - dir))
                    || GUIMain.getBlack().hasPieceAt(toPos(x, y - dir))))
                canMoveArr[1] = normalMoveArr[1];
            if (isWhite == true && TextUIMain.getBlack().hasPieceAt(toPos(x - 1, y - 1)))
                canMoveArr[2] = normalMoveArr[2];
            if (isWhite == true && TextUIMain.getBlack().hasPieceAt(toPos(x + 1, y - 1)))
                canMoveArr[3] = normalMoveArr[3];
            if (isWhite == false && TextUIMain.getWhite().hasPieceAt(toPos(x - 1, y + 1)))
                canMoveArr[2] = normalMoveArr[2];
            if (isWhite == false && TextUIMain.getWhite().hasPieceAt(toPos(x + 1, y + 1)))
                canMoveArr[3] = normalMoveArr[3];

            if (isWhite && getPosY() == 4 && (TextUIMain.getBlack().hasPieceAt(pos + 1) ||
                    TextUIMain.getBlack().hasPieceAt(pos - 1)) && toPosY(pos + 1) != 5
                    && toPosY(pos - 1) != 3) {
                if (TextUIMain.getBlack().pieceAt(pos + 1) instanceof Pawn && 
                        TextUIMain.getWhite().getMoveCount() == ((Pawn) TextUIMain.getBlack().pieceAt(pos + 1)).enPassantMove + 1)
                    canMoveArr[3] = normalMoveArr[3];
                if (TextUIMain.getBlack().pieceAt(pos - 1) instanceof Pawn && 
                        TextUIMain.getWhite().getMoveCount() == ((Pawn) TextUIMain.getBlack().pieceAt(pos - 1)).enPassantMove + 1)
                    canMoveArr[2] = normalMoveArr[2];
            }
            if (!isWhite && getPosY() == 5 && (TextUIMain.getWhite().hasPieceAt(pos + 1) ||
                    TextUIMain.getWhite().hasPieceAt(pos - 1)) && toPosY(pos + 1) != 6
                    && toPosY(pos - 1) != 4) {
                if (TextUIMain.getWhite().pieceAt(pos + 1) instanceof Pawn && 
                        TextUIMain.getBlack().getMoveCount() == ((Pawn) TextUIMain.getWhite().pieceAt(pos + 1)).enPassantMove)
                    canMoveArr[3] = normalMoveArr[3];
                if (TextUIMain.getWhite().pieceAt(pos - 1) instanceof Pawn && 
                        TextUIMain.getBlack().getMoveCount() == ((Pawn) TextUIMain.getWhite().pieceAt(pos - 1)).enPassantMove)
                    canMoveArr[2] = normalMoveArr[2];
            }
        }
        
        int[] legalMoveArr = checkLegality(canMoveArr);
        return legalMoveArr;
    }
    
    /**
     * Records the turn on which a pawn was moved two squares so to only allow
     * en passant on the immediate next turn
     * @param move the turn on which the pawn was moved two squares
     */
    public void setEnPassantMove(int move) {
        enPassantMove = move;
    }
    
    /**
     * Changes the pawn to a queen, rook, bishop, or knight upon user choice
     */
    public void promotePiece() {
        //This method is essentially case-by-case and rather inefficient. I did 
        //not have much success with trying to pass classes or objects as parameters.
        if (Main.getPlayGraphics() == 0) {
            String[] pieceChoices = {"Queen", "Rook", "Bishop", "Knight"};
            int choice = JOptionPane.showOptionDialog(null, "Which piece would you like?", 
                    "Pawn Promotion", JOptionPane.YES_NO_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null, pieceChoices,  null);
            //I used a switch system here because I have a nice set of integers 
            //already assigned by the JOptionPane
            if (isWhite) {
                switch (choice) {
                    case 0:
                        GUIMain.getWhite().replacePawnWith(this, new Queen(toPosX(pos), toPosY(pos), true));
                        break;
                    case 1:
                        GUIMain.getWhite().replacePawnWith(this, new Rook(toPosX(pos), toPosY(pos), true));
                        break;
                    case 2:
                        GUIMain.getWhite().replacePawnWith(this, new Bishop(toPosX(pos), toPosY(pos), true));
                        break;
                    case 3:
                        GUIMain.getWhite().replacePawnWith(this, new Knight(toPosX(pos), toPosY(pos), true));
                        break;
                }
            } else {
                switch (choice) {
                    case 0:
                        GUIMain.getBlack().replacePawnWith(this, new Queen(toPosX(pos), toPosY(pos), false));
                        break;
                    case 1:
                        GUIMain.getBlack().replacePawnWith(this, new Rook(toPosX(pos), toPosY(pos), false));
                        break;
                    case 2:
                        GUIMain.getBlack().replacePawnWith(this, new Bishop(toPosX(pos), toPosY(pos), false));
                        break;
                    case 3:
                        GUIMain.getBlack().replacePawnWith(this, new Knight(toPosX(pos), toPosY(pos), false));
                        break;
                }
            }
        } else {
            boolean validInput = false;
            //However, the uncertain nature of user input makes it easier to just
            //create a tree of if-statements
            while (!validInput) {
                System.out.print("Choose a promotion (Bishop/Knight/Queen/Rook): ");
                String temp = userInput.next();
                if (isWhite) { 
                    if (temp.equalsIgnoreCase("Bishop")) {
                        TextUIMain.getWhite().replacePawnWith(this, new Bishop(toPosX(pos), toPosY(pos), true));
                        validInput = true;}
                    if (temp.equalsIgnoreCase("Knight")) {
                        TextUIMain.getWhite().replacePawnWith(this, new Knight(toPosX(pos), toPosY(pos), true));
                        validInput = true;}
                    if (temp.equalsIgnoreCase("Queen")) {
                        TextUIMain.getWhite().replacePawnWith(this, new Queen(toPosX(pos), toPosY(pos), true));
                        validInput = true;}
                    if (temp.equalsIgnoreCase("Rook")) {
                        TextUIMain.getWhite().replacePawnWith(this, new Rook(toPosX(pos), toPosY(pos), true));
                        validInput = true;}
                } else {
                    if (temp.equalsIgnoreCase("Bishop")) {
                        TextUIMain.getBlack().replacePawnWith(this, new Bishop(toPosX(pos), toPosY(pos), false));
                        validInput = true;}
                    if (temp.equalsIgnoreCase("Knight")) {
                        TextUIMain.getBlack().replacePawnWith(this, new Knight(toPosX(pos), toPosY(pos), false));
                        validInput = true;}
                    if (temp.equalsIgnoreCase("Queen")) {
                        TextUIMain.getBlack().replacePawnWith(this, new Queen(toPosX(pos), toPosY(pos), false));
                        validInput = true;}
                    if (temp.equalsIgnoreCase("Rook")) {
                        TextUIMain.getBlack().replacePawnWith(this, new Rook(toPosX(pos), toPosY(pos), false));
                        validInput = true;}
                }
            }
        }
    }
    
    /**
     * Changes the pawn to a specified piece parameter (for computer use). As
     * of November 15, 2015, this method only lets the computer promote to a queen.
     * @param piece the piece type of promotion
     */
    public void promotePiece(String piece) {
        if (Main.getPlayGraphics() == 0) {
            if (isWhite) {
                GUIMain.getWhite().replacePawnWith(this, new Queen(toPosX(pos), toPosY(pos), true));
            } else {
                GUIMain.getBlack().replacePawnWith(this, new Queen(toPosX(pos), toPosY(pos), false));
            }
        } else {
            if (isWhite) {
                TextUIMain.getWhite().replacePawnWith(this, new Queen(toPosX(pos), toPosY(pos), true));
            } else {
                TextUIMain.getBlack().replacePawnWith(this, new Queen(toPosX(pos), toPosY(pos), false));
            }
        }
    }
}
