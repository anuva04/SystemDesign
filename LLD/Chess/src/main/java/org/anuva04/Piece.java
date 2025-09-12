package org.anuva04;

public abstract class Piece {
    protected PieceColor pieceColor;
    protected int x;
    protected int y;

    public Piece(PieceColor pieceColor, int x, int y) {
        this.pieceColor = pieceColor;
        this.x = x;
        this.y = y;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean isValidMove(Board board, int newX, int newY);

    public abstract char getPieceSymbol();
}
