package si.kamdelat.gui;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JDialog;

import si.kamdelat.events.OpenNewVisit;

import com.google.common.eventbus.Subscribe;

public class NewVisitWindow implements ApplicationWindow {
  private final JDialog frame;

  public NewVisitWindow(final Window owner, final String title) {
    frame = new JDialog(owner, title);
    frame.setMinimumSize(new Dimension(600, 400));
    frame.setModal(true);
    frame.setModalityType(ModalityType.APPLICATION_MODAL);
  }

  @Override
  public void close() throws RuntimeException {
    frame.dispose();
  }

  @Override
  public java.awt.Window getFrame() {
    return null;
  }

  @Override
  public void hide() {
    frame.setVisible(false);
  }

  @Subscribe
  public void open(final OpenNewVisit event) {
    show();
  }

  @Override
  public void show() {
    frame.pack();
    frame.setVisible(true);
  }
}
