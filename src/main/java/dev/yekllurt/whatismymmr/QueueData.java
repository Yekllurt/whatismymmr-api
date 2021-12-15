package dev.yekllurt.whatismymmr;

import dev.yekllurt.whatismymmr.util.Queue;

public class QueueData {

    private final Queue queue;
    private final Integer average;
    private final Integer error;
    private final boolean reducedAccuracy;
    private final Long timestamp;
    private final String closestRank;
    private final Double percentile;

    public QueueData(Queue queue, Integer average, Integer error, boolean reducedAccuracy, Long timestamp, String closestRank, Double percentile) {
        this.queue = queue;
        this.average = average;
        this.error = error;
        this.reducedAccuracy = reducedAccuracy;
        this.timestamp = timestamp;
        this.closestRank = closestRank;
        this.percentile = percentile;
    }

    public Queue getQueue() {
        return queue;
    }

    /**
     * @return The estimated MMR for the summoner in this queue. Is null if no estimate is available.
     */
    public Integer getAverage() {
        return average;
    }

    /**
     * @return The margin of error for the estimate using an 95% confidence interval for this queue.
     */
    public Integer getError() {
        return error;
    }

    /**
     * @return A boolean flag indicating that the estimate may have reduced accuracy due to insufficient match data.
     */
    public boolean hasReducedAccuracy() {
        return reducedAccuracy;
    }

    /**
     * @return The UTC timestamp of the last data processed for the summoner in this queue.
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * @return The closest equivalent rank to the estimated MMR. For unranked queues this is derived by mapping the percentile to the ranked distribution.
     */
    public String getClosestRank() {
        return closestRank;
    }

    /**
     * @return A rough percentile for the closest ranked division. For unranked queues this is for the overall distribution instead.
     */
    public Double getPercentile() {
        return percentile;
    }

    @Override
    public String toString() {
        return "QueueData{" +
                "queue=" + queue +
                ", average='" + average + '\'' +
                ", error='" + error + '\'' +
                ", reducedAccuracy=" + reducedAccuracy +
                ", timestamp=" + timestamp +
                ", closestRank='" + closestRank + '\'' +
                ", percentile=" + percentile +
                '}';
    }

}
