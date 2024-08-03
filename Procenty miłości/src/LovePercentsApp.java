import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class LovePercentsApp extends MIDlet implements CommandListener {
  private Display display;
  private Form form;
  private TextField txtName1, txtName2;
  private Command okCommand, exitCommand;
  public void startApp () {
    display = Display.getDisplay (this);
    form = new Form ("Procenty mi³oœci");
    txtName1 = new TextField ("Imiê ch³opaka:", "", 20, TextField.ANY);
    form.append (txtName1);
    txtName2 = new TextField ("Imiê dziewczyny:", "", 20, TextField.ANY);
    form.append (txtName2);
    okCommand = new Command ("Oblicz", Command.OK, 1);
    form.addCommand (okCommand);
    exitCommand = new Command ("WyjdŸ", Command.EXIT, 2);
    form.addCommand (exitCommand);
    form.setCommandListener (this);
    display.setCurrent (form);
  }
  public void pauseApp () {
    destroyApp (true);
  }
  public void destroyApp (boolean unconditional) {
    notifyDestroyed ();
  }
  public void commandAction (Command c, Displayable d) {
    int type = c.getCommandType();
    if (type == Command.OK) {
      String name1 = txtName1.getString().toUpperCase();
      String name2 = txtName2.getString().toUpperCase();
      if (name1.length() > 0 && name2.length() > 0) {
        int love = calcLove (name1, name2);
        Alert alert = new Alert ("Wynik", "Mi³oœæ na "+love+"%", null, null);
        alert.setTimeout (Alert.FOREVER);
        display.setCurrent (alert, form);
      }
    } else if (type == Command.EXIT) {
      destroyApp (true);
    }
  }
  private int calcLove (String name1, String name2) {
    int sum1 = alphaSum (name1);
    int sum2 = alphaSum (name2);
    int diff = Math.abs (sum1 - sum2);
    int love = 100 - diff;
    if (love < 0) love = 0;
    if (love > 100) love = 100;
    return love;
  }
  private int alphaSum (String word) {
    int sum = 0;
    for (int i = 0; i < word.length(); i++) {
      char c = stripPolish (word.charAt (i));
      if (c >= 'A' && c <= 'Z') sum += (int) (c - 'A' + 1);
    }
    return sum;
  }
  private char stripPolish (char c) {
    switch (c) {
      case '¥': return '¥';
      case 'Æ': return 'C';
      case 'Ê': return 'E';
      case '£': return 'L';
      case 'Ñ': return 'N';
      case 'Ó': return 'O';
      case 'Œ': return 'S';
      case '':
      case '¯': return 'Z';
    }
    return c;
  }
}