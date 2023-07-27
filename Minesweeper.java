import java.util.*;

public class Minesweeper {
    private int[][] board;
    
    public Minesweeper (){
        board = new int[5][5];
        initBoard();
    }

    /**
     * Generates a random number from 0 to 9 with 9 being a mine
     * @return random number from 0 to 9
     */
    private int randomNumGen() {
        Random random = new Random();
        int num = random.nextInt(10);
        return num;
    }

    private void initBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                int num = randomNumGen();
                // if the number is 9 then it is a mine
                if(num == 9) {
                    board[i][j] = num;
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    private void printRow(int[] row) {
        for(int i : row) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private void printBoard() {
        for(int[] row : board) {
            printRow(row);
        }
    }

    public void runGame() {
        printBoard();
    }
}
