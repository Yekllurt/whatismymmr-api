package dev.yekllurt.whatismymmr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.yekllurt.whatismymmr.exception.SummonerNotFoundException;
import dev.yekllurt.whatismymmr.exception.UnknownException;
import dev.yekllurt.whatismymmr.util.Queue;
import dev.yekllurt.whatismymmr.util.Region;
import dev.yekllurt.whatismymmr.util.UserAgent;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class WhatIsMyMMR {

    private final int CACHE_DURATION = 1000 * 60 * 60; // 60 minutes
    private final Map<Region, Map<String, SummonerData>> CACHE_SUMMONER_DATA = new HashMap<>();
    private final Map<Region, Map<String, Long>> CACHE_SUMMONER_DATA_TIMESTAMP = new HashMap<>();

    private final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private final Gson GSON = new GsonBuilder().create();
    private final UserAgent userAgent;

    public WhatIsMyMMR(UserAgent userAgent) {
        this.userAgent = userAgent;
        for (Region region : Region.values()) {
            CACHE_SUMMONER_DATA.put(region, new HashMap<>());
            CACHE_SUMMONER_DATA_TIMESTAMP.put(region, new HashMap<>());
        }
    }

    /**
     * Fetch asynchronously summoner data
     *
     * @param region the region
     * @param summonerName the summoner name
     * @return the fetched summoner data
     * @throws SummonerNotFoundException thrown when the summoner is not-recorded in the specific region
     * @throws UnknownException thrown when an unknown response code is returned
     * @throws IOException thrown when an error occurs reading the data from the API
     */
    public Future<SummonerData> fetchSummonerData(Region region, String summonerName) {
        if (containsCache(region, summonerName)) {
            return CompletableFuture.completedFuture(CACHE_SUMMONER_DATA.get(region).get(summonerName));
        }
        return EXECUTOR_SERVICE.submit(() -> fetchSummonerDataSync(region, summonerName));
    }

    /**
     *  Fetch synchronously summoner data
     *
     * @param region the region
     * @param summonerName the summoner name
     * @return the fetched summoner data
     * @throws SummonerNotFoundException thrown when the summoner is not-recorded in the specific region
     * @throws UnknownException thrown when an unknown response code is returned
     * @throws IOException thrown when an error occurs reading the data from the API
     */
    public SummonerData fetchSummonerDataSync(Region region, String summonerName) throws SummonerNotFoundException, UnknownException, IOException {
        if (containsCache(region, summonerName)) return CACHE_SUMMONER_DATA.get(region).get(summonerName);

        URL url = new URL(region.getSummonerURL(summonerName));
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setConnectTimeout(1000);
        httpsURLConnection.setRequestProperty("User-Agent", userAgent.toString());
        if (httpsURLConnection.getResponseCode() == 404)
            throw new SummonerNotFoundException(String.format("The summoner %s is not on record in the region %s", summonerName, region.getRegion()));
        if (httpsURLConnection.getResponseCode() != 200)
            throw new UnknownException(String.format("Error code: %s, error message: %s", httpsURLConnection.getResponseCode(), httpsURLConnection.getResponseMessage()));
        JsonObject jsonObject = toJson(httpsURLConnection.getInputStream());
        SummonerData summonerData = constructSummonerDataFromJsonObject(region, summonerName, jsonObject);
        CACHE_SUMMONER_DATA.get(region).put(summonerName, summonerData);
        CACHE_SUMMONER_DATA_TIMESTAMP.get(region).put(summonerName, System.currentTimeMillis());
        return summonerData;
    }

    private boolean containsCache(Region region, String summonerName) {
        return CACHE_SUMMONER_DATA.get(region).containsKey(summonerName) && CACHE_SUMMONER_DATA_TIMESTAMP.get(region).containsKey(summonerName)
                && System.currentTimeMillis() - CACHE_SUMMONER_DATA_TIMESTAMP.get(region).get(summonerName) <= CACHE_DURATION;
    }

    private JsonObject toJson(InputStream inputStream) {
        return GSON.fromJson(new InputStreamReader(inputStream), JsonObject.class);
    }

    private QueueData constructQueueDataFromJsonObject(Queue queue, JsonObject jsonObject) {
        if (jsonObject.get("avg").isJsonNull()) return null;
        return new QueueData(queue,
                jsonObject.get("avg").getAsInt(),
                jsonObject.get("err").getAsInt(),
                jsonObject.get("warn").getAsBoolean(),
                jsonObject.get("timestamp").getAsLong(),
                jsonObject.get("closestRank").getAsString(),
                jsonObject.get("percentile").getAsDouble());
    }

    private SummonerData constructSummonerDataFromJsonObject(Region region, String summonerName, JsonObject jsonObject) {
        return new SummonerData(region,
                summonerName,
                constructQueueDataFromJsonObject(Queue.RANKED, jsonObject.getAsJsonObject(Queue.RANKED.getQueue())),
                constructQueueDataFromJsonObject(Queue.NORMAL, jsonObject.getAsJsonObject(Queue.NORMAL.getQueue())),
                constructQueueDataFromJsonObject(Queue.ARAM, jsonObject.getAsJsonObject(Queue.ARAM.getQueue())));
    }

}