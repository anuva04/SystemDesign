package org.anuva04;

public class Rook extends Piece {
    public Rook(PieceColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(Board board, int endX, int endY) {
        if(endY != y && endX != x) return false;

        if(endX == x) {
            int yMin = Math.min(y, endY);
            int yMax = Math.max(y, endY);

            for(int i = yMin+1; i < yMax; i++) {
                if(board.getCell(x, i).isOccupied()) return false;
            }
        } else {
            int xMin = Math.min(x, endX);
            int xMax = Math.max(x, endX);

            for(int i = xMin+1; i < xMax; i++) {
                if(board.getCell(i, y).isOccupied()) return false;
            }
        }

        Piece capturedPiece = board.getCell(endX, endY).getPiece();
        return capturedPiece == null || capturedPiece.getPieceColor() != this.pieceColor;
    }

    @Override
    public char getPieceSymbol() {
        return 'r';
    }
}
