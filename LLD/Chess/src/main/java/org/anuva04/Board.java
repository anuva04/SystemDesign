package org.anuva04;

public class Board {
    private final Cell[][] cells;

    public Board() {
        cells = new Cell[8][8];
        init();
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            return null; // Out of bounds
        }
        return cells[x][y];
    }

    public boolean movePiece(int startX, int startY, int endX, int endY) {
        Piece pieceToMove = getCell(startX, startY).getPiece();
        if(pieceToMove == null) return false;

        cells[startX][startY].setPiece(null);
        cells[endX][endY].setPiece(pieceToMove);
        pieceToMove.setPosition(endX, endY);
        if(pieceToMove instanceof Pawn) {
            ((Pawn) pieceToMove).setMoved();
        }

        return true;
    }

    public boolean isKingInCheck(PieceColor kingColor) {
        int kingX = -1;
        int kingY = -1;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = cells[x][y].getPiece();
                if (piece instanceof King && piece.getPieceColor() == kingColor) {
                    kingX = x;
                    kingY = y;
                    break;
                }
            }
            if (kingX != -1) break;
        }

        PieceColor opponentColor = (kingColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Piece piece = getCell(i, j).getPiece();
                if(piece != null && piece.getPieceColor() == opponentColor) {
                    if(piece.isValidMove(this, kingX, kingY)) return true;
                }
            }
        }
        return false;
    }

    public boolean isLegalMove(int startX, int startY, int endX, int endY, PieceColor currentPlayerColor) {
        if (startX < 0 || startX >= 8 || startY < 0 || startY >= 8 ||
                endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
            return false;
        }

        Piece pieceToMove = getCell(startX, startY).getPiece();
        if(pieceToMove == null || pieceToMove.getPieceColor() != currentPlayerColor) return false;
        if(!pieceToMove.isValidMove(this, endX, endY)) return false;

        Piece capturedPiece = getCell(endX, endY).getPiece();
        if(capturedPiece != null && capturedPiece.getPieceColor() == currentPlayerColor) return false;

        Board nextBoardState = this.deepCopy();
        nextBoardState.movePiece(startX, startY, endX, endY);
        return !nextBoardState.isKingInCheck(currentPlayerColor);
    }

    private Board deepCopy() {
        Board board = new Board();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = cells[x][y].getPiece();
                if (p != null) {
                    if (p instanceof Pawn) {
                        board.cells[x][y].setPiece(new Pawn(p.getPieceColor(), p.getX(), p.getY()));
                    } else if (p instanceof Rook) {
                        board.cells[x][y].setPiece(new Rook(p.getPieceColor(), p.getX(), p.getY()));
                    } else if (p instanceof Knight) {
                        board.cells[x][y].setPiece(new Knight(p.getPieceColor(), p.getX(), p.getY()));
                    } else if (p instanceof Bishop) {
                        board.cells[x][y].setPiece(new Bishop(p.getPieceColor(), p.getX(), p.getY()));
                    } else if (p instanceof Queen) {
                        board.cells[x][y].setPiece(new Queen(p.getPieceColor(), p.getX(), p.getY()));
                    } else if (p instanceof King) {
                        board.cells[x][y].setPiece(new King(p.getPieceColor(), p.getX(), p.getY()));
                    }
                }
            }
        }

        return board;
    }

    public boolean isCheckMate(PieceColor playerColor) {
        if(!isKingInCheck(playerColor)) return false;

        for(int startX = 0; startX < 8; startX++) {
            for(int startY = 0; startY < 8; startY++) {
                Piece piece = getCell(startX, startY).getPiece();
                if(piece == null || piece.getPieceColor() != playerColor) continue;

                for(int endX = 0; endX < 8; endX++) {
                    for(int endY = 0; endY < 8; endY++) {
                        if(isLegalMove(startX, startY, endX, endY, playerColor)) return false;
                    }
                }
            }
        }

        return true;
    }

    private void init() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        cells[0][0].setPiece(new Rook(PieceColor.WHITE, 0, 0));
        cells[1][0].setPiece(new Knight(PieceColor.WHITE, 1, 0));
        cells[2][0].setPiece(new Bishop(PieceColor.WHITE, 2, 0));
        cells[3][0].setPiece(new Queen(PieceColor.WHITE, 3, 0));
        cells[4][0].setPiece(new King(PieceColor.WHITE, 4, 0));
        cells[5][0].setPiece(new Bishop(PieceColor.WHITE, 5, 0));
        cells[6][0].setPiece(new Knight(PieceColor.WHITE, 6, 0));
        cells[7][0].setPiece(new Rook(PieceColor.WHITE, 7, 0));
        for (int i = 0; i < 8; i++) {
            cells[i][1].setPiece(new Pawn(PieceColor.WHITE, i, 1));
        }

        cells[0][7].setPiece(new Rook(PieceColor.BLACK, 0, 7));
        cells[1][7].setPiece(new Knight(PieceColor.BLACK, 1, 7));
        cells[2][7].setPiece(new Bishop(PieceColor.BLACK, 2, 7));
        cells[3][7].setPiece(new Queen(PieceColor.BLACK, 3, 7));
        cells[4][7].setPiece(new King(PieceColor.BLACK, 4, 7));
        cells[5][7].setPiece(new Bishop(PieceColor.BLACK, 5, 7));
        cells[6][7].setPiece(new Knight(PieceColor.BLACK, 6, 7));
        cells[7][7].setPiece(new Rook(PieceColor.BLACK, 7, 7));
        for (int i = 0; i < 8; i++) {
            cells[i][6].setPiece(new Pawn(PieceColor.BLACK, i, 6));
        }
    }

    public void printBoard() {
        System.out.println("  a  b  c  d  e  f  g  h");
        System.out.println("-------------------------");
        for (int y = 7; y >= 0; y--) { // Print from top to bottom
            System.out.print(y + 1 + "|");
            for (int x = 0; x < 8; x++) {
                Piece piece = getCell(x, y).getPiece();
                if (piece == null) {
                    System.out.print(".. ");
                } else {
                    String symbol = piece.getPieceSymbol() + String.valueOf(piece.getPieceColor().toString().charAt(0));
                    System.out.print(symbol + " ");
                }
            }
            System.out.println("|" + (y + 1));
        }
        System.out.println("-------------------------");
        System.out.println("  a  b  c  d  e  f  g  h");
    }
}
