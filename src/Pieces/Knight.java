/*
 * Program: ChessProject
 * This: Knight.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: The Knight class codes the specific movement and game logic for the chess
piece called a knight.
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
public class Knight extends Piece {

    /**
     * Constructs a Knight piece for a specified color
     * @param posX the starting x-coordinate
     * @param posY the starting y-coordinate
     * @param isWhite the color specifier, true if white and false if black
     */
    public Knight (int posX, int posY, boolean isWhite) {
        super(posX, posY, isWhite);
    }

    /**
     * Returns an integer array of the legal moves this Knight can make
     * @return an array of legal moves (integers)
     */
    @Override
    public int[] canMove() {
        int[] normalMoveArr = new int[8];
        int x = getPosX(), y = getPosY();
        //Generates the possible knight moves within board boundaries
        if (x - 1 > 0 && y - 2 > 0) normalMoveArr[0] = toPos(x - 1, y - 2);
        if (x + 1 < 9 && y - 2 > 0) normalMoveArr[1] = toPos(x + 1, y - 2);
        if (x + 2 < 9 && y - 1 > 0) normalMoveArr[2] = toPos(x + 2, y - 1);
        if (x + 2 < 9 && y + 1 < 9) normalMoveArr[3] = toPos(x + 2, y + 1);
        if (x + 1 < 9 && y + 2 < 9) normalMoveArr[4] = toPos(x + 1, y + 2);
        if (x - 1 > 0 && y + 2 < 9) normalMoveArr[5] = toPos(x - 1, y + 2);
        if (x - 2 > 0 && y + 1 < 9) normalMoveArr[6] = toPos(x - 2, y + 1);
        if (x - 2 > 0 && y - 1 > 0) normalMoveArr[7] = toPos(x - 2, y - 1);
        
        //Removes moves that are blocked by same colored pieces
        int[] canMoveArr = new int[8];
        if (Main.getPlayGraphics() == 0) {
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
        } else {
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
        }
        
        int[] legalMoveArr = checkLegality(canMoveArr);
        return legalMoveArr;
    }
}
