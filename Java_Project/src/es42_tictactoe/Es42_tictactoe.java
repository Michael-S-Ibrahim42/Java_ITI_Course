package es42_tictactoe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Es42_tictactoe extends Application implements Initializable {

    static class Move {

        int row, col;
    };
    public Stage stage;
    public Scene homescene;
    public Scene winScene;
    public Parent root;
    public Parent winRoot;
    public Parent loseRoot;
    static char player = 'o', opponent = 'x';
    static char board[][] = new char[3][3];
    public static Button[] menuBtns;
    public static TextField[][] gameBoxesSingle;
    public static TextField[][] gameBoxes;

    public static boolean[] isWinner;
    public static int[] turn;
    public static int[] cord;
    public static int[] playerScore;
    public static Controller[] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();
    public static Controller gamepad1;
    public static Controller gamepad2;
    public static EventQueue queue1;
    public static EventQueue queue2;
    public static Event event = new Event();
    public static int sceneID;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBtns = new Button[3];
        menuBtns[0] = btn1;
        menuBtns[1] = btn2;
        menuBtns[2] = btn3;
    }//initialize

    /* This function returns true if there are moves remaining on the board. It returns false if there are no moves left to play. */
    static Boolean isMovesLeft() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    return true;
                }
            }
        }
        return false;
    }
