package si.kamdelat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Boot {
  private static Boot                     booter;
  private final Logger                    logger = LoggerFactory.getLogger(getClass());
  private final GenericApplicationContext context;

  public Boot(final String[] args) {
    logger.info("Initializing application.");

    logger.trace("Creating application context.");
    context = new GenericXmlApplicationContext("classpath:root-applicationContext.xml");

    logger.trace("Registering shutdown hook.");
    context.registerShutdownHook();

    logger.info("Initialization finished.");
  }

  /**
   * @param args
   */
  public static void main(final String[] args) {
    booter = new Boot(args);
    booter.run();
  }

  private void run() {
    logger.info("Starting application.");
  }

}
