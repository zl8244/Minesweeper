import java.util.*;

public class Minesweeper {
    private String[][] board;
    private boolean[][] mines;
    private int[][] hints;
    private boolean[][] revealedBoard;
    private boolean[][] flags;
    private boolean winGame;
    private boolean loseGame;
    private int totalRow;
    private int totalCol;
    
    public Minesweeper(String diff){
        if(diff.equals("E") || diff.equals("e")) {
            totalRow = 5;
            totalCol = 5;
        }
        if(diff.equals("M") || diff.equals("m")) {
            totalRow = 7;
            totalCol = 7;
        } 
        if(diff.equals("H") || diff.equals("h")) {
            totalRow = 10;
            totalCol = 10;
        }
        board = new String[totalRow][totalCol];
        mines = new boolean[totalRow][totalCol];
        hints = new int[totalRow][totalCol];
        revealedBoard = new boolean[totalRow][totalCol];
        flags = new boolean[totalRow][totalCol];
        winGame = false;
        loseGame = false;
    }

    /**
     * Checks if the game has been won
     * @return if the game has been won
     */
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
                revealedBoard[row][col] = false;
                mines[row][col] = false;
                flags[row][col] = false;
            }
        }
    }

    /**
     * Randomly places mines on the board except the x and y coordinate
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void placeMines(int x, int y) {
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                int num = randomNumGen();
                if(num == 9 && (row != x && col != y))
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
                    if(row-1 >= 0 && mines[row-1][col])
                        hint++;
                    // check below
                    if(row+1 < board.length && mines[row+1][col])
                        hint++;
                    // check left
                    if(col-1 >= 0 && mines[row][col-1])
                        hint++;
                    // check right
                    if(col+1 < board[row].length && mines[row][col+1])
                        hint++;
                    // check top-left
                    if((row-1 >= 0 && col-1 >= 0) && mines[row-1][col-1])
                        hint++;
                    // check top-right
                    if((row-1 >= 0 && col+1 < board[row].length) && mines[row-1][col+1])
                        hint++;
                    // check bottom-left
                    if((row+1 < board.length && col-1 >= 0) && mines[row+1][col-1])
                        hint++;
                    // check bottom-right
                    if((row+1 < board.length && col+1 < board[row].length) && mines[row+1][col+1])
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

    /**
     * Parses inputted coordinates into usable data
     * @param coords the inputed coordinates seperated by a comma(x,y)
     * @return an array with index 0 being the x coordinate and index 1 being the y coordinate
     */
    private int[] parseInput(String coords) {
        String[] coordinates = coords.split(",");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int[] result = {x,y};
        return result;
    }

    /**
     * Reveals space at x and y, if the space is 0 then it reveals everything around it
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void revealSpace(int x, int y){
        if(mines[x][y]) {
            System.out.println("You revealed a mine! You Lose!");
            loseGame = true;
            board[x][y] = Integer.toString(hints[x][y]);
            revealedBoard[x][y] = true;
        } else if(revealedBoard[x][y]) {
            System.out.println("You already revealed this space. Try Again.");
        } else if(hints[x][y] == 0) {
            board[x][y] = Integer.toString(hints[x][y]);
            revealedBoard[x][y] = true;
            if(x-1 > -1) {
                board[x-1][y] = Integer.toString(hints[x-1][y]);
                revealedBoard[x-1][y] = true;
            }
            if(y-1 > -1) {
                board[x][y-1] = Integer.toString(hints[x][y-1]);
                revealedBoard[x][y-1] = true;
            }
            if(x-1 > -1 && y-1 > -1) {
                board[x-1][y-1] = Integer.toString(hints[x-1][y-1]);
                revealedBoard[x-1][y-1] = true;
            }
            if(x-1 > -1 && y+1 < board[x-1].length) {
                board[x-1][y+1] = Integer.toString(hints[x-1][y+1]);
                revealedBoard[x-1][y+1] = true;
            }
            if(x+1 < board.length) {
                board[x+1][y] = Integer.toString(hints[x+1][y]);
                revealedBoard[x+1][y] = true;
            }
            if(y+1 < board[x].length) {
                board[x][y+1] = Integer.toString(hints[x][y+1]);
                revealedBoard[x][y+1] = true;
            }
            if(x+1 < board.length && y+1 < board[x+1].length) {
                board[x+1][y+1] = Integer.toString(hints[x+1][y+1]);
                revealedBoard[x+1][y+1] = true;
            }
            if(x+1 < board.length && y-1 > -1) {
                board[x+1][y-1] = Integer.toString(hints[x+1][y-1]);
                revealedBoard[x+1][y-1] = true;
            }
        } else {
            board[x][y] = Integer.toString(hints[x][y]);
            revealedBoard[x][y] = true;
        }
    }

    /**
     * Makes the space at x and y a flagged space, preventing the space from being revealed
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void flagSpace(int x, int y) {
        if(flags[x][y]) {
            board[x][y] = "/";
            flags[x][y] = false;
        } else {
            board[x][y] = "F";
            flags[x][y] = true;
        }
    }

    /**
     * Runs the Minesweeper game
     */
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
            System.out.println("What would you like to do?");
            System.out.println("(D)ig");
            System.out.println("(F)lag/Unflag");
            input = scan.nextLine();
            if(input.equals("D")) {
                System.out.println("Enter coordinates to reveal seperated by a comma(x,y): ");
                input = scan.nextLine();
                coords = parseInput(input);
                if(!flags[coords[0]][coords[1]]) {
                    revealSpace(coords[0], coords[1]);
                } else {
                    System.out.println("That space has a flag!");
                }
            } else if(input.equals("F")) {
                System.out.println("Enter coordinates to flag seperated by a comma(x,y): ");
                input = scan.nextLine();
                coords = parseInput(input);
                if(!revealedBoard[coords[0]][coords[1]]) {
                    flagSpace(coords[0], coords[1]);
                } else {
                    System.out.println("That space has already been revealed!");
                }
            }
            winGame = checkWinGame();
        }
        if(winGame)
            System.out.println("Congrats! You Win!");
        scan.close();
    }
}
