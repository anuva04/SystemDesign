package org.anuva04;

import java.util.concurrent.atomic.AtomicBoolean;

public class Game {
    private final Board board;
    public final Player white;
    public final Player black;
    private Player currentPlayer;
    private final Object gameLock = new Object();
    private final AtomicBoolean isGameOver = new AtomicBoolean(false);

    public Game(String whiteName, String blackName) {
        board = new Board();
        white = new Player(whiteName, PieceColor.WHITE);
        black = new Player(blackName, PieceColor.BLACK);
        currentPlayer = this.white;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return isGameOver.get();
    }

    public void setGameOver() {
        isGameOver.set(true);
    }

    public Object getGameLock() {
        return gameLock;
    }

    public void startGame() {
        System.out.println("Chess game started!");
        board.printBoard();
    }

    public boolean makeMove(int startX, int startY, int endX, int endY) {
        synchronized (gameLock) {
            if(!board.isLegalMove(startX, startY, endX, endY, currentPlayer.getColor())) {
                return false;
            }

            board.movePiece(startX, startY, endX, endY);
            board.printBoard();

            PieceColor opponentColor = (currentPlayer.getColor() == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
            if(board.isKingInCheck(opponentColor)) {
                if(board.isCheckMate(opponentColor)) {
                    System.out.println("Checkmate! " + currentPlayer.getName() + " wins!");
                    setGameOver();
                } else {
                    System.out.println("Check!");
                }
            }

            currentPlayer = (currentPlayer.getColor() == PieceColor.WHITE) ? black : white;
            gameLock.notifyAll();
            return true;
        }
    }
}
