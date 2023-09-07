package main;

import inputs.KeyboardInput;
import inputs.MouseInput;

import javax.swing.*;
import java.awt.*;

public class GPanel extends JPanel {
  private int xDelta = 100;
  private int yDelta = 100;
  public GPanel() {
    addKeyListener(new KeyboardInput(this));
    addMouseMotionListener(new MouseInput(this));
  }
  public void move(int xDelta, int yDelta) {
    this.xDelta += xDelta;
    this.yDelta += yDelta;
  }

  public void moveCrosshair(int xDelta, int yDelta) {
    this.xDelta = xDelta;
    this.yDelta = yDelta;
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.RED);
    g.fillRect(xDelta, yDelta, 300, 400);
    repaint();
  }
}
