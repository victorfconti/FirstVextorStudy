package br.com.victor.starter.eventbus.custom_codec;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongExample {
  private static final Logger LOG = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(PingVerticle.class.getName(), logOnError());
    vertx.deployVerticle(PongVerticle.class.getName(), logOnError());
  }

  private static Handler<AsyncResult<String>> logOnError() {
    return ar -> {
      if (ar.failed()) {
        LOG.error("err", ar.cause());
      }
    };
  }

  public static class PingVerticle extends AbstractVerticle{

    private static final Logger logger = LoggerFactory.getLogger(PingVerticle.class.getName());
    static final String ADDRESS = PingVerticle.class.getName();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      var eventBus = vertx.eventBus();
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));

      final Ping message = new Ping("Hello", true);
      logger.error("Sending {}", message);
      //Register only once
      eventBus.<Pong>request(ADDRESS, message, reply -> {
        if (reply.failed()) {
          LOG.error("Failed: ", reply.cause());
          return;
        }
        LOG.debug("Response: {}", reply.result().body());
      });
      startPromise.complete();
    }
  }

  public static class PongVerticle extends AbstractVerticle{
    private static final Logger logger = LoggerFactory.getLogger(PongVerticle.class.getName());

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
      vertx.eventBus().<Ping>consumer(PingVerticle.ADDRESS, message -> {
        logger.error("Received Message: {}", message.body());
        message.reply(new Pong(0));
      }).exceptionHandler(error ->
        logger.error("Error: ", error));
      startPromise.complete();
    }
  }

}
