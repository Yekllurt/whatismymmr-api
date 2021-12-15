package dev.yekllurt.whatismymmr.util;

/**
 * All available queues
 */
public enum Queue {

    RANKED("ranked"), NORMAL("normal"), ARAM("ARAM"), UNKNOWN("unknown");

    String queue;

    Queue(String queue) {
        this.queue = queue;
    }

    public static Queue fromString(String queue) {
        for (Queue q : values()) {
            if (q.getQueue().equals(queue)) {
                return q;
            }
        }
        return Queue.UNKNOWN;
    }

    public String getQueue() {
        return queue;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "queue='" + queue + '\'' +
                '}';
    }

}
