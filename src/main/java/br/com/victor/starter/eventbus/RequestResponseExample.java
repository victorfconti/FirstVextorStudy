package br.com.victor.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExample {
  private static final String ADDRESS = "my.request.address";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(RequestVerticle.class.getName());
    vertx.deployVerticle(ResponseVerticle.class.getName());
  }

  public static class RequestVerticle extends AbstractVerticle{
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final String message = "Hello World!";
      LOGGER.error("Sending: {}", message);
      eventBus.<String>request(ADDRESS, "Hello World", reply ->
        LOGGER.error("Response: {}", reply.result().body()));
    }
  }

  public static class ResponseVerticle extends AbstractVerticle{
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      eventBus.<String>consumer(ADDRESS, message -> {
        LOGGER.error("Received Message: {}", message.body());
        message.reply("Recieved your message, Thanx");
      });
    }
  }

}
