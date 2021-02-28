package br.com.victor.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PublishSubscriberExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Publish());
    vertx.deployVerticle(new Subscriber1());
    vertx.deployVerticle(Subscriber2.class.getName(), new DeploymentOptions().setInstances(2));
  }

  public static class Publish extends AbstractVerticle{

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(Duration.ofSeconds(1).toMillis(), id ->
      vertx.eventBus().publish(Publish.class.getName(), "Vo mata o Donaldo"));
    }
  }

  public static class Subscriber1 extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      Logger logger = LoggerFactory.getLogger(Subscriber1.class);
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publish.class.getName(), message -> logger.error(message.body()));
    }
  }

  public static class Subscriber2 extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      Logger logger = LoggerFactory.getLogger(Subscriber2.class);
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publish.class.getName(), message -> logger.error(message.body()));
    }
  }

}
