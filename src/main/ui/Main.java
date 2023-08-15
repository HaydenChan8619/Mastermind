package ui;

/*
This class calls Mastermind.masterMindGame and runs the game.
 */

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            Mastermind game = new Mastermind();
            game.masterMindGame();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }
    }
}