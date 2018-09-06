/*
 * Program: ChessProject
 * This: Main.java
 * Date: October 30, 2015
 * Author: Maxwell Jong
 * Purpose: This is the main class that runs the chess engine and graphics for my
version of click-controlled or text-based chess. It controls initial user input and the 
instantiation of the UI. Despite this, the central class of the program is either
GUIMain.java in the Interface package or TextUIMain.java in the TextInterface package.
 */
package Engine;

import Interface.DisplayPieces;
import Interface.GUIMain;
import TextInterface.TextUIMain;
import java.io.IOException;
import javax.swing.JOptionPane;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class Main {
    
    private static int playGraphics;
    
    /**
     * The main method that controls the version of chess to run and the initial
     * side to play.
     * @param args
     * @throws IOException when the piece sprite file read in Interface.Highlights.java
     * is missing
     */
    public static void main(String[] args) throws IOException {
        Object[] yesNo = {"Yes", "No"};
        playGraphics = JOptionPane.showOptionDialog(null, "Would you like to play with graphics?", 
                "Jong - Eagles Chess Engine", JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, yesNo,  null);
        
        //Initiates either the graphics or text-based chess program
        if (playGraphics == 0) {
            Object[] colors = {"White", "Black"};
            int color = JOptionPane.showOptionDialog(null, "Which side do you want to play?", 
                "Jong - Eagles Chess Engine", JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, colors,  null);
            GUIMain chessGame = new GUIMain(color);
            if (color == 1) DisplayPieces.flipBoard();
            chessGame.setVisible(true);
        } else {
            TextUIMain textChess = new TextUIMain();
        }
        
    }
    
    /**
     * Returns the integer specifying if the program is running text-based or graphics
     * based chess. This allows the program to choose the right UIMain to reference
     * its methods
     * @return the integer specifying graphics display, a 0 for graphics and a 1
     * for the text-based option
     */
    public static int getPlayGraphics() {
        return playGraphics;
    }
}
