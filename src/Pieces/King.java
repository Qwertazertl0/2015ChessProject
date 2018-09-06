/*
 * Program: ChessProject
 * This: King.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: The King class codes the specific movement and game logic for the chess
piece called a king.
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
public class King extends Piece {

    /**
     * Constructs a King piece for a specified color
     * @param posX the starting x-coordinate
     * @param posY the starting y-coordinate
     * @param isWhite the color specifier, true if white and false if black
     */
    public King (int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite);
    }

    /**
     * Returns an integer array of the legal moves this King can make
     * @return an array of legal moves (integers)
     */
    @Override
    public int[] canMove() {
        int[] normalMoveArr = new int[8];
        int x = getPosX(), y = getPosY();
        
        //First generates possible movements within board boundaries
        if (x - 1 > 0 && y + 1 < 9) normalMoveArr[0] = toPos(x - 1, y + 1);
        if (x - 1 > 0 && y - 1 > 0) normalMoveArr[1] = toPos(x - 1, y - 1);
        if (x - 1 > 0) normalMoveArr[2] = toPos(x - 1, y);
        if (y + 1 < 9) normalMoveArr[3] = toPos(x, y + 1);
        if (y - 1 > 0) normalMoveArr[4] = toPos(x, y - 1);
        if (x + 1 < 9) normalMoveArr[5] = toPos(x + 1, y);
        if (x + 1 < 9 && y + 1 < 9) normalMoveArr[6] = toPos(x + 1, y + 1);
        if (x + 1 < 9 && y - 1 > 0) normalMoveArr[7] = toPos(x + 1, y - 1);
        
        int[] canMoveArr = new int[8];
        if (Main.getPlayGraphics() == 0) {
            //Removes moves blocked by same colored pieces
            if (isWhite == true) 
                for (int i = 0; i < 8; i++) {
                    if (!(GUIMain.getWhite().hasPieceAt(normalMoveArr[i])))
                        canMoveArr[i] = normalMoveArr[i];
                }
            else 
                for (int i = 0; i < 8; i++) {
                    if (!(GUIMain.getBlack().hasPieceAt(normalMoveArr[i])))
                        canMoveArr[i] = normalMoveArr[i];
                }

            //Castling code (case-by-case checking)
            int[] legalMoveArr = checkLegality(canMoveArr); //Called twice to prevent castling through check
            if (hasMoved == false && GUIMain.getWhite().pieceAt(64) != null 
                    && checkIfEmpty(new int[] {62, 63}) && isWhite 
                    && GUIMain.getWhite().pieceAt(64).hasMoved == false
                    && contains(legalMoveArr, 62))
                legalMoveArr[6] = 63;
            if (hasMoved == false && GUIMain.getBlack().pieceAt(8) != null 
                    && checkIfEmpty(new int[] {6, 7}) && !isWhite 
                    && GUIMain.getBlack().pieceAt(8).hasMoved == false
                    && contains(legalMoveArr, 6))
                legalMoveArr[7] = 7;
            if (hasMoved == false && GUIMain.getWhite().pieceAt(57) != null 
                    && checkIfEmpty(new int[] {58, 59, 60}) && isWhite 
                    && GUIMain.getWhite().pieceAt(57).hasMoved == false
                    && contains(legalMoveArr, 60))
                legalMoveArr[0] = 59;
            if (hasMoved == false && GUIMain.getBlack().pieceAt(1) != null 
                    && checkIfEmpty(new int[] {2, 3, 4}) && !isWhite 
                    && GUIMain.getBlack().pieceAt(1).hasMoved == false
                    && contains(legalMoveArr, 4))
                legalMoveArr[1] = 3;
            
            legalMoveArr = checkLegality(legalMoveArr);
            return legalMoveArr;
        } else {
            //TextUI version (identical)
            if (isWhite == true) 
                for (int i = 0; i < 8; i++) {
                    if (!(TextUIMain.getWhite().hasPieceAt(normalMoveArr[i])))
                        canMoveArr[i] = normalMoveArr[i];
                }
            else 
                for (int i = 0; i < 8; i++) {
                    if (!(TextUIMain.getBlack().hasPieceAt(normalMoveArr[i])))
                        canMoveArr[i] = normalMoveArr[i];
                }

            int[] legalMoveArr = checkLegality(canMoveArr);
            if (hasMoved == false && TextUIMain.getWhite().pieceAt(64) != null 
                    && checkIfEmpty(new int[] {62, 63}) && isWhite 
                    && TextUIMain.getWhite().pieceAt(64).hasMoved == false
                    && contains(legalMoveArr, 62))
                legalMoveArr[6] = 63;
            if (hasMoved == false && TextUIMain.getBlack().pieceAt(8) != null 
                    && checkIfEmpty(new int[] {6, 7}) && !isWhite 
                    && TextUIMain.getBlack().pieceAt(8).hasMoved == false
                    && contains(legalMoveArr, 6))
                legalMoveArr[7] = 7;
            if (hasMoved == false && TextUIMain.getWhite().pieceAt(57) != null 
                    && checkIfEmpty(new int[] {58, 59, 60}) && isWhite 
                    && TextUIMain.getWhite().pieceAt(57).hasMoved == false
                    && contains(legalMoveArr, 60))
                legalMoveArr[0] = 59;
            if (hasMoved == false && TextUIMain.getBlack().pieceAt(1) != null 
                    && checkIfEmpty(new int[] {2, 3, 4}) && !isWhite 
                    && TextUIMain.getBlack().pieceAt(1).hasMoved == false
                    && contains(legalMoveArr, 4))
                legalMoveArr[1] = 3;
        
        legalMoveArr = checkLegality(legalMoveArr);
        return legalMoveArr;
        }
    }
    
    /**
     * Checks to see if an array contains a specific member
     * @param array the array to be checked
     * @param pos the member to be looked for
     * @return a boolean, true if the array contains the member and false otherwise
     */
    private boolean contains(int[] array, int pos) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == pos) return true;
        }
        return false;
    }
    
    /**
     * Checks to see if the king is or would be in check. This method only considers
     * each pieces test position rather than their actual positions
     * @return a boolean, true if the king is in check and false otherwise
     */
    public boolean isInCheck() {
        //Note that each section essentially assumes the king as being able to move
        //like any of the six pieces, since in general, most every piece's moves are
        //reversible. For example, if the king tries moves like a knight and finds an
        //enemy knight in its possible movements, it is in check.
        int x = getPosXTest(), y = getPosYTest();
        
        //Checks for an adjacent king or pawn that puts this king in check
        int[] kingMoveArr = new int[8];
        if (x - 1 > 0 && y + 1 < 9) kingMoveArr[0] = toPos(x - 1, y + 1);
        if (x - 1 > 0 && y - 1 > 0) kingMoveArr[1] = toPos(x - 1, y - 1);
        if (x - 1 > 0) kingMoveArr[2] = toPos(x - 1, y);
        if (y + 1 < 9) kingMoveArr[3] = toPos(x, y + 1);
        if (y - 1 > 0) kingMoveArr[4] = toPos(x, y - 1);
        if (x + 1 < 9) kingMoveArr[5] = toPos(x + 1, y);
        if (x + 1 < 9 && y + 1 < 9) kingMoveArr[6] = toPos(x + 1, y + 1);
        if (x + 1 < 9 && y - 1 > 0) kingMoveArr[7] = toPos(x + 1, y - 1);
        for (int i = 0; i < kingMoveArr.length; i++) {
            if (Main.getPlayGraphics() == 0) {
                if (isWhite && GUIMain.getBlack().hasPieceAtTest(kingMoveArr[i])) {
                    if (GUIMain.getBlack().pieceAtTest(kingMoveArr[i]) instanceof Pawn && (i == 1
                            || i == 7))
                        return true;
                    if (GUIMain.getBlack().pieceAtTest(kingMoveArr[i]) instanceof King)
                        return true;
                }
                if (!isWhite && GUIMain.getWhite().hasPieceAtTest(kingMoveArr[i])) {
                    if (GUIMain.getWhite().pieceAtTest(kingMoveArr[i]) instanceof Pawn && (i == 0
                            || i == 6))
                        return true;
                    if (GUIMain.getWhite().pieceAtTest(kingMoveArr[i]) instanceof King)
                        return true;
                }
            } else {
                if (isWhite && TextUIMain.getBlack().hasPieceAtTest(kingMoveArr[i])) {
                    if (TextUIMain.getBlack().pieceAtTest(kingMoveArr[i]) instanceof Pawn && (i == 1
                            || i == 7))
                        return true;
                    if (TextUIMain.getBlack().pieceAtTest(kingMoveArr[i]) instanceof King)
                        return true;
                }
                if (!isWhite && TextUIMain.getWhite().hasPieceAtTest(kingMoveArr[i])) {
                    if (TextUIMain.getWhite().pieceAtTest(kingMoveArr[i]) instanceof Pawn && (i == 0
                            || i == 6))
                        return true;
                    if (TextUIMain.getWhite().pieceAtTest(kingMoveArr[i]) instanceof King)
                        return true;
                }
            }
        }
        
        //Checks for straight moving pieces (Bishop, Rook, or Queen). See the method
        //"canMoveBishopRookQueen" in the Piece abstract class for more detailed explanations
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                boolean findNext = true;
                while (findNext) {
                    int posThis = toPos(x + i, y + j);
                    if (Main.getPlayGraphics() == 0) {
                        if (x + i < 9 && x + i > 0 && y + j < 9 && y + j > 0
                                && !GUIMain.getWhite().hasPieceAtTest(posThis)
                                && !GUIMain.getBlack().hasPieceAtTest(posThis)) {
                            x += i;
                            y += j;
                        } else if (x + i > 8 || x + i < 1 || y + j > 8 || y + j < 1) {
                            findNext = false;
                        } else if (GUIMain.getWhite().hasPieceAtTest(posThis)) {
                            if (isWhite) findNext = false;
                            else {
                                if (Math.abs(i + j) == 1 && (GUIMain.getWhite().pieceAtTest(posThis)
                                        instanceof Rook || GUIMain.getWhite().pieceAtTest(posThis)
                                        instanceof Queen))
                                    return true;
                                if ((Math.abs(i + j) == 0 || Math.abs(i + j) == 2)
                                        && (GUIMain.getWhite().pieceAtTest(posThis) instanceof Bishop 
                                        || GUIMain.getWhite().pieceAtTest(posThis) instanceof Queen))
                                    return true;
                                findNext = false;
                            }
                        } else if (GUIMain.getBlack().hasPieceAtTest(posThis)){
                            if (isWhite) {
                                if (Math.abs(i + j) == 1 && (GUIMain.getBlack().pieceAtTest(posThis)
                                        instanceof Rook || GUIMain.getBlack().pieceAtTest(posThis)
                                        instanceof Queen))
                                    return true;
                                if ((Math.abs(i + j) == 0 || Math.abs(i + j) == 2)
                                        && (GUIMain.getBlack().pieceAtTest(posThis) instanceof Bishop 
                                        || GUIMain.getBlack().pieceAtTest(posThis) instanceof Queen))
                                    return true;
                                findNext = false;
                            } else {
                                findNext = false;
                            }
                        } else findNext = false;
                    } else {
                        if (x + i < 9 && x + i > 0 && y + j < 9 && y + j > 0
                                && !TextUIMain.getWhite().hasPieceAtTest(posThis)
                                && !TextUIMain.getBlack().hasPieceAtTest(posThis)) {
                            x += i;
                            y += j;
                        } else if (x + i > 8 || x + i < 1 || y + j > 8 || y + j < 1) {
                            findNext = false;
                        } else if (TextUIMain.getWhite().hasPieceAtTest(posThis)) {
                            if (isWhite) findNext = false;
                            else {
                                if (Math.abs(i + j) == 1 && (TextUIMain.getWhite().pieceAtTest(posThis)
                                        instanceof Rook || TextUIMain.getWhite().pieceAtTest(posThis)
                                        instanceof Queen))
                                    return true;
                                if ((Math.abs(i + j) == 0 || Math.abs(i + j) == 2)
                                        && (TextUIMain.getWhite().pieceAtTest(posThis) instanceof Bishop 
                                        || TextUIMain.getWhite().pieceAtTest(posThis) instanceof Queen))
                                    return true;
                                findNext = false;
                            }
                        } else if (TextUIMain.getBlack().hasPieceAtTest(posThis)){
                            if (isWhite) {
                                if (Math.abs(i + j) == 1 && (TextUIMain.getBlack().pieceAtTest(posThis)
                                        instanceof Rook || TextUIMain.getBlack().pieceAtTest(posThis)
                                        instanceof Queen))
                                    return true;
                                if ((Math.abs(i + j) == 0 || Math.abs(i + j) == 2)
                                        && (TextUIMain.getBlack().pieceAtTest(posThis) instanceof Bishop 
                                        || TextUIMain.getBlack().pieceAtTest(posThis) instanceof Queen))
                                    return true;
                                findNext = false;
                            } else {
                                findNext = false;
                            }
                        } else findNext = false;
                    }
                }
                x = getPosXTest();
                y = getPosYTest();
            }
        }
        
        //Checks for knights
        int[] knightMoveArr = new int[8];
        if (x - 1 > 0 && y - 2 > 0) knightMoveArr[0] = toPos(x - 1, y - 2);
        if (x + 1 < 9 && y - 2 > 0) knightMoveArr[1] = toPos(x + 1, y - 2);
        if (x + 2 < 9 && y - 1 > 0) knightMoveArr[2] = toPos(x + 2, y - 1);
        if (x + 2 < 9 && y + 1 < 9) knightMoveArr[3] = toPos(x + 2, y + 1);
        if (x + 1 < 9 && y + 2 < 9) knightMoveArr[4] = toPos(x + 1, y + 2);
        if (x - 1 > 0 && y + 2 < 9) knightMoveArr[5] = toPos(x - 1, y + 2);
        if (x - 2 > 0 && y + 1 < 9) knightMoveArr[6] = toPos(x - 2, y + 1);
        if (x - 2 > 0 && y - 1 > 0) knightMoveArr[7] = toPos(x - 2, y - 1);
        for (int i = 0; i < knightMoveArr.length; i++) {
            if (Main.getPlayGraphics() == 0) {
                if (isWhite && GUIMain.getBlack().hasPieceAtTest(knightMoveArr[i])) {
                    if (GUIMain.getBlack().pieceAtTest(knightMoveArr[i]) instanceof Knight)
                        return true;
                }
                if (!isWhite && GUIMain.getWhite().hasPieceAtTest(knightMoveArr[i])) {
                    if (GUIMain.getWhite().pieceAtTest(knightMoveArr[i]) instanceof Knight)
                        return true;
                }
            } else {
                if (isWhite && TextUIMain.getBlack().hasPieceAtTest(knightMoveArr[i])) {
                    if (TextUIMain.getBlack().pieceAtTest(knightMoveArr[i]) instanceof Knight)
                        return true;
                }
                if (!isWhite && TextUIMain.getWhite().hasPieceAtTest(knightMoveArr[i])) {
                    if (TextUIMain.getWhite().pieceAtTest(knightMoveArr[i]) instanceof Knight)
                        return true;
                }
            }                
        }
        
        //Should no opposing pieces be found, the king is not in check
        return false;
    }
}
