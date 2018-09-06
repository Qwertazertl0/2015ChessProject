/*
 * Program: ChessProject
 * This: Position.java
 * Date: December 12, 2015
 * Author: Maxwell Jong
 * Purpose: This class creates objects that represent snapshots of the current
ongoing game. This way, the program can compare accumulated positions and allow
draw by threefold repetition.
 */

package Engine;


import Pieces.*;
import java.util.Arrays;

//Note: The user 12core refers to my computer at home, on which I created most
//of these classes.
/**
 *
 * @author 12core
 */
public class Position {

    private final int[] board = new int[64];
    
    public Position(PlayerWhite white, PlayerBlack black) {
        for (int i = 1; i < 65; i++) {
            if (!white.hasPieceAt(i) && !black.hasPieceAt(i)) {
                this.board[i-1] = 0;
            } else if (white.hasPieceAt(i)) {
                if (white.pieceAt(i) instanceof Pawn) this.board[i-1] = 1;
                if (white.pieceAt(i) instanceof Rook) this.board[i-1] = 2;
                if (white.pieceAt(i) instanceof Knight) this.board[i-1] = 3;
                if (white.pieceAt(i) instanceof Bishop) this.board[i-1] = 4;
                if (white.pieceAt(i) instanceof Queen) this.board[i-1] = 5;
                if (white.pieceAt(i) instanceof King) this.board[i-1] = 6;
            } else {
                if (black.pieceAt(i) instanceof Pawn) this.board[i-1] = 7;
                if (black.pieceAt(i) instanceof Rook) this.board[i-1] = 8;
                if (black.pieceAt(i) instanceof Knight) this.board[i-1] = 9;
                if (black.pieceAt(i) instanceof Bishop) this.board[i-1] = 10;
                if (black.pieceAt(i) instanceof Queen) this.board[i-1] = 11;
                if (black.pieceAt(i) instanceof King) this.board[i-1] = 12;
            }
        }
    }
    
    public boolean equals(Position position) {
        return Arrays.equals(this.board, position.board);
    }
}
