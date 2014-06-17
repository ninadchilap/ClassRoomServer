import java.awt.Dialog; 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FocusedIpPanel extends Dialog
{

	JPanel ipPanel;
	JLabel ipLabel=new JLabel(ServerFrame.ipAddressString);
	JLabel sessionIdLabel=new JLabel(ServerFrame.sessionIdString);
	public FocusedIpPanel(ServerFrame sf) 
	{
		super(sf,true);
		setTitle("Lecture Details");
		this.setResizable(false);
		//this.pack();
		setSize(new Dimension(800,200));
		//this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		this.setLocation(300, 200);
		ipPanel=new JPanel(); 
		ipPanel.setLayout(new GridLayout(2,1,10,10));

		ipLabel.setFont(new Font("lucida console",Font.ITALIC,50));
		sessionIdLabel.setFont(new Font("lucida console",Font.ITALIC,50));
		
		ipPanel.add(ipLabel);
		ipPanel.add(sessionIdLabel);
		this.add(ipPanel);
		addWindowListener(new DlgAdapter(this));
	}

	public Insets getInsets()
	{
		return new Insets(25,20,20,20);
	}
}
