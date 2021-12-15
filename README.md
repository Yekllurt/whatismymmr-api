# whatismymmr-api

An unofficial simple [WhatIsMyMMR](https://euw.whatismymmr.com/) API Wrapper that returns for the League of Legends game modes normal, ranked and aram an estimated mmr with a 95% confidence interval.

## Examples
Sync data fetching
```
WhatIsMyMMR api = new WhatIsMyMMR(new UserAgent("java-api", "dev.yekllurt.whatismymmr", "1.0.0"));
SummonerData sd = api.fetchSummonerDataSync(Region.EU_WEST, "Yekllurt");
System.out.println(String.format("Summoner name: %s", sd.getSummonerName()));
System.out.println(String.format("Region: %s", sd.getRegion().getRegion()));
System.out.println(String.format("Queue data normal: %s", sd.getQueueDataNormal()));
System.out.println(String.format("Queue data ranked: %s", sd.getQueueDataRanked()));
System.out.println(String.format("Queue data aram: %s", sd.getQueueDataARAM()));
```
Async data fetching
```
WhatIsMyMMR api = new WhatIsMyMMR(new UserAgent("java-api", "dev.yekllurt.whatismymmr", "1.0.0"));
SummonerData sd = api.fetchSummonerData(Region.EU_WEST, "Yekllurt").get();
System.out.println(String.format("Summoner name: %s", sd.getSummonerName()));
System.out.println(String.format("Region: %s", sd.getRegion().getRegion()));
System.out.println(String.format("Queue data normal: %s", sd.getQueueDataNormal()));
System.out.println(String.format("Queue data ranked: %s", sd.getQueueDataRanked()));
System.out.println(String.format("Queue data aram: %s", sd.getQueueDataARAM()));
```

## Rules by the API provider
In order to follow the rules from the API provider you must use a unique user-agent consisting of the `target platform`, an `application identifier` and a `version string` as well as limit your requests to `60 per minute`. If you require more than 60 requests per minute, then you must contact the API provider.

## License
As the [API provider](https://euw.whatismymmr.com/) offers the service under the [Creative Commons Attribution 4.0 International](https://creativecommons.org/licenses/by/4.0/) you must attribute the API provider.

## Support
If you frequently use the API, then I suggest that you support the API provider via [Patreon](https://www.patreon.com/whatismymmr).

## Further Information
You can find more information about the API under the following [link](https://dev.whatismymmr.com).