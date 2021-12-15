package dev.yekllurt.whatismymmr;

import dev.yekllurt.whatismymmr.util.Region;

/**
 * An object containing all the fetched data related to a summoner
 */
public class SummonerData {

    private final Region region;
    private final String summonerName;
    private final QueueData queueDataRanked;
    private final QueueData queueDataNormal;
    private final QueueData queueDataARAM;

    public SummonerData(Region region, String summonerName, QueueData queueDataRanked, QueueData queueDataNormal, QueueData queueDataARAM) {
        this.region = region;
        this.summonerName = summonerName;
        this.queueDataRanked = queueDataRanked;
        this.queueDataNormal = queueDataNormal;
        this.queueDataARAM = queueDataARAM;
    }

    public Region getRegion() {
        return region;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public QueueData getQueueDataRanked() {
        return queueDataRanked;
    }

    public QueueData getQueueDataNormal() {
        return queueDataNormal;
    }

    public QueueData getQueueDataARAM() {
        return queueDataARAM;
    }

    @Override
    public String toString() {
        return "SummonerData{" +
                "region=" + region +
                ", summonerName='" + summonerName + '\'' +
                ", queueDataRanked=" + queueDataRanked +
                ", queueDataNormal=" + queueDataNormal +
                ", queueDataARAM=" + queueDataARAM +
                '}';
    }

}
