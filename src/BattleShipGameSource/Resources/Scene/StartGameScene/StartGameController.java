package BattleShipGameSource.Resources.Scene.StartGameScene;

import BattleShipGameSource.Project.modules.GameManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.*;
import BattleShipGameSource.Resources.BattleShipGame;

import java.io.File;

import static java.lang.Thread.sleep;

public class StartGameController implements Initializable{

    @FXML private Button btnLoadXmlFile;
    @FXML private Button btnStart;
    @FXML private Button btnExit;
    @FXML private Text textStatus;

    // !!! not sure of the field type - ask moshe
    private BattleShipGame gameDescriptor = null;
    private GameManager game = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert btnLoadXmlFile != null : "fx:id=\"btnLoadXmlFile\" was not injected: check your FXML file 'GameScreen.fxml'.";
        assert btnStart != null : "fx:id=\"btnStart\" was not injected: check your FXML file 'GameScreen.fxml'.";

    }

    @FXML
    void handlebtnExit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }


    @FXML
    void handlebtnLoadXmlFile(ActionEvent event) {
        try {
            loadXml();
        }
        catch (Exception error){
            textStatus.setText(error.getMessage());
//            diaplayErrorWindow(error.getMessage());
        }
    }

    void loadXml() throws Exception {
        //labelTotalMoves.setText("-");
        Stage newStage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resources File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
//                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
//                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(newStage);

        System.out.println(selectedFile.toString());

//        String filename;
//
//        if (selectedFile != null) {
////            textStatus.setVisible(true);
//            textStatus.setText(" Loading XML file, please wait... ");
//            try {
//                gameDescriptor = new BattleShipGame();
//
//            } catch (Exception e) {
//                Exception error = new Exception("Non XML file");
//                btnStart.setDisable(true);
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
//                    // !!! need to create game
//                    //theGame = new Game(gameDescriptor);
//                }
//                catch (Exception error) {
//                    btnStart.setDisable(true);
//                    textStatus.setText(error.getMessage());
//                    throw error;
//                }
//
////                if (game.isValidBoard()) {
////                    xmlLoaded = true;
////                    textStatus.setText(" Board OK. Click 'Start!' or 'Load XML' to load another file");
////                    buttonStart.setDisable(false);
////
////                    try {
////                        theGame.startAll(gameDescriptor);
////                    }
////                    catch (Exception error) {
////                        buttonStart.setDisable(true);
////                        textStatus.setText(error.getMessage());
////                        throw error;
////                    }
////
//
////                    drawBoard(boardGrid, true);
////                    drawPlayerTable();
////                    labelTotalMoves.setText(String.valueOf(theGame.moves));
////
////                }
////                else {
////                    textStatus.setText(" Board parameters NOT valid! Try again");
////                    buttonStart.setDisable(true);
////                }
////                playerTable = new TableView<>();
//            }
//            else {
//                textStatus.setText("XML file not loaded, bad parameters, try again. ");
//            }
//        }
    }

}
