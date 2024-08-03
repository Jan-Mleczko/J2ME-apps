import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.util.Random;

public class JavaDiceApp extends MIDlet
implements CommandListener
{
  private Display display;
  private Form form;
  private Alert rollAlert;
  private Image[] images;
  private ImageItem imageItem;
  private Command rollCommand, quitCommand;
  public void startApp ()
  {
    try
    {
      images = new Image[]
      {
        Image.createImage ("/dice1.png"),
        Image.createImage ("/dice2.png"),
        Image.createImage ("/dice3.png"),
        Image.createImage ("/dice4.png"),
        Image.createImage ("/dice5.png"),
        Image.createImage ("/dice6.png")
      };
    } catch (Exception e) {}
    form = new Form ("JavaDice");
    rollAlert = new Alert ("JavaDice", "Rolling", null, null);
    rollAlert.setTimeout (500);
    imageItem = new ImageItem ("Click to roll", images[0], Item.LAYOUT_EXPAND, "1");
    rollCommand = new Command ("Roll", Command.OK, 1);
    quitCommand = new Command ("Quit", Command.EXIT, 2);
    form.addCommand (rollCommand);
    form.addCommand (quitCommand);
    form.append (imageItem);
    form.setCommandListener (this);
    display = Display.getDisplay (this);
    display.setCurrent (form);
  }
  public void pauseApp ()
  {
    destroyApp (true);
  }
  public void destroyApp (boolean unconditional)
  {
    notifyDestroyed ();
  }
  public void commandAction(Command command, Displayable displayable)
  {
    Random random = new Random ();
    int wall;
    String label = command.getLabel ();
    if (label.equals ("Quit"))
      destroyApp (false);
    else if (label.equals ("Roll"))
    {
      display.setCurrent (rollAlert, form);
      random.setSeed (System.currentTimeMillis ());
      wall = Math.abs (random.nextInt () % 6);
      imageItem.setImage (images[wall]);
      imageItem.setAltText (Integer.toString (wall + 1));
    }
  }
}