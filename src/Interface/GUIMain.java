/*
 * Program: ChessProject
 * This: GUIMain.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: The main class of the graphical interface that interacts with the 
user to display and play the classical game of chess. This JFrame extension holds
each graphical components and interprets the player's mouse clicks.
 */
package Interface;

import Engine.Computer;
import Engine.PlayerBlack;
import Engine.PlayerWhite;
import Engine.Position;
import Pieces.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class GUIMain extends JFrame {

    //Instantiate the piece/move-related objects
    private static Container pane;
    private static DisplayPieces whiteP, blackP;
    private static PlayerWhite white = new PlayerWhite();
    private static PlayerBlack black = new PlayerBlack();
    private static Computer computer = new Computer(true);
    private static Highlights highlights = new Highlights();
    //Instantiate board and other visual objects
    private final Board board = new Board(80);
    private final ChessMenu menuBar = new ChessMenu();
    private final Dimension frameSize = new Dimension(640, 640);
    //Instantiate user-interaction logic variables
    private static int totalMoveCount = 0;
    private static ArrayList<Position> positions = new ArrayList<>();
    private static ArrayList<Position> positionsRepeated = new ArrayList<>();
    private static int fiftyMoveCounter = 0;
    private static boolean threefoldDraw = false;
    private int x, y, sqX, sqY;
    private static int playWhiteInt;
    private static boolean playComputer = false;

    /**
     * Constructs the graphics-based chess program
     * @param playWhite an integer, 0 to play white and 1 to play black
     * @throws IOException 
     */
    public GUIMain(int playWhite) throws IOException {
        playWhiteInt = playWhite;
        pane = getContentPane();
        
        setSize(frameSize);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Jong - Eagles Chess Engine");
        setJMenuBar(menuBar);
        
        whiteP = new DisplayPieces(getWhite().getPieceArray(), "White");
        blackP = new DisplayPieces(getBlack().getPieceArray(), "Black");
        
        pane.add(whiteP, 0);
        pane.add(blackP, 1);
        pane.add(highlights, 2);
        pane.add(board, 3);
        addMouseListener(new BoardListener());
        pack();
        
        positions.add(new Position(white, black));
    }

    /**
     * Private class that interprets the user input (mouse clicks)
     */
    private class BoardListener extends MouseAdapter {

        /**
         * Method that runs on every mouse click
         * @param e mouse event
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            //Determine where the user clicked the mouse
            if (DisplayPieces.getFlipped() == false) {
                x = e.getX() - 3;
                y = e.getY() - 52;
            } else {
                x = -e.getX() + 643;
                y = -e.getY() + 692;
            }

            //Translate the absolute position to a specific board square
            int sqX2 = x - (x % 80);
            int sqY2 = y - (y % 80);
            int pos = getPos((sqX2 + 80) / 80, (sqY2 + 80) / 80);
            int oldPos = getPos((sqX + 80) / 80, (sqY + 80) / 80);
            //Runs through a tree of if-statements to cover all possible cases
            if (!(sqX2 == sqX && sqY2 == sqY)) { //If a different square is clicked
                //White pieces code
                if (highlights.isVisible() == true && getWhite().hasPieceAt(oldPos)
                        && highlights.getSelectedPiece().canMoveTo(pos) && !getWhite().getGameOver()) {
                    //Check for castling
                    if (getWhite().pieceAt(oldPos) instanceof King && (pos == 59 || pos == 63) &&
                            !getWhite().pieceAt(oldPos).getHasMoved()) {
                        if (pos == 59) getWhite().pieceAt(57).moveTo(60);
                        else getWhite().pieceAt(64).moveTo(62);
                    } 
                    //Check for promotion and set-up en-passant
                    if (getWhite().pieceAt(oldPos) instanceof Pawn) {
                        if (Math.abs(pos - oldPos) == 16)
                            ((Pawn) getWhite().pieceAt(oldPos)).setEnPassantMove(getWhite().getMoveCount());
                        if (!getBlack().hasPieceAt(pos) && (oldPos - pos == 7 || oldPos - pos == 9))
                            getBlack().removePieceAt(pos + 8);
                        if (Piece.toPosY(pos) == 1) ((Pawn) getWhite().pieceAt(oldPos)).promotePiece();
                        fiftyMoveCounter = 0;
                        clearPositions();
                    } 
                    //Executes movement and updates game logic
                    getWhite().pieceAt(oldPos).moveTo(pos);
                    getWhite().incMoveCount();
                    if (getBlack().hasPieceAt(pos)) {
                        getBlack().removePieceAt(pos);
                        fiftyMoveCounter = 0;
                        clearPositions();
                    } else fiftyMoveCounter++;
                    highlights.setVisible(false);
                    updateTotalMoveCount();
                    getWhite().checkForWin();
                    if (getBlack().getKing().isInCheck() && !getWhite().getGameOver())
                        JOptionPane.showMessageDialog(null, "Check!");
                    updatePositions();
                } 
                //Black pieces code; identical in structure to the white pieces
                else if (highlights.isVisible() == true && getBlack().hasPieceAt(oldPos)
                        && highlights.getSelectedPiece().canMoveTo(pos) && !getBlack().getGameOver()) {
                    if (getBlack().pieceAt(oldPos) instanceof King && (pos == 3 || pos == 7) &&
                            !getBlack().pieceAt(oldPos).getHasMoved()) {
                        if (pos == 3) getBlack().pieceAt(1).moveTo(4);
                        else getBlack().pieceAt(8).moveTo(6);
                    }
                    if (getBlack().pieceAt(oldPos) instanceof Pawn) {
                        if (Math.abs(pos - oldPos) == 16)
                            ((Pawn) getBlack().pieceAt(oldPos)).setEnPassantMove(getBlack().getMoveCount());
                        if (!getWhite().hasPieceAt(pos) && (oldPos - pos == -7 || oldPos - pos == -9))
                            getWhite().removePieceAt(pos - 8);
                        if (Piece.toPosY(pos) == 8) ((Pawn) getBlack().pieceAt(oldPos)).promotePiece();
                        fiftyMoveCounter = 0;
                        clearPositions();
                    }
                    getBlack().pieceAt(oldPos).moveTo(pos);
                    getBlack().incMoveCount();
                    if (getWhite().hasPieceAt(pos)) {
                        getWhite().removePieceAt(pos);
                        fiftyMoveCounter = 0;
                        clearPositions();
                    } else fiftyMoveCounter++;
                    highlights.setVisible(false);
                    updateTotalMoveCount();
                    getBlack().checkForWin();
                    if (getWhite().getKing().isInCheck() && !getBlack().getGameOver())
                        JOptionPane.showMessageDialog(null, "Check!");
                    updatePositions();
                } 
                //If a invalid/null/other square is selected, update piece selection
                else {
                    highlights.setXY(sqX2, sqY2);
                    highlights.setVisible(true);
                    highlights.setSelectedPiece(null);
                    if (getWhite().hasPieceAt(pos))
                        highlights.setSelectedPiece(getWhite().pieceAt(pos));
                    if (getBlack().hasPieceAt(pos))
                        highlights.setSelectedPiece(getBlack().pieceAt(pos));
                }
                updateTotalMoveCount();
                sqX = sqX2;
                sqY = sqY2;
            } 
            //If the same square is clicked twice
            else { 
                //If the square is lighted, unhighlight it
                if (highlights.isVisible() == true) { 
                    highlights.setVisible(false);
                    highlights.setSelectedPiece(null);
                }
                //Highlight the square, set the piece selection
                else { 
                    highlights.setVisible(true);
                    if (getWhite().hasPieceAt(pos))
                        highlights.setSelectedPiece(getWhite().pieceAt(pos));
                    if (getBlack().hasPieceAt(pos))
                        highlights.setSelectedPiece(getBlack().pieceAt(pos));
                }
                highlights.setXY(sqX2, sqY2);
            }
            
            //Make a computer move if necessary
            if (totalMoveCount % 2 != playWhiteInt && playComputer) {
                if (playWhiteInt == 0) {
                    computer.makeMoveBlack();
                    updatePositions();
                } else {
                    computer.makeMoveWhite();
                    updatePositions();
                }
            }
            updateTotalMoveCount();
        }
    }
    
    /**
     * Converts two integers specifying row and column into a single integer
     * denoting board position (1-64)
     * @param column the column position
     * @param row the row position
     * @return a single integer of the same board position
     */
    private int getPos(int column, int row) {
        int pos = 8 * (row - 1) + column;
        return pos;
    }
    
    /**
     * Returns the total move count
     * @return the total move count
     */
    public static int getTotalMoveCount() {
        return totalMoveCount;
    }
    
    /**
     * Updates the total move count.
     */
    public static void updateTotalMoveCount() {
        totalMoveCount = getWhite().getMoveCount() + getBlack().getMoveCount();
    }
    
    /**
     * Set the computer state
     * @param play a Boolean, true to set the computer to running and false otherwise
     */
    public static void setPlayComputer(boolean play) {
        playComputer = play;
        if (totalMoveCount % 2 != playWhiteInt && playComputer) {
                if (playWhiteInt == 0) computer.makeMoveBlack();
                else computer.makeMoveWhite();
        }
    }
    
    /**
     * Returns the state of the computer
     * @return a Boolean, true if running and false otherwise
     */
    public static boolean getPlayComputer() {
        return playComputer;
    }
    
    /**
     * Restarts the game logic and asks the user to choose a side
     * @throws IOException 
     */
    public static void resetGame() throws IOException {
        Object[] colors = {"White", "Black"};
        int color = JOptionPane.showOptionDialog(null, "Which side do you want to play?", 
                "Jong - Eagles Chess Engine", JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, colors,  null);
        setPlayWhiteInt(color);
        computer = new Computer(true);
        black = new PlayerBlack();
        white = new PlayerWhite();
        clearPositions();
        positions.add(new Position(white, black));
        pane.remove(whiteP);
        pane.remove(blackP);
        whiteP = new DisplayPieces(getWhite().getPieceArray(), "White");
        blackP = new DisplayPieces(getBlack().getPieceArray(), "Black");
        pane.add(whiteP, 0);
        pane.add(blackP, 1);
        highlights.setVisible(false);
        highlights.setSelectedPiece(null);
        pane.repaint();
    }
    
    /**
     * Returns the PlayerWhite object instantiated upon construction
     * @return the current PlayerWhite
     */
    public static PlayerWhite getWhite() {
        return white;
    }
    
    /**
     * Returns the PlayerBlack object instantiated upon construction
     * @return the current PlayerBlack
     */
    public static PlayerBlack getBlack() {
        return black;
    }
    
    /**
     * Return the integer that determines if the player controls the white or black pieces.
     * 0 for white and 1 for black.
     * @return an integer that represents the player side, 0 for white and 1 for black
     */
    public static int getPlayWhiteInt() {
        return playWhiteInt;
    }
    
    /**
     * Sets which side the player will play
     * @param side 0 for white and 1 for black
     */
    private static void setPlayWhiteInt(int side) {
        playWhiteInt = side;
    }
    
    /**
     * Returns the number of moves since the last capture or pawn push. Regulates
     * the 50-move draw rule
     * @return the integer tracking the number of moves since the last capture
     * or pawn push
     */
    public static int getFiftyMoveCounter() {
        return fiftyMoveCounter;
    }
    
    /**
     * Sets the counter monitoring the 50-move rule.
     * @param counter the new value of the counter
     */
    public static void setFiftyMoveCounter(int counter) {
        fiftyMoveCounter = counter;
    }
    
    /**
     * Adds the current position to the position search space to monitor threefold
     * repetition. If the position has been found to be in both the first-time
     * and second-time position arrays, it becomes possible to claim a draw.
     */
    private void updatePositions() {
        Position position = new Position(white, black);
        boolean isRepeated = false;
        threefoldDraw = false;
        
        //Check for first appearance
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).equals(position)) {
                isRepeated = true;
                if (positionsRepeated.isEmpty()) {
                    positionsRepeated.add(position);
                    isRepeated = false;
                }
                break;
            } else if (i == positions.size() - 1) {
                positions.add(position);
                break;
            }
        }

        //Check for second or third appearance
        if (isRepeated) {
            for (int i = 0; i < positionsRepeated.size(); i++) {
                if (positionsRepeated.get(i).equals(position)) {
                    threefoldDraw = true;
                    break;
                } else if (i == positionsRepeated.size() - 1) {
                    positionsRepeated.add(position);
                    break;
                }
            }
        }
    }
    
    /**
     * Clears the position search space monitoring draw by threefold repetition.
     */
    public static void clearPositions() {
        positions.clear();
        positionsRepeated.clear();
        positions.add(new Position(white, black));
    }
    
    /**
     * Returns whether or not the game can be drawn at this moment by threefold repetition.
     * @return a boolean describing if a draw can be claimed by threefold repetition
     */
    public static boolean getThreefoldDraw() {
        return threefoldDraw;
    }
}
