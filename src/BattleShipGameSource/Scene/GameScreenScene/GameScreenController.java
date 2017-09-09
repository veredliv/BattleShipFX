package BattleShipGameSource.Scene.GameScreenScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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

//    void loadXml() throws Exception {
//        labelTotalMoves.setText("-");
//        Stage newStage = new Stage();
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Resource File");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
////                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
////                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
//                new FileChooser.ExtensionFilter("All Files", "*.*"));
//        File selectedFile = fileChooser.showOpenDialog(newStage);
//
//        String filename;
//
//        if (selectedFile != null) {
////            textStatus.setVisible(true);
//            textStatus.setText(" Loading XML file, please wait... ");
//            try {
//                gameDescriptor = new MyGameDescriptor(selectedFile);
//
//            } catch (Exception e) {
//                Exception error = new Exception("Non XML file");
//                buttonStart.setDisable(true);
//                textStatus.setText(error.getMessage());
//                throw error;
//            }
//
//            if (gameDescriptor != null) {
////                textStatus.setVisible(true);
//                textStatus.setText(" XML file loaded, testing the file, please wait... ");
////                textStatus.setVisible(true);
//
//                try {
//                    theGame = new Game(gameDescriptor);
//                }
//                catch (Exception error) {
//                    buttonStart.setDisable(true);
//                    textStatus.setText(error.getMessage());
//                    throw error;
//                }
//
//                if (theGame.isValidBoard()) {
//                    xmlLoaded = true;
//                    textStatus.setText(" Board OK. Click 'Start!' or 'Load XML' to load another file");
//                    buttonStart.setDisable(false);
//
//                    try {
//                        theGame.startAll(gameDescriptor);
//                    }
//                    catch (Exception error) {
//                        buttonStart.setDisable(true);
//                        textStatus.setText(error.getMessage());
//                        throw error;
//                    }
//
//
//                    drawBoard(boardGrid, true);
//                    drawPlayerTable();
//                    labelTotalMoves.setText(String.valueOf(theGame.moves));
//
//                }
//                else {
//                    textStatus.setText(" Board parameters NOT valid! Try again");
//                    buttonStart.setDisable(true);
//                }
////                playerTable = new TableView<>();
//            }
//            else {
//                textStatus.setText("XML file not loaded, bad parameters, try again. ");
//            }
//        }
//    }
}
