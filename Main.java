import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper! Please select your difficulty: ");
        System.out.println("(E)asy\n(M)edium\n(H)ard");
        String difficulty = scan.nextLine();
        String[] validDiffs = {"E", "e", "M", "m", "H", "h"};
        for(int i = 0; i < validDiffs.length; i++) {
            if(difficulty.equals(validDiffs[i]))
                break;
        }
        Minesweeper game = new Minesweeper(difficulty);
        game.runGame();
        scan.close();
    }
}
