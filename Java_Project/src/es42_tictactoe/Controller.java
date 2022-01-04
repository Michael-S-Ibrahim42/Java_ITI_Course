package es42_tictactoe;

import static es42_tictactoe.Es42_tictactoe.sceneID;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Controller implements Initializable {

    public static Stage stage;
    public static Scene scene;
    private Parent root;
    public static Parent winRoot;
    public static Parent loseRoot;
    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    /* Static Variables Section */
    public static Button[] menuBtns;
    public static TextField[][] gameBoxes;
    public static boolean[] isWinner;
    public static int[] turn;
    public static int[] cord;
    public static int[] playerScore;

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
}//class Controller
