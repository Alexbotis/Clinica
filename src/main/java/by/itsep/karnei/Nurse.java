package by.itsep.karnei;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Nurse {
    // очередь пациентов  мы в дальнейшем представим как блокирующую очередь
    // chairs = new ArrayBlockingQueue(chairQuantity)
    private Queue<Patient> chairs;

    public Nurse(int chairQuantity) {
        this.chairs = new ArrayBlockingQueue(chairQuantity);
    }

    public synchronized Patient getFromQueue(){
        return this.chairs.poll();
    }

    public synchronized void queuePatient(Patient patient) {
        boolean added = false;// флаг

        try {
            // пытаемся добавить пациента в очередь
            added = this.chairs.add(patient);
        } catch (Exception ex) {
            // ошибка (нет мест в очереди)
            // выводим сообщение что пациента не удалось добавить в очередь
            Main.LOG.error("Patient # " + patient.getNumber() + " go away NOT serve - queue fulled");
        }

        if (!added) {
            return;
        }

        // раз пациента добавили в очередь
        // создаем поток который удалить его из очереди через 10 секунд
        // если его к тому времени не обслужат
        new Thread(() ->
        {
            try {
                // поток ждет 10 секунд
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // закрываем поток
                Thread.currentThread().interrupt();
            }

            // через 10 секунд пытаемся удалить пациета из очереди
            boolean present = chairs.remove(patient);
            if (present) {
                // если пациент был в очереди и его успешно удалили, значит он ушен не обслуженным
                Main.LOG.info("Patient № " + patient.getNumber() + " go away NOT serve - time over!");
            }
        }, "Patient #" + patient.getNumber() + " wait").start();

        // выводим сообщение что пациента успешно добавили в очередь
        Main.LOG.info("Patient #" + patient.getNumber() + " add to queue");
        this.notify();
    }
}
