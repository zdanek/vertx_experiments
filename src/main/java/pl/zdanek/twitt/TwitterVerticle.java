package pl.zdanek.twitt;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.logging.*;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;
import pl.zdanek.EventBusAddresses;
import twitter4j.*;

import java.util.List;

public class TwitterVerticle extends Verticle {


    private Logger logger;

    @Override
    public void start() {
        logger = container.logger();
        container.logger().info(this.getClass() + "Started from noarg!");

        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                String twitterMessage = status.getUser().getName() + " : " + status.getText();
                vertx.eventBus().publish(EventBusAddresses.TWITTER_MESSAGE, twitterMessage);
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
        filter.track(new String[]{"#friday"});
        logger.info("Tracking " + filter);
        twitterStream.filter(filter);
    }
}
