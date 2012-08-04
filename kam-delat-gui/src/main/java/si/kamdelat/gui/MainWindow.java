package si.kamdelat.gui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.kamdelat.events.CloseEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class MainWindow implements Window {
  private final Logger   logger = LoggerFactory.getLogger(this.getClass());
  private final JFrame   frame;
  private final EventBus bus;

  public MainWindow(final String title, final EventBus bus) {
    logger.info("Building main window.");
    this.bus = bus;
    frame = new JFrame(title);
    frame.setMinimumSize(new Dimension(600, 400));

    registerWindowListener();
  }

  @Override
  public void close() throws RuntimeException {
    logger.info("Closing main window.");
    frame.dispose();
  }

  @Subscribe
  public void close(final CloseEvent event) {
    close();
  }

  @Override
  public void hide() {
    frame.setVisible(false);
  }

  @Override
  public void show() {
    frame.pack();
    frame.setVisible(true);
  }

  private void registerWindowListener() {
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        bus.post(new CloseEvent(MainWindow.this));
      }
    });
  }
}
