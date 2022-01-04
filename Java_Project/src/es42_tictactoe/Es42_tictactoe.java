package es42_tictactoe;

import static es42_tictactoe.Controller.cord;
import static es42_tictactoe.Controller.gameBoxes;
import static es42_tictactoe.Controller.isWinner;
import static es42_tictactoe.Controller.playerScore;
import static es42_tictactoe.Controller.stage;
import static es42_tictactoe.Controller.turn;
import static es42_tictactoe.Controller.winRoot;
import java.net.URL;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.util.Duration;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Es42_tictactoe extends Application {

    public Scene scene;
    public Parent root;
    public Controller[] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();
    public Controller gamepad1;
    public Controller gamepad2;
    public EventQueue queue1;
    public EventQueue queue2;
    public Event event = new Event();

    public static int sceneID;

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
        es42_tictactoe.Controller.menuBtns[0].setStyle("-fx-background-color: #3333ff;");
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
                                        es42_tictactoe.Controller.menuBtns[clrPos].setStyle("-fx-background-color: #00000090;");
                                        es42_tictactoe.Controller.menuBtns[setPos].setStyle("-fx-background-color: #3333ff;");
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
                                        es42_tictactoe.Controller.menuBtns[firePos].fire();
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
                                        es42_tictactoe.Controller.menuBtns[clrPos].setStyle("-fx-background-color: #00000090;");
                                        es42_tictactoe.Controller.menuBtns[setPos].setStyle("-fx-background-color: #3333ff;");
                                    } //run method
                                    );//Platform runLater
                                    break;
                                case "Button 1":
                                    System.exit(0);
                                    break;
                                case "Button 2":
                                    final int firePos = setMenuPos;
                                    Platform.runLater(() -> {
                                        es42_tictactoe.Controller.menuBtns[firePos].fire();
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
