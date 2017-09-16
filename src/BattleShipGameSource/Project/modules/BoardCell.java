package BattleShipGameSource.Project.modules;

import javafx.scene.layout.AnchorPane;
import BattleShipGameSource.Resources.BattleShipGame.Boards.Board.Ship.Position;

public class BoardCell {
    private AnchorPane m_cell = new AnchorPane();
    private Position m_pos = new Position();

    public Position getPos() {
        return m_pos;
    }

    public AnchorPane getM_cell() {
        return m_cell;
    }
}
