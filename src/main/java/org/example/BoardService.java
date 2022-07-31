package org.example;

import java.util.Arrays;

public class BoardService {

    /**
     * Create the visualization of the board
     *
     * @param s
     * @return
     */
    public int[][] createBoard(String s) {
        int boardColumnSize = getBoardColumnSize(s);
        int boardLineSize = getBoardLineSize(s);

        int[][] board = new int[boardLineSize][boardColumnSize];
        char[] charArray = s.toCharArray();
        int lineNumber = 0;
        int columnNumber = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (",".equals(String.valueOf(charArray[i]))) {
                lineNumber++;
                columnNumber = 0;
                continue;
            }
            board[lineNumber][columnNumber] = Integer.parseInt(String.valueOf(charArray[i]));
            System.out.println(Arrays.deepToString(board));
            columnNumber++;
        }
        // System.out.println("Board : " + Arrays.deepToString(board));
        return board;
    }

    /**
     * Give the side of the board (can be used for lines and columns length)
     *
     * @param s
     * @return size
     */
    public int getBoardColumnSize(String s) {
        int side = s.indexOf(",");
        System.out.println("Side size: " + side);
        return side;
    }

    /**
     * Spot the number of ',' to get the number of lines
     *
     * @param s
     * @return the number of lines of the piece
     */
    public int getBoardLineSize(String s) {
        int linesCount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (',' == s.charAt(i)) {
                linesCount++;
            }
        }
        // System.out.println("There are " + (linesCount+1) + " lines in this piece");
        return linesCount+1;
    }

    public int getNumberOfPositionsPossible(int[][] piece, int boardColumnSize, int boardLineSize) {
        // System.out.println("Position on the line: " + boardLineSize/piece.length + " - Modulo: " + boardLineSize%piece.length);
        // System.out.println("Position on the column: " + boardColumnSize/piece[0].length + " - Modulo: " + boardColumnSize%piece[0].length);
        return (boardLineSize/piece.length + boardLineSize%piece.length) * (boardColumnSize/piece[0].length + boardColumnSize%piece[0].length);
    }

}
