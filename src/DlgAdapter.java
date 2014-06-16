/*
 * This class is used as a derived adapter class 
 * to perform closing events of both window and dialog
 */

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DlgAdapter extends WindowAdapter
{
	Window d;
	DlgAdapter(Window d)
	{
		this.d=d;
	}
	public void windowClosing(WindowEvent we)
	{
		d.dispose();
		if(d instanceof WelcomeDialog)
		{
			/*
			 * if the dialog is the welcome dialog
			 * then we have to exit
			 * otherwise we just need to dispose the frame(other than WelomeDialog)
			 */
			System.exit(1);
		}
	}
}