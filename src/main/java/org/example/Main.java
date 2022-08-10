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

        File file = new File("src/main/resources/02.txt");
        ArrayList<String> lines = new ArrayList<>();

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        int depthValue = Integer.parseInt(lines.get(0));

        int[][] board = boardService.createBoard(lines.get(1));
        int[][] testBoard = boardService.createBoard(lines.get(1));
        piecesService.listingArrayList(board);
        int numberOfPieces = piecesService.numberOfPieces(lines.get(2));

        ArrayList<int[][]> pieces = piecesService.createPieces(lines.get(2));
        piecesService.listingArrayList(pieces);
        int[][] finalPositions = new int[pieces.size()][2];

        int[][] numberOfPositionsPossibleForAPiece = new int[pieces.size()][1];
        int maxPositionsPossible = 0;
        int boardColumnSize = boardService.getElementColumnSize(lines.get(1));
        int boardLineSize = boardService.getElementLineSize(lines.get(1));

        // Generate an array with the number of positions possible for each piece
        int totalOfCombinaisonPossible = 1;
        for (int i = 0; i < pieces.size(); i++) {
            int[] positions = boardService.getNumberOfPositionsPossible(pieces.get(i), boardLineSize, boardColumnSize);
            numberOfPositionsPossibleForAPiece[i][0] = positions[0]*positions[1];
            if (numberOfPositionsPossibleForAPiece[i][0] > maxPositionsPossible) {
                maxPositionsPossible = numberOfPositionsPossibleForAPiece[i][0];
            }
            totalOfCombinaisonPossible *= numberOfPositionsPossibleForAPiece[i][0];
            /*if (numberOfPositionsPossibleForAPiece[i][0] == 1) {
                board = piecesService.addPieceToBoard(board, pieces.get(i), new int[]{0, 0}, depthValue);
                finalPositions[i] = new int[]{0, 0};
            }*/
        }

        boolean previousPieceTried = false;
        ArrayList<int[][]> bestposition;
        int[] positionTriedPerPieces = new int[maxPositionsPossible+1];

        // Ajouter toutes les positions 0
        // Puis remonter les pièces 1 à 1 en testant toutes les combinaisons des autres pièces

        boolean[] piecesAlreadyRemoved = new boolean[pieces.size()-1];

        boardService.listingArrayList(board);
        // Adding the pieces with only one solution first
        for (int i = 0; i < pieces.size()-1; i++){
            if (numberOfPositionsPossibleForAPiece[i][0] == 1) {
                finalPositions[i][0] = 0;
                finalPositions[i][1] = 0;
                board = piecesService.addPieceToBoard(board, pieces.get(i), finalPositions[i], depthValue);
                System.out.println("Add Piece n°" + (i+1) + " to the position [" + finalPositions[i][0] + "][" + finalPositions[i][1] + "] - " + solvingService.valueOfTheElement(board));
                boardService.listingArrayList(board);
            }
        }

        // Ading the rest of the pieces except the last one
        for (int i = 0; i < pieces.size()-1; i++){
            if (numberOfPositionsPossibleForAPiece[i][0] != 1) {
                piecesAlreadyRemoved[i] = false;
                bestposition = solvingService.findBestPostion(board, pieces.get(i));
                finalPositions[i][0] = bestposition.get(positionTriedPerPieces[i])[0][0];
                finalPositions[i][1] = bestposition.get(positionTriedPerPieces[i])[0][1];
                board = piecesService.addPieceToBoard(board, pieces.get(i), finalPositions[i], depthValue);
                System.out.println("Add Piece n°" + (i+1) + " to the position [" + finalPositions[i][0] + "][" + finalPositions[i][1] + "] - " + solvingService.valueOfTheElement(board));
                boardService.listingArrayList(board);
            }
        }

        boardService.listingArrayList(numberOfPositionsPossibleForAPiece);
        int iteration = 0;
        boardService.listingArrayList(board);
        for (int i = pieces.size()-1; i > -1; i--) {
            if (i == pieces.size()-1) {
                bestposition = solvingService.findBestPostion(board, pieces.get(i));
                finalPositions[i][0] = bestposition.get(positionTriedPerPieces[i])[0][0];
                finalPositions[i][1] = bestposition.get(positionTriedPerPieces[i])[0][1];
                board = piecesService.addPieceToBoard(board, pieces.get(i), finalPositions[i], depthValue);
                // System.out.println("Add Piece n°" + (i+1) + " to the position [" + finalPositions[i][0] + "][" + finalPositions[i][1] + "] - " + solvingService.valueOfTheElement(board));
                // boardService.listingArrayList(board);
                iteration++;

                if (solvingService.valueOfTheElement(board) == 0) {
                    break;
                }
                board = piecesService.removePieceFromBoard(board, pieces.get(i), finalPositions[i], depthValue);
                // System.out.println("Remove Piece n°" + (i+1) + " from the position [" + finalPositions[i][0] + "][" + finalPositions[i][1] + "]");
                // boardService.listingArrayList(board);
                continue;
            }

            if (numberOfPositionsPossibleForAPiece[i][0] == 1 && i != 0) {
                continue;
            } else if (i == 0 && positionTriedPerPieces[i+1] == numberOfPositionsPossibleForAPiece[i+1][0]-1) {
                i += 2;
                positionTriedPerPieces[i-1]++;
                continue;
            }

            // Deleting the last piece added
            if (!piecesAlreadyRemoved[i]) {
                board = piecesService.removePieceFromBoard(board, pieces.get(i), finalPositions[i], depthValue);
                // System.out.println("Remove Piece n°" + (i+1) + " from the position [" + finalPositions[i][0] + "][" + finalPositions[i][1] + "]");
                // boardService.listingArrayList(board);
                piecesAlreadyRemoved[i] = false;
            }
            bestposition = solvingService.findBestPostion(board, pieces.get(i));
            // boardService.listingArrayList(bestposition);

            // Going to the next best solution
            positionTriedPerPieces[i]++;

            if (positionTriedPerPieces[i] <= numberOfPositionsPossibleForAPiece[i][0]) {
                // Add it
                finalPositions[i][0] = bestposition.get(positionTriedPerPieces[i]-1)[0][0];
                finalPositions[i][1] = bestposition.get(positionTriedPerPieces[i]-1)[0][1];
                board = piecesService.addPieceToBoard(board, pieces.get(i), finalPositions[i], depthValue);
                // System.out.println("Add Piece n°" + (i+1) + " to the position [" + finalPositions[i][0] + "][" + finalPositions[i][1] + "]");
                // boardService.listingArrayList(board);
                piecesAlreadyRemoved[i] = false;
                iteration++;
                if (numberOfPositionsPossibleForAPiece[i+1][0] == 1) {
                    i += 3;
                } else {
                    i += 2;
                }
                if (i == pieces.size()) {
                    i = pieces.size();
                }
            } else {
                positionTriedPerPieces[i] = 0;
                piecesAlreadyRemoved[i] = true;
            }
        }


        System.out.println(iteration);
        boardService.listingArrayList(board);
        boardService.listingArrayList(finalPositions);

        for (int i = 0; i < finalPositions.length; i++){
            for (int j = 0; j < finalPositions[i].length; j++){
                System.out.print(finalPositions[i][j]);
                if (j == finalPositions[0].length-1){
                    System.out.print(" ");
                } else {
                    System.out.print(",");
                }
            }
        }

        System.out.println("\nTest of the board");
        boardService.listingArrayList(testBoard);

        for (int i = 0; i < pieces.size(); i++) {
            System.out.println("Pieces n°" + (i+1));
            piecesService.listingArrayList(pieces.get(i));
            testBoard = piecesService.addPieceToBoard(testBoard, pieces.get(i), finalPositions[i], depthValue);
            boardService.listingArrayList(testBoard);
        }

        if (solvingService.valueOfTheElement(testBoard) == 0) {
            System.out.println("It truly works");
        }
    }
}