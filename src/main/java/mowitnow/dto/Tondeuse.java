package mowitnow.dto;

/**
 * Classe java qui représente une tondeuse
 */
public class Tondeuse {
    private int positionX;
    private int positionY;
    private char orientation;
    private Pelouse pelouse;

    public Tondeuse() {
    }

    public Tondeuse(int positionX, int positionY, char orientation, Pelouse pelouse) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.orientation = orientation;
        this.pelouse = pelouse;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    public Pelouse getPelouse() {
        return pelouse;
    }

    public void setPelouse(Pelouse pelouse) {
        this.pelouse = pelouse;
    }

    @Override
    public String toString() {
        return positionX + " " + positionY + " " + orientation;
    }
}
