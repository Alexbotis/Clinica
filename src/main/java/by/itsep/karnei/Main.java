package by.itsep.karnei;

import org.apache.log4j.Logger;

/**
 * Created by admin on 10.04.2019.
 */
public class Main {
    final static public Logger LOG = Logger.getLogger("logger");

    public static void main(String[] args) {
        // создаем клинику с 5 комнатами для врачей и 5 стульями для очереди
        Clinica clinica = new Clinica(5, 5);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(clinica, "Clinica").start();
    }
}
