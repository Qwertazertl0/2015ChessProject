/*
 * Program: ChessProject
 * This: Test.java
 * Date: November 10, 2015
 * Author: Maxwell Jong
 * Purpose: This is a simple testing class for isolating portions of code to test.
 */
package Engine;

import javax.swing.JOptionPane;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
//--11/19/15 Note-- Honestly, I did not use this class much, this class was kind of a 
//"just-in-case-I-need-it" kind of thing.
/**
 *
 * @author 12core
 */
public class Test {
    
    public static void main(String[] args) {
        String[] pieceChoices = {"Queen", "Rook", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(null, "Which side do you want to play?", 
                "Jong - Eagles Chess Engine", JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, pieceChoices,  null);
        System.out.println(choice);
    }
}
