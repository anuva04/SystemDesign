package org.anuva04;

public class Bishop extends Piece {
    public Bishop(PieceColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(Board board, int endX, int endY) {
        int dx = Math.abs(x - endX);
        int dy = Math.abs(y - endY);

        if(dx != dy || dx == 0) return false;

        int xDir = endX > x ? 1 : -1;
        int yDir = endY > y ? 1 : -1;

        for(int step = 1; step < dx; step++) {
            if(board.getCell(x + step*xDir, y + step*yDir).isOccupied()) return false;
        }

        Piece capturedPiece = board.getCell(endX, endY).getPiece();
        return capturedPiece == null || capturedPiece.getPieceColor() != this.pieceColor;
    }

    @Override
    public char getPieceSymbol() {
        return 'b';
    }
}
