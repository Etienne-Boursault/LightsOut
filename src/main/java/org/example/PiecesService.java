package org.example;

import java.util.ArrayList;

public class PiecesService {

    /**
     * Create all the pieces from the third line of the file
     *
     * @param s
     * @return pieces
     */
    public ArrayList<int[][]> createPieces(String s) {

        // System.out.println("All the pieces in a form of a line : " + s);
        // Create a list with all the pieces
        String[] pieces = s.split(" ");
        // System.out.println("All the pieces in a form of an Array : " + Arrays.deepToString(pieces));

        ArrayList<int[][]> arrayOfEachPieces = new ArrayList<>();

        for (int i = 0; i < pieces.length; i++) {
            //System.out.println("Piece number " + ((int) i+1) + ": " + pieces[i]);
            char[] charArray = pieces[i].toCharArray();
            // System.out.println("La taille de la pièce numéro " + ((int) i+1) + " est de " + charArray.length);
            //System.out.println("La pièce numéro " + (i+1) + ": " + charArray);

            // Get the size of the piece
            int numberOfColumns = countColumnOfPiece(pieces[i]);
            int numberOfLines = countLinesOfPiece(pieces[i]);

            // System.out.println("Number of lines: " + numberOfLines + "\nNumber of columns: " + numberOfColumns);
            int[][] piecesToAdd = new int[numberOfLines][numberOfColumns];

            int lineNumber = 0;
            int columnNumber = 0;
            for (int k = 0; k < charArray.length; k++) {
                if (",".equals(String.valueOf(charArray[k]))) {
                    lineNumber++;
                    columnNumber = 0;
                    continue;
                }
                //System.out.println("Character: " + charArray[k]);
                piecesToAdd[lineNumber][columnNumber] = convertDotAndX(String.valueOf(charArray[k]));
                //System.out.println("Pieces n°" + (i+1) + "[" + lineNumber + "][" + columnNumber + "]: " + piecesToAdd[lineNumber][columnNumber]);
                columnNumber++;
            }
            arrayOfEachPieces.add(piecesToAdd);

            // System.out.println("Piece n°" + (i+1) + ": " + Arrays.deepToString(arrayOfEachPieces.get(i)));
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
        // System.out.println("There are " + (linesCount+1) + " lines in this piece");
        return linesCount+1;
    }

    /**
     * Spot the number of letter before a ',' or return the lenght of the string
     *
     * @param s
     * @return the number of columns of the piece
     */
    private int countColumnOfPiece(String s) {
        // System.out.println("There are " + s.indexOf(',') + " columns in this piece");
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
}
