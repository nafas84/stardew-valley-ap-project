package io.Ap.StardewValley.Common.Model;

import com.badlogic.gdx.Input;

public class KeyManager {
    private int moveUp = Input.Keys.W;
    private int moveDown = Input.Keys.S;
    private int moveLeft = Input.Keys.A;
    private int moveRight = Input.Keys.D;

    private int zoom = Input.Keys.CONTROL_LEFT;
    private int pauseGame = Input.Keys.ESCAPE;

    private int aynazCheat = Input.Keys.Z;
    private int nafisehCheat = Input.Keys.ENTER;

    // animal:
    private int animalList = Input.Keys.P;

    // miniGame:
    private int miniGame = Input.Keys.G;

    // multiplayer:
    private int scoreboard = Input.Keys.M;
    //inventory:
    private int openInventory = Input.Keys.I;

    //cooking:
    private int openRefrigerator = Input.Keys.C;

    //tools
    private int leftClick = Input.Buttons.LEFT;

    //shipping bin
    private int openShippingBin = Input.Keys.B;



    public int getMoveUp() {
        return moveUp;
    }

    public void setMoveUp(int moveUp) {
        this.moveUp = moveUp;
    }

    public int getMoveDown() {
        return moveDown;
    }

    public void setMoveDown(int moveDown) {
        this.moveDown = moveDown;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public int getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(int moveRight) {
        this.moveRight = moveRight;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public int getPauseGame() {
        return pauseGame;
    }

    public void setPauseGame(int pauseGame) {
        this.pauseGame = pauseGame;
    }

    public int getOpenInventory() {
        return openInventory;
    }

    public void setOpenInventory(int openInventory) {
        this.openInventory = openInventory;
    }

    public int getAynazCheat() {
        return aynazCheat;
    }

    public void setAynazCheat(int aynazCheat) {
        this.aynazCheat = aynazCheat;
    }

    public int getOpenRefrigerator() {
        return openRefrigerator;
    }

    public void setOpenRefrigerator(int openRefrigerator) {
        this.openRefrigerator = openRefrigerator;
    }

    public int getLeftClick() {
        return leftClick;
    }

    public void setLeftClick(int leftClick) {
        this.leftClick = leftClick;
    }

    public int getNafisehCheat() {
        return nafisehCheat;
    }

    public void setNafisehCheat(int nafisehCheat) {
        this.nafisehCheat = nafisehCheat;
    }

    public int getOpenShippingBin() {
        return openShippingBin;
    }

    public void setOpenShippingBin(int openShippingBin) {
        this.openShippingBin = openShippingBin;
    }

    public int getAnimalList() {
        return animalList;
    }

    public void setAnimalList(int animalList) {
        this.animalList = animalList;
    }

    public int getMiniGame() {
        return miniGame;
    }

    public void setMiniGame(int miniGame) {
        this.miniGame = miniGame;
    }

    public int getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(int scoreboard) {
        this.scoreboard = scoreboard;
    }
}

