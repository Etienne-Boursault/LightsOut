package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final BoardService boardService = new BoardService();
        final PiecesService piecesService = new PiecesService();
        final SolvingService solvingService = new SolvingService();

        boolean debugView = false;

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

        int[] numberOfPositionsPossibleForAPiece = new int[pieces.size()];
        int boardColumnSize = boardService.getElementColumnSize(lines.get(1));
        int boardLineSize = boardService.getElementLineSize(lines.get(1));


        // Generate an array with the number of positions possible for each piece
        int totalOfCombinaisonPossible = 1;
        for (int i = 0; i < pieces.size(); i++) {
            int[] positions = boardService.getNumberOfPositionsPossible(pieces.get(i), boardLineSize, boardColumnSize);
            numberOfPositionsPossibleForAPiece[i] = positions[0]*positions[1];
            totalOfCombinaisonPossible *= numberOfPositionsPossibleForAPiece[i];
            System.out.println("Number of position(s) for piece n°" + (i+1) + ": " + numberOfPositionsPossibleForAPiece[i]);
        }

        //boardService.listingArrayList(pieces);

        System.out.println("findBestPostion");
        ArrayList<int[][]> bestposition = new ArrayList<>();
        bestposition = solvingService.findBestPostion(board, pieces.get(2));
        piecesService.listingArrayList(bestposition);


    }
}