import java.util.*;

public class Minesweeper {
    private String[][] board;
    private boolean[][] mines;
    private int[][] hints;
    private boolean[][] revealedBoard;
    private boolean winGame;
    private boolean loseGame;
    
    public Minesweeper (){
        board = new String[5][5];
        mines = new boolean[5][5];
        hints = new int[5][5];
        revealedBoard = new boolean[5][5];
        winGame = false;
        loseGame = false;
    }

    private boolean checkWinGame() {
        for(int row = 0; row < revealedBoard.length; row++) {
            for(int col = 0; col < revealedBoard[row].length; col++) {
                if(!mines[row][col]) {
                    if(!revealedBoard[row][col])
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Generates a random number from 0 to 9 with 9 being a mine
     * @return random number from 0 to 9
     */
    private int randomNumGen() {
        Random random = new Random();
        int num = random.nextInt(6);
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
                revealedBoard[row][col] = false;
                mines[row][col] = false;
            }
        }
    }

    private void placeMines(int x, int y) {
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                int num = randomNumGen();
                if(num == 5 && (row != x && col != y))
                    mines[row][col] = true;
                else 
                    mines[row][col] = false;
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

    private void printBoard() {
        for(String[] row : board) {
            printRow(row);
        }

        for(int[] row : hints) {
            printHints(row);
        }
    }

    private int[] parseInput(String coords) {
        String[] coordinates = coords.split(",");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int[] result = {x,y};
        return result;
    }

    private void revealSpace(int x, int y){
        if(mines[x][y]) {
            System.out.println("You revealed a mine! You Lose!");
            loseGame = true;
        } else if(revealedBoard[x][y]) {
            System.out.println("You already revealed this space. Try Again.");
        } else {
            board[x][y] = Integer.toString(hints[x][y]);
            revealedBoard[x][y] = true;
        }
    }

    public void runGame() {
        initBoard();
        printBoard();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter coordinates to reveal seperated by a comma(x,y): ");
        String input = scan.nextLine();
        int[] coords = parseInput(input);
        placeMines(coords[0], coords[1]);
        placeHints();
        revealSpace(coords[0], coords[1]);
        while(!winGame && !loseGame) {
            printBoard();
            System.out.println("Enter coordinates to reveal seperated by a comma(x,y): ");
            input = scan.nextLine();
            coords = parseInput(input);
            revealSpace(coords[0], coords[1]);
            winGame = checkWinGame();
        }
        if(winGame)
            System.out.println("Congrats! You Win!");
        scan.close();
    }
}
