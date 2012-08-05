package si.kamdelat.gui;

import java.awt.Window;

import si.kamdelat.common.Closeable;

public interface ApplicationWindow extends Closeable {

  Window getFrame();

  void hide();

  void show();
}
