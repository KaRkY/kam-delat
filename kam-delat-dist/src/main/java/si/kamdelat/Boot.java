package si.kamdelat;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import si.kamdelat.gui.Window;

public class Boot {
  private static Boot                     booter;

  private final Logger                    logger = LoggerFactory.getLogger(getClass());
  private final GenericApplicationContext context;

  public Boot(final String[] args) {
    logger.info("Initializing application.");

    logger.trace("Registering SLF4J for JUL bridge handler.");
    SLF4JBridgeHandler.install();

    logger.trace("Creating application context.");
    context = new GenericXmlApplicationContext("classpath:root-applicationContext.xml");

    logger.trace("Registering shutdown hook.");
    context.registerShutdownHook();

    logger.trace("Setting look and feel.");
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      logger.error("Look and feel could not be set.", e);
    }

    Thread.currentThread();
    Thread.setDefaultUncaughtExceptionHandler(new LoggingExceptionHandler());
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

    logger.trace("Invoking main window.");
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        final Window mainWindow = context.getBean("mainWindow", Window.class);
        mainWindow.show();
      }
    });
  }

  public class LoggingExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(final Thread thread, final Throwable e) {
      logger.error("Uncaught exception in thread {}.", thread.getName(), e);
    }

  }

}
