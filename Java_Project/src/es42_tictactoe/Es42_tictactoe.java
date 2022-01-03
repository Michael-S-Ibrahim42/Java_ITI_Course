package es42_tictactoe;

import animatefx.animation.*;
import static es42_tictactoe.Controller.cord;
import static es42_tictactoe.Controller.gameBoxes;
import static es42_tictactoe.Controller.isWinner;
import static es42_tictactoe.Controller.menuBtns;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Es42_tictactoe extends Application {

    private Scene scene;
    private Parent root;
    private Controller[] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();
    private Controller gamepad1;
    private Controller gamepad2;
    private EventQueue queue1;
    private EventQueue queue2;
    private Event event = new Event();

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
            boolean waitPress = false;
            int setMenuPos = 0;
            int clrMenuPos = 0;
            int xPos = 0;
            int yPos = 0;

            while (true) {
                gamepad1.poll();
                gamepad2.poll();
                switch (sceneID) {
                    case 0:
                        while (queue1.getNextEvent(event)) {
                            waitPress = true;
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
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            es42_tictactoe.Controller.menuBtns[clrPos].setStyle("-fx-background-color: #00000090;");
                                            es42_tictactoe.Controller.menuBtns[setPos].setStyle("-fx-background-color: #3333ff;");
                                        }//run method
                                    });//Platform runLater
                                    break;
                                case "Button 1":
                                    System.exit(0);
                                    break;
                                case "Button 2":
                                    final int firePos = setMenuPos;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            es42_tictactoe.Controller.menuBtns[firePos].fire();
                                        }//run method
                                    });//Platform runLater
                                    break;
                            }//switch
                        }//while
                        break;
                    case 1:
                        while (queue1.getNextEvent(event)) {
                            waitPress = true;
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
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            es42_tictactoe.Controller.menuBtns[clrPos].setStyle("-fx-background-color: #00000090;");
                                            es42_tictactoe.Controller.menuBtns[setPos].setStyle("-fx-background-color: #3333ff;");
                                        }//run method
                                    });//Platform runLater
                                    break;
                                case "Button 1":
                                    System.exit(0);
                                    break;
                                case "Button 2":
                                    final int firePos = setMenuPos;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            es42_tictactoe.Controller.menuBtns[firePos].fire();
                                        }//run method
                                    });//Platform runLater
                                    break;
                            }//switch
                        }//while
                        break;
                    case 2: //multiplayer Scene
                        while (queue1.getNextEvent(event) || queue2.getNextEvent(event)) {
                            waitPress = true;
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
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    gameBoxes[cord[0]][cord[1]].requestFocus();
                                                    gameBoxes[cord[0]][cord[1]].deselect();
                                                }
                                            });//Platform
                                        }//if
                                    }//if
                                    else if (stickValue == -1.0f) {//I'm going left
                                        if (!isWinner[0]) {
                                            cord[1]--;
                                            if (cord[1] < 0) {
                                                cord[1] = 2;
                                            }
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    gameBoxes[cord[0]][cord[1]].requestFocus();
                                                    gameBoxes[cord[0]][cord[1]].deselect();
                                                }
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
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    gameBoxes[cord[0]][cord[1]].requestFocus();
                                                    gameBoxes[cord[0]][cord[1]].deselect();
                                                }
                                            });//Platform
                                        }//if
                                    }//if
                                    else if (stickValue == -1.0f) {//I'm going up
                                        if (!isWinner[0]) {
                                            cord[0]--;
                                            if (cord[0] < 0) {
                                                cord[0] = 2;
                                            }
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    gameBoxes[cord[0]][cord[1]].requestFocus();
                                                    gameBoxes[cord[0]][cord[1]].deselect();
                                                }
                                            });
                                        }//if
                                    }//else if
                                    break;
                                case "Button 1":
                                    sceneID = 0;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            primaryStage.setScene(scene);
                                        }//run method
                                    });//Platform runLater
                                    break;
                                case "Button 2":
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            
                                        }//run method
                                    });//Platform runLater
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
