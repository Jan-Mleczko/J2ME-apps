import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class MemoryInfoApp extends MIDlet {
  private Display display;
  private Runtime runtime;
  private Form form;
  public void startApp () {
    long totalMem, freeMem, usedMem;
    runtime = Runtime.getRuntime ();
    totalMem = runtime.totalMemory();
    freeMem = runtime.freeMemory();
    usedMem = totalMem - freeMem;
    form = new Form ("Memory information");
    form.append (new StringItem ("Free memory:", "" + freeMem));
    form.append (new StringItem ("Used memory:", "" + usedMem));
    form.append (new StringItem ("Total memory:", "" + totalMem));
    display = Display.getDisplay (this);
    display.setCurrent (form);
  }
  public void pauseApp () {
  }
  public void destroyApp (boolean unconditional) {
    notifyDestroyed ();
  }
}