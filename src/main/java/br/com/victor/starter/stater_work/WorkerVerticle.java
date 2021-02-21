package br.com.victor.starter.stater_work;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerVerticle.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOGGER.error("Deployed as worker verticle");
    startPromise.complete();
  }
}
