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
import BattleShipGameSource.Project.modules.BoardCell;
import BattleShipGameSource.Project.modules.GameManager;
import BattleShipGameSource.Project.modules.Player;
import BattleShipGameSource.ProjectFx.UIFx.BoardButton;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import BattleShipGameSource.Resources.BattleShipGame;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.*;
import javafx.util.Duration;

public class GameScreenController implements Initializable {

    @FXML public Label labelTime;
    @FXML static public Label labelNuberOfAttack = new Label("0");
    @FXML static public Label labelNumberOfHit = new Label("0");
    @FXML static public Label labelNumberOfMissing = new Label("0");
    @FXML static public Label lableScore = new Label("0");
    @FXML static public Label labelAvgTimeOfMove = new Label("0");
    @FXML static public Label lableMovesMade = new Label("0");
    @FXML private Button btnLoadXML;
    @FXML private Button btnStart;
    @FXML private Button btnExit;
    @FXML private Label labelCurrentPlayer;
    @FXML private GridPane boardGrid;
    @FXML private static TabPane boardTabs;
    GridPane controlGrid = new GridPane();
    GridPane shipsGrid= new GridPane();
    Boolean checkHit;

    @FXML
    private HBox HboxBoards;


    private static Boolean gameLoaded = false;
    private static GameManager myGame;
    ArrayList<PlayerBoard> playerBoards = new ArrayList<>();
    @FXML private Tab tabMyBoard;
    @FXML private Tab tabGuessBoard = new Tab();

    PlayerBoard myBoard;

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

    @FXML
    public Boolean LoadXML(MouseEvent mouseEvent) {
//        labelTotalMoves.setText("-");
        Stage newStage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resources File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        //File selectedFile = fileChooser.showOpenDialog(newStage);

        String fileName = "C:\\Users\\moshonb6\\IdeaProjects\\BattleShipsEx2\\BattleShipsNew\\src\\Resources\\battleShipGame.xml";
        File selectedFile = new File(fileName);


        try {
            XmlLoader xml = new XmlLoader(selectedFile);
            gameLoaded = xml.loadBattelShipsConfig();
            System.out.println("gameLoaded: " + gameLoaded);
        }   catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }

        return gameLoaded;
    }



    private void startGame() throws Exception{
        setGameTime();
        updateStatistic();
    }

    private void initBoard() {

        Region spaceBetweenBoards = new Region();

        spaceBetweenBoards.setPrefWidth(86);
        spaceBetweenBoards.setPrefHeight(428);
        spaceBetweenBoards.setLayoutX(298);
        spaceBetweenBoards.setLayoutY(10);


        setBoardsOnGridPane(controlGrid,GameManager.getCurrentPlayer().getOponentBoardMat(),shipsGrid,
                GameManager.getCurrentPlayer().getMyBoardMat());
        //add to the middele hbox the 2 grids of boards
        HboxBoards.getChildren().add(shipsGrid);
        HboxBoards.getChildren().add(spaceBetweenBoards);
        HboxBoards.getChildren().add(controlGrid);


    }

    private void setBoardsOnGridPane(GridPane i_controlGrid,int[][]i_controlBoard, GridPane i_shipGrid, int[][]i_shipsBoard)
    {
        setShipsBoardOnGridPane(i_shipGrid,i_shipsBoard);
        setControlBoardOnGridPane(i_controlGrid,i_controlBoard);//TODO: remenber to draw only op board

    }

    private void setShipsBoardOnGridPane(GridPane i_ShipBoardOnGridPaneGrid, int[][] i_shipsBoard)
    {
        showUpperBoard(i_ShipBoardOnGridPaneGrid);

        for (int x = 0; x <= GameManager.getBoardSize() - 1; x++)
        {
            for (int y = 0; y <= GameManager.getBoardSize() - 1; y++)
            {
                Pane controlCellBoard = createControlCellNode(x, y,i_shipsBoard);
                i_ShipBoardOnGridPaneGrid.add(controlCellBoard,y,x);
            }
        }
    }

    private BoardCell createOpShipCellNode(int x, int y, int[][] i_controlBoard)
    {
        Label label=new Label();
        BoardCell cell =new BoardCell();
        cell.getPos().setX(x);
        cell.getPos().setY(y);

        cell.getM_cell().setOnMouseClicked((event) -> {
            try {
                Boolean hit = OnClickCellInShipsBoard(event,cell);
                if(!hit){
                    setBoardsOnGridPane(controlGrid,GameManager.getCurrentPlayer().getOponentBoardMat(),shipsGrid,
                            GameManager.getCurrentPlayer().getMyBoardMat());
                }

            } catch (Exception overlapMine) {

            }
        });

        cell.getM_cell().getStyleClass().add("cellInBoard");
        cell.getM_cell().setPadding(new Insets(1));

        //empty cell
        //Image seaJpg = new Image(getClass().getResourceAsStream("../../../resources/sea_04.jpg"),55,65,true,true);
        //ImageView sea = new ImageView(seaJpg);

        ///mine
        Image MineJpg = new Image(getClass().getResourceAsStream("../../../resources/bomb_02.jpg"),55,65,true,true);
        //ImageView mine = new ImageView(seaJpg);

        javafx.scene.image.Image seaJpg = new Image(getClass().getResourceAsStream("../../../resources/sea_04.jpg"),55,65,true,true);
        ImageView sea = new ImageView(seaJpg);
        //label.setGraphic(sea);

        switch (i_controlBoard[x][y])
        {
            case 0:
                label.setGraphic(sea);
                break;
            case 1:
                label.setStyle("-fx-background-color: green;");
                label.setText("V");
                label.setAlignment(Pos.BOTTOM_CENTER);
                break;
            case 2:
                label.setStyle("-fx-background-color: red;");
                label.setText("X");
                label.setAlignment(Pos.BOTTOM_CENTER);
                break;
        }

        cell.getM_cell().getChildren().add(label);
        return cell;

    }

    Boolean OnClickCellInShipsBoard(MouseEvent event, BoardCell cell ) throws Exception {
        boolean isPlayerSetMine=false;
        Boolean myHit;
        Point point = new Point();
        int x = cell.getPos().getX();
        int y = cell.getPos().getY();
        point.setLocation(x,y);
        myHit = myGame.checkHit(GameManager.getCurrentPlayer(), point);

        if(!myHit){
            myGame.switchPlayers();
        }

        return myHit;

    }

    public void setControlBoardOnGridPane(GridPane i_ControlBoardOnGridPane,int[][] i_controlBoard) {

        showUpperBoard(i_ControlBoardOnGridPane);


        for (int x = 0; x <= GameManager.getBoardSize() - 1; x++)
        {
            for (int y = 0; y <= GameManager.getBoardSize() - 1; y++)
            {
                BoardCell controlCellBoard = createOpShipCellNode(x, y,i_controlBoard);
                i_ControlBoardOnGridPane.add(controlCellBoard.getM_cell(),y,x);
            }
        }
    }

    private Pane createControlCellNode(final int row, final int column, int[][] i_controlBoard)
    {

        Label label=new Label();
        AnchorPane cell = new AnchorPane();
        cell.getStyleClass().add("cellInBoard");
        cell.setPadding(new Insets(1));
        javafx.scene.image.Image seaJpg = new Image(getClass().getResourceAsStream("../../../resources/sea_04.jpg"),55,65,true,true);
        ImageView sea = new ImageView(seaJpg);
        javafx.scene.image.Image shipJpg = new Image(getClass().getResourceAsStream("../../../resources/ship1.jpg"),55,65,true,true);
        ImageView ship = new ImageView(shipJpg);

        if( i_controlBoard[row][column] == 0){
            label.setGraphic(sea);
        }
        else if( i_controlBoard[row][column] > 0){
            label.setGraphic(ship);
        }
        else if( i_controlBoard[row][column] == -1){//battleship hit
            label.setStyle("-fx-background-color: red;");
            label.setText("X");
        }
        else if( i_controlBoard[row][column] == -2){//Mine case
            label.setStyle("-fx-background-color: black;");
            label.setText("X");
        }

        cell.getChildren().add(label);
        return cell;
    }

    private void showUpperBoard(GridPane i_BoardOnGridPane)
    {
        for(int i=0;i<GameManager.getBoardSize();i++)
        {
            Label label=new Label();
            Pane cell = new AnchorPane();

            cell.getStyleClass().add("cellInUpperBoard");
            cell.setPadding(new Insets(1));

            //cell.setStyle("-fx-background-color: white;");
            //label.setText(String.valueOf((char)( i+'A')));
            label.setTextFill(Paint.valueOf("#175cdd"));
            label.setFont(javafx.scene.text.Font.font(Font.BOLD));
            label.setFont(javafx.scene.text.Font.font("Bodoni MT Black",18));
            label.setAlignment(Pos.BOTTOM_RIGHT);

            //put the label inside the cell
            cell.getChildren().add(label);
            //add the cell to the grid in the first row
            i_BoardOnGridPane.add(cell,i+1,0);
        }

    }


    public void StartGame(MouseEvent mouseEvent) {

        boardTabs = new TabPane();

        ArrayList<BoardButton> boardButtons = new ArrayList<>();

        btnLoadXML.setDisable(true);
        btnStart.setDisable(true);

        try {
            myGame = new GameManager();
            myGame.playGame();
            initBoard();
            startGame();
        } catch (Exception e) {
            System.out.println("aaaaaaaaaa");

        }
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
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();

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
        int bosrdSize = GameManager.getBoardSize();
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

    public void printMyBoard(Player player){

    }

    public void updateStatistic() throws Exception{
        try {
            labelCurrentPlayer.setText(GameManager.getCurrentPlayer().getName());
            labelNuberOfAttack.setText(String.valueOf(GameManager.getNumOfTurns()));
            labelNumberOfHit.setText(String.valueOf(GameManager.getCurrentPlayer().getShots()-GameManager.getCurrentPlayer().getMissed()));
            labelNumberOfMissing.setText(String.valueOf(GameManager.getCurrentPlayer().getMissed()));
            lableScore.setText(String.valueOf(GameManager.getCurrentPlayer().getScore()));
            labelAvgTimeOfMove.setText(String.valueOf(GameManager.getCurrentPlayer().getAvgTimeForMove()));
            //lableMovesMade.setText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGameTime()
    {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis( 999 ),
                        event ->
                        {
                            labelTime.setText( GameManager.Timespan());
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }
}