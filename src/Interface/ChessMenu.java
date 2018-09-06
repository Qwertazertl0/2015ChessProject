/*
 * Program: ChessProject
 * This: ChessMenu.java
 * Date: November 5, 2015
 * Author: Maxwell Jong
 * Purpose: A basic drop-down JMenu that provides the players options to control
the game such as: starting a new game, flipping the board, or turning on the
computer player. It was chosen to be less intrusive to the player than a constantly
visible JPanel.
 */
package Interface;

import Engine.Computer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class ChessMenu extends JMenuBar {
    
    private final JMenu menuOptions = new JMenu("Options");
    private final JMenuItem itemNew = new JMenuItem("New Game");
    private final JMenuItem itemResign = new JMenuItem("Resign");
    private final JMenuItem itemOffer = new JMenuItem("Offer Draw (PvP only)");
    private final JMenuItem itemClaim = new JMenuItem("Claim Draw");
    private final JMenuItem itemFlip = new JMenuItem("Flip Board");
    private final JCheckBoxMenuItem itemComp = new JCheckBoxMenuItem("Turn On Computer");
    private final JCheckBoxMenuItem itemAuto = new JCheckBoxMenuItem("Auto-Board Flip");
    
    private final JMenu menuInfo = new JMenu("Info");
    private final JMenuItem itemInfo = new JMenuItem("Current Game Info");
    private final JMenuItem itemRules = new JMenuItem("Draw Claim Rules");
    
    /**
     * Constructor for the options menu at the top of the program; adds each necessary
     * menu item.
     */
    public ChessMenu() {
        add(menuOptions);
        menuOptions.add(itemNew);
        menuOptions.addSeparator();
        menuOptions.add(itemResign);
        menuOptions.add(itemOffer);
        menuOptions.add(itemClaim);
        menuOptions.addSeparator();
        menuOptions.add(itemFlip);
        menuOptions.add(itemComp);
        menuOptions.add(itemAuto);
        itemNew.addActionListener(new NewGameListener());
        itemResign.addActionListener(new ResignListener());
        itemOffer.addActionListener(new DrawListener());
        itemClaim.addActionListener(new ClaimListener());
        itemFlip.addActionListener(new FlipListener());
        itemComp.addItemListener(new CompListener());
        itemAuto.addItemListener(new AutoListener());
        
        add(menuInfo);
        menuInfo.add(itemInfo);
        menuInfo.add(itemRules);
        itemInfo.addActionListener(new InfoListener());
        itemRules.addActionListener(new RulesListener());
    }

    /**
     * Controls whether the computer is running or not.
     */
    private class CompListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (GUIMain.getPlayComputer()) GUIMain.setPlayComputer(false);
            else GUIMain.setPlayComputer(true);
            if (itemAuto.isSelected() && itemComp.isSelected()) {
                itemAuto.setSelected(false);
            }
        }
    }

    /**
     * Initiates a new game
     */
    private class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                GUIMain.resetGame();
            } catch (IOException ex) {
                Logger.getLogger(ChessMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Resigns for the current player
     */
    private class ResignListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!GUIMain.getBlack().getGameOver()) {
                if (GUIMain.getTotalMoveCount() % 2 == 0) {
                    JOptionPane.showMessageDialog(null, "White resigns!\nBlack wins!");
                } else {
                    JOptionPane.showMessageDialog(null, "Black resigns!\nWhite wins!");
                }
                Computer.setComputerRun(false);
                GUIMain.getBlack().setGameOver(true);
                GUIMain.getWhite().setGameOver(true);
            }
        }
    }

    /**
     * Asks the opposing player for a draw. Only functions in player vs player mode.
     * Will not operate with the computer.
     */
    private class DrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (GUIMain.getPlayComputer() == false && !(GUIMain.getBlack().getGameOver()
                    || GUIMain.getWhite().getGameOver())) {
                int draw;
                Object[] yesNo = {"Yes", "No"};
                draw = JOptionPane.showOptionDialog(null, "Would you like a draw?", 
                "Jong - Eagles Chess Engine", JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, yesNo,  null);
                if (draw == 0) {
                    JOptionPane.showMessageDialog(null, "Game drawn by agreement.");
                    Computer.setComputerRun(false);
                    GUIMain.getBlack().setGameOver(true);
                    GUIMain.getWhite().setGameOver(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Draw refused.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Game is already over.");
            }
        }
    }

    /**
     * Flips the display of the board and mouse click logic to play from the
     * perspective the other player.
     */
    private class FlipListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            itemAuto.setSelected(false);
            DisplayPieces.flipBoard();
        }
    }

    /**
     * Requests a check of the optional draw rules for the player.
     */
    private class ClaimListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (GUIMain.getBlack().getGameOver() || GUIMain.getWhite().getGameOver()) {
                JOptionPane.showMessageDialog(null, "Game is already over.");
            } else {
                if (GUIMain.getFiftyMoveCounter() > 49) {
                    JOptionPane.showMessageDialog(null, "Draw by the 50-move rule.");
                    Computer.setComputerRun(false);
                    GUIMain.getBlack().setGameOver(true);
                    GUIMain.getWhite().setGameOver(true);
                } else if (GUIMain.getThreefoldDraw()) {
                    JOptionPane.showMessageDialog(null, "Draw by threefold repetition.");
                    Computer.setComputerRun(false);
                    GUIMain.getBlack().setGameOver(true);
                    GUIMain.getWhite().setGameOver(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Draw claim is invalid.");
                    System.out.println(GUIMain.getThreefoldDraw());
                }
            }
        }
    }

    /**
     * Switches on and off the automatic flipping function for the two player mode.
     */
    private class AutoListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (!DisplayPieces.getAutoFlip()) DisplayPieces.autoFlip(true);
            else DisplayPieces.autoFlip(false);
            if (itemComp.isSelected() && itemAuto.isSelected()) {
                itemComp.setSelected(false);
            }
        }
    }

    /**
     * Displays information about the current game. This includes move count,
     * whose turn it is, and moves since last capture or pawn move.
     */
    private class InfoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String white = "", black = "", winner = "N/A", turn = "";
            
            if (GUIMain.getPlayWhiteInt() == 0) {
                white = "Human Player";
                if (GUIMain.getPlayComputer()) {
                    black = "Computer";
                } else black = "Human Player";
            } else {
                black = "Human Player";
                if (GUIMain.getPlayComputer()) {
                    white = "Computer";
                } else white = "Human Player";
            }
            
            if (GUIMain.getTotalMoveCount() % 2 == 0) turn = "White";
            else turn = "Black";
            
            if (GUIMain.getBlack().getGameOver() || GUIMain.getWhite().getGameOver()) {
                if (GUIMain.getBlack().getWon()) winner = "Black";
                else if (GUIMain.getWhite().getWon()) winner = "White";
                else winner = "It was a draw.";
            }
            
            JOptionPane.showMessageDialog(null, "Here is the current game info:"
                    + "\nWhite:   " + white
                    + "\nBlack:   " + black
                    + "\nCurrent Move #:   " + (GUIMain.getTotalMoveCount() / 2 + 1)
                    + "\nTurn:   " + turn
                    + "\nMoves since last capture/pawn move:   " + GUIMain.getFiftyMoveCounter()
                    + "\nWinner:   " + winner);
        }
    }

    /**
     * Shows a dialog with the rules for claiming a draw.
     */
    private class RulesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "A draw can be claimed if either:"
                    + "\n\t1. The current position has appeared three times. "
                    + "If the draw is not claimed \n\timmediately following the third "
                    + "appearance of the position, the draw claim is invalid."
                    + "\n\t2. 50 moves have been made by both sides without a "
                    + "capture or pawn movement by either side.");
        }
    }
}
