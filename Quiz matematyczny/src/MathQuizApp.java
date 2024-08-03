import java.util.Random;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class MathQuizApp extends MIDlet implements CommandListener {
  private Display display;
  private Form form;
  private Command cmdGuess, cmdExit, cmdSkip, cmdHelp;
  private StringItem strFormula;
  private TextField txtResult;
  private Alert goodAlert, badAlert, helpAlert;
  private Random random;
  private MathematicalQuestion question;
  public void startApp () {
    display = Display.getDisplay (this);
    form = new Form ("Quiz matematyczny");
    cmdGuess = new Command ("Zgadnij", Command.OK, 1);
    form.addCommand (cmdGuess);
    cmdExit = new Command ("Wyjdü", Command.EXIT, 2);
    form.addCommand (cmdExit);
    cmdSkip = new Command ("PomiÒ", Command.CANCEL, 3);
    form.addCommand (cmdSkip);
    cmdHelp = new Command ("Instrukcja", Command.HELP, 4);
    form.addCommand (cmdHelp);
    form.setCommandListener (this);
    strFormula = new StringItem ("Dzia≥anie", "");
    form.append (strFormula);
    txtResult = new TextField ("Wynik", "", 2, TextField.NUMERIC);
    form.append (txtResult);
    display.setCurrent (form);
    goodAlert = new Alert (null);
    goodAlert.setString ("Dobrze!");
    goodAlert.setType (AlertType.INFO);
    goodAlert.setTimeout (500);
    badAlert = new Alert (null);
    badAlert.setString ("èle!");
    badAlert.setType (AlertType.WARNING);
    badAlert.setTimeout (500);
    helpAlert = new Alert ("Quiz matematyczny - Instrukcja");
    helpAlert.setString (
      "Podaj wynik dzia≥ania i wybierz Zgadnij, jeúli nie umiesz - wybierz " +
      "PomiÒ.\nAutor: Jan \"Manna5\" Mleczko.\n"
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
      int guess = Integer.parseInt (txtResult.getString());
      if (question.correct (guess)) {
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
    question = new MathematicalQuestion (random);
    strFormula.setText (question.toString());
    txtResult.setString ("");
  }
}