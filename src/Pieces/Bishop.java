/*
 * Program: ChessProject
 * This: Bishop.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: The Bishop class codes the specific movement and game logic for the chess
piece called a bishop.
 */
package Pieces;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class Bishop extends Piece {

    /**
     * Constructs a Bishop piece for a specified color
     * @param posX the starting x-coordinate
     * @param posY the starting y-coordinate
     * @param isWhite the color specifier, true if white and false if black
     */
    public Bishop (int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite);
    }

    /**
     * Returns an integer array of the legal moves this Bishop can make
     * @return an array of legal moves (integers)
     */
    @Override
    public int[] canMove() {
        int[] legalMoveArr = checkLegality(canMoveBishopRookQueen(1));
        return legalMoveArr;
    }
}
