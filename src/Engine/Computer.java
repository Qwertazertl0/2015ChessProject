/*
 * Program: ChessProject
 * This: Computer.java
 * Date: November 10, 2015
 * Author: Maxwell Jong
 * Purpose: The computer class controls the automated movement of the side 
opposing the human player. Currently, only random movements are possible to be
made.
 */
package Engine;


import Interface.GUIMain;
import Pieces.King;
import Pieces.Pawn;
import Pieces.Piece;
import TextInterface.TextUIMain;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
//Additional Note: The absurd amount of repetition in this class is rather
//unsightly, but is a result of splitting the players into two classes as well
//as accommodating both the GUIMain and the TextUIMain classes.
/**
 * 
 * @author 12core
 */
public class Computer {
    
    private final Random generator = new Random();
    private static boolean computerRun = true;
    
    /**
     * Constructs a computer that is either running or not
     * @param runComp 
     */
    public Computer(boolean runComp) {
        computerRun = runComp;
    }
    
    /**
     * Makes an automated, but random legal move for the PlayerBlack side
     */
    public void makeMoveBlack() {
        if (computerRun) { //Check the computer run state
            ArrayList<Piece> allMovablePieces = new ArrayList<>();
            ArrayList<Integer> allMoves = new ArrayList<>();
            if (Main.getPlayGraphics() == 0) { //Generate a list of movable pieces
                for (int i = 0; i < GUIMain.getBlack().getPieceCount(); i++) {
                    GUIMain.getBlack().getPieceArray()[i].canMove();
                    if (GUIMain.getBlack().getPieceArray()[i].getHasLegalMoves())
                        allMovablePieces.add(GUIMain.getBlack().getPieceArray()[i]);
                }
            } else { //Copied section for the TextUIMain
                for (int i = 0; i < TextUIMain.getBlack().getPieceCount(); i++) {
                    TextUIMain.getBlack().getPieceArray()[i].canMove();
                    if (TextUIMain.getBlack().getPieceArray()[i].getHasLegalMoves())
                        allMovablePieces.add(TextUIMain.getBlack().getPieceArray()[i]);
                }
            }
            
            //Choose a random piece and generate a list of all its legal moves
            Piece selectedPiece = allMovablePieces.get(generator.nextInt(allMovablePieces.size()));
            for (int i = 0; i < selectedPiece.canMove().length; i++) {
                if (selectedPiece.canMove()[i] != 0)
                    allMoves.add(selectedPiece.canMove()[i]);
            }

            int newPos = allMoves.get(generator.nextInt(allMoves.size()));
            
            if (Main.getPlayGraphics() == 0) { //Game logic checks (similar to those in the GUIMain)
                //Castling checks
                if (selectedPiece instanceof King && (newPos == 3 || newPos == 7) &&
                        !selectedPiece.getHasMoved()) {
                    if (newPos == 3) GUIMain.getBlack().pieceAt(1).moveTo(4);
                    else GUIMain.getBlack().pieceAt(8).moveTo(6);
                } 
                //Special pawn move checks
                if (selectedPiece instanceof Pawn) {
                    ((Pawn) selectedPiece).setEnPassantMove(GUIMain.getBlack().getMoveCount());
                    if (!GUIMain.getWhite().hasPieceAt(newPos) && (selectedPiece.getPos() - newPos == -7 
                            || selectedPiece.getPos() - newPos == -9))
                        GUIMain.getWhite().removePieceAt(newPos - 8);
                    if (Piece.toPosY(newPos) == 8) {
                        int oldPos = selectedPiece.getPos();
                        ((Pawn) selectedPiece).promotePiece("Queen");
                        selectedPiece = GUIMain.getBlack().pieceAt(oldPos);
                    }
                    GUIMain.setFiftyMoveCounter(0);
                    GUIMain.clearPositions();
                } 
                //Movement code
                selectedPiece.moveTo(newPos);
                GUIMain.getBlack().incMoveCount();
                if (GUIMain.getWhite().hasPieceAt(newPos)) {
                    GUIMain.getWhite().removePieceAt(newPos);
                    GUIMain.setFiftyMoveCounter(0);
                    GUIMain.clearPositions();
                }
                //Win-state verification
                GUIMain.updateTotalMoveCount();
                GUIMain.getBlack().checkForWin();
                if (GUIMain.getWhite().getKing().isInCheck() && !GUIMain.getWhite().getGameOver())
                    JOptionPane.showMessageDialog(null, "Check!");
            } 
            //Copied code for the TextUIMain
            else { 
                if (selectedPiece instanceof King && (newPos == 3 || newPos == 7) &&
                        !selectedPiece.getHasMoved()) {
                    if (newPos == 3) TextUIMain.getBlack().pieceAt(1).moveTo(4);
                    else TextUIMain.getBlack().pieceAt(8).moveTo(6);
                }
                if (selectedPiece instanceof Pawn) {
                    ((Pawn) selectedPiece).setEnPassantMove(TextUIMain.getBlack().getMoveCount());
                    if (!TextUIMain.getWhite().hasPieceAt(newPos) && (selectedPiece.getPos() - newPos == -7 
                            || selectedPiece.getPos() - newPos == -9))
                        TextUIMain.getWhite().removePieceAt(newPos - 8);
                    if (Piece.toPosY(newPos) == 8) {
                        int oldPos = selectedPiece.getPos();
                        ((Pawn) selectedPiece).promotePiece("Queen");
                        selectedPiece = GUIMain.getBlack().pieceAt(oldPos);
                    }
                    GUIMain.setFiftyMoveCounter(0);
                    GUIMain.clearPositions();
                }
                selectedPiece.moveTo(newPos);
                TextUIMain.getBlack().incMoveCount();
                if (TextUIMain.getWhite().hasPieceAt(newPos)) {
                    TextUIMain.getWhite().removePieceAt(newPos);
                    GUIMain.setFiftyMoveCounter(0);
                    GUIMain.clearPositions();
                }
                TextUIMain.updateTotalMoveCount();
                TextUIMain.getBlack().checkForWin();
                if (TextUIMain.getWhite().getKing().isInCheck() && !TextUIMain.getWhite().getGameOver())
                    JOptionPane.showMessageDialog(null, "Check!");
            }
        }
    }
    
