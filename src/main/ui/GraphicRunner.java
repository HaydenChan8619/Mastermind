package ui;

import java.io.FileNotFoundException;

/*
This class calls GraphicOperations and runs the game with Graphics.
 */

public class GraphicRunner {

    //EFFECTS: Operates the game
    public static void main(String[] args) {
        try {
            new GraphicOperations();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
        }
    }
}
