package by.itsep.karnei;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Student on 05.04.2019.
 */
public class Clinica implements Runnable {
    public Nurse nurse;

    // комнаты для врачей room = new ArrayList()
    private List<Room> rooms = new ArrayList();
    private AtomicInteger count;

    public Clinica(int chairQuantity, int roomsQuantity) {
        this.nurse = new Nurse(5);
        this.count = new AtomicInteger(0);

        for (int i = 0; i < roomsQuantity; i++) {
            Room room = initializeRooms(i);
            this.rooms.add(room);
        }
    }

    public void run() {
        // в клинику последовательно заходит 11 пациентов
        for (int i = 0; i < 11; i++) {
            this.createPatient();
        }

        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // заходит следующие 11 пациентов
        for (int i = 0; i < 11; i++) {
            this.createPatient();
        }
    }

    // клиника пытается обслужить пациетов
    private void createPatient() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Patient patient = new Patient(1 + new Random().nextInt(4));

        // выдаем номер по порядку прибытия в регистратуру
        patient.setNumber(count.incrementAndGet());
        Main.LOG.info("Patient #" + patient.getNumber() + " enter to clinica");

        synchronized (this.nurse) {
            // пациент пытается стать в очередь т.е садиться на стул
            this.nurse.queuePatient(patient);
        }
    }

    // обслуживание пациента в регистратуре
    private void servePatient(final Patient patient) {
    }

    private Room initializeRooms(int number) {// инициализация комнаты
        int quantityPatient = 10 + new Random().nextInt(5);

        // создаем доктора с прикрепленной к нему очередью пациентов на стульях
        Doctor doctor = new Doctor(quantityPatient, this.nurse);

        // доктор входит в комнату
        Room room = new Room(doctor);

        new Thread(doctor, "Room #" + (number + 1)).start();

        return room;
    }
}
