/*
 * Program: ChessProject
 * This: TextUIMain.java
 * Date: November 11, 2015
 * Author: Maxwell Jong
 * Purpose: The is the main class for the text-based version of the chess program.
It is essentially analagous to the GUIMain in the Interface package.
 */

package TextInterface;


import Engine.Computer;
import Engine.PlayerBlack;
import Engine.PlayerWhite;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class TextUIMain {

    
    private TextDrawer drawer = new TextDrawer();
    private static PlayerWhite white = new PlayerWhite();
    private static PlayerBlack black = new PlayerBlack();
    private final Computer computer = new Computer(true);
    private static boolean running = true;
    private static boolean playComputer = false;
    
    private static int playWhiteInt = 0;
    private static int totalMoveCount = 0;

    /**
     * Initiates the text-based chess program, analogous to the GUIMain class.
     */
    public TextUIMain() {
        System.out.println("Welcome to Text-Based Chess!");
        System.out.println("Certain advanced draw rules and game conventions present\n"
                + "in the graphics version are not in effect here.");
        System.out.println("Press \"Q\" at any time to quit.");
        drawer.drawBoard();
        System.out.println("Note: Each piece is lettered according to standard chess notation\n"
                + "with the exception of the white pawn, which is here renamed to \"W\".\n"
                + "Here, black is denoted by lowercase-letters.");
        drawer.setGameParameters();
        
        //Basic game loop
        while (running) {
            updateTotalMoveCount();
            if (totalMoveCount % 2 != playWhiteInt && playComputer) {
                if (playWhiteInt == 0) computer.makeMoveBlack();
                else computer.makeMoveWhite();
                drawer.drawBoard();
            } else {
                drawer.userMove();
                drawer.drawBoard();
            }
        }
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
     * Set the computer state
     * @param play a Boolean, true to set the computer to running and false otherwise
     */
    public static void setPlayComputer(boolean play) {
        playComputer = play;
    }
    
    /**
     * Returns the state of the computer
     * @return a Boolean, true if running and false otherwise
     */
    public static boolean getPlayComputer() {
        return playComputer;
    }
    
    /**
     * Returns an integer specifying the player's color
     * @return an integer, 0 for white and 1 for black
     */
    public static int getPlayWhiteInt() {
        return playWhiteInt;
    }
    
    /**
     * Sets which side the player will play
     * @param side 0 for white and 1 for black
     */
    public static void setPlayWhiteInt(int side) {
        playWhiteInt = side;
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
     * Stops the text-based chess program and effectively terminates the program.
     */
    public static void stopRunning() {
        running = false;
    }
}
