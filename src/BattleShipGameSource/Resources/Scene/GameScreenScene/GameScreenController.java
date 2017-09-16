package BattleShipGameSource.Resources.Scene.GameScreenScene;

import BattleShipGameSource.Project.UI.PlayerTab;
import BattleShipGameSource.Resources.BattleShipGame.Boards.Board.Ship;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import BattleShipGameSource.Project.UI.Main;
import BattleShipGameSource.Project.UI.PlayerBoard;
import BattleShipGameSource.Project.UI.XmlLoader;
import BattleShipGameSource.Project.modules.BattelShip;
import BattleShipGameSource.Project.modules.Board;
import BattleShipGameSource.Project.modules.GameManager;
import BattleShipGameSource.Project.modules.Player;
import BattleShipGameSource.ProjectFx.UIFx.BoardButton;
import BattleShipGameSource.Resources.BattleShipGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    @FXML private Tab tabGuessBoard = new Tab();

    PlayerBoard myBoard;
    PlayerBoard enemyBoard;

    PlayerTab myTab = new PlayerTab();
    PlayerTab enemyTab = new PlayerTab();

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
        ArrayList<BoardButton> myBoard = drawTab(theGame.getCurrentPlayer(), myTab, tabMyBoard, false);
        ArrayList<BoardButton> enemyBoard = drawTab(theGame.getCurrentPlayer(), enemyTab, tabGuessBoard, true);
        //drawShipsOnBoard(tabMyBoard, GameManager.getCurrentPlayer().getBattelShipsArray());
        myTab.tab = tabMyBoard;
        enemyTab.tab = tabGuessBoard;

        btnLoadXML.setDisable(true);
        btnStart.setDisable(true);

    }

    private void drawShipsOnBoard(Tab tabMyBoard, BattelShip[] battelShipsArray) {

        for(BattelShip battelShip : battelShipsArray ) {
            ArrayList<Point> shipsPos = new ArrayList<>();
            shipsPos = getShipPoint(battelShip);
            for (Point point : shipsPos) {
                int xVal = (int)point.getX();
                int yVal = (int)point.getY();
                BoardButton currButton = (BoardButton)(myBoard.getPlayerBoardButtons().get(yVal * battelShip.getLength() + xVal + 1));
                currButton.textProperty().setValue("x");
            }
            return;
        }
    }

    ArrayList<Point> getShipPoint(BattelShip ship) {
        ArrayList<Point> shipsPos = new ArrayList<>();

        if (ship.getDirection() == "ROW") {
            for (int i=0; i<ship.getLength(); i++) {
                int xValue = (int)ship.getPosition().getX() + i;
                int yValue = (int)ship.getPosition().getY();

                shipsPos.add(new Point(xValue, yValue));
            }
        }

        else if (ship.getDirection() == "COLUMN") {
            for (int i=0; i<ship.getLength(); i++) {
                int xValue = (int)ship.getPosition().getX();
                int yValue = (int)ship.getPosition().getY() + i;
                shipsPos.add(new Point(xValue, yValue));
            }
        }

        else if(ship.getDirection() == "RIGHT_DOWN") {
            int i;
            for (i=0; i<ship.getLength(); i++) {
                int xValue = (int)ship.getPosition().getX();
                int yValue = (int)ship.getPosition().getY() + i;

                shipsPos.add(new Point(xValue, yValue));
            }
            for (int j = 1; j<ship.getLength(); j++) {
                int xValue = (int)ship.getPosition().getX() + j;
                int yValue = (int)ship.getPosition().getY() + i;

                shipsPos.add(new Point(xValue, yValue));
            }
        }
        else if(ship.getDirection() == "RIGHT_UP") {
            int i;
            for (i=0; i<ship.getLength(); i++) {
                int xValue = (int)ship.getPosition().getX();
                int yValue = (int)ship.getPosition().getY() + i;

                shipsPos.add(new Point(xValue, yValue));
            }
            for (int j = 1; j<ship.getLength(); j--) {
                int xValue = (int)ship.getPosition().getX() + j;
                int yValue = (int)ship.getPosition().getY() + i;

                shipsPos.add(new Point(xValue, yValue));
            }
        }
        else if(ship.getDirection() == "UP_RIGHT") {
            int i;
            for (i=0; i<ship.getLength(); i--) {
                int xValue = (int)ship.getPosition().getX() + i;
                int yValue = (int)ship.getPosition().getY();

                shipsPos.add(new Point(xValue, yValue));
            }
            for (int j = 1; j<ship.getLength(); j++) {
                int xValue = (int)ship.getPosition().getX() + i;
                int yValue = (int)ship.getPosition().getY() + j;

                shipsPos.add(new Point(xValue, yValue));
            }
        }
        else if(ship.getDirection() == "DOWN_RIGHT") {
            int i;
            for (i=0; i<ship.getLength(); i++) {
                int xValue = (int)ship.getPosition().getX() + i;
                int yValue = (int)ship.getPosition().getY();

                shipsPos.add(new Point(xValue, yValue));
            }
            for (int j = 1; j<ship.getLength(); j++) {
                int xValue = (int)ship.getPosition().getX() + i;
                int yValue = (int)ship.getPosition().getY() + j;

                shipsPos.add(new Point(xValue, yValue));
            }
        }

        return shipsPos;
    }

    ArrayList<BoardButton> drawTab(Player currentPlayer, PlayerTab playerTab, Tab tab, boolean isClickable) {
        GameScreenController.boardTabs.getTabs().clear();
        ArrayList<BoardButton> boardButtons = new ArrayList<>();
        //drawTabForPlayer(currentPlayer);
        tab.setClosable(false);

        playerTab.scrollPane = new ScrollPane();
        playerTab.gridPane = new GridPane();

        playerTab.scrollPane.setContent(playerTab.gridPane);
        playerTab.scrollPane.setFitToHeight(true);
        playerTab.scrollPane.setFitToWidth(true);
        playerTab.scrollPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
        playerTab.scrollPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

        boardButtons = drawBoard(playerTab.gridPane, !isClickable);

        tab.setContent(playerTab.scrollPane);
        playerTab.gridPane.setVisible(true);

        GameScreenController.boardTabs.getTabs().add(tab);

        PlayerBoard playerBoard = new PlayerBoard(boardButtons);
        playerBoards.add(playerBoard);

        return boardButtons;
    }

    private void drawTabForPlayer(Player currentPlayer) {

    }

    public void ExitGame(MouseEvent mouseEvent) throws IOException {
//        !!!Need to show the statistic of the players
//        Stage stage = (Stage) btnExit.getScene().getWindow();
//        stage.close();
//
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        URL url = getClass().getResource("src\\BattleShipGameSource\\Resources\\Scene\\GameOverScene\\GameOver.fxml");
//        fxmlLoader.setLocation(url);
//        Parent root = fxmlLoader.load(url);
//        Main.window.setScene(new Scene(root, 500, 500));
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

                button.setPrefWidth(50);
                button.setPrefHeight(50);

                playerBoardButtons.add(button);
                GridPane.setConstraints(button, x, y);
                boardGrid.getChildren().add(button);
            }
        }

        return playerBoardButtons;
    }
}