/////////////////////////////////////////////// Move Ya Gad3 /////////////////////////////////////////

    /* This is the evaluation function as discussed */
    static int evaluate() {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == player) {
                    return +10;
                } else if (board[row][0] == opponent) {
                    return -10;
                }
            }
        }
        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == player) {
                    return +10;
                } else if (board[0][col] == opponent) {
                    return -10;
                }
            }
        }
        // Checking for Diagonals for X or O victory.
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == player) {
                return +10;
            } else if (board[0][0] == opponent) {
                return -10;
            }
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == player) {
                return +10;
            } else if (board[0][2] == opponent) {
                return -10;
            }
        }
        // Else if none of them have won then return 0
        return 0;
    }//evaluate method

    /* This is the minimax function. It considers all the possible ways the game 
        can go and returns the value of the board */
    static int minimax(int depth, Boolean isMax) {
        int score = evaluate();
        /* If Maximizer has won the game return his/her evaluated score */
        if (score == 10) {
            return score;
        }
        /* If Minimizer has won the game return his/her evaluated score */
        if (score == -10) {
            return score;
        }
        /* If there are no more moves and no winner then it is a tie */
        if (isMovesLeft() == false) {
            return 0;
        }
        /* If this maximizer's move */
        if (isMax) {
            int best = -1000;
            /* Traverse all cells */
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    /* Check if cell is empty */
                    if (board[i][j] == '_') {
                        board[i][j] = player; // Make the move
                        /* Call minimax recursively and choose the maximum value */
                        best = Math.max(best, minimax(depth + 1, !isMax));
                        /* Undo the move */
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        } // If this minimizer's move
        else {
            int best = 1000;
            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == '_') {
                        // Make the move
                        board[i][j] = opponent;
                        /* Call minimax recursively and choose the minimum value */
                        best = Math.min(best, minimax(depth + 1, !isMax));
                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    /* This will return the best possible move for the player */
    static Move findBestMove() {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;
        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board[i][j] == '_') {
                    // Make the move
                    board[i][j] = player;
                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(0, false);
                    // Undo the move
                    board[i][j] = '_';
                    // If the value of the current move is
                    // more than the best value, then update
                    // best
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        System.out.printf("The value of the best Move " + "is : %d\n\n", bestVal);
        return (bestMove);
    }

    public void singlePlayer(ActionEvent event) {
        sceneID = 1;
        board[0][0] = board[0][1] = board[0][2] = '_';
        board[1][0] = board[1][1] = board[1][2] = '_';
        board[2][0] = board[2][1] = board[2][2] = '_';

        TilePane gameTile = new TilePane();
        gameTile.setPrefColumns(3);
        gameBoxesSingle = new TextField[3][3];//The boxes of the game
        playerScore = new int[2];//0 --> playerX && 1 --> playerO
        playerScore[0] = 0;//Default begin : playerX score
        playerScore[1] = 0;//Default begin : playerO score
        isWinner = new boolean[1];
        isWinner[0] = false; // defines whether there is a winner or not
        turn = new int[1]; //0 --> 'O' && 1 --> 'X'
        turn[0] = 1; // To begin playing with 'X'
        cord = new int[2];//The "X,Y" Coordinates ... 0 --> 'Y' && 1 --> 'X'
        cord[0] = 0;//set default 'Y' value
        cord[1] = 0;//set default 'X' value
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextField gameBox = new TextField();
                gameBoxesSingle[i][j] = gameBox;
                gameBox.setEditable(false);
                gameBox.setFont(new Font(50));
                gameBox.setAlignment(Pos.CENTER);
                gameBox.setPrefSize(100, 100);
                gameTile.getChildren().add(gameBox);
            }//for ... embedded for
        }//for
        stage = (Stage) (((Node) (event.getSource())).getScene().getWindow());
        homescene = new Scene(gameTile);
        stage.setScene(homescene);
        stage.show();
    }//singlePlayer    

    public void multiPlayer(ActionEvent event) {
        sceneID = 2;
        TilePane gameTile = new TilePane();
        gameTile.setPrefColumns(3);
        gameBoxes = new TextField[3][3];//The boxes of the game
        playerScore = new int[2];//0 --> playerX && 1 --> playerO
        playerScore[0] = 0;//Default begin : playerX score
        playerScore[1] = 0;//Default begin : playerO score
        isWinner = new boolean[1];
        isWinner[0] = false; // defines whether there is a winner or not
        turn = new int[1]; //0 --> 'O' && 1 --> 'X'
        turn[0] = 1; // To begin playing with 'X'
        cord = new int[2];//The "X,Y" Coordinates ... 0 --> 'Y' && 1 --> 'X'
        cord[0] = 0;//set default 'Y' value
        cord[1] = 0;//set default 'X' value
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextField gameBox = new TextField();
                gameBoxes[i][j] = gameBox;
                gameBox.setEditable(false);
                gameBox.setFont(new Font(50));
                gameBox.setAlignment(Pos.CENTER);
                gameBox.setPrefSize(100, 100);
                gameTile.getChildren().add(gameBox);
            }//for ... embedded for
        }//for
        stage = (Stage) (((Node) (event.getSource())).getScene().getWindow());
        homescene = new Scene(gameTile);
        stage.setScene(homescene);
        stage.show();
    }//multiPlayer

    public void exit(ActionEvent event) {
        System.exit(0);
    }//exit

    @Override
    public void start(Stage primaryStage) {
        sceneID = 0;
        int assignmentFlag = 0;
        for (int i = 0; i < controllerList.length; i++) {
            if (controllerList[i].getType() == Controller.Type.STICK) {
                if (assignmentFlag == 0) {
                    gamepad1 = controllerList[i];
                    queue1 = gamepad1.getEventQueue();
                    assignmentFlag = 1;
                }//if
                else {
                    gamepad2 = controllerList[i];
                    queue2 = gamepad2.getEventQueue();
                }//else
            }//if
        }//for
        Image icon = new Image(Es42_tictactoe.class.getResourceAsStream("ITI.png"));
        try {
            root = FXMLLoader.load(this.getClass().getResource("HomeScene.fxml"));
        }//try
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }//catch
        menuBtns[0].setStyle("-fx-background-color: #3333ff;");
        Thread stickThread = new Thread(() -> {
            int setMenuPos = 0;
            int clrMenuPos = 0;

            while (true) {
                gamepad1.poll();
                gamepad2.poll();
                switch (sceneID) {
                    case 0://Home Scene
                        while (queue1.getNextEvent(event)) {
                            Component stickComp = event.getComponent();
                            float stickValue = event.getValue();
                            switch (stickComp.getName()) {
                                case "Y Axis":
                                    if (stickValue == 1.0f) {
                                        clrMenuPos = setMenuPos;
                                        setMenuPos += 1;
                                        if (setMenuPos > 2) {
                                            setMenuPos = 0;
                                        }//if
                                    }//if
                                    else if (stickValue == -1.0f) {
                                        clrMenuPos = setMenuPos;
                                        setMenuPos -= 1;
                                        if (setMenuPos < 0) {
                                            setMenuPos = 2;
                                        }//if
                                    }//else if
                                    final int setPos = setMenuPos;
                                    final int clrPos = clrMenuPos;
                                    Platform.runLater(() -> {
                                        menuBtns[clrPos].setStyle("-fx-background-color: #00000090;");
                                        menuBtns[setPos].setStyle("-fx-background-color: #3333ff;");
                                    } //run method
                                    );//Platform runLater
                                    break;
                                case "Button 1":
                                    if (stickValue == 0) {
                                        break;
                                    }
                                    System.exit(0);
                                    break;
                                case "Button 2":
                                    if (stickValue == 0) {
                                        break;
                                    }//if
                                    final int firePos = setMenuPos;
                                    Platform.runLater(() -> {
                                        menuBtns[firePos].fire();
                                    } //run method
                                    );//Platform runLater
                                    break;
                            }//switch
                        }//while
                        break;
                    case 1:
                        while (queue1.getNextEvent(event)) {
                            Component stickComp = event.getComponent();
                            float stickValue = event.getValue();
                            switch (stickComp.getName()) {
                                case "X Axis":
                                    if (stickValue == 1.0f) {//I'm going right
                                        if (!isWinner[0]) {
                                            cord[1]++;//X coordinate
                                            if (cord[1] == 3) {
                                                cord[1] = 0;
                                            }
                                            Platform.runLater(() -> {
                                                gameBoxesSingle[cord[0]][cord[1]].requestFocus();
                                                gameBoxesSingle[cord[0]][cord[1]].deselect();
                                            });//Platform
                                        }//if
                                    }//if
                                    else if (stickValue == -1.0f) {//I'm going left
                                        if (!isWinner[0]) {
                                            cord[1]--;
                                            if (cord[1] < 0) {
                                                cord[1] = 2;
                                            }
                                            Platform.runLater(() -> {
                                                gameBoxesSingle[cord[0]][cord[1]].requestFocus();
                                                gameBoxesSingle[cord[0]][cord[1]].deselect();
                                            });//Platform
                                        }//if
                                    }
                                    break;
                                case "Y Axis":
                                    if (stickValue == 1.0f) {//I'm going down
                                        if (!isWinner[0]) {
                                            cord[0]++;
                                            if (cord[0] == 3) {
                                                cord[0] = 0;
                                            }
                                            Platform.runLater(() -> {
                                                gameBoxesSingle[cord[0]][cord[1]].requestFocus();
                                                gameBoxesSingle[cord[0]][cord[1]].deselect();
                                            });//Platform
                                        }//if
                                    }//if
                                    else if (stickValue == -1.0f) {//I'm going up
                                        if (!isWinner[0]) {
                                            cord[0]--;
                                            if (cord[0] < 0) {
                                                cord[0] = 2;
                                            }
                                            Platform.runLater(() -> {
                                                gameBoxesSingle[cord[0]][cord[1]].requestFocus();
                                                gameBoxesSingle[cord[0]][cord[1]].deselect();
                                            });
                                        }//if
                                    }//else if
                                    break;
                                case "Button 1":
                                    sceneID = 0;
                                    Platform.runLater(() -> {
                                        primaryStage.setScene(homescene);
                                    } //run method
                                    );//Platform runLater
                                    break;
                                case "Button 2":
                                    if (isWinner[0] || stickValue == 0) {
                                        break;
                                    }//if
                                    if (gameBoxesSingle[cord[0]][cord[1]].getText().equals("")) {
                                        gameBoxesSingle[cord[0]][cord[1]].setText("x");
                                        gameBoxesSingle[cord[0]][cord[1]].setStyle("-fx-text-inner-color: blue");
                                        if (gameBoxesSingle[cord[0]][0].getText().equals(gameBoxesSingle[cord[0]][1].getText()) && gameBoxesSingle[cord[0]][0].getText().equals(gameBoxesSingle[cord[0]][2].getText())) {
                                            System.out.println("I'm in First");
                                            playerScore[turn[0]]++;//Player won
                                            gameBoxesSingle[cord[0]][0].setStyle("-fx-background-color: lime");
                                            gameBoxesSingle[cord[0]][1].setStyle("-fx-background-color: lime");
                                            gameBoxesSingle[cord[0]][2].setStyle("-fx-background-color: lime");
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        winRoot = FXMLLoader.load(this.getClass().getResource("WinScene.fxml"));
                                                    } catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                    }//catch
                                                    winScene = new Scene(winRoot);
                                                }//run
                                            });
                                            PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                            delay.setOnFinished(x -> primaryStage.setScene(winScene));
                                            delay.play();
                                        }//if 
                                        else if (gameBoxesSingle[0][cord[1]].getText().equals(gameBoxesSingle[1][cord[1]].getText()) && gameBoxesSingle[0][cord[1]].getText().equals(gameBoxesSingle[2][cord[1]].getText())) {
                                            System.out.println("I'm in Second");
                                            playerScore[turn[0]]++;//Player Won
                                            gameBoxesSingle[0][cord[1]].setStyle("-fx-background-color: lime");
                                            gameBoxesSingle[1][cord[1]].setStyle("-fx-background-color: lime");
                                            gameBoxesSingle[2][cord[1]].setStyle("-fx-background-color: lime");
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        winRoot = FXMLLoader.load(this.getClass().getResource("WinScene.fxml"));
                                                    } catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                    }//catch
                                                    winScene = new Scene(winRoot);
                                                }//run
                                            });
                                            PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                            delay.setOnFinished(x -> primaryStage.setScene(winScene));
                                            delay.play();
                                        }//else if
                                        else if (cord[0] == cord[1]) {
                                            System.out.println("I'm in Third");
                                            if (gameBoxesSingle[0][0].getText().equals(gameBoxesSingle[1][1].getText()) && gameBoxesSingle[0][0].getText().equals(gameBoxesSingle[2][2].getText())) {
                                                playerScore[turn[0]]++;//Player Won
                                                gameBoxesSingle[0][0].setStyle("-fx-background-color: lime");
                                                gameBoxesSingle[1][1].setStyle("-fx-background-color: lime");
                                                gameBoxesSingle[2][2].setStyle("-fx-background-color: lime");
                                                Platform.runLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            winRoot = FXMLLoader.load(this.getClass().getResource("WinScene.fxml"));
                                                        } catch (Exception e) {
                                                            System.out.println(e.getMessage());
                                                        }//catch
                                                        winScene = new Scene(winRoot);
                                                    }//run
                                                });
                                                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                                delay.setOnFinished(x -> primaryStage.setScene(winScene));
                                                delay.play();
                                            }//if
                                            else {
                                                System.out.println("I'm in proper case");
                                                board[cord[0]][cord[1]] = 'x';
                                                Move perfectMove = findBestMove();
                                                cord[0] = perfectMove.row;
                                                cord[1] = perfectMove.col;
                                                board[perfectMove.row][perfectMove.col] = 'o';
                                                gameBoxesSingle[perfectMove.row][perfectMove.col].setText("o");
                                                gameBoxesSingle[perfectMove.row][perfectMove.col].setStyle("-fx-text-inner-color: blue");
                                            }
                                        }//else if
                                        else if ((cord[0] + cord[1]) == 2) {
                                            System.out.println("I'm in Fourth");
                                            if (gameBoxesSingle[0][2].getText().equals(gameBoxesSingle[1][1].getText()) && gameBoxesSingle[0][2].getText().equals(gameBoxesSingle[2][0].getText())) {
                                                playerScore[turn[0]]++;//Player Won
                                                gameBoxesSingle[0][2].setStyle("-fx-background-color: lime");
                                                gameBoxesSingle[1][1].setStyle("-fx-background-color: lime");
                                                gameBoxesSingle[2][0].setStyle("-fx-background-color: lime");
                                                Platform.runLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            winRoot = FXMLLoader.load(this.getClass().getResource("WinScene.fxml"));
                                                        } catch (Exception e) {
                                                            System.out.println(e.getMessage());
                                                        }//catch
                                                        winScene = new Scene(winRoot);
                                                    }//run
                                                });
                                                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                                delay.setOnFinished(x -> primaryStage.setScene(winScene));
                                                delay.play();
                                            }//if
                                            else {
                                                System.out.println("I'm in proper case");
                                                board[cord[0]][cord[1]] = 'x';
                                                Move perfectMove = findBestMove();
                                                cord[0] = perfectMove.row;
                                                cord[1] = perfectMove.col;
                                                board[perfectMove.row][perfectMove.col] = 'o';
                                                gameBoxesSingle[perfectMove.row][perfectMove.col].setText("o");
                                                gameBoxesSingle[perfectMove.row][perfectMove.col].setStyle("-fx-text-inner-color: blue");
                                            }
                                        }//else if
                                        else {
                                            System.out.println("I'm in proper case");
                                            board[cord[0]][cord[1]] = 'x';
                                            Move perfectMove = findBestMove();
                                            cord[0] = perfectMove.row;
                                            cord[1] = perfectMove.col;
                                            board[perfectMove.row][perfectMove.col] = 'o';
                                            gameBoxesSingle[perfectMove.row][perfectMove.col].setText("o");
                                            gameBoxesSingle[perfectMove.row][perfectMove.col].setStyle("-fx-text-inner-color: blue");
                                        }
                                    }//if
                                    break;
                            }//switch
                        }//while
                        break;
                    case 2: //multiplayer Scene
                        while (queue1.getNextEvent(event) || queue2.getNextEvent(event)) {
                            Component stickComp = event.getComponent();
                            float stickValue = event.getValue();
                            switch (stickComp.getName()) {
                                case "X Axis":
                                    if (stickValue == 1.0f) {//I'm going right
                                        if (!isWinner[0]) {
                                            cord[1]++;//X coordinate
                                            if (cord[1] == 3) {
                                                cord[1] = 0;
                                            }
                                            Platform.runLater(() -> {
                                                gameBoxes[cord[0]][cord[1]].requestFocus();
                                                gameBoxes[cord[0]][cord[1]].deselect();
                                            });//Platform
                                        }//if
                                    }//if
                                    else if (stickValue == -1.0f) {//I'm going left
                                        if (!isWinner[0]) {
                                            cord[1]--;
                                            if (cord[1] < 0) {
                                                cord[1] = 2;
                                            }
                                            Platform.runLater(() -> {
                                                gameBoxes[cord[0]][cord[1]].requestFocus();
                                                gameBoxes[cord[0]][cord[1]].deselect();
                                            });//Platform
                                        }//if
                                    }
                                    break;
                                case "Y Axis":
                                    if (stickValue == 1.0f) {//I'm going down
                                        if (!isWinner[0]) {
                                            cord[0]++;
                                            if (cord[0] == 3) {
                                                cord[0] = 0;
                                            }
                                            Platform.runLater(() -> {
                                                gameBoxes[cord[0]][cord[1]].requestFocus();
                                                gameBoxes[cord[0]][cord[1]].deselect();
                                            });//Platform
                                        }//if
                                    }//if
                                    else if (stickValue == -1.0f) {//I'm going up
                                        if (!isWinner[0]) {
                                            cord[0]--;
                                            if (cord[0] < 0) {
                                                cord[0] = 2;
                                            }
                                            Platform.runLater(() -> {
                                                gameBoxes[cord[0]][cord[1]].requestFocus();
                                                gameBoxes[cord[0]][cord[1]].deselect();
                                            });
                                        }//if
                                    }//else if
                                    break;
                                case "Button 1":
                                    sceneID = 0;
                                    Platform.runLater(() -> {
                                        primaryStage.setScene(homescene);
                                    } //run method
                                    );//Platform runLater

                                    break;
                                case "Button 2":
                                    if (isWinner[0] || stickValue == 0) {
                                        break;
                                    }//if
                                    if (gameBoxes[cord[0]][cord[1]].getText().equals("")) {
                                        switch (turn[0]) {
                                            case 0:
                                                gameBoxes[cord[0]][cord[1]].setText("o");
                                                gameBoxes[cord[0]][cord[1]].setStyle("-fx-text-inner-color: blue");
                                                turn[0] = 1;
                                                break;
                                            case 1:
                                                gameBoxes[cord[0]][cord[1]].setText("x");
                                                gameBoxes[cord[0]][cord[1]].setStyle("-fx-text-inner-color: red");
                                                turn[0] = 0;
                                                break;
                                        }
                                        if (gameBoxes[cord[0]][0].getText().equals(gameBoxes[cord[0]][1].getText()) && gameBoxes[cord[0]][0].getText().equals(gameBoxes[cord[0]][2].getText())) {
                                            playerScore[turn[0]]++;//Player won
                                            gameBoxes[cord[0]][0].setStyle("-fx-background-color: lime");
                                            gameBoxes[cord[0]][1].setStyle("-fx-background-color: lime");
                                            gameBoxes[cord[0]][2].setStyle("-fx-background-color: lime");
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        winRoot = FXMLLoader.load(this.getClass().getResource("WinScene.fxml"));
                                                    } catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                    }//catch
                                                    winScene = new Scene(winRoot);
                                                }//run
                                            });
                                            PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                            delay.setOnFinished(x -> primaryStage.setScene(winScene));
                                            delay.play();
                                        }//if 
                                        else if (gameBoxes[0][cord[1]].getText().equals(gameBoxes[1][cord[1]].getText()) && gameBoxes[0][cord[1]].getText().equals(gameBoxes[2][cord[1]].getText())) {
                                            playerScore[turn[0]]++;//Player Won
                                            gameBoxes[0][cord[1]].setStyle("-fx-background-color: lime");
                                            gameBoxes[1][cord[1]].setStyle("-fx-background-color: lime");
                                            gameBoxes[2][cord[1]].setStyle("-fx-background-color: lime");
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        winRoot = FXMLLoader.load(this.getClass().getResource("WinScene.fxml"));
                                                    } catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                    }//catch
                                                    winScene = new Scene(winRoot);
                                                }//run
                                            });
                                            PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                            delay.setOnFinished(x -> primaryStage.setScene(winScene));
                                            delay.play();
                                        }//else if
                                        else if (cord[0] == cord[1]) {
                                            if (gameBoxes[0][0].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][0].getText().equals(gameBoxes[2][2].getText())) {
                                                playerScore[turn[0]]++;//Player Won
                                                gameBoxes[0][0].setStyle("-fx-background-color: lime");
                                                gameBoxes[1][1].setStyle("-fx-background-color: lime");
                                                gameBoxes[2][2].setStyle("-fx-background-color: lime");
                                                Platform.runLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            winRoot = FXMLLoader.load(this.getClass().getResource("WinScene.fxml"));
                                                        } catch (Exception e) {
                                                            System.out.println(e.getMessage());
                                                        }//catch
                                                        winScene = new Scene(winRoot);
                                                    }//run
                                                });
                                                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                                delay.setOnFinished(x -> primaryStage.setScene(winScene));
                                                delay.play();
                                            }//if
                                        }//else if
                                        else if ((cord[0] + cord[1]) == 2) {
                                            if (gameBoxes[0][2].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][2].getText().equals(gameBoxes[2][0].getText())) {
                                                playerScore[turn[0]]++;//Player Won
                                                gameBoxes[0][2].setStyle("-fx-background-color: lime");
                                                gameBoxes[1][1].setStyle("-fx-background-color: lime");
                                                gameBoxes[2][0].setStyle("-fx-background-color: lime");
                                                Platform.runLater(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            winRoot = FXMLLoader.load(this.getClass().getResource("WinScene.fxml"));
                                                        } catch (Exception e) {
                                                            System.out.println(e.getMessage());
                                                        }//catch
                                                        winScene = new Scene(winRoot);
                                                    }//run
                                                });
                                                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                                delay.setOnFinished(x -> primaryStage.setScene(winScene));
                                                delay.play();
                                            }//if
                                        }//else if
                                    }//if
                                    break;
                            }//switch
                        }//while
                        break;
                    default://Debugging Purpose
                        System.out.println("Out Of bounds sceneID Value");
                        System.exit(0);
                }//switch
                /* Thread sleep to let the Application thread run */
                try {
                    Thread.sleep(20);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }//while
        } //run
        );//thread
        stickThread.start();
        String css = this.getClass().getResource("Home.css").toExternalForm();
        homescene = new Scene(root);
        homescene.getStylesheets().add(css);
        primaryStage.setScene(homescene);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Tic_Tac_Toe");
        primaryStage.show();
    }//start

    @Override
    public void stop() {
        System.exit(0);
    }//stop

    public static void main(String[] args) {
        Application.launch(args);
    }//main
}//class Main
