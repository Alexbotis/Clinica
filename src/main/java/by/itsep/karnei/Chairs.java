package by.itsep.karnei;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Chairs {
    // стулья которрые мы в дальнейшем представим как блокирующую очередь  chair = new ArrayBlockingQueue(chairQuantity)
    private Queue<Patient> chair;

    public Chairs(int chairQuantity) {
        chair = new ArrayBlockingQueue(chairQuantity);
    }

    public synchronized boolean add(Patient patient){
        return this.chair.add(patient);
    }

    public synchronized Patient poll(){
        return this.chair.poll();
    }

    public synchronized boolean remove(Patient patient){
        return this.chair.remove(patient);
    }

    public synchronized int size(){
        return this.chair.size();
    }
}
