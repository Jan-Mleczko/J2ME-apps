import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Ukl15MIDlet extends MIDlet implements CommandListener {
	private Display display;
	private Form form;
	private Image boardImage;
	private Graphics boardGraphics;
	private ImageItem boardImageItem;
	private Alert helpAlert;
	private Command moveLeftCommand, moveRightCommand, moveUpCommand,
	  moveDownCommand, undoMoveCommand, helpCommand, exitCommand;
	private final int MOVE_LEFT = 1;
	private final int MOVE_RIGHT = 2;
	private final int MOVE_UP = 3;
	private final int MOVE_DOWN = 4;
	private int[][] boardState;
	private int holeX, holeY;
	private int lastValidMove = 0;
	private void initBoardState () {
		int i, j, field;
		field = 2;
		for (i = 0; i < 4; i++)
			for (j = 0; j < 4; j++) {
				boardState[j][i] = field;
				if (field == 14)
				  field = 1;
				else
				  field += 2;
			}
		holeX = 3; holeY = 3;
		boardState[holeX][holeY] = 0;
	}
	private void paintBoard (Graphics g) {
		int i, j;
		g.setGrayScale (255);
		g.fillRect (0, 0, 128, 128);
		g.setGrayScale (0);
		g.setStrokeStyle (Graphics.SOLID);
		for (i = 0; i < 128; i += 32) {
			g.drawLine (0, i, 127, i);
		}
		g.drawLine (0, 127, 127, 127);
		for (i = 0; i < 128; i += 32) {
			g.drawLine (i, 0, i, 127);
		}
		g.drawLine (127, 0, 127, 127);
		g.setFont (Font.getFont (Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN,
		  Font.SIZE_MEDIUM));
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				int fieldState = boardState[i][j];
				if (fieldState > 0) {
					g.setGrayScale (0);
					g.drawString ("" + fieldState, i * 32 + 30, j * 32 + 30,
					  Graphics.BOTTOM | Graphics.RIGHT);
				} else {
					g.setGrayScale (128);
					g.fillRect (i * 32 + 1, j * 32 + 1, 31, 31);
				}
			}
		}
	}
	private void updateBoard () {
		paintBoard (boardImage.getGraphics ());
		boardImageItem.setImage (Image.createImage (boardImage));
	}
	private void doMove (int move) {
		int newHoleX, newHoleY, movedPiece;
		newHoleX = holeX; newHoleY = holeY;
		if (move < 1 || move > 4)
			return;
		if (move == MOVE_LEFT && holeX < 3)
		  newHoleX++;
		else if (move == MOVE_RIGHT && holeX > 0)
		  newHoleX--;
		else if (move == MOVE_UP && holeY < 3)
		  newHoleY++;
		else if (move == MOVE_DOWN && holeY > 0)
		  newHoleY--;
		if (newHoleX != holeX || newHoleY != holeY) {
		  movedPiece = boardState[newHoleX][newHoleY];
		  boardState[newHoleX][newHoleY] = 0;
		  boardState[holeX][holeY] = movedPiece;
		  holeX = newHoleX; holeY = newHoleY;
		  updateBoard ();
		  lastValidMove = move;
	  }
	}
	private int reverseMove (int move) {
		if (move == MOVE_LEFT)
		  return MOVE_RIGHT;
		if (move == MOVE_RIGHT)
		  return MOVE_LEFT;
		if (move == MOVE_UP)
			return MOVE_DOWN;
		if (move == MOVE_DOWN)
			return MOVE_UP;
		return 0;
	}
	private boolean started = false;
	public void startApp () {
		if (started) return;
		started = true;
		boardState = new int[4][4];
		initBoardState ();
		display = Display.getDisplay (this);
		form = new Form ("Uk³adanka-15");
		boardImage = Image.createImage (128, 128);
	  paintBoard (boardImage.getGraphics ());
	  boardImageItem = new ImageItem ("Plansza", boardImage,
	    ImageItem.LAYOUT_LEFT, "Plansza");
	  form.append (boardImageItem);
	  helpCommand = new Command ("Info", Command.OK, 1);
	  form.addCommand (helpCommand);
		moveLeftCommand = new Command ("Ruch w lewo", Command.SCREEN, 2);
		form.addCommand (moveLeftCommand);
		moveRightCommand = new Command ("Ruch w prawo", Command.SCREEN, 2);
		form.addCommand (moveRightCommand);
		moveUpCommand = new Command ("Ruch w górê", Command.SCREEN, 2);
		form.addCommand (moveUpCommand);
		moveDownCommand = new Command ("Ruch w dó³", Command.SCREEN, 2);
		form.addCommand (moveDownCommand);
		undoMoveCommand = new Command ("Cofnij ost. ruch", Command.SCREEN, 2);
		form.addCommand (undoMoveCommand);
		exitCommand = new Command ("Zakoñcz", Command.EXIT, 3);
		form.addCommand (exitCommand);
		form.setCommandListener (this);
		helpAlert = new Alert ("Uk³adanka-15");
		helpAlert.setString ("Przesuwaj elementy w celu ustawienia ich w " +
		  "kolejnoœci rosn¹cej. \nAplikacja napisana przez Jana Mleczko.");
		helpAlert.setTimeout (Alert.FOREVER);
		display.setCurrent (form);
	}
	public void pauseApp () {}
	public void destroyApp (boolean unconditional) {
		notifyDestroyed ();
	}
	public void commandAction (Command command, Displayable d) {
		if (command == exitCommand) {
			destroyApp (false);
		} else if (command == helpCommand) {
			display.setCurrent (helpAlert, form);
		} else if (command == moveLeftCommand) {
			doMove (MOVE_LEFT);
		} else if (command == moveRightCommand) {
			doMove (MOVE_RIGHT);
		} else if (command == moveUpCommand) {
			doMove (MOVE_UP);
		} else if (command == moveDownCommand) {
			doMove (MOVE_DOWN);
		} else if (command == undoMoveCommand) {
			doMove (reverseMove (lastValidMove));
		}
	}
}