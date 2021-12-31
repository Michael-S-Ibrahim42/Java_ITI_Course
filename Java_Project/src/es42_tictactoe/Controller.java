package es42_tictactoe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
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

public class Controller implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Parent winRoot;
    private Parent loseRoot;
    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    public static Button[] menuBtns;
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBtns = new Button[3];
        menuBtns[0] = btn1;
        menuBtns[1] = btn2;
        menuBtns[2] = btn3;
    }//initialize
    
    public void homeScene(ActionEvent event){
        try{
            URL fxml = this.getClass().getResource("HomeScene.fxml");
            root = FXMLLoader.load(fxml); 
        }//try
        catch(Exception e){
            System.out.println(e.getMessage());
        }//catch
        stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        String css = this.getClass().getResource("Home.css").toExternalForm();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }//homeScene
    
    public void singlePlayer(ActionEvent event){
        try{
            URL fxml = this.getClass().getResource("WinScene.fxml");
            root = FXMLLoader.load(fxml);
        }//try
        catch(Exception e){
            System.out.println(e.getMessage());
        }//catch
        stage = (Stage)(((Node)(event.getSource())).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }//singlePlayer    
    
    public void multiPlayer(ActionEvent event){
        TilePane gameTile = new TilePane();//contain the boxes of the game
        gameTile.setPrefColumns(3);
        TextField[][] gameBoxes = new TextField[3][3];//The boxes of the game
        int[] player = new int[2];//0 --> playerX && 1 --> playerO
        player[0] = 0;//Default begin : playerX score
        player[1] = 0;//Default begin : playerO score
        boolean isWinner[] = new boolean[1];
        isWinner[0] = false; // defines whether there is a winner or not
        int[] turn = new int[1]; //0 --> 'O' && 1 --> 'X'
        turn[0] = 1; // To begin playing with 'X'
        int[] cord = new int[2];//The "X,Y" Coordinates ... 0 --> 'Y' && 1 --> 'X'
        cord[0] = 0;//set default 'Y' value
        cord[1] = 0;//set default 'X' value
        try{
            URL fxml = this.getClass().getResource("WinScene.fxml");
            winRoot = FXMLLoader.load(fxml);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }//catch
        for(int i = 0 ; i <3 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                TextField gameBox = new TextField();
                gameBoxes[i][j] = gameBox;
                gameBox.setEditable(false);
                gameBox.setFont(new Font(50));
                gameBox.setAlignment(Pos.CENTER);
                gameBox.setPrefSize(100, 100);
                gameBox.setOnKeyPressed((e) ->{
                    switch(e.getCode()){
                        case RIGHT:
                            if( !isWinner[0] ){
                                cord[1]++;
                                if(cord[1] == 3) cord[1] = 0;
                                gameBoxes[cord[0]][cord[1]].requestFocus();
                                gameBoxes[cord[0]][cord[1]].deselect();
                            }//if
                            break;
                            
                        case LEFT:
                            if( !isWinner[0] ){
                                cord[1]--;
                                if(cord[1] < 0) cord[1] = 2;
                                gameBoxes[cord[0]][cord[1]].requestFocus();
                                gameBoxes[cord[0]][cord[1]].deselect();
                            }//if
                            break;

                        case DOWN:
                            if( !isWinner[0] ){
                                cord[0]++;
                                if(cord[0] == 3) cord[0] = 0;
                                gameBoxes[cord[0]][cord[1]].requestFocus();
                                gameBoxes[cord[0]][cord[1]].deselect();
                            }//if
                            break;

                        case UP:
                            if( !isWinner[0] ){
                                cord[0]--;
                                if(cord[0] < 0) cord[0] = 2;
                                gameBoxes[cord[0]][cord[1]].requestFocus();
                                gameBoxes[cord[0]][cord[1]].deselect();
                            }//if
                            break;

                        case SPACE://put 'O' turn[0] = 0, else 'x' 
                            if( isWinner[0] ){
                                break;
                            }//if
                            if(gameBoxes[cord[0]][cord[1]].getText().equals("")){
                                switch(turn[0]){
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
                                if(gameBoxes[cord[0]][0].getText().equals(gameBoxes[cord[0]][1].getText()) && gameBoxes[cord[0]][0].getText().equals(gameBoxes[cord[0]][2].getText())){
                                    player[turn[0]]++;//Player won
                                    gameBoxes[cord[0]][0].setStyle("-fx-background-color: lime");
                                    gameBoxes[cord[0]][1].setStyle("-fx-background-color: lime");
                                    gameBoxes[cord[0]][2].setStyle("-fx-background-color: lime");
                                    scene = new Scene(winRoot);
                                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                    delay.setOnFinished( x -> stage.setScene(scene) );
                                    delay.play();
                                }//if 
                                else if(gameBoxes[0][cord[1]].getText().equals(gameBoxes[1][cord[1]].getText()) && gameBoxes[0][cord[1]].getText().equals(gameBoxes[2][cord[1]].getText())){
                                    player[turn[0]]++;//Player Won
                                    gameBoxes[0][cord[1]].setStyle("-fx-background-color: lime");
                                    gameBoxes[1][cord[1]].setStyle("-fx-background-color: lime");
                                    gameBoxes[2][cord[1]].setStyle("-fx-background-color: lime");
                                    scene = new Scene(winRoot);
                                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                    delay.setOnFinished( x -> stage.setScene(scene) );
                                    delay.play();                        
                                }//else if
                                else if(cord[0] == cord[1]){
                                    if(gameBoxes[0][0].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][0].getText().equals(gameBoxes[2][2].getText())){
                                        player[turn[0]]++;//Player Won
                                        gameBoxes[0][0].setStyle("-fx-background-color: lime");
                                        gameBoxes[1][1].setStyle("-fx-background-color: lime");
                                        gameBoxes[2][2].setStyle("-fx-background-color: lime");
                                        scene = new Scene(winRoot);
                                        PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                        delay.setOnFinished( x -> stage.setScene(scene) );
                                        delay.play();
                                    }//if
                                }//else if
                                else if((cord[0] + cord[1]) == 2){
                                    if(gameBoxes[0][2].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][2].getText().equals(gameBoxes[2][0].getText())){
                                        player[turn[0]]++;//Player Won
                                        gameBoxes[0][2].setStyle("-fx-background-color: lime");
                                        gameBoxes[1][1].setStyle("-fx-background-color: lime");
                                        gameBoxes[2][0].setStyle("-fx-background-color: lime");
                                        scene = new Scene(winRoot);
                                        PauseTransition delay = new PauseTransition(Duration.seconds(2));
                                        delay.setOnFinished( x -> stage.setScene(scene) );
                                        delay.play();
                                    }//if
                                }//else if
                            }//if
                            break;
                    }//switch
                });
                gameTile.getChildren().add(gameBox);
            }//for ... embedded for
        }//for
        scene = new Scene(gameTile);
        stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }//multiPlayer
    
    public void exit(ActionEvent event){
        stage = (Stage)(((Node)(event.getSource())).getScene().getWindow());
        stage.close();
    }//exit
}//class Controller