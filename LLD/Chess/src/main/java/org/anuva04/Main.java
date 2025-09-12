package org.anuva04;

public class Main {
    public static void main(String[] args) {
        Game game = new Game("Player 1", "Player 2");
        game.startGame();

        Thread whitePlayerThread = new Thread(new PlayerThread(game, game.white), "White Player Thread");
        Thread blackPlayerThread = new Thread(new PlayerThread(game, game.black), "Black Player Thread");

        whitePlayerThread.start();
        blackPlayerThread.start();
    }
}