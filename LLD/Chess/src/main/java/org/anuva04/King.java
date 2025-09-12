package org.anuva04;

public class King extends Piece {
    public King(PieceColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(Board board, int endX, int endY) {
        int dx = Math.abs(endX - this.x);
        int dy = Math.abs(endY - this.y);
        if (dx > 1 || dy > 1 || (dx == 0 && dy == 0)) {
            return false;
        }

        Piece capturedPiece = board.getCell(endX, endY).getPiece();
        return capturedPiece == null || capturedPiece.getPieceColor() != this.pieceColor;
    }

    @Override
    public char getPieceSymbol() {
        return 'k';
    }
}
