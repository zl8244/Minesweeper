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

    /**
     * Creates the board the player sees, the board that indicates where the mines are, 
     * and the board for the hints that indicate how many mines are in adjacent spaces
     */
    private void initBoard() {
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                board[row][col] = "/";
                mines[row][col] = false;
            }
        }
        placeMines();
        placeHints();
    }

    /**
     * Randomly places mines in spaces
     */
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

    /**
     * Places the hints for number of mines in adjacent spaces
     */
    private void placeHints() {
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                if(!mines[row][col]) {
                    int hint = 0;
                    // check above
                    if(row-1 != -1 && mines[row-1][col])
                        hint++;
                    // check below
                    if(row+1 != board.length && mines[row+1][col])
                        hint++;
                    // check left
                    if(col-1 != -1 && mines[row][col-1])
                        hint++;
                    // check right
                    if(col+1 != board[row].length && mines[row][col+1])
                        hint++;
                    // check top-left
                    if((row-1 != -1 && col-1 != -1) && mines[row-1][col-1])
                        hint++;
                    // check top-right
                    if((row-1 != -1 && col+1 != board[row].length) && mines[row-1][col+1])
                        hint++;
                    // check bottom-left
                    if((row+1 != board.length && col-1 != -1) && mines[row+1][col-1])
                        hint++;
                    // check bottom-right
                    if((row+1 != board.length && col+1 != board[row].length) && mines[row+1][col+1])
                        hint++;
                    hints[row][col] = hint;
                } else {
                    hints[row][col] = 9;
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

    private void printHints(int[] row) {
        for(int i : row) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private void printMines(boolean[] row) {
        for(boolean i : row) {
            if(i)
                System.out.print(i + "  ");
            else
                System.out.print(i + " ");
        }
        System.out.println();
    }

    private void printBoard() {
        for(String[] row : board) {
            printRow(row);
        }

        for(int[] row : hints) {
            printHints(row);
        }

        for(boolean[] row : mines) {
            printMines(row);
        }
    }

    public void runGame() {
        printBoard();
    }
}
