

import BattleShipGameSource.ProjectFx.UIFx.BoardButton;

import java.util.ArrayList;

/**
 * Created by Itsik on 23/09/2016.
 */
public class PlayerBoard {

    int movesMade;
    int roundsPlayed;
    int score;

    public ArrayList<BoardButton> getPlayerBoardButtons() {
        return playerBoardButtons;
    }

    ArrayList<BoardButton> playerBoardButtons;

    PlayerBoard(ArrayList<BoardButton> _playerBoardButtons) {
        playerBoardButtons = new ArrayList<BoardButton>();
        playerBoardButtons = _playerBoardButtons;
//        movesMade = 0;
//        roundsPlayed = 0;
//        score = 0;
    }
}
