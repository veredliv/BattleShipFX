package BattleShipGameSource.Resources.Scene.GameScreenScene;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import BattleShipGameSource.Project.UI.XmlLoader;
import BattleShipGameSource.Project.modules.GameManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

public class GameScreenController implements Initializable {

    @FXML
    private Button btnLoadXML;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnExit;

    private static Boolean gameLoaded = false;

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
        fileChooser.setTitle("Open Resources File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(newStage);

//        Test
//        String fileName = "C:\\Java\\BattleShipFX\\src\\BattleShipGameSource\\Resources\\battleShip_5_basic.xml";
//        File selectedFile = new File(fileName);

        GameManager gameManager = new GameManager();
        try {
            XmlLoader xml = new XmlLoader(selectedFile);
            gameLoaded = xml.loadBattelShipsConfig();
        }   catch (Exception e){
                    System.out.println("Exception: " + e.getMessage());
                }
    }


    public void StartGame(MouseEvent mouseEvent) {
//        !!! need to draw player A board with his ships.
//        on the tabMyBoard
        System.out.println("START");
        btnLoadXML.setDisable(true);
        btnStart.setDisable(true);

        //textStatus.setText("Game Started!");
        //boardTabs = new TabPane();

    }

    public void ExitGame(MouseEvent mouseEvent) {

//        !!!Need to show the statistic of the players
        System.out.println("EXIT");

    }
}