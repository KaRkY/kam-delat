package si.kamdelat;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.JOptCommandLinePropertySource;

import si.kamdelat.config.KamDelatConfig;
import si.kamdelat.gui.ApplicationWindow;

import com.google.common.collect.ImmutableSet;

public class Boot {
  private static Boot                              booter;
  private static final Class<?>                    CONFIG_CLASS = KamDelatConfig.class;

  private final Logger                             logger       = LoggerFactory.getLogger(getClass());
  private final AnnotationConfigApplicationContext applicationContext;

  public Boot(final String[] args) {
    logger.info("Initializing application.");

    logger.trace("Registering SLF4J for JUL bridge handler.");
    SLF4JBridgeHandler.install();

    setLookAndFeel();

    final OptionParser optionParser = prepareParser();
    AnnotationConfigApplicationContext context = null;
    try {
      final OptionSet options = optionParser.parse(args);

      if (options.has("h")) {
        printHelp(optionParser);
        System.exit(0);
      }

      context = createApplicationContext(options);

      logger.trace("Registering shutdown hook.");
      context.registerShutdownHook();
    } catch (final OptionException e) {
      System.out.println(e.getMessage());
      printHelp(optionParser);
      System.exit(1);
    }

    applicationContext = context;
    Thread.setDefaultUncaughtExceptionHandler(new LoggingExceptionHandler());
    logger.info("Initialization finished.");
  }

  public void run() {
    logger.info("Starting application.");

    logger.trace("Invoking main window.");
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        final ApplicationWindow mainWindow = applicationContext.getBean("mainWindow", ApplicationWindow.class);
        mainWindow.show();
      }
    });
  }

  private AnnotationConfigApplicationContext createApplicationContext(final OptionSet options) {
    AnnotationConfigApplicationContext context;
    logger.trace("Creating application context.");
    context = new AnnotationConfigApplicationContext();
    context.register(Boot.CONFIG_CLASS);
    context.getEnvironment().getPropertySources().addFirst(new JOptCommandLinePropertySource(options));
    context.refresh();
    return context;
  }

  private OptionParser prepareParser() {
    final OptionParser parser = new OptionParser();

    parser.acceptsAll(ImmutableSet.of("?", "h", "help"), "Displays this message.");

    return parser;
  }

  private void printHelp(final OptionParser optionParser) {
    try {
      optionParser.printHelpOn(System.out);
    } catch (final IOException e1) {
    }
  }

  private void setLookAndFeel() {
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
    Boot.booter = new Boot(args);
    Boot.booter.run();
  }

  public class LoggingExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(final Thread thread, final Throwable e) {
      logger.error("Uncaught exception in thread {}.", thread.getName(), e);
    }

  }

}
