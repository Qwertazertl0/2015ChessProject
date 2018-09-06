/*
 * Program: ChessProject
 * This: Queen.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: The Queen class codes the specific movement and game logic for the chess
piece called a queen.
 */
package Pieces;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class Queen extends Piece {
    
    /**
     * Constructs a Queen piece for a specified color
     * @param posX the starting x-coordinate
     * @param posY the starting y-coordinate
     * @param isWhite the color specifier, true if white and false if black
     */
    public Queen (int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite);
    }

    /**
     * Returns an integer array of the legal moves this Queen can make
     * @return an array of legal moves (integers)
     */
    @Override
    public int[] canMove() {
        int[] legalMoveArr = checkLegality(canMoveBishopRookQueen(3));
        return legalMoveArr;
    }
    
}
