package es42_tictactoe;

import animatefx.animation.*;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
        
public class Es42_tictactoe extends Application{
    private Scene scene;
    private Parent root;
    private Controller[] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();
    private Controller gamepad1;
    private Controller gamepad2;
    private static Component[] pad1Components;
    private static Component[] pad2Components;
    public static IntegerProperty xJ1Btn = new SimpleIntegerProperty(0);
    public static IntegerProperty yJ1Btn = new SimpleIntegerProperty(0);
    public static IntegerProperty selectJ1Btn = new SimpleIntegerProperty(0);
    public static IntegerProperty backJ1Btn = new SimpleIntegerProperty(0);
    public static IntegerProperty xJ2Btn = new SimpleIntegerProperty(0);
    public static IntegerProperty yJ2Btn = new SimpleIntegerProperty(0);
    public static IntegerProperty selectJ2Btn = new SimpleIntegerProperty(0);
    public static IntegerProperty backJ2Btn = new SimpleIntegerProperty(0);
    
    @Override
    public void start(Stage primaryStage){
        int assignmentFlag = 0;
        for(int i = 0; i < controllerList.length; i++){
            if(controllerList[i].getType() == Controller.Type.STICK){
                if(assignmentFlag == 0){
                    gamepad1 = controllerList[i];
                    pad1Components = gamepad1.getComponents();
                    assignmentFlag = 1;
                }//if
                else{
                    gamepad2 = controllerList[i];
                    pad2Components = gamepad2.getComponents();
                }//else
            }//if
        }//for
        System.out.println(pad1Components[0].getName());// X
        System.out.println(pad1Components[1].getName());// Y 
        System.out.println(pad1Components[6].getName());// Btn1 --- O
        System.out.println(pad1Components[7].getName());// Btn2 --- X
        System.out.println(pad1Components[8].getName());// Btn3 ---[]
        System.out.println("----------------------");
        System.out.println(pad2Components[0].getName());// X 
        System.out.println(pad2Components[1].getName());// Y 
        System.out.println(pad2Components[6].getName());// Btn1 --- O
        System.out.println(pad2Components[7].getName());// Btn2 --- X
        System.out.println(pad2Components[8].getName());// Btn3 ---[]
        

        
        Image icon = new Image(Es42_tictactoe.class.getResourceAsStream("ITI.png"));
        try{
            URL fxml = this.getClass().getResource("HomeScene.fxml");
            root = FXMLLoader.load(fxml); 
        }//try
        catch(Exception exception){
            System.out.println(exception.getMessage());
        }//catch
        String css = this.getClass().getResource("Home.css").toExternalForm();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Tic_Tac_Toe");
        primaryStage.show();
    }//start
    public static void main(String[] args){
        Application.launch(args);
    }//main
}//class Main