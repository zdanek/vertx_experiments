package pl.zdanek.twitt;

import org.vertx.java.core.json.impl.Json;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;
import pl.zdanek.EventBusAddresses;
import twitter4j.*;

import java.util.HashMap;
import java.util.Map;

public class TwitterVerticle extends Verticle {


    private Logger logger;
    private Map<String, Long> keywordsCounter = new HashMap<>();

    @Override
    public void start() {
        logger = container.logger();
        container.logger().info(this.getClass() + "Started from noarg!");

        final String[] keywords = new String[]{"#friday", "justin", "#Laura1", "Alparslan Türkeş"};

        for (String keyWord : keywords) {
            keywordsCounter.put(keyWord, 0L);
        }

        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                String twitterMessage = null;

                for (String keyWord : keywords){
                    if (status.getText().contains(keyWord)) {
                        Long cnt = keywordsCounter.get(keyWord);
                        if (cnt == null) {
                            logger.error("Counter not found for keyword: " + keyWord);
                        } else {
                            cnt++;
                            keywordsCounter.put(keyWord, cnt);
                            twitterMessage = "{\"" + keyWord + "\":" + cnt + "}";
//                            twitterMessage = Json.encode(keywordsCounter);
                        }
                    }
                }

                if (twitterMessage != null) {
                    vertx.eventBus().publish(EventBusAddresses.TWITTER_MESSAGE, twitterMessage);
                }
            }
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

            @Override
            public void onScrubGeo(long l, long l2) { }

            @Override
            public void onStallWarning(StallWarning stallWarning) {}

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        FilterQuery filter = new FilterQuery();
        filter.track(keywords);
        logger.info("Tracking " + filter);

        twitterStream.filter(filter);
    }
}
