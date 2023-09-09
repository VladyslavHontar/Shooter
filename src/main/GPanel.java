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
  private BufferedImage crosshair;
  private BufferedImage[][] animations;
  private int animationTick, animationIndex;
  public GPanel() {
    addKeyListener(new KeyboardInput(this));
    addMouseMotionListener(new MouseInput(this));
    setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
            new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
            new Point(0, 0),
            "invisibleCursor"
    ));
    importImg();
    loadAnimations();
  }

  private void loadAnimations() {
    animations = new BufferedImage[3][12];
    for (int i = 0; i < animations.length; i++) {
      for (int j = 0; j < animations[i].length; j++) {
        animations[i][j] = img.getSubimage(i * 64, j * 64, 64, 64);
      }
    }
  }

  public void setPanelSize(int width, int height) {
    setPreferredSize(new Dimension(width, height));
    setMinimumSize(new Dimension(width, height));
    setMaximumSize(new Dimension(width, height));
  }
  private void importImg() {
    InputStream is = getClass().getResourceAsStream("/Character-Sheet.png");
    InputStream is2 = getClass().getResourceAsStream("/crosshair.png");
    try {
      img = ImageIO.read(is);
      crosshair = ImageIO.read(is2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
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
    double scaleFactor = 1.5;

    // Calculate the new width and height of the scaled image
    int newWidth = (int) (64 * scaleFactor);
    int newHeight = (int) (64 * scaleFactor);

    // Calculate the position to draw the scaled image so that it's centered
    int drawX = centerX - (newWidth / 2);
    int drawY = centerY - (newHeight / 2);

    // Rotate the graphics context
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.rotate(angle, centerX, centerY);

    updateAnimationTick();

    // Draw the scaled and rotated image
    g2d.drawImage(animations[0][animationIndex], drawX, drawY, newWidth, newHeight, null);

    // Draw a small rectangle at the mouse position
    g2d.setColor(Color.RED);
    g.drawImage(crosshair, CxDelta - 5, CyDelta - 5, crosshair.getWidth() * 2, crosshair.getHeight() * 2, null);

    g2d.dispose();
  }

  private void updateAnimationTick() {
    animationTick++;
    if (animationTick >= 25) {
      animationTick = 0;
      animationIndex++;
      if (animationIndex >= 12) {
        animationIndex = 0;
      }
    }
  }
}
