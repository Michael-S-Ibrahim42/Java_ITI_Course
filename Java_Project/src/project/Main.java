package project;

import java.net.URL;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Version;
        
public class Main extends Application{
    private Scene scene;
    private Parent root;
    public Controller[] controllersList;
    public Controller gamepad1;
    public Controller gamepad2;
    public Component[] componentsOf1;
    public Component[] componentsOf2;
    public IntegerProperty xB1;
    public IntegerProperty yB1;
    public IntegerProperty eB1;
    public IntegerProperty bB1;
    public IntegerProperty sB1;
    public IntegerProperty xB2;
    public IntegerProperty yB2;
    public IntegerProperty eB2;
    public IntegerProperty bB2;
    public IntegerProperty sB2;
    
    @Override
    public void start(Stage primaryStage){
      
        controllersList = ControllerEnvironment.getDefaultEnvironment().getControllers();    
        int tempor = 0;
        for(int counter = 0; counter < controllersList.length; counter++){
            if(controllersList[counter].getType().equals(Controller.Type.STICK)){
               if(tempor == 0){
                   gamepad1 = controllersList[counter];
                   tempor++;
                   System.out.println("Gamepad1 assigned");
               }//if
               else{
                   gamepad2 = controllersList[counter];
                   System.out.println("Gamepad2 assigned");
               }//else
            }//if
        }//for
        componentsOf1 = gamepad1.getComponents();
        componentsOf1 = gamepad1.getComponents();
        
        xB1 = new SimpleIntegerProperty(0);
        yB1 = new SimpleIntegerProperty(0);
        eB1 = new SimpleIntegerProperty(0);
        bB1 = new SimpleIntegerProperty(0);
        sB1 = new SimpleIntegerProperty(0);
        
        xB2 = new SimpleIntegerProperty(0);
        yB2 = new SimpleIntegerProperty(0);
        eB2 = new SimpleIntegerProperty(0);
        bB2 = new SimpleIntegerProperty(0);
        sB2 = new SimpleIntegerProperty(0);
        
        Image icon = new Image(Main.class.getResourceAsStream("ITI.png"));
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