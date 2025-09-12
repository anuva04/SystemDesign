package org.anuva04;

import java.util.Scanner;

public class PlayerThread implements Runnable {
    private final Game game;
    private final Player player;
    private final Scanner scanner;

    public PlayerThread(Game game, Player player) {
        this.game = game;
        this.player = player;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println(player.getName() + " thread started.");

        while(!game.isGameOver()) {
            synchronized (game.getGameLock()) {
                while(game.getCurrentPlayer().getColor() != player.getColor() && !game.isGameOver()) {
                    try {
                        game.getGameLock().wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(player.getName() + " thread interrupted.");
                        return;
                    }
                }

                if (game.isGameOver()) break;

                System.out.println("\n" + player.getName() + "'s turn (" + player.getColor() + ").");
                System.out.print("Enter move (e.g., a2 a4): ");
                String move = scanner.nextLine();

                try {
                    int[] coords = parseMove(move);
                    if(!game.makeMove(coords[0], coords[1], coords[2], coords[3])) {
                        System.out.println("Invalid move. Try again.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private int[] parseMove(String move) {
        String[] parts = move.split(" ");
        if(parts.length != 2) {
            throw new IllegalArgumentException("Invalid move format. Use 'a2 a4'.");
        }

        String start = parts[0], end = parts[1];
        if (start.length() != 2 || end.length() != 2) {
            throw new IllegalArgumentException("Invalid coordinate format. Use 'a2'.");
        }

        int startX = start.charAt(0) - 'a';
        int startY = Character.getNumericValue(start.charAt(1)) - 1;
        int endX = end.charAt(0) - 'a';
        int endY = Character.getNumericValue(end.charAt(1)) - 1;

        if (startX < 0 || startX >= 8 || startY < 0 || startY >= 8 || endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            throw new IllegalArgumentException("Coordinates are out of board bounds.");
        }

        return new int[]{startX, startY, endX, endY};
    }
}
