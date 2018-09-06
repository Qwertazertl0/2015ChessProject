/*
 * Program: ChessProject
 * This: PlayerBlack.java
 * Date: November 1, 2015
 * Author: Maxwell Jong
 * Purpose: This class creates a player object who plays the black pieces in the
chess game. It is essentially identical to PlayerWhite.java. The choice to make
separate classes for each side was made to make logic easier to code; in 
retrospect, it may have been more efficient to make one class.
 */
package Engine;

import Interface.GUIMain;
import Pieces.*;
import TextInterface.TextUIMain;
import javax.swing.JOptionPane;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class PlayerBlack {
    
    private final Piece[] pieces = new Piece[16];
    private boolean gameOver = false;
    private boolean won = false;
    private int pieceCount = 16;
    private int moveCount = 0;
    private final King king;
    
    /**
     * Constructor which assigns a new set of pieces in their starting positions
     * for the PlayerBlack side.
     */
    public PlayerBlack() {
        for (int i = 0; i < 8; i++) {
            pieces[i] = new Pawn(i + 1, 2, false);
        }
        pieces[8] = new Rook(1, 1, false);
        pieces[9] = new Rook(8, 1, false);
        pieces[10] = new Knight(2, 1, false);
        pieces[11] = new Knight(7, 1, false);
        pieces[12] = new Bishop(3, 1, false);
        pieces[13] = new Bishop(6, 1, false);
        pieces[14] = new Queen(4, 1, false);
        king = new King(5, 1, false);
        pieces[15] = king;
    }
    
    /**
     * Checks if any of the player's pieces has any legal moves, so as to verify
     * checkmate
     * @return a Boolean, true if there are no legal moves and false otherwise
     */
    public boolean hasNoLegalMoves() {
        for (int i = 0; i < pieceCount; i++) {
            pieces[i].canMove();
            if (pieces[i].getHasLegalMoves())
                return false;
        }
        return true;
    }
    
    /**
     * Returns the current array of chess pieces
     * @return an array with all the player's pieces
     */
    public Piece[] getPieceArray() {
        return pieces;
    }
    
    /**
     * Returns the piece count of how many pieces the player still has on the board
     * @return an integer number of pieces the player has on the board
     */
    public int getPieceCount() {
        return pieceCount;
    }
    
    /**
     * Returns the number of moves this player has made in the current game
     * @return an integer number of moves the player has made in the current game
     */
    public int getMoveCount() {
        return moveCount;
    }
    
    /**
     * Returns a Boolean describing whether the game is over
     * @return a Boolean, true if the game has ended and false otherwise
     */
    public boolean getGameOver() {
        return gameOver;
    }
    
    /**
     * Set the state of the boolean gameOver which determines whether the player
     * can move
     * @param state the new state of the game, true if not over and false otherwise
     */
    public void setGameOver(boolean state) {
        gameOver = state;
    }
    
    /**
     * Returns a boolean describing whether or not black has won.
     * @return true if black has won and false otherwise
     */
    public boolean getWon() {
        return won;
    }
    
    /**
     * An accessor method for the king piece of this player
     * @return the King object (extension of Piece) for this player
     */
    public King getKing() {
        return king;
    }
    
    /**
     * Increments the move count
     */
    public void incMoveCount() {
        moveCount++;
    }
    
    /**
     * Returns the piece that the player has at a given position.
     * @param pos the integer position to be checked
     * @return the Piece at the given position
     */
    public Piece pieceAt(int pos) {
        for (int i = 0; i < pieceCount; i++) {
            if (pieces[i].getPos() == pos)
                return pieces[i];
        }
        return null;
    }
    
    /**
     * Returns the piece that the player has at a given test position
     * @param posTest the integer test position to be checked
     * @return the Piece at the given test position
     */
    public Piece pieceAtTest(int posTest) {
        for (int i = 0; i < pieceCount; i++) {
            if (pieces[i].getPosTest() == posTest)
                return pieces[i];
        }
        return null;
    }
    
    /**
     * Promotes a given pawn to another given piece
     * @param pawn the Pawn piece to be promoted
     * @param piece the target Piece the pawn is to be changed to
     */
    public void replacePawnWith(Pawn pawn, Piece piece) {
        for (int i = 0; i < pieceCount; i++) {
            if (pieces[i].equals(pawn)) pieces[i] = piece;
        }
    }
    
    /**
     * Returns a Boolean describing if the player has a piece at the specified position
     * @param pos the position to be checked
     * @return a Boolean, true if the player has a piece at the given position and false
     * otherwise
     */
    public boolean hasPieceAt(int pos) {
        boolean hasPiece;
        hasPiece = (pieceAt(pos) != null);
        return hasPiece;
    }
    
    /**
     * Returns a Boolean describing if the player has a piece at the specified test position
     * @param posTest the test position to be checked
     * @return a Boolean, true if the player has a piece at the given test position and false
     * otherwise
     */
    public boolean hasPieceAtTest(int posTest) {
        boolean hasPiece;
        hasPiece = (pieceAtTest(posTest) != null);
        return hasPiece;
    }
    
    /**
     * Removes the Piece at a given position from the logical size of the piece array
     * @param pos the position of the piece to be removed 
     */
    public void removePieceAt(int pos) {
        int x = pieceCount - 1;
        for (int i = 0; i < pieceCount; i++) {
            if (pieces[i].getPos() == pos)
                x = i;
        }
        
        Piece temp = pieces[pieceCount - 1];
        pieces[pieceCount - 1] = pieces[x];
        pieces[x] = temp;
        pieceCount--;
    }
    
    /**
     * Checks to see if the current position is a win for PlayerBlack or a stalemate
     */
    public void checkForWin() {
        if (Main.getPlayGraphics() == 0) {
            if (GUIMain.getWhite().hasNoLegalMoves()) {
                if (GUIMain.getWhite().getKing().isInCheck()) {
                    JOptionPane.showMessageDialog(null, "Checkmate!\nBlack wins!");
                    won = true;
                }
                else JOptionPane.showMessageDialog(null, "It's a stalemate!");
                Computer.setComputerRun(false);
                gameOver = true;
            }
        } else {
            if (TextUIMain.getWhite().hasNoLegalMoves()) {
                if (TextUIMain.getWhite().getKing().isInCheck())
                    JOptionPane.showMessageDialog(null, "Checkmate!\nBlack wins!");
                else JOptionPane.showMessageDialog(null, "It's a stalemate!");
                Computer.setComputerRun(false);
                TextUIMain.stopRunning();
                gameOver = true;
            }
        }
    }
}
