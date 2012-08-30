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

import si.kamdelat.gui.ApplicationWindow;

public class Boot {
  private static Boot                     booter;

  private final Logger                    logger = LoggerFactory.getLogger(getClass());
  private final GenericApplicationContext context;

  public Boot(final String[] args) {

    logger.info("Initializing application.");

    installJULSLF4JBridgeHandler();

    setupSwingLookAndFeel();

    context = createApplicationContext();

    context.registerShutdownHook();

    registerUncaughtExceptionHandler();
    logger.info("Initialization finished.");
  }

  private GenericApplicationContext createApplicationContext() {
    logger.trace("Creating application context.");
    return new GenericXmlApplicationContext("classpath:root-applicationContext.xml");
  }

  private void installJULSLF4JBridgeHandler() {
    logger.trace("Registering SLF4J for JUL bridge handler.");
    SLF4JBridgeHandler.install();
  }

  private void registerUncaughtExceptionHandler() {
    logger.trace("Registering uncaught exception handler.");
    Thread.setDefaultUncaughtExceptionHandler(new LoggingExceptionHandler());
  }

  private void run() {
    logger.info("Starting application.");

    logger.trace("Invoking main window.");
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        final ApplicationWindow mainWindow = context.getBean("mainWindow", ApplicationWindow.class);
        mainWindow.show();
      }
    });
  }

  private void setupSwingLookAndFeel() {
    logger.trace("Setting look and feel.");
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      logger.error("Look and feel could not be set.", e);
    }
  }

  /**
   * @param args
   */

  public static void main(final String[] args) {
    booter = new Boot(args);
    booter.run();
  }

  public class LoggingExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(final Thread thread, final Throwable e) {
      logger.error("Uncaught exception in thread {}.", thread.getName(), e);
    }

  }

}
