package si.kamdelat.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.kamdelat.events.CloseEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.Resources;

public class MainWindow implements Window {
  private final Logger   logger = LoggerFactory.getLogger(this.getClass());
  private final JFrame   frame;
  private final EventBus bus;

  public MainWindow(final String title, final EventBus bus) {
    logger.info("Building main window.");
    this.bus = bus;
    frame = new JFrame(title);
    frame.setMinimumSize(new Dimension(600, 400));
    frame.setLayout(new FlowLayout());
    frame.setLocationByPlatform(true);

    final URL applicationIconURL = Resources.getResource("icons/application.png");
    if (applicationIconURL != null)
      frame.setIconImage(Toolkit.getDefaultToolkit().getImage(applicationIconURL));

    final ImageIcon icon = new ImageIcon(Resources.getResource("icons/button/accept.png"));
    final JButton button = new JButton(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    // button.setBorder(BorderFactory.createEmptyBorder());
    // button.setBackground(new Color(0, 0, 0, 0));
    frame.add(button);

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
