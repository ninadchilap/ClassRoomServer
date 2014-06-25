

import javax.swing.UIManager;
import javax.swing.ImageIcon;

public class SplashScreenMain {

  SplashScreen screen;

  public SplashScreenMain() {
    // initialize the splash screen
    splashScreenInit();
    // do something here to simulate the program doing something that
    // is time consuming
    System.out.println("start the splash screen");
    for (int i = 0; i <= 2000; i++)
    {
      for (long j=0; j<50; ++j)    //50000
      {
        String poop = " " + (j + i);
      }
      // run either of these two -- not both
      SplashScreen.progressBar.setValue(i);
     // screen.setProgress("Yo " + i, i);  // progress bar with a message
      //screen.setProgress(i);           // progress bar with no message
    }
    splashScreenDestruct();
    //System.exit(0);
    System.out.println("splash screen destructed");
    Server.mainExecution("","","","");
  }

  private void splashScreenDestruct() {
    screen.setScreenVisible(false);
  }

  private void splashScreenInit() {
    ImageIcon myImage = new ImageIcon(SplashScreenMain.class.getResource("iitlogo.png"));
    screen = new SplashScreen(myImage);
    screen.setLocationRelativeTo(null);
    screen.setProgressMax(2000);
    screen.setScreenVisible(true);
  }

  public static void main(String[] args)
  {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    new SplashScreenMain();
  }

}