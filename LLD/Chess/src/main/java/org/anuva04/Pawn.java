package org.anuva04;

public class Pawn extends Piece {
    private boolean isFirstMove;

    public Pawn(PieceColor pieceColor, int x, int y) {
        super(pieceColor, x, y);
        isFirstMove = true;
    }

    public void setMoved() {
        isFirstMove = false;
    }

    @Override
    public char getPieceSymbol() {
        return 'p';
    }

    @Override
    public boolean isValidMove(Board board, int newX, int newY) {
        if(pieceColor == PieceColor.WHITE && newY <= y) return false;
        if(pieceColor == PieceColor.BLACK && newY >= y) return false;

        if(x == newX && Math.abs(y - newY) == 1) {
            return !board.getCell(newX, newY).isOccupied();
        }

        if(isFirstMove && newX == x && Math.abs(newY - y) == 2) {
            int midYStep = pieceColor == PieceColor.WHITE ? 1 : -1;
            return !board.getCell(x, y + midYStep).isOccupied()
                    && !board.getCell(x, newY).isOccupied();
        }

        if (Math.abs(newX - x) == 1 && Math.abs(newY - y) == 1) {
            Piece capturedPiece = board.getCell(newX, newY).getPiece();
            return capturedPiece != null && capturedPiece.getPieceColor() != this.pieceColor;
        }

        return false;
    }
}
