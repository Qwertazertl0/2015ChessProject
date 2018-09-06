/*
 * Program: ChessProject
 * This: Rook.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: The Rook class codes the specific movement and game logic for the chess
piece called a rook.
 */
package Pieces;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class Rook extends Piece {

    /**
     * Constructs a Rook piece for a specified color
     * @param posX the starting x-coordinate
     * @param posY the starting y-coordinate
     * @param isWhite the color specifier, true if white and false if black
     */
    public Rook (int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite);
    }

    /**
     * Returns an integer array of the legal moves this Rook can make
     * @return an array of legal moves (integers)
     */
    @Override
    public int[] canMove() {
        int[] legalMoveArr = checkLegality(canMoveBishopRookQueen(2));
        return legalMoveArr;
    }
    
}
