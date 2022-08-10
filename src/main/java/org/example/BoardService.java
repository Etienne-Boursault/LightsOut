package org.example;

public class BoardService extends ElementService {

    /**
     * Create the visualization of the board
     *
     * @param s
     * @return
     */
    public int[][] createBoard(String s) {
        int boardColumnSize = getElementColumnSize(s);
        int boardLineSize = getElementLineSize(s);

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
            columnNumber++;
        }
        return board;
    }

    public int[] getNumberOfPositionsPossible(int[][] piece, int boardColumnSize, int boardLineSize) {
        int[] positions = new int[2];
        positions[0] = boardLineSize/piece.length + boardLineSize%piece.length;
        positions[1] = boardColumnSize/piece[0].length + boardColumnSize%piece[0].length;
        return positions;
    }
}
