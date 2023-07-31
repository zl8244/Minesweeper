import java.util.*;

public class Minesweeper {
    private String[][] board;
    private boolean[][] mines;
    private int[][] hints;
    
    public Minesweeper (){
        board = new String[5][5];
        mines = new boolean[5][5];
        hints = new int[5][5];
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
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                board[row][col] = "/";
                mines[row][col] = false;
            }
        }
        placeMines();
    }

    private void placeMines() {
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                int num = randomNumGen();
                if(num == 9) {
                    mines[row][col] = true;
                }
            }
        }
    }

    private void printRow(String[] row) {
        for(String i : row) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private void printBoard() {
        for(String[] row : board) {
            printRow(row);
        }
    }

    public void runGame() {
        printBoard();
    }
}
