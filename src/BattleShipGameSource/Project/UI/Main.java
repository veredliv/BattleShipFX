package BattleShipGameSource.Project.UI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Main extends Application{
    Stage window;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getClassLoader().getResource("BattleShipGameSource/Resource/Scene/GameScreenScene/GameScreen.fxml ");
        fxmlLoader.setLocation(url);
        System.out.println(url);
        System.out.println(fxmlLoader.getLocation());
        Parent root = fxmlLoader.load(url);
        primaryStage.setScene(new Scene(root, 800, 800));
        XmlLoader.getBattleShipsPlayer1();
        XmlLoader.getBattleShipsPlayer2();
        System.out.println("LOAD");

        primaryStage.show();

//        window = primaryStage;
//
//        primaryStage.setTitle("BattleShip");
//        primaryStage.setResizable(true);
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        //URL url = getClass().getResource("Resource/battleShip_5_basic.xml");
//        //URL url = getClass().getResource("Scene/StartGameScene/StartGame.fxml");
//        URL url = getClass().getResource("Scene/GameScreenScene/GameScreenScene.fxml");
//        //URL url = getClass().getResource("Scene/GameOverScene/GameOver.fxml");
//        fxmlLoader.setLocation(url);
//        // !!! This line doesn't work - not sure why
//        Parent root = fxmlLoader.load(url.openStream());
//
//        MainScreenController mainScreenController = fxmlLoader.getController();
//
//        ScrollPane sp = new ScrollPane(root);
//        primaryStage.setScene(new Scene(sp));
//          primaryStage.show();

    }
}
