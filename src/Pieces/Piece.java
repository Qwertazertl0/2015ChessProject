/*
 * Program: ChessProject
 * This: Piece.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: This abstract class provides the template for the six different types
of pieces in chess. It details all the methods that are common to every piece
including basic position and interaction manipulation.
 */
package Pieces;

import Engine.Main;
import Interface.GUIMain;
import TextInterface.TextUIMain;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
abstract public class Piece {
    
    //Instantiates each piece with all common identical fields. Each piece starts
    //with the hasMoved field as false.
    protected int pos;
    protected int posTest;
    protected boolean hasMoved = false;
    protected boolean hasLegalMoves;
    protected boolean isWhite;
    
    /**
     * General constructor for an extension of the Piece class
     * @param posX the starting x-coordinate
     * @param posY the starting y-coordinate
     * @param isWhite the color, true if white and false if black
     */
    protected Piece(int posX, int posY, boolean isWhite) {
        pos = 8 * (posY - 1) + posX;
        posTest = pos;
        this.isWhite = isWhite;
    }
    
    /**
     * General constructor for an extension of the Piece class
     * @param pos the starting position (1-64)
     * @param isWhite the color, true if white and false if black
     */
    protected Piece(int pos, boolean isWhite) {
        this.pos = pos;
        posTest = pos;
        this.isWhite = isWhite;
    }
    
    /**
     * A necessary method that every subclass must define
     * @return the array of legal moves as defined by a specific subclass (piece type)
     */
    abstract public int[] canMove();
    
    /**
     * Reduces a possible array of moves to an array of legal moves that can be played
     * @param array the raw array of possible moves, not necessarily legal
     * @return an array of every legal position the piece can move to
     */
    protected int[] checkLegality(int[] array) {
        int[] legalMoveArr = new int[array.length];
        
        //Checks each move to see if it would place the king in check
        if (Main.getPlayGraphics() == 0) {
            for (int i = 0; i < array.length; i++) {
                if (isWhite) {
                    //Pretend to capture the piece by moving it to a none-existing square
                    if (GUIMain.getBlack().hasPieceAtTest(array[i])) {
                        GUIMain.getBlack().pieceAtTest(array[i]).moveToTest(65);
                    }
                    //Test if the move would jeopardize the king
                    moveToTest(array[i]);
                    if (GUIMain.getWhite().getKing().isInCheck() == false) {
                        legalMoveArr[i] = array[i];
                        moveToTest(pos);
                    } else moveToTest(pos);
                    //If a piece needed to be displaced, put it back
                    if (GUIMain.getBlack().hasPieceAtTest(65)) {
                        GUIMain.getBlack().pieceAtTest(65).moveToTest(array[i]);
                    }
                } else {
                    //Same code for black
                    if (GUIMain.getWhite().hasPieceAtTest(array[i])) {
                        GUIMain.getWhite().pieceAtTest(array[i]).moveToTest(65);
                    }
                    moveToTest(array[i]);
                    if (GUIMain.getBlack().getKing().isInCheck() == false) {
                        legalMoveArr[i] = array[i];
                        moveToTest(pos);
                    } else moveToTest(pos);
                    if (GUIMain.getWhite().hasPieceAtTest(65)) {
                        GUIMain.getWhite().pieceAtTest(65).moveToTest(array[i]);
                    }
                }
            }
        } else {
            //Exact same code for the TextUI classes
            for (int i = 0; i < array.length; i++) {
                if (isWhite) {
                    if (TextUIMain.getBlack().hasPieceAtTest(array[i])) {
                        TextUIMain.getBlack().pieceAtTest(array[i]).moveToTest(65);
                    }
                    moveToTest(array[i]);
                    if (TextUIMain.getWhite().getKing().isInCheck() == false) {
                        legalMoveArr[i] = array[i];
                        moveToTest(pos);
                    } else moveToTest(pos);
                    if (TextUIMain.getBlack().hasPieceAtTest(65)) {
                        TextUIMain.getBlack().pieceAtTest(65).moveToTest(array[i]);
                    }
                } else {
                    if (TextUIMain.getWhite().hasPieceAtTest(array[i])) {
                        TextUIMain.getWhite().pieceAtTest(array[i]).moveToTest(65);
                    }
                    moveToTest(array[i]);
                    if (TextUIMain.getBlack().getKing().isInCheck() == false) {
                        legalMoveArr[i] = array[i];
                        moveToTest(pos);
                    } else moveToTest(pos);
                    if (TextUIMain.getWhite().hasPieceAtTest(65)) {
                        TextUIMain.getWhite().pieceAtTest(65).moveToTest(array[i]);
                    }
                }
            }
        }
        
        //Determines if a piece can move; used for checkmate purposes
        for (int i = 0; i < legalMoveArr.length; i++) {
            if (legalMoveArr[i] != 0) {
                hasLegalMoves = true;
                break;
            } else hasLegalMoves = false;
        }
        
        //If it not the piece's side to move, automatically empty the array
        if (Main.getPlayGraphics() == 0) {
            if (GUIMain.getTotalMoveCount() % 2 == 1 && isWhite) legalMoveArr = emptyArray(legalMoveArr);
            if (GUIMain.getTotalMoveCount() % 2 == 0 && !isWhite) legalMoveArr = emptyArray(legalMoveArr);
        } else {
            if (TextUIMain.getTotalMoveCount() % 2 == 1 && isWhite) legalMoveArr = emptyArray(legalMoveArr);
            if (TextUIMain.getTotalMoveCount() % 2 == 0 && !isWhite) legalMoveArr = emptyArray(legalMoveArr);
        }
        
        return legalMoveArr;
    }
    
