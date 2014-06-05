import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomeDialog extends Dialog 
{
	JPanel parentPanel;
	JPanel topicPanel;
	JLabel topicLabel;
	WelcomeDialog(ServerFrame serverFrame)
	{
		super(serverFrame,true);
		initialiseGraphicComponents();
		setSize(500,300);
		setLocation(100,100);
		topicPanel.add(topicLabel);
		parentPanel.setLayout(new BorderLayout(10,10));
		topicLabel.setFont(new Font("lucida console",Font.PLAIN,40));
		parentPanel.add(topicPanel,BorderLayout.NORTH);
		
		add(parentPanel);
		
		addWindowListener(new DlgAdapter(this));
		
	}
	void initialiseGraphicComponents()
	{
		parentPanel=new JPanel();
		topicLabel=new JLabel("Lecture Details");
		topicPanel=new JPanel();
	}
}
