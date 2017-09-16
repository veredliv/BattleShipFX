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

public class GameScreenController implements Initializable {

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
//            myGame = new GameManager();
//            myGame.playGame();
//            initBoard();
//            startGame();



        }   catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }

        return gameLoaded;
    }



    private void startGame() {

    }

    private void initBoard() {

        Region spaceBetweenBoards = new Region();
        //Button cell = new Button();

        //set the space properties
        spaceBetweenBoards.setPrefWidth(86);
        spaceBetweenBoards.setPrefHeight(428);
        spaceBetweenBoards.setLayoutX(298);
        spaceBetweenBoards.setLayoutY(10);


        //drow the logic board on the appropriate grid
        System.out.println(GameManager.getCurrentPlayer());
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
            //set the cells for the number in the side of the board
            Label label=new Label();
            Pane cell = new AnchorPane();
            cell.getStyleClass().add("cellInLeftBoard");
            cell.setPadding(new Insets(1));
            cell.setStyle("-fx-background-color: white;");
            label.setText(String.valueOf(x));
            label.setTextFill(Paint.valueOf("#175cdd"));
            label.setFont(javafx.scene.text.Font.font(Font.BOLD));
            label.setFont(javafx.scene.text.Font.font("Bodoni MT Black",18));
            label.setAlignment(Pos.BOTTOM_RIGHT);
            //finish set the left side of the board


            //put the label inside the cell
            cell.getChildren().add(label);
            //add the cell to the grid in the first row
            i_ShipBoardOnGridPaneGrid.add(cell,0,x);

            for (int y = 0; y <= GameManager.getBoardSize() - 1; y++)
            {
                BoardCell controlCellBoard = createShipCellNode(x, y,i_shipsBoard);
                i_ShipBoardOnGridPaneGrid.add(controlCellBoard.getM_cell(),y,x);
            }
        }
    }

    private BoardCell createShipCellNode(int x, int y, int[][] i_attackBoard)
    {

        Label label=new Label();
        BoardCell cell =new BoardCell();
        cell.getPos().setX(x);
        cell.getPos().setY(y);

        cell.getM_cell().setOnMouseClicked((event) -> {
            try {
                OnClickCellInShipsBoard(event,cell);
                setBoardsOnGridPane(controlGrid,GameManager.getCurrentPlayer().getOponentBoardMat(),shipsGrid,
                        GameManager.getCurrentPlayer().getMyBoardMat());

            } catch (Exception overlapMine) {

            }
        });

        cell.getM_cell().getStyleClass().add("cellInBoard");
        cell.getM_cell().setPadding(new Insets(1));

        //empty cell
        Image seaJpg = new Image(getClass().getResourceAsStream("../../../resources/sea_04.jpg"),55,65,true,true);
        ImageView sea = new ImageView(seaJpg);

        ///mine
        Image MineJpg = new Image(getClass().getResourceAsStream("../../../resources/bomb_02.jpg"),55,65,true,true);
        ImageView mine = new ImageView(seaJpg);

       /* //ship
        Image ShipJpg = new Image(getClass().getResourceAsStream("/resources/textures/sea_01.jpg"),55,65,true,true);
        ImageView ship = new ImageView(seaJpg);*/

        if (i_attackBoard[x][y] == -2)
        { //means there is a mine there
            //label.setGraphic(mine);
        }
        else if  (i_attackBoard[x][y]> 0)
        { // means there is a ship there
             // label.setGraphic(mine);
        }
        else
        {//empty cell
            label.setGraphic(sea);
        }
//        cell.setOnMouseClicked((EventHandler<? super MouseEvent>) ActionCellInShipsBoard);
        cell.getM_cell().getChildren().add(label);
        return cell;

    }

    void OnClickCellInShipsBoard(MouseEvent event, BoardCell cell ) throws Exception {
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

        //gameManager.PlayRound(cell.getPos().getX(),cell.getPos().getX(),isPlayerSetMine);

    }

    public void setControlBoardOnGridPane(GridPane i_ControlBoardOnGridPane,int[][] i_controlBoard) {

        showUpperBoard(i_ControlBoardOnGridPane);


        for (int x = 0; x <= GameManager.getBoardSize() - 1; x++)
        {
            //set the cells for the number in the side of the board
            Label label=new Label();
            Pane cell = new AnchorPane();
            cell.getStyleClass().add("cellInLeftBoard");
            cell.setPadding(new Insets(1));
            cell.setStyle("-fx-background-color: white;");
            label.setText(String.valueOf(x));
            label.setTextFill(Paint.valueOf("#175cdd"));
            label.setFont(javafx.scene.text.Font.font(Font.BOLD));
            label.setFont(javafx.scene.text.Font.font("Bodoni MT Black",18));
            label.setAlignment(Pos.BOTTOM_RIGHT);
            //finish set the ledt side of the board

            //put the label inside the cell
            cell.getChildren().add(label);
            //add the cell to the grid in the first row
            i_ControlBoardOnGridPane.add(cell,0,x);

            for (int y = 0; y <= GameManager.getBoardSize() - 1; y++)
            {
                Pane controlCellBoard = createControlCellNode(x, y,i_controlBoard);
                i_ControlBoardOnGridPane.add(controlCellBoard,y,x);
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

//        cell.getM_cell().setOnMouseClicked((event) -> {
//            try {
//                OnClickCellInShipsBoard(event,cell);
//                setBoardsOnGridPane(controlGrid,GameManager.getCurrentPlayer().getOponentBoardMat(),shipsGrid,
//                        GameManager.getCurrentPlayer().getMyBoardMat());
//
//            } catch (Exception overlapMine) {
//
//            }
//        });


        switch (i_controlBoard[row][column])
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

        // button.setOnAction((t) -> System.out.println("Shoot On " + row + ":" + column));
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

            cell.setStyle("-fx-background-color: white;");
            label.setText(String.valueOf((char)( i+'A')));
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
//        !!! need to draw player A board with his ships.
//        on the tabMyBoard
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
<<<<<<< HEAD

=======
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
>>>>>>> 4333ca1b6da1f8f0c286858805463172bdf337a3
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
}