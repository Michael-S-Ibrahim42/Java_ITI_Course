package es42_tictactoe;

import animatefx.animation.*;
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
    public EventQueue queue1;
    public EventQueue queue2;
    
    public Event event = new Event();

    @Override
    public void start(Stage primaryStage) {
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
        Thread thread = new Thread(() -> {
            es42_tictactoe.Controller.menuBtns[0].setStyle("-fx-background-color: #3333ff;");
            int pos = 0;
            while (true) {
                gamepad1.poll();
                gamepad2.poll();
                while (queue1.getNextEvent(event)) {
                    Component comp = event.getComponent();
                    float value = event.getValue();
                    if (comp.getName().equalsIgnoreCase("Button 2")) {
                        pos = pos + 1;
                        es42_tictactoe.Controller.menuBtns[pos-1].setStyle("-fx-background-color: #00000090;");
                        es42_tictactoe.Controller.menuBtns[pos].setStyle("-fx-background-color: #3333ff;");
                        final int passed = pos;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                es42_tictactoe.Controller.menuBtns[1].requestFocus();
                                //es42_tictactoe.Controller.gameTile.setOrientation(Orientation.VERTICAL);
                                //es42_tictactoe.Controller.gameBoxes[0][1].setText("o");
                            }
                        });
                    }
                    System.out.println(comp.getName());
                }//while
                while (queue2.getNextEvent(event)) {
                    Component comp = event.getComponent();
                    float value = event.getValue();
                    System.out.println(comp.getName());
                }//while
                try {
                    Thread.sleep(20);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }//while
        } //run
        );//thread
        thread.start();
        String css = this.getClass().getResource("Home.css").toExternalForm();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Tic_Tac_Toe");
        primaryStage.show();
    }//start
    @Override
    public void stop(){
        System.exit(0);
    }
    public static void main(String[] args) {
        Application.launch(args);
    }//main
}//class Main
