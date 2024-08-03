import java.util.Random;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class SnakeMIDlet extends MIDlet implements CommandListener {
  private Display display;
  private Canvas canvas;
  public void startApp () {
    display = Display.getDisplay (this);
    canvas = new SnakeCanvas ();
    canvas.addCommand (new Command ("Exit", Command.EXIT, 2));
    canvas.setCommandListener (this);
    display.setCurrent (canvas);
  }
  public void pauseApp () {}
  public void destroyApp (boolean unconditional) {
    notifyDestroyed ();
  }
  public void commandAction (Command c, Displayable d) {
    destroyApp (false);
  }
}

class SnakeCanvas extends Canvas {
  private int xLimit, yLimit;
  private int[] snakePartX;
  private int[] snakePartY;
  private int snakeLength;
  private int snakeDirection;
  private Random random;
  private int appleX, appleY;
  private boolean crashed;
  private static final int BLOCK = 10;
  SnakeCanvas () {
    xLimit = getWidth () / BLOCK;
    yLimit = getHeight () / BLOCK;
    snakePartX = new int [1000];
    snakePartX[0] = 3;
    snakePartX[1] = 4;
    snakePartX[2] = 5;
    snakePartY = new int [1000];
    snakePartY[0] = 3;
    snakePartY[1] = 3;
    snakePartY[2] = 3;
    snakeLength = 3;
    snakeDirection = RIGHT;
    random = new Random (System.currentTimeMillis());
    randomApple ();
    crashed = false;
  }
  public void paint (Graphics g) {
    g.setGrayScale (0);
    g.fillRect (0, 0, getWidth(), getHeight());
    g.setGrayScale (128);
    g.fillArc (appleX * BLOCK, appleY * BLOCK, BLOCK, BLOCK, 0, 360); 
    g.setGrayScale (255);
    for (int i = 0; i < snakeLength; i++) {
      g.fillRect (snakePartX[i] * BLOCK, snakePartY[i] * BLOCK,
        BLOCK, BLOCK);
    }
    g.setFont (Font.getFont (Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN,
      Font.SIZE_SMALL));
    g.drawString ("" + (snakeLength - 3), 5, 5, 0);
    if (crashed) return;
    updateSnake ();
    try {
      Thread.sleep (150);
      repaint ();
    } catch (InterruptedException ie) {}
  }
  public void keyPressed (int keyCode) {
    int ga = getGameAction (keyCode);
    if (ga == UP || ga == DOWN || ga == LEFT || ga == RIGHT) {
      snakeDirection = ga;
    }
  }
  private void updateSnake () {
    int headX = snakePartX [snakeLength - 1];
    int headY = snakePartY [snakeLength - 1];
    switch (snakeDirection) {
      case LEFT:  headX--; break;
      case RIGHT: headX++; break;
      case UP:    headY--; break;
      case DOWN:  headY++; break;
    }
    if (headX < 0 || headY < 0 || headX >= xLimit || headY >= yLimit) {
      crashed = true;
    }
    for (int i = 0; i < snakeLength; i++) {
      if (headX == snakePartX[i] && headY == snakePartY[i]) {
        crashed = true;
        break;
      }
    }
    snakePartX [snakeLength] = headX;
    snakePartY [snakeLength] = headY;
    if (headX == appleX && headY == appleY) {
      snakeLength++;
      randomApple ();
    } else {
      for (int i = 0; i < snakeLength; i++) {
        snakePartX [i] = snakePartX [i + 1];
        snakePartY [i] = snakePartY [i + 1];
      }
    }
  }
  private void drawBlock (Graphics g, int x, int y, int shade) {
    g.setGrayScale (shade);
    g.fillRect (x * BLOCK, y * BLOCK, BLOCK, BLOCK);
  }
  private void randomApple () {
    appleX = 1 + Math.abs (random.nextInt()) % (xLimit - 2);
    appleY = 1 + Math.abs (random.nextInt()) % (yLimit - 2);
  }
}