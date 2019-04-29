package by.itsep.karnei;

/**
 * Created by Student on 05.04.2019.
 */
public class Patient {

    private int health;
    // время обслуживания пациента
    private int number;
    // номер который дается пациентку в регистратуре

    public Patient(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