    /**
     * Returns the field hasLegalMoves, which indicates if a piece can legally move
     * @return a boolean, true if the piece can legally move and false otherwise
     */
    public boolean getHasLegalMoves() {
        return hasLegalMoves;
    }
    
    /**
     * Moves the piece to a specified position on the board by changing its position
     * field and its test position field
     * @param column the column or x-coordinate of the new position
     * @param row the row or y-coordinate of the new position
     */
    public final void moveTo(int column, int row) {
        if (8 * (row - 1) + column != this.pos) {
            hasMoved();
        }
        pos = 8 * (row - 1) + column;
        posTest = pos;
    }
    
    /**
     * Moves the piece to a specified position on the board by changing its position
     * field and its test position field
     * @param pos the new position on the board
     */
    public final void moveTo(int pos) {
        if (pos != this.pos) {
            hasMoved();
        }
        this.pos = pos;
        posTest = pos;
    }
    
    /**
     * Moves the test position field only so the computer may check if the resulting
     * position is legal
     * @param column the x-coordinate of the new test position
     * @param row the y-coordinate of the new test position
     */
    public final void moveToTest(int column, int row) {
        posTest = 8 * (row - 1) + column;
    }
    
    /**
     * Moves the test position field only so the computer may check if the resulting
     * position is legal
     * @param pos the new test position
     */
    public final void moveToTest(int pos) {
        posTest = pos;
    }
    
    /**
     * Returns the true position of the piece
     * @return the position
     */
    public final int getPos() {
        return pos;
    }
    
    /**
     * Returns the test position of the piece
     * @return the test position
     */
    public final int getPosTest() {
        return posTest;
    }
    
    /**
     * Returns the true x-coordinate of the piece
     * @return the true x-coordinate
     */
    public final int getPosX() {
        int x = pos % 8;
        if (x == 0) x = 8;
        return x;
    }
    
    /**
     * Returns the true y-coordinate of the piece
     * @return the true x-coordinate
     */
    public final int getPosY() {
        int y = (pos - (pos % 8)) / 8 + 1;
        if (pos % 8 == 0) y -= 1;
        return y;
    }
    
    /**
     * Returns the test x-coordinate of the piece
     * @return the test x-coordinate
     */
    public final int getPosXTest() {
        int x = posTest % 8;
        if (x == 0) x = 8;
        return x;
    }
    
    /**
     * Returns the test y-coordinate of the piece
     * @return the test y-coordinate
     */
    public final int getPosYTest() {
        int y = (posTest - (posTest % 8)) / 8 + 1;
        if (posTest % 8 == 0) y -= 1;
        return y;
    }
    
    /**
     * Checks a position to see if it is part of the array of legal moves
     * @param position the position to be checked
     * @return a boolean, true if moving to the position is legal and false otherwise
     */
    public boolean canMoveTo(int position) {
        for (int i = 0; i < canMove().length; i++) {
            canMove();
            if (position == canMove()[i])
                return true;
        }
        return false;
    }
    
