package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class ElementService {

    /**
     * It's used to duplicate a board without using the same board reference
     *
     * @param element
     * @return a copy of the board
     */
    public int[][] copyElement(int[][] element) {
        int[][] newElement = new int[getElementLineSize(element)][getElementColumnSize(element)];
        for (int i = 0; i < getElementLineSize(element); i++){
            for (int j = 0; j < getElementColumnSize(element); j++) {
                newElement[i][j] = element[i][j];
            }
        }
        return newElement;
    }
    /**
     * It's used to duplicate a board without using the same board reference
     *
     * @param element
     * @return a copy of the board
     */
    public int[][] copyElementPositionWithValue(int[][] element) {
        int[][] newElement = new int[2][1];
        newElement[0] = element[0];
        newElement[1] = element[1];
        return newElement;
    }


    /**
     * Give the side of the board (can be used for lines and columns length)
     *
     * @param s second line of the file or once the board is created, the board itself
     * @return size
     */
    public int getElementColumnSize(String s) {
        int side = s.indexOf(",");
        return side;
    }
    public int getElementColumnSize(int[][] board) {
        return board[0].length;
    }

    /**
     * Spot the number of ',' to get the number of lines
     *
     * @param s second line of the file or once the board is created, the board itself
     * @return the number of lines of the piece
     */
    public int getElementLineSize(String s) {
        int linesCount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (',' == s.charAt(i)) {
                linesCount++;
            }
        }
        return linesCount+1;
    }
    public int getElementLineSize(int[][] board) {
        return board.length;
    }


    public void listingArrayList(int[][] arrayInt) {
        System.out.println(Arrays.deepToString(arrayInt));
    }
    public void listingArrayList(ArrayList<int[][]> arrayList) {
        for (int i = 0; i < arrayList.size(); i++){
            System.out.println(Arrays.deepToString(arrayList.get(i)));
        }
    }

}
