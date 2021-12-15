package dev.yekllurt.whatismymmr.util;

/**
 * All available regions
 */
public enum Region {

    NORTH_AMERICA("na"), EU_WEST("euw"), EU_NORDIC_EAST("eune"), KOREA("kr");

    String region;

    Region(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public String getBaseURL() {
        return String.format("https://%s.whatismymmr.com/api", region);
    }

    public String getSummonerURL(String summoner) {
        return String.format(getBaseURL() + "/v1/summoner?name=%s", summoner);
    }

    @Override
    public String toString() {
        return "Region{" +
                "region='" + region + '\'' +
                '}';
    }

}