    /**
     * Checks if an array of board positions is completely empty or devoid of pieces
     * @param array the array of positions to be checked
     * @return a boolean, true if the positions are completely empty and false otherwise
     */
    public boolean checkIfEmpty(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (Main.getPlayGraphics() == 0) {
                if (GUIMain.getWhite().hasPieceAt(array[i]) || GUIMain.getBlack().hasPieceAt(array[i]))
                    return false;
            } else {
                if (TextUIMain.getWhite().hasPieceAt(array[i]) || TextUIMain.getBlack().hasPieceAt(array[i]))
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Replaces every element in a provided array with a zero
     * @param array the array to be emptied
     * @return a zeroed array of the same size as the provided array
     */
    public int[] emptyArray(int[] array) {
        int[] empty = new int[array.length];
        return empty;
    }
    
    /**
     * Sets the boolean hasMoved to true, indicating that the piece has moved. The
     * piece should never revert this field so a reversible mutator is unnecessary.
     */
    private void hasMoved() {
        hasMoved = true;
    }
    
    /**
     * Returns the hasMoved field which indicates if a piece has moved yet
     * @return a boolean, true if the piece has moved and false otherwise
     */
    public boolean getHasMoved() {
        return hasMoved;
    }
    
    /**
     * Returns a string representation of the piece. It returns the piece name,
     * the piece position, and the piece color.
     * @return a string representation of the piece
     */
    @Override
    public String toString() {
        String color;
        if (isWhite) color = "White";
        else color = "Black";
        return "Piece: " + this.getClass().getCanonicalName() + "\nPosition: " +
                pos + "\nColor: " + color;
    }
    
    /**
     * Converts a position in a two-coordinate form to a single integer form
     * @param column the x-coordinate
     * @param row the y-coordinate
     * @return the same position but in its single integer form
     */
    public static int toPos(int column, int row) {
        int position = 8 * (row - 1) + column;
        return position;
    }
    
    /**
     * Returns the x-coordinate of a given position in its single integer form
     * @param position the position to be converted
     * @return the x-coordinate of the given position
     */
    public static int toPosX(int position) {
        int x = position % 8;
        if (x == 0) x = 8;
        return x;
    }
    
    /**
     * Returns the y-coordinate of a given position in its single integer form
     * @param position the position to be converted
     * @return the y-coordinate of the given position
     */
    public static int toPosY(int position) {
        int y = (position - (position % 8)) / 8 + 1;
        if (position % 8 == 0) y -= 1;
        return y;
    }
    
    /**
     * Returns the possible movements of a straight-moving piece (Queen, Rook, or Bishop).
     * However, the array of movements may not necessarily be legal.
     * @param p an integer differentiating between Queen, Rook, and Bishop. A 3
     * returns Queen movement, a 2 returns Rook movement, and a 1 returns Bishop movement
     * @return a raw integer array of possible positions to move to, but not necessarily legal
     */
    protected int[] canMoveBishopRookQueen(int p) {
        int[] canMoveArr = new int[27 - (-6 * p * p + 17 * p + 3)];
        int x = getPosX(), y = getPosY();
        int count = 0;
        
        //Loops to check straight movement in each of eight directions
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                //Ignore specific cases (or directions) for specific pieces
                if (i == 0 && j == 0) continue;
                if (p == 2 && (Math.abs(i + j) == 2 || (i + j) == 0)) continue;
                if (p == 1 && Math.abs(i + j) == 1) continue;
                //A continuous loop exploring a single direction for possible moves
                boolean findNext = true;
                while (findNext) {
                    if (Main.getPlayGraphics() == 0) {
                        int posThis = toPos(x + i, y + j);
                        if (x + i < 9 && x + i > 0 && y + j < 9 && y + j > 0
                                && !GUIMain.getWhite().hasPieceAt(posThis)
                                && !GUIMain.getBlack().hasPieceAt(posThis)) {
                            //If square is empty, add it and move to the next square
                            canMoveArr[count] = posThis;
                            count++;
                            x += i;
                            y += j;
                        } else if (x + i > 8 || x + i < 1 || y + j > 8 || y + j < 1) {
                            //If square is beyond the board boundaries, move to the next direction
                            findNext = false;
                        } else if (GUIMain.getWhite().hasPieceAt(posThis)) {
                            //If there is a white piece on the square, move to the next direction
                            //but add it if the piece to move is black (capture possibility)
                            if (isWhite) findNext = false;
                            else {
                                canMoveArr[count] = posThis;
                                count++;
                                findNext = false;
                            }
                        } else if (GUIMain.getBlack().hasPieceAt(posThis)){
                            //If there is a black piece on the square, move to the next direction
                            //but add it if the piece to move is white (capture possibility)
                            if (isWhite) {
                                canMoveArr[count] = posThis;
                                count++;
                                findNext = false;
                            } else {
                                findNext = false;
                            }
                        } else findNext = false; //Technically unnecessary but included for insurance
                    } else {
                        //The exact same code as the above loops, but for the TextUI classes instead
                        int posThis = toPos(x + i, y + j);
                        if (x + i < 9 && x + i > 0 && y + j < 9 && y + j > 0
                                && !TextUIMain.getWhite().hasPieceAt(posThis)
                                && !TextUIMain.getBlack().hasPieceAt(posThis)) {
                            canMoveArr[count] = posThis;
                            count++;
                            x += i;
                            y += j;
                        } else if (x + i > 8 || x + i < 1 || y + j > 8 || y + j < 1) {
                            findNext = false;
                        } else if (TextUIMain.getWhite().hasPieceAt(posThis)) {
                            if (isWhite) findNext = false;
                            else {
                                canMoveArr[count] = posThis;
                                count++;
                                findNext = false;
                            }
                        } else if (TextUIMain.getBlack().hasPieceAt(posThis)){
                            if (isWhite) {
                                canMoveArr[count] = posThis;
                                count++;
                                findNext = false;
                            } else {
                                findNext = false;
                            }
                        } else findNext = false;
                    }
                }
                //Return square search to current piece position to start looking in another direction
                x = getPosX();
                y = getPosY();
            }
        }
        return canMoveArr;
    }
}
