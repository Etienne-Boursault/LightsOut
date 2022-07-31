package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final BoardService boardService = new BoardService();
        final PiecesService piecesService = new PiecesService();

        boolean debugView = true;

        File file = new File("src/main/resources/01.txt");
        ArrayList<String> lines = new ArrayList<>();

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        if (debugView) System.out.println("Extract of the file : " + lines);

        int depthValue = Integer.parseInt(lines.get(0));

        if (debugView) System.out.println("Depth Value : " + depthValue);
        if (debugView) System.out.println("Length of the board line : " + lines.get(1).length());

        int[][] board = boardService.createBoard(lines.get(1));
        int numberOfPieces = piecesService.numberOfPieces(lines.get(2));

        if (debugView) System.out.println("There are " + numberOfPieces + " pieces");

        ArrayList<int[][]> pieces = piecesService.createPieces(lines.get(2));
        /*for (int[][] piece: pieces){
            System.out.println(Arrays.deepToString(piece));
        }*/

        int[] numberOfPositionsPossibleForAPiece = new int[pieces.size()];
        int boardColumnSize = boardService.getBoardColumnSize(lines.get(1));
        int boardLineSize = boardService.getBoardLineSize(lines.get(1));

        int totalOfCombinaisonPossible = 1;
        for (int i = 0; i < pieces.size(); i++) {
            System.out.println("Piece n°" + (i+1) + ": " + Arrays.deepToString(pieces.get(i)));
            numberOfPositionsPossibleForAPiece[i] = boardService.getNumberOfPositionsPossible(pieces.get(i), boardLineSize, boardColumnSize);
            totalOfCombinaisonPossible *= numberOfPositionsPossibleForAPiece[i];
            System.out.println("Number of position(s) for piece n°" + (i+1) + ": " + numberOfPositionsPossibleForAPiece[i]);
        }
        System.out.println("Number of combinaison: " + totalOfCombinaisonPossible);
    }
}