package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javax.swing.Box.createHorizontalBox;

/*
This class represents the GUI for the application
 */
public class GraphicOperations extends JFrame implements ActionListener, WindowListener,
        WindowFocusListener,
        WindowStateListener {

    private JTextField boardSizeTextPrompt;
    private JLabel maxSizeInquiry;
    private JFrame gameFrame;
    private JMenuBar options;
    private JPanel gamePanel;
    private JMenu fileOptions;
    private JMenu pegOptions;
    private JPanel gameBoardPanel;
    private JPanel peg;
    private HashMap<String,Color> colorCodes;
    private Box verticalBox;
    private Box horizontalBox;
    private List<JPanel> panelHistory;

    private static int MAXSIZE = 4;
    private static final int MAXROUNDS = 10;
    int attemptNumber;
    private CombinationChecker checker = new CombinationChecker();

    private PegsCombination currentCombination;
    private static PegsCombination answerKeyCombination;
    private PegsCombination currentPins;
    private PegsCombination pegsPinsCombination;
    private GameBoard gameBoard;

    // These fields are modelled on the JsonSerializationDemo example on EDX
    private static final String JSON_STORE = "./data/mastermind.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the game
    public GraphicOperations() throws FileNotFoundException {
        super("Mastermind");
        setUpColorCodes();
        newGame(false);

    }

    // MODIFIES: this
    // EFFECTS: Establishes the hashmap
    private void setUpColorCodes() {
        colorCodes = new HashMap<>();
        colorCodes.put("red", Color.red);
        colorCodes.put("blue", Color.blue);
        colorCodes.put("green", Color.green);
        colorCodes.put("yellow", Color.yellow);
        colorCodes.put("brown", new Color(110, 60, 24));
        colorCodes.put("orange", Color.orange);
        colorCodes.put("black", Color.black);
        colorCodes.put("white", Color.white);
        colorCodes.put("empty", Color.lightGray);
    }

    // MODIFIES: this
    // EFFECTS: start the game by opening the maxsize prompt and generating answerkey
    // Modelled on the example provided on EDX
    private void startGame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 130));
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        maxSizeInquiry = new JLabel("What would you like the size of the combination to be?");
        JButton btn = new JButton("Confirm");
        btn.setActionCommand("setMaxSize");
        btn.addActionListener(this);
        boardSizeTextPrompt = new JTextField(5);
        add(maxSizeInquiry);
        add(boardSizeTextPrompt);
        add(btn);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        attemptNumber = 0;
    }

    // MODIFIES: this
    //EFFECTS: set up the screen
    private void screenSetUp() {
        gameFrame = new JFrame("Game");
        gameFrame.addWindowListener(this);
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gamePanel = new JPanel(new BorderLayout());
        options = new JMenuBar();
        fileOptionsSetUp();
        pegOptionsSetUp();
        options.add(fileOptions);
        options.add(pegOptions);
        gamePanel.add(options,BorderLayout.NORTH);
        gameBoardPanelSetUp();
        gamePanel.add(gameBoardPanel,BorderLayout.CENTER);
        gameFrame.add(gamePanel);
        gameFrame.setContentPane(gamePanel);
        gameFrame.setPreferredSize(new Dimension(500, 500));
        gameFrame.getContentPane().setBounds(100,100, 100, 100);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up gameBoard
    private void gameBoardPanelSetUp() {
        gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new BoxLayout(gameBoardPanel,BoxLayout.PAGE_AXIS));
        verticalBox = Box.createVerticalBox();
        horizontalBox = Box.createHorizontalBox();
        verticalBox.add(horizontalBox,TOP_ALIGNMENT);
        gameBoardPanel.add(verticalBox);
        gameBoardPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up file options menu
    private void fileOptionsSetUp() {
        fileOptions = new JMenu("File Options");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem restartGame = new JMenuItem("Restart Game");
        undo.setActionCommand("undo");
        save.setActionCommand("save game");
        load.setActionCommand("load game");
        newGame.setActionCommand("new game");
        restartGame.setActionCommand("restart game");
        undo.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        newGame.addActionListener(this);
        restartGame.addActionListener(this);
        fileOptions.add(undo);
        fileOptions.add(save);
        fileOptions.add(load);
        fileOptions.add(newGame);
        fileOptions.add(restartGame);
    }

    // MODIFIES: this
    //EFFECTS: Set up Peg options menu
    private void pegOptionsSetUp() {
        pegOptions = new JMenu("Peg Options");
        JMenuItem redPeg = new JMenuItem("Red Peg");
        JMenuItem bluePeg = new JMenuItem("Blue Peg");
        JMenuItem greenPeg = new JMenuItem("Green Peg");
        JMenuItem yellowPeg = new JMenuItem("Yellow Peg");
        JMenuItem brownPeg = new JMenuItem("Brown Peg");
        JMenuItem orangePeg = new JMenuItem("Orange Peg");
        JMenuItem blackPeg = new JMenuItem("Black Peg");
        JMenuItem whitePeg = new JMenuItem("White Peg");
        pegsSetActionCommand(redPeg, bluePeg, greenPeg, yellowPeg, brownPeg, orangePeg, blackPeg, whitePeg);
        pegsAddActionListener(redPeg, bluePeg, greenPeg, yellowPeg, brownPeg, orangePeg, blackPeg, whitePeg);
        pegsAddIntoPegsOption(redPeg, bluePeg, greenPeg, yellowPeg, brownPeg, orangePeg, blackPeg, whitePeg);
    }

    // MODIFIES: this
    // EFFECTS: Adds the pegs into the pegsOptions JMenu
    private void pegsAddIntoPegsOption(JMenuItem redPeg, JMenuItem bluePeg,
                                       JMenuItem greenPeg, JMenuItem yellowPeg,
                                       JMenuItem brownPeg, JMenuItem orangePeg,
                                       JMenuItem blackPeg, JMenuItem whitePeg) {
        pegOptions.add(redPeg);
        pegOptions.add(bluePeg);
        pegOptions.add(greenPeg);
        pegOptions.add(yellowPeg);
        pegOptions.add(brownPeg);
        pegOptions.add(orangePeg);
        pegOptions.add(blackPeg);
        pegOptions.add(whitePeg);
    }

    // EFFECTS: adds action listener to each of the JMenuItem
    private void pegsAddActionListener(JMenuItem redPeg, JMenuItem bluePeg,
                                       JMenuItem greenPeg, JMenuItem yellowPeg,
                                       JMenuItem brownPeg, JMenuItem orangePeg,
                                       JMenuItem blackPeg, JMenuItem whitePeg) {
        redPeg.addActionListener(this::handleRedPeg);
        bluePeg.addActionListener(this::handleBluePeg);
        greenPeg.addActionListener(this::handleGreenPeg);
        yellowPeg.addActionListener(this::handleYellowPeg);
        brownPeg.addActionListener(this::handleBrownPeg);
        orangePeg.addActionListener(this::handleOrangePeg);
        blackPeg.addActionListener(this::handleBlackPeg);
        whitePeg.addActionListener(this::handleWhitePeg);
    }

    // EFFECTS: sets the action Command for each of the JMenuItem
    private static void pegsSetActionCommand(JMenuItem redPeg, JMenuItem bluePeg,
                                             JMenuItem greenPeg, JMenuItem yellowPeg,
                                             JMenuItem brownPeg, JMenuItem orangePeg,
                                             JMenuItem blackPeg, JMenuItem whitePeg) {
        redPeg.setActionCommand("add red peg");
        bluePeg.setActionCommand("add blue peg");
        greenPeg.setActionCommand("add green peg");
        yellowPeg.setActionCommand("add yellow peg");
        brownPeg.setActionCommand("add brown peg");
        orangePeg.setActionCommand("add orange peg");
        blackPeg.setActionCommand("add black peg");
        whitePeg.setActionCommand("add white peg");
    }

    // MODIFIES: this
    // EFFECTS: adds a white peg onto the gameBoardPanel; if full afterward then adds in the pins
    private void handleWhitePeg(ActionEvent e) {
        if (currentCombination.getSize() < MAXSIZE) {
            createPeg(Color.WHITE);
            pegAddition("white");
            if (currentCombination.getSize() == MAXSIZE) {
                fillPins();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a red peg onto the gameBoardPanel; if full afterward then adds in the pins
    private void handleRedPeg(ActionEvent e) {
        if (currentCombination.getSize() < MAXSIZE) {
            createPeg(Color.RED);
            pegAddition("red");
            if (currentCombination.getSize() == MAXSIZE) {
                fillPins();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a blue peg onto the gameBoardPanel; if full afterward then adds in the pins
    private void handleBluePeg(ActionEvent e) {
        if (currentCombination.getSize() < MAXSIZE) {
            createPeg(Color.BLUE);
            pegAddition("blue");
            if (currentCombination.getSize() == MAXSIZE) {
                fillPins();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a green peg onto the gameBoardPanel; if full afterward then adds in the pins
    private void handleGreenPeg(ActionEvent e) {
        if (currentCombination.getSize() < MAXSIZE) {
            createPeg(Color.GREEN);
            pegAddition("green");
            if (currentCombination.getSize() == MAXSIZE) {
                fillPins();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a yellow peg onto the gameBoardPanel; if full afterward then adds in the pins
    private void handleYellowPeg(ActionEvent e) {
        if (currentCombination.getSize() < MAXSIZE) {
            createPeg(Color.YELLOW);
            pegAddition("yellow");
            if (currentCombination.getSize() == MAXSIZE) {
                fillPins();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a brown peg onto the gameBoardPanel; if full afterward then adds in the pins
    private void handleBrownPeg(ActionEvent e) {
        if (currentCombination.getSize() < MAXSIZE) {
            createPeg(new Color(110, 60, 24));
            pegAddition("brown");
            if (currentCombination.getSize() == MAXSIZE) {
                fillPins();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an orange peg onto the gameBoardPanel; if full afterward then adds in the pins
    private void handleOrangePeg(ActionEvent e) {
        if (currentCombination.getSize() < MAXSIZE) {
            createPeg(Color.ORANGE);
            pegAddition("orange");
            if (currentCombination.getSize() == MAXSIZE) {
                fillPins();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a black peg onto the gameBoardPanel; if full afterward then adds in the pins
    private void handleBlackPeg(ActionEvent e) {
        if (currentCombination.getSize() < MAXSIZE) {
            createPeg(Color.BLACK);
            pegAddition("black");
            if (currentCombination.getSize() == MAXSIZE) {
                fillPins();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deals with the effects of fileOptions JMenuItems, and the maxSizePrompt button
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "setMaxSize":
                setMaxSize();
                break;
            case "undo":
                undoPegChoice();
                break;
            case "save game":
                saveMastermind();
                break;
            case "load game":
                loadGame();
                break;
            case "new game":
                newGame(true);
                break;
            case "restart game":
                restartGame();
                break;

        }
    }

    // MODIFIES: this
    // EFFECTS: Removes the last peg added to current Combination
    private void undoPegChoice() {
        horizontalBox.remove(panelHistory.remove(panelHistory.size() - 1));
        currentCombination.undo();
        horizontalBox.setVisible(true);
        gameFrame.pack();
    }

    // MODIFIES: this
    // EFFECTS: sets the maxsize according to the input integer
    private void setMaxSize() {
        MAXSIZE = Integer.parseInt(boardSizeTextPrompt.getText());
        System.out.println(MAXSIZE);
        answerKeyCombination.generateAnswerKey(MAXSIZE);
        screenSetUp();
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: restarts the game by making a new JFrame, without asking for the amount of pegs again
    private void restartGame() {
        currentCombination = new PegsCombination();
        pegsPinsCombination = new PegsCombination();
        currentPins = new PegsCombination();
        gameBoard = new GameBoard();
        answerKeyCombination = new PegsCombination();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        answerKeyCombination.generateAnswerKey(MAXSIZE);
        panelHistory = new ArrayList<>();
        gameFrame.dispose();
        screenSetUp();

    }

    // MODIFIES: this
    // EFFECTS: create a JPanel (a peg), sets the color, and add it to the gameBoardPanel
    private void createPeg(Color c) {
        peg = new JPanel();
        peg.setBackground(c);
        peg.setSize(1, 1);
        horizontalBox.add(peg);
        panelHistory.add(peg);
        peg.setVisible(true);
        horizontalBox.setVisible(true);
        gameFrame.pack();
    }

    // MODIFIES: this
    // EFFECTS: adds a peg into the current combination
    private void pegAddition(String s) {
        ColourPegs currentPeg = new ColourPegs();
        currentPeg.setColour(s);
        currentCombination.addPegs(currentPeg);
    }

    // MODIFIES: this
    // EFFECTS: fills the pins based on input, and resets current pins, current combination, and pegsPinsCombination
    private void fillPins() {
        currentPins = checker.generatePins(currentCombination,answerKeyCombination);
        addPinsToPanel(currentPins,false);
        pegsPinsCombination.combinePegsAndPins(currentCombination,currentPins);
        gameBoard.addPegsPinsToBoard(pegsPinsCombination);
        if (currentPins.checkWin(currentPins)) {
            MAXSIZE = -1;
            System.out.println("You Win!");
        } else if (attemptNumber == MAXROUNDS) {
            MAXSIZE = -1;
            System.out.println("You Lose!");
        }
        currentPins = new PegsCombination();
        currentCombination = new PegsCombination();
        pegsPinsCombination = new PegsCombination();
        horizontalBox = Box.createHorizontalBox();
        verticalBox.add(horizontalBox,TOP_ALIGNMENT);
        panelHistory = new ArrayList<>();
        attemptNumber += 1;

    }

    // MODIFIES: this
    // EFFECTS: adds colour pegs one by one, given a pegsCombination with a list of ColourPegs
    private void addPinsToPanel(PegsCombination p, boolean isLoadGame) {
        if (isLoadGame) {
            horizontalBox = Box.createHorizontalBox();
            verticalBox.add(horizontalBox,TOP_ALIGNMENT);
        }
        for (ColourPegs c : p.getCombination()) {
            createPeg(colorCodes.get(c.getColour()));
        }
    }

    // MODIFIES: this
    // EFFECTS: starts up a new game with numerous fields initiated
    private void newGame(boolean isRestart) {
        if (isRestart) {
            gameFrame.dispose();
        }
        startGame();
        currentCombination = new PegsCombination();
        pegsPinsCombination = new PegsCombination();
        currentPins = new PegsCombination();
        gameBoard = new GameBoard();
        answerKeyCombination = new PegsCombination();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        panelHistory = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: loads the game data, and add in all the pegs from the save file
    private void loadGame() {
        loadMastermind();
        gameFrame.dispose();
        screenSetUp();
        for (PegsCombination pc : gameBoard.getBoard()) {
            addPinsToPanel(pc,true);
        }
        horizontalBox = Box.createHorizontalBox();
        verticalBox.add(horizontalBox,TOP_ALIGNMENT);
        addPinsToPanel(currentCombination,false);
        panelHistory = new ArrayList<>();
    }

    // MODIFIES: JSON_STORE
    // EFFECTS: saves game to JSON_STORE
    private void saveMastermind() {
        try {
            jsonWriter.open();
            jsonWriter.write(MAXSIZE, attemptNumber,answerKeyCombination,currentCombination,gameBoard);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Load game from JSON_STORE
    private void loadMastermind() {
        try {
            jsonReader.read();
            MAXSIZE = jsonReader.readMaxSize();
            attemptNumber = jsonReader.readAttemptNumber();
            answerKeyCombination = jsonReader.readAnswerKey();
            currentCombination = jsonReader.readCurrentCombination();
            gameBoard = jsonReader.readGameBoard();
        } catch (IOException e) {
            System.out.println("Unable to load file!");
        }
    }

    // EFFECTS: Nothing
    @Override
    public void windowGainedFocus(WindowEvent e) {

    }

    // EFFECTS: Nothing
    @Override
    public void windowLostFocus(WindowEvent e) {

    }

    // EFFECTS: Nothing
    @Override
    public void windowOpened(WindowEvent e) {

    }

    // EFFECTS: prints the eventLog
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.getDescription());
        }
    }

    // EFFECTS: Nothing
    @Override
    public void windowClosed(WindowEvent e) {

    }

    // EFFECTS: Nothing
    @Override
    public void windowIconified(WindowEvent e) {

    }

    // EFFECTS: Nothing
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    // EFFECTS: Nothing
    @Override
    public void windowActivated(WindowEvent e) {

    }

    // EFFECTS: Nothing
    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    // EFFECTS: Nothing
    @Override
    public void windowStateChanged(WindowEvent e) {

    }
}
