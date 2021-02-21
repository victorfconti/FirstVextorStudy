package br.com.victor.starter.stater_work;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExample extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new WorkerVerticle(),
      new DeploymentOptions()
        .setWorker(true)
        .setWorkerPoolName("my-worker-verticle"));
    startPromise.complete();
    executingBlockingCode();

  }

  private void executingBlockingCode() {
    vertx.executeBlocking(event -> {
      LOGGER.error("Executing blocking code");
      try{
        Thread.sleep(5000);
        event.complete();
      } catch (InterruptedException e){
        LOGGER.error("Failed");
        event.fail(e);
      }
    }, result -> {
      if(result.succeeded()){
        LOGGER.error("Deu certo");
      }else{
        LOGGER.error("Essa caralha deu errada");
      }
    });
  }
}
