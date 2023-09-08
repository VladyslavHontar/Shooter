package main;

import inputs.KeyboardInput;
import inputs.MouseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GPanel extends JPanel {
  private int xDelta = 100;
  private int yDelta = 100;
  private int CxDelta = 0;
  private int CyDelta = 0;
  private BufferedImage img;
  public GPanel() {
    addKeyListener(new KeyboardInput(this));
    addMouseMotionListener(new MouseInput(this));
    setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
            new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
            new Point(0, 0),
            "invisibleCursor"
    ));
    importImg();
  }

  private void importImg() {
    try (InputStream is = getClass().getResourceAsStream("/Character.png")) {
      img = ImageIO.read(is);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void move(int xDelta, int yDelta) {
    this.xDelta += xDelta;
    this.yDelta += yDelta;
  }

  public void moveCrosshair(int xDelta, int yDelta) {
    this.CxDelta = xDelta;
    this.CyDelta = yDelta;
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Calculate the center of the panel
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;

    // Calculate the angle between the mouse and the center of the panel
    double angle = Math.atan2(CyDelta - centerY, CxDelta - centerX) + Math.toRadians(90);

    // Calculate the scale factor to make the image 3 times bigger
    double scaleFactor = 2.2;

    // Calculate the new width and height of the scaled image
    int newWidth = (int) (img.getWidth() * scaleFactor);
    int newHeight = (int) (img.getHeight() * scaleFactor);

    // Calculate the position to draw the scaled image so that it's centered
    int drawX = centerX - (newWidth / 2);
    int drawY = centerY - (newHeight / 2);

    // Rotate the graphics context
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.rotate(angle, centerX, centerY);

    // Draw the scaled and rotated image
    g2d.drawImage(img, drawX, drawY, newWidth, newHeight, null);

    // Draw a small rectangle at the mouse position
    g2d.setColor(Color.RED);
    g.fillRect(CxDelta + 5, CyDelta, 5, 3);
    g.fillRect(CxDelta - 7, CyDelta, 5, 3);
    g.fillRect(CxDelta, CyDelta + 5, 3, 5);
    g.fillRect(CxDelta, CyDelta - 7, 3, 5);

    g2d.dispose();
  }
}
