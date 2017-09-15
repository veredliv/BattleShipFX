package BattleShipGameSource.ProjectFx.UIFx;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;


public class BoardButton extends ToggleButton {
    int row;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    Image imageMark = new Image("BattleShipGameSource/Resources/Pics/Mark.png");
    Image imageV = new Image("BattleShipGameSource/Resources/Pics/V.png");
    Image imageX = new Image("BattleShipGameSource/Resources/Pics/X.png");

    int col;
    char ch;

    public BoardButton(int row, int col) {
        this.setSelected(false);
        this.setDisable(true);
        this.row = row;
        this.col = col;
    }

    void setState(int selection){
        switch (selection) {
            case 0:
                setStyleFull();
                setText("");
                break;
            case 1:
                setStyleEmpty();
                setText("");
                break;
            case 2:
                setStyleUndefined();
                setText("?");
                break;
        }
    }

    void setStyleFull(){
        setStyle("-fx-base: #313b8c");
        setText("");
    }

    void setStyleEmpty(){
        setStyle("-fx-base: #ffffff");
        setText("");
    }
    void setStyleUndefined(){
        setStyle("-fx-base: #d3f3ff");
        setText("?");
    }

    void setHitPos()
    {
        //setGraphic();
    }



}
