package org.anuva04;

public class Queen extends Piece {
    public Queen(PieceColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(Board board, int endX, int endY) {
        int dx = Math.abs(x - endX);
        int dy = Math.abs(y - endY);

        boolean isStraightMove = (x == endX && y != endY) || (x != endX && y == endY);
        boolean isDiagonalMove = dx == dy;

        if(!isStraightMove && !isDiagonalMove) return false;

        if(isStraightMove) {
            if(x == endX) {
                int y1 = Math.min(y, endY), y2 = Math.max(y, endY);
                for(int i = y1+1; i < y2; i++) {
                    if(board.getCell(x, i).isOccupied()) return false;
                }
            } else {
                int x1 = Math.min(x, endX), x2 = Math.max(x, endX);
                for(int i = x1+1; i < x2; i++) {
                    if(board.getCell(i, y).isOccupied()) return false;
                }
            }
        } else {
            int xDir = (endX > x) ? 1 : -1;
            int yDir = (endY > y) ? 1 : -1;

            for(int step = 1; step < dx; step++) {
                if(board.getCell(x + step*xDir, y + step*yDir).isOccupied()) return false;
            }
        }

        Piece capturedPiece = board.getCell(endX, endY).getPiece();
        return capturedPiece == null || capturedPiece.getPieceColor() != this.pieceColor;
    }

    @Override
    public char getPieceSymbol() {
        return 'q';
    }
}
