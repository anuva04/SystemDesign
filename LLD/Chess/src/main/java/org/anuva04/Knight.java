package org.anuva04;

public class Knight extends Piece {
    public Knight(PieceColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(Board board, int endX, int endY) {
        int dx = Math.abs(x - endX);
        int dy = Math.abs(y - endY);

        if ((dx == 1 && dy == 2) || (dx == 2 && dy == 1)) {
            Piece capturedPiece = board.getCell(endX, endY).getPiece();
            return capturedPiece == null || capturedPiece.getPieceColor() != this.pieceColor;
        }

        return false;
    }

    @Override
    public char getPieceSymbol() {
        return 'n';
    }
}
