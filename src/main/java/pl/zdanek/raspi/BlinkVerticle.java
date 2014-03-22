package pl.zdanek.raspi;

import com.pi4j.io.gpio.*;
import org.vertx.java.core.Handler;
import org.vertx.java.platform.Verticle;

public class BlinkVerticle extends Verticle {

    @Override
    public void start() {
        container.logger().info("Setting blink timer");

        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);

        vertx.setPeriodic(2000L, new Handler<Long>() {
            @Override
            public void handle(Long aLong) {
                pin.blink(500L);
            }
        });
    }
}
