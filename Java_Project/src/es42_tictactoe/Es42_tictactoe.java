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

    public Stage stage;
    public Scene scene;
    public Parent root;
    public Parent winRoot;
    public Parent loseRoot;
    /* Static Variables Section */
    public static Button[] menuBtns;
    public static TextField[][] gameBoxes;
    public static boolean[] isWinner;
    public static int[] turn;
    public static int[] cord;
    public static int[] playerScore;
    public Controller[] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();
    public Controller gamepad1;
    public Controller gamepad2;
    public EventQueue queue1;
    public EventQueue queue2;
    public Event event = new Event();
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

    public void singlePlayer(ActionEvent event) {
        sceneID = 1;
        try {
            URL fxml = this.getClass().getResource("WinScene.fxml");
            root = FXMLLoader.load(fxml);
        }//try
        catch (Exception e) {
            System.out.println(e.getMessage());
        }//catch
        stage = (Stage) (((Node) (event.getSource())).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
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
        try {
            URL fxml = this.getClass().getResource("WinScene.fxml");
            winRoot = FXMLLoader.load(fxml);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }//catch
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
        scene = new Scene(gameTile);
        stage.setScene(scene);
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
            URL fxml = this.getClass().getResource("HomeScene.fxml");
            root = FXMLLoader.load(fxml);
        }//try
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }//catch
        es42_tictactoe.Es42_tictactoe.menuBtns[0].setStyle("-fx-background-color: #3333ff;");
        Thread stickThread = new Thread(() -> {
            int setMenuPos = 0;
            int clrMenuPos = 0;

            while (true) {
                gamepad1.poll();
                gamepad2.poll();
                switch (sceneID) {
                    case 0:
                        System.out.println("You are in home");
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
                                        es42_tictactoe.Es42_tictactoe.menuBtns[clrPos].setStyle("-fx-background-color: #00000090;");
                                        es42_tictactoe.Es42_tictactoe.menuBtns[setPos].setStyle("-fx-background-color: #3333ff;");
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
                                    System.out.println("set" + setMenuPos);
                                    System.out.println("clr" + clrMenuPos);
                                    final int firePos = setMenuPos;
                                    Platform.runLater(() -> {
                                        es42_tictactoe.Es42_tictactoe.menuBtns[firePos].fire();
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
                                case "Y Axis":
                                    //System.out.println("I'm in case with value = " + stickValue);
                                    if (stickValue == 1.0f) {
                                        System.out.println("I'm in 1 if");
                                        clrMenuPos = setMenuPos;
                                        setMenuPos += 1;
                                        if (setMenuPos > 2) {
                                            setMenuPos = 0;
                                        }//if
                                    }//if
                                    else if (stickValue == -1.0f) {
                                        System.out.println("I'm in -1 if");
                                        clrMenuPos = setMenuPos;
                                        setMenuPos -= 1;
                                        if (setMenuPos < 0) {
                                            setMenuPos = 2;
                                        }//if
                                    }//else if
                                    final int setPos = setMenuPos;
                                    final int clrPos = clrMenuPos;
                                    Platform.runLater(() -> {
                                        es42_tictactoe.Es42_tictactoe.menuBtns[clrPos].setStyle("-fx-background-color: #00000090;");
                                        es42_tictactoe.Es42_tictactoe.menuBtns[setPos].setStyle("-fx-background-color: #3333ff;");
                                    } //run method
                                    );//Platform runLater
                                    break;
                                case "Button 1":
                                    System.exit(0);
                                    break;
                                case "Button 2":
                                    final int firePos = setMenuPos;
                                    Platform.runLater(() -> {
                                        es42_tictactoe.Es42_tictactoe.menuBtns[firePos].fire();
                                    } //run method
                                    );//Platform runLater
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
                                            cord[1]++;
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
                                    scene.setRoot(root);
                                    Platform.runLater(() -> {
                                        primaryStage.setScene(scene);
                                    } //run method
                                    );//Platform runLater
                                    break;
                                case "Button 2":
                                    System.out.println(stickValue);

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
                                            scene = new Scene(winRoot);
                                            PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                            delay.setOnFinished(x -> stage.setScene(scene));
                                            delay.play();
                                        }//if 
                                        else if (gameBoxes[0][cord[1]].getText().equals(gameBoxes[1][cord[1]].getText()) && gameBoxes[0][cord[1]].getText().equals(gameBoxes[2][cord[1]].getText())) {
                                            playerScore[turn[0]]++;//Player Won
                                            gameBoxes[0][cord[1]].setStyle("-fx-background-color: lime");
                                            gameBoxes[1][cord[1]].setStyle("-fx-background-color: lime");
                                            gameBoxes[2][cord[1]].setStyle("-fx-background-color: lime");
                                            scene = new Scene(winRoot);
                                            PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                            delay.setOnFinished(x -> stage.setScene(scene));
                                            delay.play();
                                        }//else if
                                        else if (cord[0] == cord[1]) {
                                            if (gameBoxes[0][0].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][0].getText().equals(gameBoxes[2][2].getText())) {
                                                playerScore[turn[0]]++;//Player Won
                                                gameBoxes[0][0].setStyle("-fx-background-color: lime");
                                                gameBoxes[1][1].setStyle("-fx-background-color: lime");
                                                gameBoxes[2][2].setStyle("-fx-background-color: lime");
                                                scene = new Scene(winRoot);
                                                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                                delay.setOnFinished(x -> stage.setScene(scene));
                                                delay.play();
                                            }//if
                                        }//else if
                                        else if ((cord[0] + cord[1]) == 2) {
                                            if (gameBoxes[0][2].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][2].getText().equals(gameBoxes[2][0].getText())) {
                                                playerScore[turn[0]]++;//Player Won
                                                gameBoxes[0][2].setStyle("-fx-background-color: lime");
                                                gameBoxes[1][1].setStyle("-fx-background-color: lime");
                                                gameBoxes[2][0].setStyle("-fx-background-color: lime");
                                                scene = new Scene(winRoot);
                                                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                                delay.setOnFinished(x -> stage.setScene(scene));
                                                delay.play();
                                            }//if
                                        }//else if
                                    }//if
                                    break;
                            }//switch
                        }//while
                        break;
                    default:
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
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
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
