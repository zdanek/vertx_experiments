package pl.zdanek.publisher;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;
import pl.zdanek.EventBusAddresses;

public class LogPublisher extends Verticle {

    @Override
    public void start() {
        container.logger().info("Starting LogPublisher");

        vertx.eventBus().registerHandler(EventBusAddresses.TWITTER_MESSAGE, new Handler<Message>() {
            @Override
            public void handle(Message message) {
                container.logger().info("Got message from Twitter");
                container.logger().info(message.body());
            }
        });

    }
}
