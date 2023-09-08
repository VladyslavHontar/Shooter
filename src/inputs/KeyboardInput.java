package inputs;

import main.GPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
  private GPanel panel;
  public KeyboardInput(GPanel panel) {
    this.panel = panel;
  }
  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
        panel.move(0, -5);
        break;
      case KeyEvent.VK_S:
        panel.move(0, 5);
        break;
      case KeyEvent.VK_A:
        panel.move(-5, 0);
        break;
      case KeyEvent.VK_D:
        panel.move(5, 0);
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
