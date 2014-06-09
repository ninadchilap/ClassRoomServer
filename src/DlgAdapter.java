import java.awt.Dialog;
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
	}
}