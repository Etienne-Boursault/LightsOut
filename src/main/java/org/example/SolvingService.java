package org.example;

import java.util.ArrayList;

public class SolvingService {
    final BoardService boardService = new BoardService();
    final PiecesService piecesService = new PiecesService();

    ArrayList<int[][]> findBestPostion(int[][] board, int[][] piece) {
        int valueOfTheNewBoard = 0;
        int boardLineSize = boardService.getElementLineSize(board);
        int boardColumnSize = boardService.getElementColumnSize(board);
        int[] position = boardService.getNumberOfPositionsPossible(piece, boardLineSize, boardColumnSize);
        int boardLine = boardService.getElementLineSize(board);
        int boardColumn = boardService.getElementColumnSize(board);
        int[][] newBoard = boardService.copyElement(board);

        for (int i = 0; i < boardLine; i++) {
            for (int j = 0; j < boardColumn; j++) {
                valueOfTheNewBoard += board[i][j];
            }
        }

        ArrayList<int[][]> orderedBestPositions = new ArrayList<>();
        for (int i = 0; i < position[0]; i++){
            for (int j = 0; j < position[1]; j++) {
                newBoard = piecesService.addPieceToBoard(board, piece, new int[]{(i), (j)}, 2);
                valueOfTheNewBoard = this.valueOfTheElement(newBoard);

                // If there is already a piece in the array, look where to put the new array
                if (!orderedBestPositions.isEmpty()) {
                    int lengthOfThePositionsPossible = orderedBestPositions.size();

                    for (int k = 0; k < lengthOfThePositionsPossible; k++) {
                        // If the value of the newBoard is stricktly inferior then, the piece take the k position inside the array
                        if (valueOfTheNewBoard < orderedBestPositions.get(k)[1][0]) {
                            orderedBestPositions.add(k, new int[][]{{i, j}, {valueOfTheNewBoard}});
                            break;
                        // But if there is a k+1 position and the value of this position is stricktly inferior to the next one, then adding at the k+1 position
                        } else if ((k+1) == lengthOfThePositionsPossible) {
                            orderedBestPositions.add(k+1, new int[][]{{i, j}, {valueOfTheNewBoard}});
                            break;
                        }
                    }
                } else { // If there is no piece: add it
                    orderedBestPositions.add(new int[][]{{i, j}, {valueOfTheNewBoard}});
                }
            }
        }

        return orderedBestPositions;
    }

    int valueOfTheElement(int[][] element) {
        int valueOfTheBoard = 0;
        int boardLine = boardService.getElementLineSize(element);
        int boardColumn = boardService.getElementColumnSize(element);

        for (int i = 0; i < boardLine; i++) {
            for (int j = 0; j < boardColumn; j++) {
                valueOfTheBoard += element[i][j];
            }
        }

        return valueOfTheBoard;
    }
}
