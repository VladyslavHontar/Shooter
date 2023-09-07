package main;

public class Launcher implements Runnable {
  private Window window;
  private GPanel panel;
  private Thread gameThread;
  private final int FPS_MAX = 240;

  public Launcher() {
    panel = new GPanel();
    window = new Window(panel);
    panel.requestFocus();
    startGame();
  }

  private void startGame() {
    gameThread = new Thread(this);
    gameThread.start();
  }
  @Override
  public void run() {
    long lastTime = System.nanoTime();
    double nsPerFrame = 1000000000.0 / FPS_MAX;
    double delta = 0.0;
    int frames = 0;
    long timer = System.currentTimeMillis();

    while (true) {
      long now = System.nanoTime();
      delta += (now - lastTime) / nsPerFrame;
      lastTime = now;

      while (delta >= 1) {
        panel.repaint();
        frames++;
        delta--;
      }
      if (System.currentTimeMillis() - timer >= 1000) {
        System.out.printf("FPS: %d\n", frames);
        frames = 0;
        timer += 1000;
      }
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
