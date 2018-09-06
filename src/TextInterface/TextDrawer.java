/*
 * Program: ChessProject
 * This: TextDrawer.java
 * Date: November 12, 2015
 * Author: Maxwell Jong
 * PurnewPose: A class to hold all text-output methods.
 */

package TextInterface;


import Pieces.*;
import java.util.Scanner;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class TextDrawer {
    
    private final String top = " _ _ _ ";
    private final String next = "|     |";
    private final String bottom = "|_ _ _|";
    private final String top2 = "_ _ _ ";
    private final String next2 = "     |";
    private final String bottom2 = "_ _ _|";
    
    private final Scanner userInput = new Scanner(System.in);
    private boolean validInput = false;
    
    /**
     * Constructor used to instantiate the object. It is empty and specifies nothing
     */
    public TextDrawer() {}
    
    /**
     * Prints out the current board with each piece in its specified position
     */
    public void drawBoard() {
        int pos = 1;
        for (int j = 0; j < 8; j++) {
            if (j == 0) {
                for (int i = 0; i < 8; i++) {
                    if (i == 0) System.out.print("  " + top);
                    else System.out.print(top2);
                }
                System.out.println("");
                for (int i = 0; i < 8; i++) {
                    if (i == 0) System.out.print("  " + next);
                    else System.out.print(next2);
                }
                System.out.println("");
                for (int i = 0; i < 8; i++) {
                    if (i == 0) System.out.print((8 - j) + " " + drawPieceLine(pos));
                    else System.out.print(drawPieceLine2(pos));
                    pos++;
                }
                System.out.println("");
                for (int i = 0; i < 8; i++) {
                    if (i == 0) System.out.print("  " + bottom);
                    else System.out.print(bottom2);
                }
                System.out.println("");
            } else {
                for (int i = 0; i < 8; i++) {
                    if (i == 0) System.out.print("  " + next);
                    else System.out.print(next2);
                }
                System.out.println("");
                for (int i = 0; i < 8; i++) {
                    if (i == 0) System.out.print((8 - j) + " " + drawPieceLine(pos));
                    else System.out.print(drawPieceLine2(pos));
                    pos++;
                }
                System.out.println("");
                for (int i = 0; i < 8; i++) {
                    if (i == 0) System.out.print("  " + bottom);
                    else System.out.print(bottom2);
                }
                System.out.println("");
            }
        }
        System.out.println("     A     B     C     D     E     F     G     H\n");
    }

    /**
     * Prints the section of the board that denotes piece position. It prints the
     * letter of the piece at the specified position.
     * @param pos the specified board position (1-64)
     * @return a String, which in an ASCII-art like fashion, represents a board
     * square section denoting which piece, if any, lies there
     */
    private String drawPieceLine(int pos) {
        String type = " ";
        //Note the near identicality to the DisplayPieces.java paintComponent method
        if (TextUIMain.getWhite().hasPieceAt(pos)) {
            if (TextUIMain.getWhite().pieceAt(pos) instanceof King) type = "K";
            else if (TextUIMain.getWhite().pieceAt(pos) instanceof Queen) type = "Q";
            else if (TextUIMain.getWhite().pieceAt(pos) instanceof Bishop) type = "B";
            else if (TextUIMain.getWhite().pieceAt(pos) instanceof Knight) type = "N";
            else if (TextUIMain.getWhite().pieceAt(pos) instanceof Rook) type = "R";
            else if (TextUIMain.getWhite().pieceAt(pos) instanceof Pawn) type = "W";
        } else if (TextUIMain.getBlack().hasPieceAt(pos)) {
            if (TextUIMain.getBlack().pieceAt(pos) instanceof King) type = "k";
            else if (TextUIMain.getBlack().pieceAt(pos) instanceof Queen) type = "q";
            else if (TextUIMain.getBlack().pieceAt(pos) instanceof Bishop) type = "b";
            else if (TextUIMain.getBlack().pieceAt(pos) instanceof Knight) type = "n";
            else if (TextUIMain.getBlack().pieceAt(pos) instanceof Rook) type = "r";
            else if (TextUIMain.getBlack().pieceAt(pos) instanceof Pawn) type = "p";
        }
        String string = "|  " + type + "  |";
        return string;
    }
    
    /**
     * Modification of the drawPieceLine method for board aesthetics
     * @param pos the board position
     * @return a String representation of the piece, if any, at the specified position
     */
    private String drawPieceLine2(int pos) {
        String string = drawPieceLine(pos);
        String cutString = string.substring(1);
        return cutString;
    }
    
    /**
     * A series of input prompts to the user to set game parameters
     */
    public void setGameParameters() {
        while (validInput == false) {
            System.out.print("Do you wish to play the computer? (Y/N): ");
            String temp = userInput.next();
            checkForQuit(temp);
            if (temp.toUpperCase().equals("Y")) {
                validInput = true;
                TextUIMain.setPlayComputer(true);
            }
            if (temp.toUpperCase().equals("N")) {
                validInput = true;
                TextUIMain.setPlayComputer(false);
            }
        }
        
        validInput = false;
        if (TextUIMain.getPlayComputer()) { 
            while (validInput == false) {
                System.out.print("Enter \"White\" or \"Black\": ");
                String temp = userInput.next();
                checkForQuit(temp);
                if (temp.toUpperCase().equals("WHITE")) {
                    validInput = true;
                    TextUIMain.setPlayWhiteInt(0);
                }
                if (temp.toUpperCase().equals("BLACK")) {
                    validInput = true;
                    TextUIMain.setPlayWhiteInt(1);
                }
            }
        }
    }
    
    /**
     * Simple quitting method that terminates the program upon the input of "q"
     * @param input the input to be checked for "q"
     */
    private void checkForQuit(String input) {
        if (input.equalsIgnoreCase("q")) {
            System.out.println("Thanks for playing Jong - Text-Based Chess!");
            System.exit(0);
        }
    }

    /**
     * A series of user prompts to make a valid chess move
     */
    public void userMove() {
        int x = 0, y = 0, oldPos, newPos;
        validInput = false;
        while (!validInput) {
            System.out.print("Enter the position of the piece you want to move: ");
            String temp = userInput.next();
            checkForQuit(temp);
            x = 0; y = 0;
            if (temp.length() == 2) {
                for (int i = 0; i < 8; i++) {
                    String test = "ABCDEFGH ";
                    if (temp.substring(0, 1).toUpperCase().equals(test.substring(i, i + 1)))
                        x = i + 1;
                }
                for (int i = 0; i < 8; i++) {
                    String test = "12345678 ";
                    if (temp.substring(1).equals(test.substring(i, i + 1)))
                        y = 8 - i;
                }
                if (TextUIMain.getPlayWhiteInt() == 0) {
                    if (x != 0 && y != 0 && TextUIMain.getWhite().hasPieceAt(Piece.toPos(x, y))) {
                        TextUIMain.getWhite().pieceAt(Piece.toPos(x, y)).canMove();
                        if (TextUIMain.getWhite().pieceAt(Piece.toPos(x, y)).getHasLegalMoves() == true) {
                            validInput = true;
                        } else {
                            System.out.println("You cannot move that piece.");
                        }
                    } else {
                        System.out.println("Error. Either you have entered an invalid board position or"
                                + "\nyou have no piece at the position you entered.");
                    }
                } else {
                    if (x != 0 && y != 0 && TextUIMain.getBlack().hasPieceAt(Piece.toPos(x, y))) {
                        TextUIMain.getBlack().pieceAt(Piece.toPos(x, y)).canMove();
                        if (TextUIMain.getBlack().pieceAt(Piece.toPos(x, y)).getHasLegalMoves()) {
                            validInput = true;
                        } else {
                            System.out.println("You cannot move that piece.");
                        }
                    }
                }
            } else {
                System.out.println("Invalid input. Enter a column and row (e.g. E2).");
            }
        }
        oldPos = Piece.toPos(x, y);
        
        validInput = false;
        while (!validInput) {
            System.out.print("Which square do you want to move it to? (Enter 'b' to go back): ");
            String temp = userInput.next();
            if (temp.equalsIgnoreCase("b")) {
                userMove(); //Restarts the method
            } else {
                checkForQuit(temp);
                x = 0; y = 0;
                if (temp.length() == 2) {
                    for (int i = 0; i < 8; i++) {
                        String test = "ABCDEFGH ";
                        if (temp.substring(0, 1).toUpperCase().equals(test.substring(i, i + 1)))
                            x = i + 1;
                    }
                    for (int i = 0; i < 8; i++) {
                        String test = "12345678 ";
                        if (temp.substring(1).toUpperCase().equals(test.substring(i, i + 1)))
                            y = 8 - i;
                    }
                    if (TextUIMain.getPlayWhiteInt() == 0) {
                        if (x != 0 && y != 0 && TextUIMain.getWhite().pieceAt(oldPos).canMoveTo(Piece.toPos(x, y))) 
                            validInput = true;
                    } else {
                        if (x != 0 && y != 0 && TextUIMain.getBlack().pieceAt(oldPos).canMoveTo(Piece.toPos(x, y))) 
                            validInput = true;
                    }
                }
            }
        }
        newPos = Piece.toPos(x, y);
        
        if (newPos != oldPos) {
            //Following game logic is near identical to certain subsections of the 
            //Computer.java and GUIMain.java code for game logic
            if (TextUIMain.getPlayWhiteInt() == 0) {
                //King checks
                if (TextUIMain.getWhite().pieceAt(oldPos) instanceof King && (newPos == 59 || newPos == 63) &&
                        !TextUIMain.getWhite().pieceAt(oldPos).getHasMoved()) {
                    if (newPos == 59) TextUIMain.getWhite().pieceAt(57).moveTo(60);
                    else TextUIMain.getWhite().pieceAt(64).moveTo(62);
                }
                //Pawn checks
                if (TextUIMain.getWhite().pieceAt(oldPos) instanceof Pawn) {
                    if (Math.abs(newPos - oldPos) == 16)
                        ((Pawn) TextUIMain.getWhite().pieceAt(oldPos)).setEnPassantMove(TextUIMain.getWhite().getMoveCount());
                    if (!TextUIMain.getBlack().hasPieceAt(newPos) && (oldPos - newPos == 7 || oldPos - newPos == 9))
                        TextUIMain.getBlack().removePieceAt(newPos + 8);
                    if (Piece.toPosY(newPos) == 1) ((Pawn) TextUIMain.getWhite().pieceAt(oldPos)).promotePiece();
                }
                //Logic updates
                TextUIMain.getWhite().pieceAt(oldPos).moveTo(newPos);
                if (TextUIMain.getBlack().hasPieceAt(newPos)) TextUIMain.getBlack().removePieceAt(newPos);
                TextUIMain.getWhite().incMoveCount();
                TextUIMain.getWhite().checkForWin();
            } else {
                if (TextUIMain.getBlack().pieceAt(oldPos) instanceof King && (newPos == 3 || newPos == 7) &&
                        !TextUIMain.getBlack().pieceAt(oldPos).getHasMoved()) {
                    if (newPos == 3) TextUIMain.getBlack().pieceAt(1).moveTo(4);
                    else TextUIMain.getBlack().pieceAt(8).moveTo(6);
                }
                if (TextUIMain.getBlack().pieceAt(oldPos) instanceof Pawn) {
                    if (Math.abs(newPos - oldPos) == 16)
                        ((Pawn) TextUIMain.getBlack().pieceAt(oldPos)).setEnPassantMove(TextUIMain.getBlack().getMoveCount());
                    if (!TextUIMain.getWhite().hasPieceAt(newPos) && (oldPos - newPos == -7 || oldPos - newPos == -9))
                        TextUIMain.getWhite().removePieceAt(newPos - 8);
                    if (Piece.toPosY(newPos) == 8) ((Pawn) TextUIMain.getBlack().pieceAt(oldPos)).promotePiece();
                }
                TextUIMain.getBlack().pieceAt(oldPos).moveTo(newPos);
                if (TextUIMain.getWhite().hasPieceAt(newPos)) TextUIMain.getWhite().removePieceAt(newPos);
                TextUIMain.getBlack().incMoveCount();
                TextUIMain.getBlack().checkForWin();
            }
        }
    }
}
