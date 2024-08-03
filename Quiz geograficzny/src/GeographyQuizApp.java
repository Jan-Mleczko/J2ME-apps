import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Random;

public class GeographyQuizApp extends MIDlet implements CommandListener {
  private Display display;
  private Form form;
  private Command cmdGuess, cmdExit, cmdSkip, cmdHelp;
  private StringItem strState;
  private TextField txtCapital;
  private Alert goodAlert, badAlert, helpAlert;
  private Random random;
  private GeographicalRecord question;
  public void startApp () {
    display = Display.getDisplay (this);
    form = new Form ("Quiz geograficzny");
    cmdGuess = new Command ("Zgadnij", Command.OK, 1);
    form.addCommand (cmdGuess);
    cmdExit = new Command ("Wyjdü", Command.EXIT, 2);
    form.addCommand (cmdExit);
    cmdSkip = new Command ("PomiÒ", Command.CANCEL, 3);
    form.addCommand (cmdSkip);
    cmdHelp = new Command ("Instrukcja", Command.HELP, 4);
    form.addCommand (cmdHelp);
    form.setCommandListener (this);
    strState = new StringItem ("PaÒstwo", "");
    form.append (strState);
    txtCapital = new TextField ("Stolica", "", 20, TextField.ANY);
    form.append (txtCapital);
    display.setCurrent (form);
    goodAlert = new Alert (null);
    goodAlert.setString ("Dobrze!");
    goodAlert.setType (AlertType.INFO);
    goodAlert.setTimeout (500);
    badAlert = new Alert (null);
    badAlert.setString ("èle!");
    badAlert.setType (AlertType.WARNING);
    badAlert.setTimeout (500);
    helpAlert = new Alert ("Quiz geograficzny - Instrukcja");
    helpAlert.setString (
      "Podaj stolicÍ i wybierz Zgadnij, jeúli nie wiesz - wybierz PomiÒ.\n" +
      "Autor: Jan \"Manna5\" Mleczko.\n" +
      "Baza: " + GeographicalDatabase.records.length + " rekordÛw."
    );
    helpAlert.setTimeout (Alert.FOREVER);
    random = new Random ();
    newQuestion ();
  }
  public void pauseApp () {
  }
  public void destroyApp (boolean unconditional) {
    notifyDestroyed ();
  }
  public void commandAction (Command c, Displayable d) {
    String label = c.getLabel();
    if (label.equals ("Wyjdü")) {
      destroyApp (false);
    } else if (label.equals ("PomiÒ")) {
      newQuestion ();
    } else if (label.equals ("Zgadnij")) {
      String guess = txtCapital.getString();
      if (question.capital.equals (guess)) {
        display.setCurrent (goodAlert, form);
        newQuestion ();
      } else {
        display.setCurrent (badAlert, form);
      }
    } else if (label.equals ("Instrukcja")) {
      display.setCurrent (helpAlert, form);
    }
  }
  private void newQuestion () {
    int index = random.nextInt (GeographicalDatabase.records.length);
    question = GeographicalDatabase.records[index];
    strState.setText (question.state);
    txtCapital.setString ("");
  }
}