    /**
     * Makes an automated, but random legal move for the PlayerWhite side
     */
    public void makeMoveWhite() {
        //Identical to makeMoveBlack() with the exception of color, which is flipped
        if (computerRun) {
            ArrayList<Piece> allMovablePieces = new ArrayList<>();
            ArrayList<Integer> allMoves = new ArrayList<>();
            if (Main.getPlayGraphics() == 0) {
                for (int i = 0; i < GUIMain.getWhite().getPieceCount(); i++) {
                    GUIMain.getWhite().getPieceArray()[i].canMove();
                    if (GUIMain.getWhite().getPieceArray()[i].getHasLegalMoves())
                        allMovablePieces.add(GUIMain.getWhite().getPieceArray()[i]);
                }
            } else {
                for (int i = 0; i < TextUIMain.getWhite().getPieceCount(); i++) {
                    TextUIMain.getWhite().getPieceArray()[i].canMove();
                    if (TextUIMain.getWhite().getPieceArray()[i].getHasLegalMoves())
                        allMovablePieces.add(TextUIMain.getWhite().getPieceArray()[i]);
                }
            }

            Piece selectedPiece = allMovablePieces.get(generator.nextInt(allMovablePieces.size()));
            for (int i = 0; i < selectedPiece.canMove().length; i++) {
                if (selectedPiece.canMove()[i] != 0)
                    allMoves.add(selectedPiece.canMove()[i]);
            }

            int newPos = allMoves.get(generator.nextInt(allMoves.size()));
            
            if (Main.getPlayGraphics() == 0) {
                if (selectedPiece instanceof King && (newPos == 59 || newPos == 63) &&
                        !selectedPiece.getHasMoved()) {
                    if (newPos == 59) GUIMain.getWhite().pieceAt(57).moveTo(60);
                    else GUIMain.getWhite().pieceAt(64).moveTo(62);
                }
                if (selectedPiece instanceof Pawn) {
                    ((Pawn) selectedPiece).setEnPassantMove(GUIMain.getWhite().getMoveCount());
                    if (!GUIMain.getBlack().hasPieceAt(newPos) && (selectedPiece.getPos() - newPos == 7 
                            || selectedPiece.getPos() - newPos == 9))
                        GUIMain.getBlack().removePieceAt(newPos + 8);
                    if (Piece.toPosY(newPos) == 1) {
                        int oldPos = selectedPiece.getPos();
                        ((Pawn) selectedPiece).promotePiece("Queen");
                        selectedPiece = GUIMain.getWhite().pieceAt(oldPos);
                    }
                }
                selectedPiece.moveTo(newPos);
                GUIMain.getWhite().incMoveCount();
                if (GUIMain.getBlack().hasPieceAt(newPos)) GUIMain.getBlack().removePieceAt(newPos);
                GUIMain.updateTotalMoveCount();
                GUIMain.getWhite().checkForWin();
                if (GUIMain.getBlack().getKing().isInCheck() && !GUIMain.getBlack().getGameOver())
                    JOptionPane.showMessageDialog(null, "Check!");
            } else {
                if (selectedPiece instanceof King && (newPos == 59 || newPos == 63) &&
                        !selectedPiece.getHasMoved()) {
                    if (newPos == 59) TextUIMain.getWhite().pieceAt(57).moveTo(60);
                    else TextUIMain.getWhite().pieceAt(64).moveTo(62);
                }
                if (selectedPiece instanceof Pawn) {
                    ((Pawn) selectedPiece).setEnPassantMove(TextUIMain.getWhite().getMoveCount());
                    if (!TextUIMain.getBlack().hasPieceAt(newPos) && (selectedPiece.getPos() - newPos == 7 
                            || selectedPiece.getPos() - newPos == 9))
                        TextUIMain.getBlack().removePieceAt(newPos + 8);
                    if (Piece.toPosY(newPos) == 1) {
                        int oldPos = selectedPiece.getPos();
                        ((Pawn) selectedPiece).promotePiece("Queen");
                        selectedPiece = GUIMain.getWhite().pieceAt(oldPos);
                    }
                }
                selectedPiece.moveTo(newPos);
                TextUIMain.getBlack().incMoveCount();
                if (TextUIMain.getBlack().hasPieceAt(newPos)) TextUIMain.getBlack().removePieceAt(newPos);
                TextUIMain.updateTotalMoveCount();
                TextUIMain.getBlack().checkForWin();
                if (TextUIMain.getBlack().getKing().isInCheck() && !TextUIMain.getBlack().getGameOver())
                    JOptionPane.showMessageDialog(null, "Check!");
                
            }
        }
    }
    
    /**
     * Sets the computer state as either running or not running
     * @param state true to set the computer running and false otherwise
     */
    public static void setComputerRun(boolean state) {
        computerRun = state;
    }
}
