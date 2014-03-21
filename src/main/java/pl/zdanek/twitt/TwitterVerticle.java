package pl.zdanek.twitt;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.logging.*;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;
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
                System.out.println(status.getUser().getName() + " : " + status.getText());
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
        filter.track(new String[]{"#java8"});
        twitterStream.filter(filter);


    }
}
