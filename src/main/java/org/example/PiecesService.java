package org.example;

import java.util.ArrayList;

public class PiecesService extends ElementService {

    final BoardService boardService = new BoardService();

    /**
     * Create all the pieces from the third line of the file
     *
     * @param s
     * @return pieces
     */
    public ArrayList<int[][]> createPieces(String s) {

        // Create a list with all the pieces
        String[] pieces = s.split(" ");

        ArrayList<int[][]> arrayOfEachPieces = new ArrayList<>();

        for (int i = 0; i < pieces.length; i++) {
            char[] charArray = pieces[i].toCharArray();

            // Get the size of the piece
            int numberOfColumns = countColumnOfPiece(pieces[i]);
            int numberOfLines = countLinesOfPiece(pieces[i]);

            int[][] piecesToAdd = new int[numberOfLines][numberOfColumns];

            int lineNumber = 0;
            int columnNumber = 0;
            for (int k = 0; k < charArray.length; k++) {
                if (",".equals(String.valueOf(charArray[k]))) {
                    lineNumber++;
                    columnNumber = 0;
                    continue;
                }
                piecesToAdd[lineNumber][columnNumber] = convertDotAndX(String.valueOf(charArray[k]));
                columnNumber++;
            }
            arrayOfEachPieces.add(piecesToAdd);
        }

        return arrayOfEachPieces;
    }

    /**
     * Spot the number of ',' to get the number of lines
     *
     * @param s
     * @return the number of lines of the piece
     */
    private int countLinesOfPiece(String s) {
        int linesCount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (',' == s.charAt(i)) {
                linesCount++;
            }
        }
        return linesCount+1;
    }

    /**
     * Spot the number of letter before a ',' or return the lenght of the string
     *
     * @param s
     * @return the number of columns of the piece
     */
    private int countColumnOfPiece(String s) {
        return s.indexOf(',') == -1 ? s.length() : s.indexOf(',') ;
    }

    /**
     *
     * @param s
     * @return the number of pieces to place on the board
     */
    public int numberOfPieces(String s) {
        return s.split(" ").length;
    }

    /**
     * Convert the . into 0 and the X into 1
     *
     * @param initialPieces
     * @return either 0 or 1
     */
    public int convertDotAndX(String initialPieces) {
        if (".".equals(initialPieces)) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     *
     * @param board
     * @param piece
     * @param positions
     * @param depth
     * @return
     */
    public int[][] addPieceToBoard(int[][] board, int[][] piece, int[] positions, int depth) {
        int[][] newBoard = boardService.copyElement(board);

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                //System.out.println("i + (positions[0]) - " + (i + (positions[0])) + " - j + (positions[1]: " + (j + (positions[1])));
                //System.out.println("positions[0]): " + positions[0] + " - positions[1]: " + positions[1]);
                newBoard[i + (positions[0])][j + (positions[1])] += piece[i][j];
                if (newBoard[i + positions[0]][j + positions[1]] == depth) {
                    newBoard[i + positions[0]][j + positions[1]] = 0;
                }
            }
        }
        return newBoard;
    }


    /**
     *
     * @param board
     * @param piece
     * @param positions
     * @param depth
     * @return
     */
    public int[][] removePieceFromBoard(int[][] board, int[][] piece, int[] positions, int depth) {
        int[][] newBoard = boardService.copyElement(board);
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                newBoard[i+(positions[0])][j+(positions[1])] -= piece[i][j];
                if (newBoard[i+positions[0]][j+positions[1]] == -1) {
                    newBoard[i+positions[0]][j+positions[1]] = depth-1;
                }
            }
        }
        return newBoard;
    }
}
