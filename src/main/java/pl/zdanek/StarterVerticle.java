package pl.zdanek;

import org.vertx.java.platform.Verticle;

public class StarterVerticle extends Verticle {

    @Override
    public void start() {
        container.logger().info("VERTICLE STARTER");

        container.deployVerticle("pl.zdanek.twitt.TwitterVerticle");
        container.deployVerticle("pl.zdanek.bridge.JSBridge");

        container.deployVerticle("pl.zdanek.publisher.LogPublisher");

//        container.deployVerticle("pl.zdanek.raspi.BlinkVerticle");

        container.logger().info("All verticles submitted");
    }
}