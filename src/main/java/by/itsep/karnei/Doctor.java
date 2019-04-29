package by.itsep.karnei;

import java.util.Queue;
import java.util.concurrent.locks.Lock;

/**
 * Created by Student on 05.04.2019.
 */
public class Doctor implements Runnable {
    private int quantityPatient;
    private Nurse nurse;

    public Doctor(int quantityPatient, Nurse nurse) {
        this.quantityPatient = quantityPatient;
        this.nurse = nurse;
    }

    // метод как доктор обслуживает пациентов
    public void run() {
        // пока пациентов не 0 и их количество не превышает нормы обслуживания за день
        while (this.quantityPatient > 0) {
            Patient nextPatient;

            synchronized (this.nurse) {
                nextPatient = this.nurse.getFromQueue();

                if (nextPatient != null) {
                    Main.LOG.info("Doctor pulled patient " + nextPatient.getNumber());
                    // в лог записываем номер пациента который был ему выдан в регистратуре
                } else {
                    try {
                        // останвливаем поток доктора и ждем notify() от пациента в очереди
                        Main.LOG.info("Doctor is free and waiting for " + this.quantityPatient + " more patients");
                        this.nurse.wait();
                        Main.LOG.info("Doctor resume working");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                        // закрываем поток
                    }
                    continue;
                }
            }

            try {
                // останавливаем поток (эмулируем работу доктора)
                Thread.sleep(nextPatient.getHealth() * 1000);

                // выводим сообщение в LOG.info
                Main.LOG.info("Patient #" + nextPatient.getNumber() + " served");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                // закрываем поток
            }

            this.quantityPatient--;
        }
    }
}
