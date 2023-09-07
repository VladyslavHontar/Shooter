package main;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
  public Window(GPanel panel) {
    super("Window");
    setUndecorated(true);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screenSize.width, screenSize.height);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(panel);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
