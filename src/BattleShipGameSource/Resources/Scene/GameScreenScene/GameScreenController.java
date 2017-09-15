package BattleShipGameSource.Resources.Scene.GameScreenScene;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import BattleShipGameSource.Project.UI.Main;
import BattleShipGameSource.Project.UI.PlayerBoard;
import BattleShipGameSource.Project.UI.XmlLoader;
import BattleShipGameSource.Project.modules.Board;
import BattleShipGameSource.Project.modules.GameManager;
import BattleShipGameSource.Project.modules.Player;
import BattleShipGameSource.ProjectFx.UIFx.BoardButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.*;

public class GameScreenController implements Initializable {

    @FXML private Button btnLoadXML;
    @FXML private Button btnStart;
    @FXML private Button btnExit;
    @FXML private Label labelCurrentPlayer;
    @FXML private GridPane boardGrid;
    @FXML private static TabPane boardTabs;
    private static Boolean gameLoaded = false;
    public static GameManager theGame = new GameManager();
    ArrayList<PlayerBoard> playerBoards = new ArrayList<>();
    @FXML private Tab tabMyBoard;

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

    public void setLabelCurrentPlayer(Label labelCurrentPlayer) {this.labelCurrentPlayer = labelCurrentPlayer; }

    public Label getLabelCurrentPlayer() { return labelCurrentPlayer; }

    public void setBoardGrid(GridPane boardGrid) { this.boardGrid = boardGrid; }

    public GridPane getBoardGrid() { return boardGrid; }

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
        boardTabs = new TabPane();

        ArrayList<BoardButton> boardButtons = new ArrayList<>();
        drawTabs(theGame.getCurrentPlayer());



        btnLoadXML.setDisable(true);
        btnStart.setDisable(true);

        //textStatus.setText("Game Started!");
        //boardTabs = new TabPane();

    }

    private void drawTabs(Player currentPlayer) {
        GameScreenController.boardTabs.getTabs().clear();
        ArrayList<BoardButton> boardButtons = new ArrayList<>();
        //drawTabForPlayer(currentPlayer);
        tabMyBoard.setClosable(false);

        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();

        scrollPane.setContent(gridPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
        scrollPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

        boardButtons = drawBoard(gridPane, false);
        tabMyBoard.setContent(scrollPane);
        gridPane.setVisible(true);

        GameScreenController.boardTabs.getTabs().add(tabMyBoard);

        PlayerBoard playerBoard = new PlayerBoard(boardButtons);
        playerBoards.add(playerBoard);
    }

    private void drawTabForPlayer(Player currentPlayer) {

    }

    public void ExitGame(MouseEvent mouseEvent) throws IOException {
//        !!!Need to show the statistic of the players
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            URL url = getClass().getResource("src\\BattleShipGameSource\\Resources\\Scene\\GameOverScene\\GameOver.fxml");
//            fxmlLoader.setLocation(url);
//            Parent root = fxmlLoader.load(url);
//            Main.window.setScene(new Scene(root, 800, 800));
//            XmlLoader.getBattleShipsPlayer1();
//            XmlLoader.getBattleShipsPlayer2();
//        }
//        catch (Exception e){
//            System.out.println("Exception: ExitGame failed fxmlLoader.load(url)");
//            System.out.println(e.getMessage());
//        }
//
//        Main.window.show();
    }


    ArrayList<BoardButton> drawBoard(GridPane boardGrid, boolean isDisabled) {
        boardGrid.getChildren().clear();
        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();

        ArrayList<BoardButton> playerBoardButtons = new ArrayList<>();
        int bosrdSize = theGame.getBoardSize();
        for (int y = 0; y < bosrdSize; y++) {
            for (int x = 0; x < bosrdSize; x++) {
                // Create a new TextField in each Iteration
                BoardButton button = new BoardButton(y, x);
                button.setDisable(isDisabled);

                button.setPrefWidth(30);
                button.setPrefHeight(30);

                playerBoardButtons.add(button);
                GridPane.setConstraints(button, x, y);
                boardGrid.getChildren().add(button);
            }
        }

        return playerBoardButtons;
    }
}