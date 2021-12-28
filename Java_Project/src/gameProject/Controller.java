package gameProject;

import java.net.URL;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Parent winRoot;
    private Parent loseRoot;
    
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
        boolean[] player = new boolean[2];//0 --> player1 && 1 --> player2
        player[0] = false;//Default begin : player1 still not win yet
        player[1] = false;//Default begin : player2 still not win yet
        boolean[] turn = new boolean[1];//0 --> 'O' && 1 --> 'X'
        turn[0] = true; // To begin playing with 'X'
        int[] cord = new int[2];//The "X,Y" Coordinates ... 0 --> 'Y' && 1 --> 'X'
        cord[0] = 0;//set default 'Y' value
        cord[1] = 0;//set default 'X' value
        try{
            URL fxml = this.getClass().getResource("WinScene.fxml");
            winRoot = FXMLLoader.load(fxml);
        }//try
        catch(Exception e){
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
                        if( !( player[0] || player[1] ) ){
                            cord[1]++;
                            if(cord[1] == 3) cord[1] = 0;
                            gameBoxes[cord[0]][cord[1]].requestFocus();
                        }//if
                        break;
                    case LEFT:
                        if( !( player[0] || player[1] ) ){
                            cord[1]--;
                            if(cord[1] < 0) cord[1] = 2;
                            gameBoxes[cord[0]][cord[1]].requestFocus();
                        }//if
                        break;
                    case DOWN:
                        if( !( player[0] || player[1] ) ){
                            cord[0]++;
                            if(cord[0] == 3) cord[0] = 0;
                            gameBoxes[cord[0]][cord[1]].requestFocus();
                        }//if
                        break;
                    case UP:
                        if( !( player[0] || player[1] ) ){
                            cord[0]--;
                            if(cord[0] < 0) cord[0] = 2;
                            gameBoxes[cord[0]][cord[1]].requestFocus();
                        }//if
                        break;
                    case SPACE://put 'O' .... turn[0] = false at 'O' turn
                        if( player[0] || player[1] || turn[0]){
                            break;
                        }//if
                        if(gameBoxes[cord[0]][cord[1]].getText().equals("")){
                            gameBoxes[cord[0]][cord[1]].setText("o");
                            if(gameBoxes[cord[0]][0].getText().equals(gameBoxes[cord[0]][1].getText()) && gameBoxes[cord[0]][0].getText().equals(gameBoxes[cord[0]][2].getText())){
                                player[0] = true;//Player0 won
                                scene = new Scene(winRoot);
                                stage.setScene(scene);
                            }//if 
                            else if(gameBoxes[0][cord[1]].getText().equals(gameBoxes[1][cord[1]].getText()) && gameBoxes[0][cord[1]].getText().equals(gameBoxes[2][cord[1]].getText())){
                                player[0] = true;//Player0 Won
                                scene = new Scene(winRoot);
                                stage.setScene(scene);                         
                            }//else if
                            else if(cord[0] == cord[1]){
                                if(gameBoxes[0][0].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][0].getText().equals(gameBoxes[2][2].getText())){
                                    player[0] = true;//Player0 Won
                                    scene = new Scene(winRoot);
                                    stage.setScene(scene);
                                }//if
                            }//else if
                            else if((cord[0] + cord[1]) == 2){
                                if(gameBoxes[0][2].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][2].getText().equals(gameBoxes[2][0].getText())){
                                    player[0] = true;//Player0 Won
                                    scene = new Scene(winRoot);
                                    stage.setScene(scene);
                                }//if
                            }//else if
                            turn[0] = true;//To make the turn for 'X' player
                        }//if
                        break;
                    case ENTER:
                        if( player[0] || player[1] || (!turn[0])){ 
                            break;
                        }//if
                        if(gameBoxes[cord[0]][cord[1]].getText().equals("")){
                            gameBoxes[cord[0]][cord[1]].setText("x");
                            if(gameBoxes[cord[0]][0].getText().equals(gameBoxes[cord[0]][1].getText()) && gameBoxes[cord[0]][0].getText().equals(gameBoxes[cord[0]][2].getText())){
                                player[1] = true;//Player1 Won
                                scene = new Scene(winRoot);
                                stage.setScene(scene);
                            }//if
                            else if(gameBoxes[0][cord[1]].getText().equals(gameBoxes[1][cord[1]].getText()) && gameBoxes[0][cord[1]].getText().equals(gameBoxes[2][cord[1]].getText())){
                                player[1] = true;//Player1 Won
                                scene = new Scene(winRoot);
                                stage.setScene(scene);
                            }//else if
                            else if(cord[0] == cord[1]){
                                if(gameBoxes[0][0].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][0].getText().equals(gameBoxes[2][2].getText())){
                                    player[1] = true;//Player1 Won
                                    scene = new Scene(winRoot);
                                    stage.setScene(scene);
                                }//if
                            }//else if
                            else if((cord[0] + cord[1]) == 2){
                                if(gameBoxes[0][2].getText().equals(gameBoxes[1][1].getText()) && gameBoxes[0][2].getText().equals(gameBoxes[2][0].getText())){
                                    player[1] = true;//Player1 Won
                                    scene = new Scene(winRoot);
                                    stage.setScene(scene);
                                }//if
                            }//else if
                            turn[0] = false;//To make the turn for 'O' player
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