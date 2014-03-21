package pl.zdanek;

import org.vertx.java.platform.Verticle;

public class StarterVerticle extends Verticle {

    @Override
    public void start() {
        container.logger().info("VERTICLE STARTER");

        container.deployVerticle("pl.zdanek.PingVerticle");
        container.deployVerticle("pl.zdanek.twitt.TwitterVerticle");


        container.logger().info("All verticles submitted");
    }
}