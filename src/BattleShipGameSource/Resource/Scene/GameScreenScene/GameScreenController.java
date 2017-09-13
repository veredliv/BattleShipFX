package BattleShipGameSource.Resource.Scene.GameScreenScene;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import BattleShipGameSource.Project.UI.XmlLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import mypackage.BattleShipGame;

public class GameScreenController implements Initializable {

    @FXML
    private Button btnLoadXML;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    }

    public void setBtnLoadXML(Button btnLoadXML) {
        this.btnLoadXML = btnLoadXML;
    }

    public Button getBtnLoadXML() {
        return btnLoadXML;
    }

    public void setBtnStart(Button btnStart) {
        this.btnStart = btnStart;
    }

    public Button getBtnStart() {
        return btnStart;
    }

    public void setBtnExit(Button btnExit) {
        this.btnExit = btnExit;
    }

    public Button getBtnExit() {
        return btnExit;
    }

    public void LoadXML(MouseEvent mouseEvent) {
//        labelTotalMoves.setText("-");
        Stage newStage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(newStage);

//        ArrayList<BattleShipGame.Boards.Board.Ship> p1 = XmlLoader.getBattleShipsPlayer1();
//        ArrayList<BattleShipGame.Boards.Board.Ship> p2 = XmlLoader.getBattleShipsPlayer2();
//        System.out.println("LOAD");
    }


    public void StartGame(MouseEvent mouseEvent) {
//        !!! need to draw player A board with his ships.
//        on the tabMyBoard
        System.out.println("START");

    }

    public void ExitGame(MouseEvent mouseEvent) {

//        !!!Need to show the statistic of the players
        System.out.println("EXIT");

    }
}